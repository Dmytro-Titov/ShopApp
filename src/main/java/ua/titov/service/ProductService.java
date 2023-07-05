package ua.titov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.titov.Converter;
import ua.titov.dto.ProductDto;
import ua.titov.entity.Product;
import ua.titov.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto addToOrder(int orderId, ProductDto productDto) {
        Product product = Converter.toProduct(orderId, productDto);
        Product savedProduct = productRepository.save(product);
        productDto.setId(savedProduct.getId());
        return productDto;
    }
}
