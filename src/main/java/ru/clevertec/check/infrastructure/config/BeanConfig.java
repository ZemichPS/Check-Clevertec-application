package ru.clevertec.check.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.check.application.ports.intput.CheckInputPort;
import ru.clevertec.check.application.ports.output.*;
import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.infrastructure.output.jpa.CheckJpaOutputAdapter;
import ru.clevertec.check.infrastructure.output.jpa.DiscountCardJpaOutputAdapter;
import ru.clevertec.check.infrastructure.output.jpa.ProductJpaOutputAdapter;
import ru.clevertec.check.infrastructure.output.jpa.repository.CheckRepository;
import ru.clevertec.check.infrastructure.output.jpa.repository.DiscountCardRepository;
import ru.clevertec.check.infrastructure.output.jpa.repository.ProductRepository;
import ru.clevertec.check.infrastructure.output.std.StdOutputAdapter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

@Configuration
public class BeanConfig {
    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    CheckOutputPort checkOutputPort(CheckRepository checkRepository) {
        return new CheckJpaOutputAdapter(checkRepository);
    }

    @Bean
    ProductOutputPort productOutputPort(ProductRepository productRepository) {
        return new ProductJpaOutputAdapter(productRepository);
    }

    @Bean
    DiscountCardOutputPort discountCardOutputPort(DiscountCardRepository discountCardRepository) {
        return new DiscountCardJpaOutputAdapter(discountCardRepository);
    }

    @Bean
    ErrorOutputPort errorOutputPort() {
        return new ErrorOutputPort() {
            @Override
            public void writeError(String errorText) throws IOException, InvocationTargetException, IllegalAccessException, URISyntaxException {
                System.out.println("ERROR: %s".formatted(errorText));
            }
        };
    }

    @Bean
    StdOutputPort stdOutputPort() {
        return new StdOutputAdapter();
    }

    @Bean
    CheckUseCase checkUseCase(
            CheckOutputPort checkOutputPort,
            ProductOutputPort productOutputPort,
            DiscountCardOutputPort discountCardOutputPort,
            ErrorOutputPort errorOutputPort,
            StdOutputPort stdOutputPort

    ) {
        return new CheckInputPort(checkOutputPort,
                productOutputPort,
                discountCardOutputPort,
                errorOutputPort,
                stdOutputPort
        );
    }

}
