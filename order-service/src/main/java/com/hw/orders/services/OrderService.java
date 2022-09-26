package com.hw.orders.services;

import com.hw.constans.dto.CartDto;
import com.hw.constans.dto.OrderItemDto;
import com.hw.constans.exceptoins.ResourceNotFoundException;
import com.hw.orders.converters.OrderConverter;
import com.hw.orders.converters.OrderItemConverter;
import com.hw.orders.dto.OrderDto;
import com.hw.orders.entitys.Order;
import com.hw.orders.entitys.OrderItem;
import com.hw.orders.entitys.OrderStatus;
import com.hw.orders.repositorys.OrderRepository;
import com.hw.orders.repositorys.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;
    private final AddressService addressService;

    private final StatusRepository statusRepository;

    public List<OrderDto> getUserOrders(String userName) {
        List<Order> myOrders = orderRepository.findAllByUsername(userName);
        List<OrderDto> myOrdersDto = myOrders.stream()
                .map(orderConverter::toOrderDto)
                .collect(Collectors.toList());
        return myOrdersDto;
    }

    public List<OrderItemDto> getOrderItems(Long orderId) {
        Order order = orderRepository.getById(orderId);
        return order.getItems().stream()
                .map(orderItemConverter::toOrderItemDto)
                .collect(Collectors.toList());
    }


    public void deleteOrderById(Long id) {
        if (notPayingOrder(id)) {
            orderRepository.deleteById(id);
        }
    }

    private boolean notPayingOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("заказа не существует"))
                .getOrderStatus()
                .getId() < 2;
    }

    @Transactional
    public void changeOrderStatus(Long orderId, Long statusId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Заказ не существует"));
        OrderStatus status = statusRepository.findById(statusId).orElseThrow(() -> new ResourceNotFoundException("Неверный статус заказа"));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    @KafkaListener(topics = "Cart", containerFactory = "userKafkaContainerFactory")
    @Transactional
    public void createOrder(CartDto cart) {
        Order order = new Order();
        order.setAddress(addressService.getAddress(cart.getAddressId()));
        order.setUsername(cart.getUsername());
        order.setOrderStatus(statusRepository.findById(1l)
                .orElseThrow(() -> new org.apache.kafka.common.errors.ResourceNotFoundException("Статус не найден")));
        order.setTotalPrice(cart.getTotalPrice());
        order.setPhone(cart.getPhone());
        List<OrderItem> orderItems = getItemsFromCart(cart, order);
        order.setItems(orderItems);
        orderRepository.save(order);
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

    public void payOrder(Long id) {
        changeOrderStatus(id, 2l);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public int getOrderStatus(Long orderId) {
        return orderRepository.findStatusIdById(orderId);
    }
}
