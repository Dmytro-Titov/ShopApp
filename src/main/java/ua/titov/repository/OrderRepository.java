package ua.titov.repository;

import org.springframework.data.repository.CrudRepository;
import ua.titov.entity.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    Order findById(int id);
    List<Order> findAll();
}
