package com.mongo.mongo.job;

import com.mongo.mongo.model.Item;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


@Component
public class MongoProcessor implements ItemProcessor<String, Item> {

   ObjectMapper objectMapper=new ObjectMapper ();
    @Override
    public Item process(String message) throws Exception {
        try {
           /* String itemJson ="{\"item_name\":\"fan\",\"item_quantity\":500}";

            System.out.println("Raw JSON string: " + itemJson);  // Log the incoming item before deserialization
            Item obj1 = objectMapper.readValue(itemJson, Item.class);

            System.out.println ("Original Message : "+message);*/
            String cleanedItemJson = message.replaceAll("\\\\", "");
            System.out.println ("cleaned message : "+cleanedItemJson);

            String cleanedJson = cleanedItemJson.substring(1, cleanedItemJson.length() - 1); // Remove the outer quotes
            System.out.println ("cleaned json : "+cleanedJson);
            Item obj = objectMapper.readValue(cleanedJson, Item.class);
         //   System.out.println("Deserialized Item: " + obj);
            return obj;
        }
        catch (Exception e){
            e.printStackTrace ();
        }
        return null;
    }


}