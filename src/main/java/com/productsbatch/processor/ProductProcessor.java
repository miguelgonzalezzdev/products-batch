package com.productsbatch.processor;

import com.productsbatch.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductProcessor implements ItemProcessor<Product, Product> {

    // Procesar el item y devolverlo
    @Override
    public Product process(Product item) throws  Exception{
        item.setId(null);
        return item;
    }
}
