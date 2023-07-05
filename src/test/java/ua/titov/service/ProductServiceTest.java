package ua.titov.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import ua.titov.dto.ProductDto;
import ua.titov.entity.Product;
import ua.titov.repository.ProductRepository;

import static org.mockito.Mockito.when;

public class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void init() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }
    @Test
    public void addToOrderTest() {
        init();

        int orderId = 5;
        ProductDto productDto = ProductDto.builder()
                .id(4)
                .name("Burger")
                .cost(165.0)
                .build();

        Product product = Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .cost(productDto.getCost())
                .orderId(orderId)
                .build();

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ProductDto savedProductDto = productService.addToOrder(orderId, productDto);

        double expectedCost = productDto.getCost();
        double actualCost = savedProductDto.getCost();

        String expectedName = productDto.getName();
        String actualName = savedProductDto.getName();

        Assert.assertEquals(expectedCost, actualCost, 0.1);
        Assert.assertTrue(expectedName.equals(actualName));
    }
}
