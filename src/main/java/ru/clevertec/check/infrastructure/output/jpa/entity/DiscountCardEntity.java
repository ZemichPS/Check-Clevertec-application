package ru.clevertec.check.infrastructure.output.jpa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "app", name = "discount_cards")
public class DiscountCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer number;
    private BigDecimal discountAmount;

    public DiscountCardEntity(Integer id,
                              Integer number,
                              BigDecimal discountAmount) {
        this.id = id;
        this.number = number;
        this.discountAmount = discountAmount;
    }

    public DiscountCardEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
