package com.mongo.mongo.job;

import com.mongo.mongo.model.Item;
import com.mongo.mongo.model.TrapDetails;
import com.mongo.mongo.repository.TrapDetailsRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class MongoTrapDetailWriter implements ItemWriter<TrapDetails> {

    private final TrapDetailsRepository trapDetailsRepository;

    @Autowired
    public MongoTrapDetailWriter(TrapDetailsRepository trapDetailsRepository) {
        this.trapDetailsRepository = trapDetailsRepository;
    }


    @Override
    public void write(Chunk<? extends TrapDetails> chunk) throws Exception {

        System.out.println (chunk.size ());
        trapDetailsRepository.saveAll (chunk);
        System.out.println ("hi");

    }


}