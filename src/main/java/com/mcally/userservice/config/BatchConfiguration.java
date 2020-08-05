package com.mcally.userservice.config;

import com.mcally.userservice.batch.JobCompletionNotificationListener;
import com.mcally.userservice.batch.PersonItemProcessor;
import com.mcally.userservice.batch.PersonToUserItemProcessor;
import com.mcally.userservice.model.Person;
import com.mcally.userservice.model.User;
import com.mcally.userservice.repository.UserRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final UserRepository userRepository;

    private final PersonToUserItemProcessor personToUserItemProcessor;

    private final PersonItemProcessor personItemProcessor;

    private final EntityManagerFactory entityManagerFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              UserRepository userRepository,
                              EntityManagerFactory entityManagerFactory,
                              PersonToUserItemProcessor personToUserItemProcessor,
                              PersonItemProcessor personItemProcessor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.userRepository = userRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.personToUserItemProcessor = personToUserItemProcessor;
        this.personItemProcessor = personItemProcessor;
    }

    @Bean
    public JsonItemReader<Person> personJsonItemReader() {
        return new JsonItemReaderBuilder<Person>()
                .name("personItemReader")
                .jsonObjectReader(new JacksonJsonObjectReader<>(Person.class))
                .resource(new ClassPathResource("people.json")).build();
    }

    @Bean
    public JpaItemWriter<User> personJpaUserWriter() {
        return new JpaItemWriterBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public JpaItemWriter<Person> personJpaPersonWriter() {
        return new JpaItemWriterBuilder<Person>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1, Step step2) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .next(step2)
                .end()
                .build();
    }

    @Bean
    public Step step1(JpaItemWriter<User> writer, JsonItemReader<Person> personJsonItemReader) {
        return stepBuilderFactory.get("step1")
                .<Person, User> chunk(10)
                .reader(personJsonItemReader)
                .processor(personToUserItemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step step2(JpaItemWriter<Person> personJpaPersonWriter, JsonItemReader<Person> personJsonItemReader) {
        return stepBuilderFactory.get("step2")
                .<Person, Person> chunk(10)
                .reader(personJsonItemReader)
                .processor(personItemProcessor)
                .writer(personJpaPersonWriter)
                .build();
    }


}
