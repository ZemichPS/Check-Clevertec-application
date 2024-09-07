package testconfig;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.entity.DiscountCard;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.domain.model.valueobject.CardId;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.CheckId;
import ru.clevertec.check.domain.model.valueobject.CheckItem;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

public class InjectCheckProcessor implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(InjectCheck.class))
                .filter(field -> field.getType().equals(Check.class))
                .forEach(field -> extractCheckInfoAndInject(testInstance, field));
    }

    private static void extractCheckInfoAndInject(Object testInstance, Field field) {
        InjectCheck annotation = field.getAnnotation(InjectCheck.class);

        String id = annotation.id();

        Check check = new Check(
                new CheckId(UUID.fromString(id)),
                LocalDate.now(),
                LocalTime.now()
        );
        DiscountCard discountCard = new RealDiscountCard(new CardId(1));
        discountCard.addDiscountAmount(BigDecimal.valueOf(5));
        discountCard.addCardNumber(new CardNumber(1111));

        check.addCheckItem(new CheckItem(7, "Coca cola", BigDecimal.valueOf(1.1), BigDecimal.valueOf(0.9), BigDecimal.valueOf(6.9)));
        check.addCheckItem(new CheckItem(8, "Free fish", BigDecimal.valueOf(4), BigDecimal.valueOf(2), BigDecimal.valueOf(8)));
        check.addDiscountCart(discountCard);


        if (field.trySetAccessible()) {
            try {
                field.set(testInstance, check);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
