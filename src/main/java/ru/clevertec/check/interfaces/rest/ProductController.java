package ru.clevertec.check.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.check.application.usecases.ProductUseCase;
import ru.clevertec.check.domain.model.entity.Product;
import ru.clevertec.check.domain.model.valueobject.Price;
import ru.clevertec.check.domain.model.valueobject.ProductName;
import ru.clevertec.check.domain.model.valueobject.SaleConditionType;
import ru.clevertec.check.interfaces.rest.request.CreateNewProductRequestDto;
import ru.clevertec.check.interfaces.rest.response.ProductResponseDto;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping
    ResponseEntity<List<ProductResponseDto>> fetchAll(){
        List<ProductResponseDto> result = productUseCase.fetchAll()
                .stream()
                .map(product -> ProductResponseDto
                        .builder()
                        .id(product.getId().id())
                        .price(product.getPrice().value())
                        .name(product.getName().description())
                        .wholesale(product.getSaleConditionType().toBool())
                        .build())
                .toList();

        return ResponseEntity.ok(result);
    }

    @PostMapping
    ResponseEntity<Void> create(CreateNewProductRequestDto newProductRequest){
        Product product = new Product();
        product.addProductPrice(new Price(newProductRequest.getPrice()));
        product.addProductName(new ProductName(newProductRequest.getName()));
        product.setSaleConditionType(SaleConditionType.valueOf(newProductRequest.isWholesale()));

        //return ResponseEntity.created("");
        return null;
    }


}
