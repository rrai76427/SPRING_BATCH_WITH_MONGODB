package com.mongo.mongo.job;


import com.mongo.mongo.domain.document.Trips;
import com.mongo.mongo.domain.model.TripCsvLine;
import com.mongo.mongo.job.step.TripItemProcessor;
import com.mongo.mongo.job.step.TripItemReader;
import com.mongo.mongo.job.step.TripItemWriter;
import com.mongo.mongo.job.step.TripStepListener;
import com.mongo.mongo.listener.KafkaMessageListenerOID;
import com.mongo.mongo.model.Item;
import com.mongo.mongo.model.TrapDetails;
import com.mongo.mongo.repository.ItemRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.DataSource;


import static com.mongo.mongo.domain.constant.BatchConstants.DEFAULT_CHUNK_SIZE;
import static com.mongo.mongo.domain.constant.BatchConstants.DEFAULT_LIMIT_SIZE;


@Configuration
public class JobConfig {

    @Autowired
    KafkaMessageListenerOID kafkaMessageListenerOID;

    @Autowired
    ItemRepository itemRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MongoPersonWriter mongoPersonWriter;

    @Autowired
    MongoProcessor mongoProcessor;

    @Autowired
    MongoTrapDetailProcessor mongoTrapDetailProcessor;

    @Autowired
    MongoTrapDetailWriter mongoTrapDetailWriter;
    private boolean isValidJson(String json) {
        try {
            new ObjectMapper().readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }


    @Bean
    public Job tripJob(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                       MongoTemplate mongoTemplate) {
        return new JobBuilder("tripJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(tripJobStep(jobRepository, transactionManager, mongoTemplate))
                .listener(new TripJobCompletionListener())
                .build();
    }

   /* @Bean
    public Step tripJobStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                            MongoTemplate mongoTemplate) {
        return new StepBuilder("tripJobCSVGenerator", jobRepository)
                .startLimit(DEFAULT_LIMIT_SIZE)
                .<String, Item>chunk(DEFAULT_CHUNK_SIZE, transactionManager)

                .reader(itemReaderOID())
                .processor(customProcessorOID())
                .writer(customWriterOID())
                .taskExecutor (taskExecutorManualOID())
                .listener(new TripStepListener ())
                .build();
    }*/

    @Bean
    public Step tripJobStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                            MongoTemplate mongoTemplate) {
        return new StepBuilder("tripJobCSVGenerator", jobRepository)
                .<String, TrapDetails>chunk(DEFAULT_CHUNK_SIZE, transactionManager)
                .reader(itemReaderOID())
                .processor(mongoTrapDetailProcessor)
                .writer(mongoTrapDetailWriter)
                //.faultTolerant()
               // .skip(Exception.class)
               // .skipLimit(100) // Set an appropriate limit for skipped items
                .taskExecutor(taskExecutorManualOID())
                .listener(new TripStepListener())
                .build();
    }


    // Custom ItemReader
    @Bean
    public ItemReader<String> itemReaderOID() {
        // return kafkaMessageListenerOID.getMessageQueue()::poll;  // Poll messages from Kafka queue
        return new ItemReader<>() {
            @Override
            public String read() {
                return kafkaMessageListenerOID.getMessageQueue().poll(); // Non-blocking read
            }
        };
    }

    // Custom ItemProcessor
  /*  @Bean
    public ItemProcessor<String, Item> customProcessorOID() {

        return item -> {
            if (item != null && !item.isEmpty()) {
                // Example: Convert the message to uppercase and append "_processed"
//                ObjectMapper mapper = new ObjectMapper();
//                MyEntity entity = mapper.readValue(rawJson, MyEntity.class);

                Item item1 = objectMapper.readValue(item, Item.class);
                return item1;
            }
            return null; // Skip null or empty items
        };
    }*/


   /* @Bean
    public ItemProcessor<String, Item> customProcessorOID() {
        return item -> {
         *//*   if (item == null || item.isEmpty()) {
                return null; // Skip null or empty items
            }
            if (!isValidJson(item)) {
                System.err.println("Invalid JSON skipped: " + item);
                return null; // Skip invalid JSON
            }*//*
           // String itemJson = "{\"item_name\": \"Test Item\", \"item_quantity\": 10}";
            try {


                //Item obj = objectMapper.readValue(itemJson, Item.class);
               // System.out.println("Deserialized Item: " + obj);
                String itemjson1=item.toString ();
                Item obj1=objectMapper.readValue(itemjson1, Item.class);
                System.out.println ("Item : "+obj1);
                //return obj;
                return obj1;

            } catch (Exception e) {
                System.err.println("Error deserializing item: " + item + ", error: " + e.getMessage());
                System.err.println("Error processing item: " + item + " | Error: " + e.getMessage());
                return null; // Skip items causing deserialization errors
            }
        };
    }*/


    @Bean
    public ItemProcessor<String, Item> customProcessorOID() {
        return item -> {
            if (item == null || item.isEmpty()) {
                System.err.println("Received empty or null item, skipping...");
                return null; // Skip null or empty items
            }
            String itemJson = "{\"item_name\": \"Test Item 11\", \"item_quantity\": 10}";
            try {
                System.out.println("Raw JSON string: " + item);  // Log the incoming item before deserialization
                Item obj = objectMapper.readValue(itemJson, Item.class);
                // Directly deserialize the incoming JSON string (item) into an Item object
                Item obj1 = objectMapper.readValue(item, Item.class);

                // Log the deserialized object to check if it worked
                System.out.println("Deserialized Item: " + obj);

                return obj1;

            } catch (Exception e) {
                // Print detailed error message for debugging
                System.err.println("Error deserializing item: " + item + ", error: " + e.getMessage());
                e.printStackTrace(); // Log stack trace for better debugging
                return null; // Skip items causing deserialization errors
            }
        };
    }



    // Custom ItemWriter
//    @Bean
//    @Transactional
//    public ItemWriter<Item> customWriterOID() {
//        return items -> {
//            for (Item item : items) {
//                System.out.println ("Writing item: " + item);
//                // Assuming Item is the entity class
//                itemRepository.save(item);
//                System.out.println ("Writing item: " + item);
//            }
//           // itemRepository.saveAll(items);
//        };
//    }






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


