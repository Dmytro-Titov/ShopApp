package ua.titov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.titov.Converter;
import ua.titov.dto.OrderDto;
import ua.titov.dto.ProductDto;
import ua.titov.entity.Order;
import ua.titov.entity.Product;
import ua.titov.repository.OrderRepository;
import ua.titov.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDto getById(int id) {
        Order order = orderRepository.findById(id);
        List<Product> products = productRepository.findByOrderId(id);
        return Converter.toOrderDto(order, products);
    }

    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(Converter.toOrderDto(order, productRepository.findByOrderId(order.getId())));
        }
        return orderDtos;
    }

    public OrderDto add(OrderDto orderDto) {
        Order order = Converter.toOrder(orderDto);
        order = orderRepository.save(order);
        List<ProductDto> productDtos = orderDto.getProducts();
        List<Product> products = new ArrayList<>();
        for (ProductDto productDto : productDtos) {
            Product savedProduct = productRepository.save(Converter.toProduct(order.getId(), productDto));
            products.add(savedProduct);
        }
        return Converter.toOrderDto(order, products);
    }
}
