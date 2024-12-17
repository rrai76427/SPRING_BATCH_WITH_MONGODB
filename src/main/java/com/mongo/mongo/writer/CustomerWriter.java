package com.mongo.mongo.writer;

import com.mongo.mongo.model.Item;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerWriter implements ItemWriter<Item> {

//    @Override
//    public void write(List<? extends String> items) throws Exception {
//        items.forEach(System.out::println); // Example: print the items
//    }

    @Override
    public void write(Chunk<? extends Item> chunk) throws Exception {

    }
}

