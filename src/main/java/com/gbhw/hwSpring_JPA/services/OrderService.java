package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.converters.ProductConverter;
import com.gbhw.hwSpring_JPA.dto.OrderDto;
import com.gbhw.hwSpring_JPA.dto.ProductDto;
import com.gbhw.hwSpring_JPA.models.Order;
import com.gbhw.hwSpring_JPA.repositorys.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;

    private final ProductConverter productConverter;

    public void toCart(Long user_id, Long product_id) {
        Order order = new Order(user_id,"in_cart");
        ProductDto productDto = productService.getProductById(product_id);
        order.addProduct(productConverter.dtoToEntity(productDto));
        orderRepository.save(order);
    }

    public List<OrderDto> getCartList(Long user_id){
        List<Long> orderList = orderRepository.getByUserIdInCart(user_id,"in_cart");
        List<ProductDto> productDTOList = productService.getProductDTOList(orderList);
        Map<ProductDto, Integer> products = productDTOList.stream().collect(Collectors.toMap(
                x -> x, value -> 1, Integer::sum
        ));
        ArrayList<OrderDto> a = new ArrayList<>();
        for (Map.Entry<ProductDto, Integer> entery : products.entrySet()) {
            a.add(new OrderDto(
                    entery.getKey().getId(),
                    entery.getKey().getTitle(),
                    entery.getKey().getCoast(),
                    entery.getValue()
            ));
        }
        return a;
    }

    public void deleteProductFromCart(Long user_id, Long product_id){
        orderRepository.getProductInCart(user_id,product_id,"in_cart");
    }
    public void deleteProductListFromCart(Long user_id){
        orderRepository.getAllProductInCart(user_id,"in_cart");
    }
}
