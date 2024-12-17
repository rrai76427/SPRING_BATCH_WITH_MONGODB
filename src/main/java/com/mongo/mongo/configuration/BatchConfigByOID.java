/*
//package com.mongo.mongo.configuration;
//
//
//
//import com.mongo.mongo.listener.KafkaMessageListenerOID;
//import com.mongo.mongo.model.Item;
//import com.mongo.mongo.processor.CustomerProcessor;
//import com.mongo.mongo.writer.CustomerWriter;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
////import org.springframework.batch.item.data.MongoItemReader;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.data.mongodb.MongoTransactionManager;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.function.Function;
//
//@Configuration
//@EnableBatchProcessing
//public class BatchConfigByOID {
//
//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;
//
//
//    @Autowired
//    private final JobRepository jobRepository;
//    @Autowired
//    private KafkaMessageListenerOID kafkaMessageListenerOID;
//
//
//    @Autowired
//    private CustomerProcessor snmpProcessorOID;
//
//
//    @Autowired
//    private CustomerWriter snmpWriterOID;
////    @Autowired
////    private MongoTransactionManager mongoTransactionManager;
//
//    @Autowired
//    @Qualifier("mongoTxManager") // Use the custom MongoDB transaction manager
//    private MongoTransactionManager mongoTransactionManager;
//
//
//
//    public BatchConfigByOID(JobRepository jobRepository, MongoTransactionManager mongoTransactionManager,CustomerProcessor snmpProcessor, CustomerWriter snmpWriter) {
//        this.jobRepository = jobRepository;
//        this.mongoTransactionManager = mongoTransactionManager;
//        this.snmpProcessorOID = snmpProcessor;
//        this.snmpWriterOID = snmpWriter;
//
//    }
//
//
//
//
//    @Bean
//    public Job kafkaContinuousJobOID() {
//        return new JobBuilder("kafkaContinuousJobOID",jobRepository)
//                .start(kafkaStepOID())
//                .incrementer(new RunIdIncrementer()) // Ensures a unique job instance ID for each execution
//                .build();
//    }
//
//
//    @Bean
//    public ItemReader<String> itemReaderOID() {
//        return new ItemReader<>() {
//            @Override
//            public String read() {
//                return kafkaMessageListenerOID.getMessageQueue().poll(); // Non-blocking read
//            }
//        };
//    }
//
//
//
//
//    @Bean
//    public Step kafkaStepOID() {
//        return new StepBuilder("kafkaStep")
//                .<String, String>chunk(1000)
//                .reader(itemReaderOID())
//                .processor(customProcessorOID())
//                .writer(customWriterOID())
//                .transactionManager(mongoTransactionManager)
//                .taskExecutor(taskExecutorManualOID())
//                .build();
//    }
//
//    // Custom ItemProcessor
//    @Bean
//    public ItemProcessor<String, String> customProcessorOID() {
//        return item -> {
//            if (item != null && !item.isEmpty()) {
//                // Example: Convert the message to uppercase and append "_processed"
//                return item.toUpperCase() + "_PROCESSED";
//            }
//            return null; // Skip null or empty items
//        };
//    }
//
//    // Custom ItemWriter
//    @Bean
//    public ItemWriter<String> customWriterOID() {
//        return items -> {
//            // Example: Print each processed item to the console
//            for (String item : items) {
//                System.out.println("Writing item: " + item);
//            }
//        };
//    }
//
//    @Bean
//    public TaskExecutor taskExecutorManualOID() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//
//        executor.setCorePoolSize(10); // Minimum threads
//        executor.setMaxPoolSize(100); // Maximum threads
//        executor.setQueueCapacity(5000); // Capacity of task queue
//        executor.setThreadNamePrefix("batch-executor-");
//        executor.initialize();
//        return executor;
//    }
//}





package com.mongo.mongo.configuration;

import com.mongo.mongo.listener.KafkaMessageListenerOID;
import com.mongo.mongo.model.Item;
import com.mongo.mongo.repository.ItemRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfigByOID {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final KafkaMessageListenerOID kafkaMessageListenerOID;
    private final MongoTransactionManager mongoTransactionManager;

    @Autowired
    private ItemRepository itemRepository;
    public BatchConfigByOID(JobBuilderFactory jobBuilderFactory,
                            StepBuilderFactory stepBuilderFactory,
                            KafkaMessageListenerOID kafkaMessageListenerOID,
                            MongoTransactionManager mongoTransactionManager) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.kafkaMessageListenerOID = kafkaMessageListenerOID;
        this.mongoTransactionManager = mongoTransactionManager;
    }

    // Define the Job
    @Bean
    public Job kafkaContinuousJobOID() {
        return jobBuilderFactory.get("kafkaContinuousJobOID")
                .incrementer(new RunIdIncrementer())
                .start(kafkaStepOID())
                .build();
    }

    // Define the Step
    @Bean
    public Step kafkaStepOID() {
        return stepBuilderFactory.get("kafkaStepOID")
                .<String, String>chunk(1000)
                .reader(itemReaderOID())
                .processor(customProcessorOID())
                .writer(customWriterOID())
                .transactionManager(mongoTransactionManager)
                .taskExecutor(taskExecutorManualOID())
                .build();
    }

    // Custom ItemReader
    @Bean
    public ItemReader<String> itemReaderOID() {
        return kafkaMessageListenerOID.getMessageQueue()::poll;  // Poll messages from Kafka queue
    }

    // Custom ItemProcessor
    @Bean
    public ItemProcessor<String, String> customProcessorOID() {
        return item -> {
            if (item != null && !item.isEmpty()) {
                // Example: Convert the message to uppercase and append "_processed"
                return item.toUpperCase() + "_PROCESSED";
            }
            return null; // Skip null or empty items
        };
    }

    // Custom ItemWriter
    @Bean
    public ItemWriter<String> customWriterOID() {
        return items -> {
            for (String item : items) {
                System.out.println ("Writing item: " + item);
                itemRepository.save (new Item (item)); // Assuming Item is the entity class
            }
        };
    }
    // TaskExecutor for parallel processing
    @Bean
    public TaskExecutor taskExecutorManualOID() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(5000);
        executor.setThreadNamePrefix("batch-executor-");
        executor.initialize();
        return executor;
    }
}
*/
