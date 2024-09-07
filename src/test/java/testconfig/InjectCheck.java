package testconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.UUID;

@Target({ElementType.PARAMETER,
        ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectCheck {
    String id() default "123e4567-e89b-12d3-a456-426614174000";
}
