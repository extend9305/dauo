package tech.exam.dauo.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.exam.dauo.batch.tasklet.SimpleTasklet;
import tech.exam.dauo.config.ConfigProperties;
import tech.exam.dauo.dao.DaouExamMapper;
import tech.exam.dauo.service.DauoExamService;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ConfigProperties configProperties;
    @Autowired
    DaouExamMapper daouExamMapper;

    @Bean
    public Job makeJob() {
        return jobBuilderFactory.get("makeJob")
                .start(makeStep1())
                .build();
    }

    @Bean
    public Step makeStep1() {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet(new SimpleTasklet(daouExamMapper,configProperties))
                .build();
    }
}
