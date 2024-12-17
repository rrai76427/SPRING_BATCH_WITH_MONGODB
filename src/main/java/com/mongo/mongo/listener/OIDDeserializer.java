package com.mongo.mongo.listener;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.snmp4j.smi.OID;

import java.io.IOException;
import java.util.Map;

public class OIDDeserializer extends JsonDeserializer<OID> {

    @Override
    public OID deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        // Parse the JSON object
        Map<String, Object> oidMap = parser.readValueAs(Map.class);

        // Extract the value array and convert to an int array
        Object valueObj = oidMap.get("value");
        if (valueObj instanceof Iterable) {
            Iterable<?> iterable = (Iterable<?>) valueObj;
            int[] values = new int[(int) ((Iterable<?>) valueObj).spliterator().getExactSizeIfKnown()];
            int i = 0;
            for (Object val : iterable) {
                values[i++] = (Integer) val;
            }
            return new OID(values);
        }

        throw new IOException("Invalid OID structure");
    }
}
