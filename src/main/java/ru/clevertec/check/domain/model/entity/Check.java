package ru.clevertec.check.domain.model.entity;

import ru.clevertec.check.domain.model.valueobject.CheckId;
import ru.clevertec.check.domain.model.valueobject.CheckItem;
import ru.clevertec.check.domain.model.valueobject.ItemHolder;
import ru.clevertec.check.domain.model.dto.TotalPricesDto;
import ru.clevertec.check.domain.specification.PositiveCheckItemPriceSpecification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Check {
    private final CheckId checkID;
    private final LocalDate creationDate;
    private final LocalTime creationTime;
    private final ItemHolder itemHolder = new ItemHolder();
    private DiscountCard discountCard;

    public Check(CheckId checkID, LocalDate creationDate, LocalTime creationTime) {
        this.checkID = checkID;
        this.creationDate = creationDate;
        this.creationTime = creationTime;
    }

    public void addDiscountCart(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    public void addCheckItem(CheckItem checkItem) {
        PositiveCheckItemPriceSpecification specification = new PositiveCheckItemPriceSpecification();
        BigDecimal price = checkItem.price();
        specification.check(price);
        this.itemHolder.addItem(checkItem);
    }

    public List<CheckItem> getItems() {
        return this.itemHolder.getItems();
    }

    public int getItemsCount() {
        return this.itemHolder.getItemsCount();
    }

    public CheckId getCheckID() {
        return checkID;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalTime getCreationTime() {
        return creationTime;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public TotalPricesDto computeAndGetTotalPrices() {

        BigDecimal totalPrice = itemHolder.getItems().stream()
                .map(CheckItem::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDiscount = itemHolder.getItems().stream()
                .map(CheckItem::discount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalWithDiscount = totalPrice.subtract(totalDiscount);

        return new TotalPricesDto(totalPrice, totalDiscount, totalWithDiscount);
    }

}
