package com.productsbatch.mapper;

import com.productsbatch.domain.Product;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class ProductFieldSetMapper implements FieldSetMapper<Product> {

    @Override
    public Product mapFieldSet(FieldSet fieldSet) {
        Product product = new Product();
        product.setName(fieldSet.readString("name"));
        product.setDescription(fieldSet.readString("description"));
        product.setCategory(fieldSet.readString("category"));
        product.setSubcategory(fieldSet.readString("subcategory"));

        // Limpiar "price" y convertir a double
        String priceStr = fieldSet.readString("price").replace("$", "").replace(",", "");
        product.setPrice(Double.parseDouble(priceStr));

        return product;
    }
}
