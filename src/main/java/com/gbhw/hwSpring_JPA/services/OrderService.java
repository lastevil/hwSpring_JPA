package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.converters.OrderMapper;
import com.gbhw.hwSpring_JPA.converters.ProductMapper;
import com.gbhw.hwSpring_JPA.dto.OrderDetailsDto;
import com.gbhw.hwSpring_JPA.dto.OrderDto;
import com.gbhw.hwSpring_JPA.dto.exceptions.ResourceNotFoundException;
import com.gbhw.hwSpring_JPA.entitys.Order;
import com.gbhw.hwSpring_JPA.entitys.OrderItem;
import com.gbhw.hwSpring_JPA.entitys.User;
import com.gbhw.hwSpring_JPA.models.Cart;
import com.gbhw.hwSpring_JPA.repositorys.OrderRepository;
import com.gbhw.hwSpring_JPA.repositorys.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    private final StatusRepository statusRepository;

    private final AddressService addressService;

    private final CartService cartService;

    private final ProductService productService;

    public List<OrderDto> getUserOrders(String userName) {
        User user = userService.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        return orderRepository.findAllByUserId(user.getId())
                .stream()
                .map(OrderMapper.MAPPER::toOrderDto)
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
}
