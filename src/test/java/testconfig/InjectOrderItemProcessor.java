package testconfig;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import ru.clevertec.check.domain.model.dto.OrderItemDto;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.entity.DiscountCard;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.domain.model.valueobject.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

public class InjectOrderItemProcessor implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(InjectOrderItem.class))
                .filter(field -> field.getType().equals(OrderItemDto.class))
                .forEach(field -> extractCheckInfoAndInject(testInstance, field));
    }

    private static void extractCheckInfoAndInject(Object testInstance, Field field) {
        InjectOrderItem annotation = field.getAnnotation(InjectOrderItem.class);
        int quantity = annotation.quantity();
        int cardNumber = annotation.cardNumber();
        boolean wholeSale = annotation.wholeSale();
        RealDiscountCard realDiscountCard = new RealDiscountCard(new CardId(1), BigDecimal.TEN);
        realDiscountCard.addCardNumber(new CardNumber(cardNumber));


        OrderItemDto orderItem = new OrderItemDto(
                realDiscountCard,
                wholeSale ? SaleConditionType.WHOLESALE : SaleConditionType.USUAL_PRICE,
                quantity,
                BigDecimal.valueOf(1.48),
                "Milk 1l."
        );

        if (field.trySetAccessible()) {
            try {
                field.set(testInstance, orderItem);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
