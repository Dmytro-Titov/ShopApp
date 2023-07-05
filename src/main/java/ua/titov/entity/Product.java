package ua.titov.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("products")
public class Product {
    @Id
    private int id;
    private String name;
    private double cost;
    private int orderId;
}
