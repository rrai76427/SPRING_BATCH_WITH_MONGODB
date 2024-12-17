package com.mongo.mongo.job;

import com.mongo.mongo.model.Item;
import com.mongo.mongo.repository.ItemRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoPersonWriter implements ItemWriter<Item> {

    private final ItemRepository itemRepository;

    @Autowired
    public MongoPersonWriter(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Override
    public void write(Chunk<? extends Item> chunk) throws Exception {

        System.out.println (chunk.size ());
        itemRepository.saveAll (chunk);
        System.out.println ("hi");

    }


}
