package ru.clevertec.check.infrastructure.output.jpa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "items")
public class CheckItemEntity {
    @Id
    private UUID uuid;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "check_uuid", referencedColumnName = "uuid")
    private CheckEntity check;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal total;

    public CheckItemEntity(UUID uuid,
                           CheckEntity check,
                           Integer quantity,
                           BigDecimal price,
                           BigDecimal discount,
                           BigDecimal total) {
        this.uuid = uuid;
        this.check = check;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.total = total;
    }

    public CheckItemEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public CheckEntity getCheck() {
        return check;
    }

    public void setCheck(CheckEntity check) {
        this.check = check;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
