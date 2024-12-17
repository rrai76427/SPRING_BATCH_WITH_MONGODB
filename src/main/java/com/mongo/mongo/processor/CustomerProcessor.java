package com.mongo.mongo.processor;

import com.mongo.mongo.model.Item;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomerProcessor implements ItemProcessor<Item, Item> {
//    @Override
//    public String process(String item) throws Exception {
//        // Add processing logic here (example: uppercase transformation)
//        return item.toUpperCase();
//    }

    @Override
    public Item process(Item item) throws Exception {
        return null;
    }
}
