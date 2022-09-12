package com.hw.orders.services;

import com.hw.constans.dto.OrderItemDto;
import com.hw.constans.exceptoins.ResourceNotFoundException;
import com.hw.orders.converters.OrderConverter;
import com.hw.orders.converters.OrderItemConverter;
import com.hw.orders.dto.OrderDto;
import com.hw.orders.entitys.Order;
import com.hw.orders.entitys.OrderStatus;
import com.hw.orders.repositorys.OrderRepository;
import com.hw.orders.repositorys.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
