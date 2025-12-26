package com.productsbatch.config;

import com.productsbatch.decision.FlowDecision;
import com.productsbatch.faultTolerant.CustomSkipPolicy;
import com.productsbatch.listener.JobListener;
import com.productsbatch.listener.ReaderListener;
import com.productsbatch.listener.StepListener;
import com.productsbatch.listener.WriterListener;
import com.productsbatch.mapper.ProductFieldSetMapper;
import com.productsbatch.domain.Product;
import com.productsbatch.processor.ProductProcessor;
import com.productsbatch.writer.DatabaseWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ReaderListener readerListenerStep1;
    private final ProductProcessor processorStep1;
    private final DatabaseWriter writerStep1;
    private final WriterListener writerListenerStep1;
    private final CustomSkipPolicy customSkipPolicy;
    private final FlowDecision flowDecision;
    private final JobListener jobListener;
    private final StepListener stepListener;

    @Bean
    public FlatFileItemReader<Product> readerStep1() {
        FlatFileItemReader<Product> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("PRODUCTS_DATA.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    @Bean
    public LineMapper<Product> lineMapper() {
        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(true); // Estricto: Solo se recogeran los campos indicados a continuacion
        lineTokenizer.setNames("name", "description", "category", "subcategory", "price");

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new ProductFieldSetMapper());

        return  lineMapper;
    }

    @Bean
    public Step step1(){
        return (Step) new StepBuilder("csvImportDatabase", jobRepository)
                .<Product, Product>chunk(10, platformTransactionManager)
                .reader(readerStep1())
                .listener(readerListenerStep1)
                .processor(processorStep1)
                .writer(writerStep1)
                .listener(writerListenerStep1)
                .faultTolerant()
                .skipPolicy(customSkipPolicy)
                .listener(stepListener)
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("importProducts",jobRepository)
                .start(step1())
                .next(flowDecision)
                .on(FlowExecutionStatus.COMPLETED.toString()).end()
                .on(FlowExecutionStatus.FAILED.toString()).end()
                .end()
                .listener(jobListener)
                .build();
    }
}
