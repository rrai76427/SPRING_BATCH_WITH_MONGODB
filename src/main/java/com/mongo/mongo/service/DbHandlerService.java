package com.mongo.mongo.service;



import com.mongo.mongo.model.Item;
import com.mongo.mongo.repository.ItemRepository;
import com.mongo.mongo.repository.TrapDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;


/**
 *
 * @author acer
 */
@Service
public class DbHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(DbHandlerService.class);
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    private TrapDetailsRepository trapDetailsRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaJSONStringMsgSender;
    public Item saveMessageToMongoDB(Item message){

        Item savedItem = itemRepository.save(message);

        // Send message to Kafka
        // kafkaProducer.send(savedItem,"ravi");

        return savedItem;
    }

    public void sendMsgToKAFKA(Object messages,String topicname) {

        try {
            ObjectMapper Obj = new ObjectMapper();
            String message=Obj.writeValueAsString(messages);;
            kafkaJSONStringMsgSender.send("snmp_info_data2",message);
            logger.info("Message Publis to Kafka !!!");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}