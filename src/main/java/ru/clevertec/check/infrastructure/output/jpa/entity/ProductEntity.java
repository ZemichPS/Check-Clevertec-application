package ru.clevertec.check.infrastructure.output.jpa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "app", name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private BigDecimal price;
    private boolean wholesale;

    public ProductEntity(Integer id,
                         String name,
                         BigDecimal price,
                         boolean wholesale) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.wholesale = wholesale;
    }

    public ProductEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isWholesale() {
        return wholesale;
    }

    public void setWholesale(boolean wholesale) {
        this.wholesale = wholesale;
    }
}
