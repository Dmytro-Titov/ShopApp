package ua.titov.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.titov.dto.OrderDto;
import ua.titov.dto.ProductDto;
import ua.titov.service.OrderService;
import ua.titov.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable int id) {
        return orderService.getById(id);
    }

    @GetMapping
    public List<OrderDto> getAll() {
        return orderService.getAll();
    }

    @PostMapping
    public OrderDto add(@RequestBody OrderDto orderDto) {
        return orderService.add(orderDto);
    }

    @PostMapping("/{orderId}/products")
    public ProductDto addToOrder(@PathVariable int orderId, @RequestBody ProductDto productDto) {
        return productService.addToOrder(orderId, productDto);
    }
}
