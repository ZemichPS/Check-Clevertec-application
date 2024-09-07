package testconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,
        ElementType.FIELD,
        ElementType.LOCAL_VARIABLE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectOrderItem {
    int quantity() default 5;

    int cardNumber() default 1111;

    boolean wholeSale() default false;
}
