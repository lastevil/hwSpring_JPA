package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.converters.OrderConverter;
import com.gbhw.hwSpring_JPA.converters.OrderItemConverter;
import com.gbhw.hwSpring_JPA.converters.ProductMapper;
import com.gbhw.hwSpring_JPA.dto.OrderDetailsDto;
import com.gbhw.hwSpring_JPA.dto.OrderDto;
import com.gbhw.hwSpring_JPA.dto.OrderItemDto;
import com.gbhw.hwSpring_JPA.dto.exceptions.ResourceNotFoundException;
import com.gbhw.hwSpring_JPA.entitys.Order;
import com.gbhw.hwSpring_JPA.entitys.OrderItem;
import com.gbhw.hwSpring_JPA.entitys.OrderStatus;
import com.gbhw.hwSpring_JPA.entitys.User;
import com.gbhw.hwSpring_JPA.models.Cart;
import com.gbhw.hwSpring_JPA.repositorys.OrderRepository;
import com.gbhw.hwSpring_JPA.repositorys.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;

    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;

    private final StatusRepository statusRepository;

    private final AddressService addressService;

    private final CartService cartService;

    private final ProductService productService;

    public List<OrderDto> getUserOrders(String userName) {
        User user = userService.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        List<Order> myOrders = orderRepository.findAllByUserId(user.getId());
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
    public void createOrder(OrderDetailsDto orderDetail, String cartName, String userName) {
        Cart cart = cartService.getCurrentCart(cartName);
        Order order = new Order();
        order.setAddress(addressService.getAddress(orderDetail.getAddressId()));
        order.setUser(userService.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", userName))));
        order.setOrderStatus(statusRepository.findById(1l)
                .orElseThrow(() -> new ResourceNotFoundException("Статус не найден")));
        order.setTotalPrice(cart.getTotalPrice());
        order.setPhone(orderDetail.getPhone());
        List<OrderItem> orderItems = getItemsFromCart(cart, order);
        order.setItems(orderItems);
        orderRepository.save(order);
        cart.clear();
    }

    public void deleteOrderById(Long id) {
        if (notPayingOrder(id)) {
            orderRepository.deleteById(id);
        }
    }

    private List<OrderItem> getItemsFromCart(Cart cart, Order order) {
        return cart.getProducts().stream().map(o -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(o.getQuantity());
            orderItem.setPricePerProduct(o.getPricePerProduct());
            orderItem.setPrice(o.getPrice());
            orderItem.setProduct(ProductMapper.MAPPER.toProduct(productService.getProductById(o.getProductId())));
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
