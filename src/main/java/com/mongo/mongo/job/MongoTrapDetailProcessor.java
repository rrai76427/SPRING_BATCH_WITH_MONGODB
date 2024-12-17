package com.mongo.mongo.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mongo.mongo.listener.KafkaMessageListenerOID;
import com.mongo.mongo.model.Item;
import com.mongo.mongo.model.TrapDetails;
import com.mongo.mongo.pojo.TrapDetailsPojo;
import com.mongo.mongo.utility.Global;
import com.mongo.mongo.utility.OidDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


import com.fasterxml.jackson.databind.ObjectMapper;



@Component
public class MongoTrapDetailProcessor implements ItemProcessor<String, TrapDetails> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageListenerOID.class);

    ObjectMapper objectMapper=new ObjectMapper ();

    SimpleModule module = new SimpleModule();

    @Override
    public TrapDetails process(String messages) throws Exception {
        try {
           /* String itemJson ="{\"item_name\":\"fan\",\"item_quantity\":500}";

            System.out.println("Raw JSON string: " + itemJson);  // Log the incoming item before deserialization
            Item obj1 = objectMapper.readValue(itemJson, Item.class);*/

            System.out.println ("Original Message : "+messages);
            String cleanedItemJson = messages.replaceAll("\\\\", "");
            System.out.println ("cleanedItemJson : "+cleanedItemJson);

            String cleanedJson = cleanedItemJson.substring(1, cleanedItemJson.length() - 1); // Remove the outer quotes
            System.out.println ("cleaned json : "+cleanedJson);
//            Item obj = objectMapper.readValue(cleanedJson, Item.class);
//            //   System.out.println("Deserialized Item: " + obj);
//            return obj;





            module.addDeserializer(OID.class, new OidDeserializer());
            objectMapper.registerModule(module);
            try {
                TrapDetailsPojo rawTrapToKafka = objectMapper.readValue(messages, TrapDetailsPojo.class);
                logger.info("ALL RAW TRAP RECEIVED !!");
                synchronized (Global.sm_rawTrapVector_all) {
                    Global.sm_rawTrapVector_all.add(rawTrapToKafka.getRawTrap());
                    Global.sm_rawTrapVector_all.notifyAll();
                }
                TrapDetails trapDetails=new TrapDetails();

                trapDetails.setTrapType(rawTrapToKafka.getTrapType());
                trapDetails.setRawTrap(rawTrapToKafka.getRawTrap());
                return trapDetails;
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }

        }
        catch (Exception e){
            e.printStackTrace ();
        }
        return null;
    }


}











