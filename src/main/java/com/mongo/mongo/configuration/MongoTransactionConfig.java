/*
package com.mongo.mongo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoTransactionConfig {

    @Bean(name = "mongoTxManager") // Rename the bean to avoid conflicts
    public MongoTransactionManager mongoTransactionManager(MongoTemplate mongoTemplate) {
        return new MongoTransactionManager (mongoTemplate.getMongoDatabaseFactory ());
    }
}
*/
