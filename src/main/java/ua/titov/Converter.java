package ua.titov;

import ua.titov.dto.OrderDto;
import ua.titov.dto.ProductDto;
import ua.titov.entity.Order;
import ua.titov.entity.Product;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    public static OrderDto toOrderDto(Order order, List<Product> products) {

        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(toProductDto(product));
        }
        double cost = 0.0;
        for (ProductDto productDto : productDtos) {
            cost += productDto.getCost();
        }
        return OrderDto.builder()
                .id(order.getId())
                .date(order.getDate())
                .cost(cost)
                .products(productDtos)
                .build();
    }

    public static Order toOrder(OrderDto orderDto) {
        return Order.builder()
                .date(Date.valueOf(LocalDate.now()))
                .build();
    }

    public static Product toProduct(int orderId, ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .cost(productDto.getCost())
                .orderId(orderId)
                .build();
    }

    public static ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .cost(product.getCost())
                .build();
    }

}
