package com.hw.orders.services;

import com.hw.orders.converters.OrderConverter;
import com.hw.orders.converters.OrderItemConverter;
import com.hw.orders.dto.OrderDto;
import com.hw.orders.entitys.Order;
import com.hw.orders.entitys.OrderItem;
import com.hw.orders.entitys.OrderStatus;
import com.hw.orders.repositorys.OrderRepository;
import com.hw.orders.repositorys.StatusRepository;
import com.hw.spring.global.dto.*;
import com.hw.spring.global.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;

    private final StatusRepository statusRepository;

    private final AddressService addressService;

    private final RestTemplate restTemplate;


    public List<OrderDto> getUserOrders(String userName) {
        List<Order> myOrders = orderRepository.findAllByUsername(userName);
        List<OrderDto> myOrdersDto = myOrders.stream()
                .map(orderConverter::toOrderDto)
                .collect(Collectors.toList());
        return myOrdersDto;
    }

    public List<OrderItemDto> getOrderItems(Long orderId){
        Order order = orderRepository.getById(orderId);
        return order.getItems().stream()
                .map(orderItemConverter::toOrderItemDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createOrder(OrderDetailsDto orderDetail, String cartname, String username) {
        CartDto cart = restTemplate.getForObject("",CartDto.class);
        Order order = new Order();
        order.setAddress(addressService.getAddress(orderDetail.getAddressId()));
        order.setUsername(username);
        order.setOrderStatus(statusRepository.findById(1l)
                .orElseThrow(() -> new ResourceNotFoundException("Статус не найден")));
        order.setTotalPrice(cart.getTotalPrice());
        order.setPhone(orderDetail.getPhone());
        List<OrderItem> orderItems = getItemsFromCart(cart, order);
        order.setItems(orderItems);
        orderRepository.save(order);
        restTemplate.postForLocation("http://localhost:8190/api/v1/cart/clear",String.class);
    }

    public void deleteOrderById(Long id) {
        if (notPayingOrder(id)) {
            orderRepository.deleteById(id);
        }
    }

    private List<OrderItem> getItemsFromCart(CartDto cart, Order order) {
        return cart.getProducts().stream().map(o -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(o.getQuantity());
            orderItem.setPricePerProduct(o.getPricePerProduct());
            orderItem.setPrice(o.getPrice());
            orderItem.setProductTitle(o.getTitle());
            return orderItem;
        }).collect(Collectors.toList());
    }

    private boolean notPayingOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("заказа не существует"))
                .getOrderStatus()
                .getId() < 2;
    }

    public void changeOrderStatus(Long orderId, Long statusId){
        Order order = orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Заказ не существует"));
        OrderStatus status = statusRepository.findById(statusId).orElseThrow(()->new ResourceNotFoundException("Неверный статус заказа"));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    public void payOrder(Long id) {
        changeOrderStatus(id,2l);
    }
}
