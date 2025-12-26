package com.productsbatch.listener;

import com.productsbatch.domain.Stats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("\n" +
                "==============================================\n" +
                "============ START PRODUCTS BATCH PROCESSING ============\n" +
                "==============================================\n");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("\n" +
                "==============================================\n" +
                "============= END PRODUCTS BATCH PROCESSING =============\n" +
                "==============================================\n");

        System.out.println("============ BATCH STATS ============");
        log.info("Errors: {}", jobExecution.getExecutionContext().getInt(Stats.TOTAL_ERRORS.name(), 0));
        log.info("Total processed: {}", jobExecution.getExecutionContext().getInt(Stats.TOTAL_PROCESSED.name(), 0));
        log.info("IMPORT PRODUCTS BATCH ENDED CORRECTLY AT: {}", new Date().toString());
        System.out.println("============ BATCH STATS ============\n");
    }

}
