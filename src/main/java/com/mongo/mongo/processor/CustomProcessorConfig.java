//package com.mongo.mongo.processor;
//
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CustomProcessorConfig {
//
//    @Bean
//    public ItemProcessor<String, String> customProcessorOID() {
//        return new ItemProcessor<>() {
//            @Override
//            public String process(String item) throws Exception {
//                // Example: Custom processing logic (e.g., append a suffix)
//                return item != null ? item + "_processed" : null;
//            }
//        };
//    }
//}
