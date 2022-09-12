package com.hw.orders.services;

import com.hw.constans.dto.CartDto;
import com.hw.orders.entitys.Order;
import com.hw.orders.entitys.OrderItem;
import com.hw.orders.repositorys.OrderRepository;
import com.hw.orders.repositorys.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final AddressService addressService;
    private final StatusRepository statusRepository;
    private final OrderRepository orderRepository;


    @Transactional
    @KafkaListener(topics = "Cart")
    public void createOrder(CartDto cart){
        Order order = new Order();
        order.setAddress(addressService.getAddress(cart.getAddressId()));
        order.setUsername(cart.getUsername());
        order.setOrderStatus(statusRepository.findById(1l)
                .orElseThrow(() -> new ResourceNotFoundException("Статус не найден")));
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
}
