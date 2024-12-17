/*
package com.mongo.mongo.listener;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mongo.mongo.model.TrapDetails;
import com.mongo.mongo.pojo.TrapDetailsPojo;
import com.mongo.mongo.repository.TrapDetailsRepository;
import com.mongo.mongo.service.DbHandlerService;
import com.mongo.mongo.utility.Global;
import com.mongo.mongo.utility.OidDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);
   // private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);
    ObjectMapper objectMapper=new ObjectMapper();
    SimpleModule module = new SimpleModule();


    public TrapDetails trapDetails=new TrapDetails();


    public DbHandlerService dbHandlerService=new DbHandlerService();

    @Autowired
    public TrapDetailsRepository trapDetailsRepository;
    @KafkaListener(topics ="all_trap_received", groupId = "mongoinformation",containerFactory = "kafkaListenerContainerFactory")
    public void listenGroup(String message) {

        System.out.println("Received Message : " + message);
        module.addDeserializer(OID.class, new OidDeserializer());
        objectMapper.registerModule(module);
        try {
            TrapDetailsPojo rawTrapToKafka = objectMapper.readValue(message, TrapDetailsPojo.class);
            logger.info("ALL RAW TRAP RECEIVED !!");
            synchronized (Global.sm_rawTrapVector_all) {
                Global.sm_rawTrapVector_all.add(rawTrapToKafka.getRawTrap());
                Global.sm_rawTrapVector_all.notifyAll();
            }
            trapDetails.setTrapType(rawTrapToKafka.getTrapType());
            trapDetails.setRawTrap(rawTrapToKafka.getRawTrap());
            dbHandlerService.saveMessageToMongoDB(trapDetails,trapDetailsRepository);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

*/
