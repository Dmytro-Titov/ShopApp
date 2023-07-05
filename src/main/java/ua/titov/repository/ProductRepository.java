package ua.titov.repository;

import org.springframework.data.repository.CrudRepository;
import ua.titov.entity.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByOrderId(int orderId);
}
