package com.mongo.mongo.utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.snmp4j.smi.OID;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * @author Sneha Prajapati
 */

@Component
public class OidDeserializer extends JsonDeserializer<OID> {

    @Override
    public OID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        ArrayNode arrayNode = (ArrayNode) jsonNode.get("value");
        int[] oidValues = new int[arrayNode.size()];
        for(int i = 0; i< arrayNode.size(); i++){
            oidValues[i] = arrayNode.get(i).asInt();
        }
        return new OID(oidValues);
    }
}
