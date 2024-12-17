package com.mongo.mongo.listener;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mongo.mongo.model.TrapDetails;
import com.mongo.mongo.pojo.TrapDetailsPojo;
import com.mongo.mongo.utility.Global;
import com.mongo.mongo.utility.OidDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static wiremock.net.minidev.json.JSONValue.isValidJson;

@Component
public class KafkaMessageListenerOID {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageListenerOID.class);
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(5000); // Adjust size as needed

    @KafkaListener(topics = "all_trap_received", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String messages) {

        System.out.println ("Listening messages");




        messageQueue.add(messages);









//        for (String message : messages) {
//
//
//            if (isValidJson(message)) {
//
//            } else {
//                System.err.println("Discarding invalid message: " + message);
//            }
//
//            if (!messageQueue.offer(message)) {
//                // Handle queue overflow (e.g., log, notify, or throttle producer)
//                System.err.println("Queue is full. Message dropped: " + message);
//            }
//
//
//
//        }
    }

    public BlockingQueue<String> getMessageQueue() {
        return messageQueue;
    }
}
