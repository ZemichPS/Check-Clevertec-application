package ru.clevertec.check.infrastructure.output.std;

import ru.clevertec.check.application.ports.output.StdOutputPort;
import ru.clevertec.check.domain.model.dto.TotalPricesDto;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.entity.DiscountCard;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class StdOutputAdapter implements StdOutputPort {
    @Override
    public void printCheck(Check check) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        DateTimeFormatter timeFormater = DateTimeFormatter.ofPattern("HH:mm:ss");

        String date = check.getCreationDate().format(dateFormatter);
        String time = check.getCreationTime().format(timeFormater);
        TotalPricesDto totalPrices = check.computeAndGetTotalPrices();
        DiscountCard discountCard = check.getDiscountCard();


        StringBuilder builder = new StringBuilder()
                .append("Date: ").append(date).append("\n")
                .append("Time: ").append(time).append("\n");
        builder.append("---------------------------\n");
        if(check.getItemsCount() > 0){
            check.getItems().forEach(checkItem -> {
                String description = checkItem.description();
                int quantity = checkItem.qty();
                BigDecimal price = checkItem.price();
                BigDecimal discount = checkItem.discount();
                BigDecimal total = checkItem.total();
                builder.append(checkItem.description())
                        .append("| quantity: ").append(quantity)
                        .append("| price: ").append(price).append("$")
                        .append("| discount: ").append(discount).append("$")
                        .append("| total: ").append(total).append("$").append("\n");

            });
            builder.append("---------------------------\n");

            if(discountCard.isValid()) {
                builder.append("Discount card number: ").append(discountCard.getCardNumber().number()).append("\n");
                builder.append("Discount percentage: ").append(discountCard.getDiscountAmount()).append("\n");
                builder.append("---------------------------\n");
            }
            builder.append("Total price: ").append(totalPrices.totalPrice()).append("$");
            builder.append("| Total discount: ").append(totalPrices.totalDiscount()).append("$");
            builder.append("| Total with discount: ").append(totalPrices.totalWithDiscount()).append("$");

            System.out.println(builder);
        }


    }

    @Override
    public void printError(String errorText) {
        System.out.println(errorText.toUpperCase());
    }


}
