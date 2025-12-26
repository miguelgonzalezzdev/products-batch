package com.productsbatch.writer;

import com.productsbatch.domain.Product;
import com.productsbatch.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseWriter implements ItemWriter<Product> {

    private final ProductRepository productRepository;

    @Override
    public void write(Chunk<? extends Product> chunk) throws Exception {
        List<? extends Product> items = chunk.getItems();
        List<? extends Product> saved = productRepository.saveAll(items);
    }
}
