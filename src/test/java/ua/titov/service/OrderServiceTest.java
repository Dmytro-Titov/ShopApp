package ua.titov.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import ua.titov.dto.OrderDto;
import ua.titov.dto.ProductDto;
import ua.titov.entity.Order;
import ua.titov.entity.Product;
import ua.titov.repository.OrderRepository;
import ua.titov.repository.ProductRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

public class OrderServiceTest {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderService orderService;
    @BeforeEach
    public void init() {
        orderRepository = Mockito.mock(OrderRepository.class);
        productRepository = Mockito.mock(ProductRepository.class);
        orderService = new OrderService(orderRepository, productRepository);
    }
    @Test
    public void getByIdTest() {
        init();

        int orderId = 3;
        Order order = Order.builder().id(orderId).date(Date.valueOf(LocalDate.now())).build();
        List<Product> products = List.of(
                Product.builder().id(1).name("French fries").cost(45.0).orderId(orderId).build(),
                Product.builder().id(2).name("Burger").cost(115.0).orderId(orderId).build(),
                Product.builder().id(3).name("Cola").cost(30.0).orderId(orderId).build()
        );

        when(orderRepository.findById(orderId)).thenReturn(order);
        when(productRepository.findByOrderId(orderId)).thenReturn(products);

        OrderDto orderById = orderService.getById(orderId);

        Date expectedDate = order.getDate();
        Date actualDate = orderById.getDate();

        int expectedListSize = products.size();
        int actualListSize = orderById.getProducts().size();

        double expectedCost = 190.0;
        double actualCost = orderById.getCost();

        Assert.assertEquals(expectedDate, actualDate);
        Assert.assertEquals(expectedListSize, actualListSize);
        Assert.assertEquals(expectedCost, actualCost, 0.1);
    }
    @Test
    public void getAllTest() {
        init();

        List<Order> orders = List.of(
                Order.builder().id(1).date(Date.valueOf(LocalDate.now())).build(),
                Order.builder().id(2).date(Date.valueOf(LocalDate.now())).build(),
                Order.builder().id(3).date(Date.valueOf(LocalDate.now())).build()
        );

        List<Product> products1 = List.of(
                Product.builder().id(1).name("French fries").cost(45.0).orderId(1).build(),
                Product.builder().id(2).name("Burger").cost(115.0).orderId(1).build(),
                Product.builder().id(3).name("Cola").cost(30.0).orderId(1).build()
        );
        List<Product> products2 = List.of(
                Product.builder().id(1).name("French fries").cost(45.0).orderId(2).build(),
                Product.builder().id(2).name("Sauce").cost(15.0).orderId(2).build()
        );
        List<Product> products3 = List.of(
                Product.builder().id(1).name("McFlury").cost(68.0).orderId(3).build()
        );

        when(orderRepository.findAll()).thenReturn(orders);
        when(productRepository.findByOrderId(1)).thenReturn(products1);
        when(productRepository.findByOrderId(2)).thenReturn(products2);
        when(productRepository.findByOrderId(3)).thenReturn(products3);

        List<OrderDto> orderDtos = orderService.getAll();

        int expectedNumberOfOrders = orders.size();
        int actualNumberOfOrders = orderDtos.size();

        double expectedCostOfSecondOrder = 60.0;
        double actualCostOfSecondOrder = orderDtos.get(1).getCost();

        Date expectedDateOfThirdOrder = orders.get(2).getDate();
        Date actualDateOfThirdOrder = orderDtos.get(2).getDate();

        Assert.assertEquals(expectedNumberOfOrders, actualNumberOfOrders);
        Assert.assertEquals(expectedCostOfSecondOrder, actualCostOfSecondOrder, 0.1);
        Assert.assertEquals(expectedDateOfThirdOrder, actualDateOfThirdOrder);
    }
    @Test
    public void addTest() {
        init();

        int orderId = 10;
        List<ProductDto> productDtos = List.of(
                ProductDto.builder().id(1).name("French fries").cost(45.0).build(),
                ProductDto.builder().id(2).name("Burger").cost(165.0).build()
        );
        OrderDto orderDto = OrderDto.builder()
                .id(orderId)
                .date(Date.valueOf(LocalDate.now()))
                .cost(210.0)
                .products(productDtos).build();

        Order order = Order.builder().id(orderDto.getId()).date(orderDto.getDate()).build();
        Product product = Product.builder().id(1).name("Cola").cost(35.0).orderId(orderId).build();

        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        OrderDto addedOrderDto = orderService.add(orderDto);

        int expectedNumberOfProducts = productDtos.size();
        int actualNumberOfProducts = addedOrderDto.getProducts().size();

        double expectedOrderCost = 70.0;
        double actualOrderCost = addedOrderDto.getCost();

        Date expectedDate = orderDto.getDate();
        Date actualDate = addedOrderDto.getDate();

        Assert.assertEquals(expectedNumberOfProducts, actualNumberOfProducts);
        Assert.assertEquals(expectedOrderCost, actualOrderCost, 0.1);
        Assert.assertEquals(expectedDate, actualDate);
    }
}
