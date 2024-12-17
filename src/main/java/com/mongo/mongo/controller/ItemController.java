package com.mongo.mongo.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongo.mongo.model.Item;
import com.mongo.mongo.repository.ItemRepository;
import com.mongo.mongo.service.DbHandlerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DbHandlerService dbHandlerService;
    @Autowired
    KafkaTemplate<String, String> kafkaJSONStringMsgSender;

    ObjectMapper objectMapper=new ObjectMapper ();



    @PostMapping
    public Item createItem(@RequestBody Item item) throws JsonProcessingException, wiremock.com.fasterxml.jackson.core.JsonProcessingException {
        // Save item to MongoDB
        Item savedItem = itemRepository.save(item);

        // Send message to Kafka


        dbHandlerService.sendMsgToKAFKA( item, "snmp_info_data");
        return savedItem;
    }

    @Scheduled(fixedRate = 1000)
    public void senddummydata(){
        Item item=new Item ();
       // item.setId ("20");
        item.setItemName("TV");
        item.setItemQuantity (600);
        try {

           String itemtosend= objectMapper.writeValueAsString(item);
            dbHandlerService.sendMsgToKAFKA( itemtosend, "snmp_info_data2");
        } catch (wiremock.com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException (e);
        }

    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}

