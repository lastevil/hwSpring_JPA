package com.hw.market.repositorys.specification;
import com.hw.market.entitys.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> coastGreaterThenOrElseThen(Integer coast) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("coast") , coast));
    }
    public static Specification<Product> coastLessThenOrElseThen(Integer coast) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("coast") , coast));
    }
}
