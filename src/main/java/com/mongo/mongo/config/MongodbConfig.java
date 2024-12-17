package com.mongo.mongo.config;




import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.text.MessageFormat;

@Configuration
public class MongodbConfig {

    private final MongodbProperties mongodbProperties;

    public MongodbConfig(MongodbProperties mongodbProperties) {
        this.mongodbProperties = mongodbProperties;
    }

    @Bean
    public MongoClient mongoClient() {
/*        String connectionString = MessageFormat.format("mongodb://{0}:{1}@{2}:{3}/{4}",
                mongodbProperties.getUsername(), mongodbProperties.getPassword(),
                mongodbProperties.getHost(), mongodbProperties.getPort(),
                mongodbProperties.getDatabase());*/
        String connectionString = MessageFormat.format("mongodb://{0}:{1}@{2}:{3}/{4}?authSource={5}",
                mongodbProperties.getUsername(), mongodbProperties.getPassword(),
                mongodbProperties.getHost(), mongodbProperties.getPort(),
                mongodbProperties.getDatabase(), "admin");  // Specify authentication database


        // Set the MongoDB authentication mechanism explicitly
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString(connectionString))
                .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, mongodbProperties.getDatabase());
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongodbProperties.getDatabase());
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }
}

/*

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.text.MessageFormat;

*/
/**
 * @author aek
 *//*

@Configuration
public class MongodbConfig {
    private final MongodbProperties mongodbProperties;

    public MongodbConfig(MongodbProperties mongodbProperties) {
        this.mongodbProperties = mongodbProperties;
    }

    @Bean
    public MongoClient mongoClient() {
        var connectionString =MessageFormat.format("mongodb://{0}:{1}@{2}:{3}/{4}",
                mongodbProperties.getUsername(), mongodbProperties.getPassword(), mongodbProperties.getHost(), mongodbProperties.getPort(), mongodbProperties.getDatabase());

        return MongoClients.create(connectionString);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, mongodbProperties.getDatabase());
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongodbProperties.getDatabase());
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

}*/
