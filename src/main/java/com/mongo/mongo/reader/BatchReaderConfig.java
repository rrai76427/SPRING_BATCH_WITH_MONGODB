//import com.mongo.mongo.model.Item;
//import org.springframework.batch.item.data.MongoItemReader;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import java.util.HashMap;
//
//@Configuration
//public class BatchReaderConfig {
//
//    @Bean
//    public MongoItemReader<Item> mongoItemReader(MongoTemplate mongoTemplate) {
//        MongoItemReader<Item> reader = new MongoItemReader<>();
//        reader.setTemplate(mongoTemplate);
//        reader.setQuery("{}");  // Reads all documents
//        reader.setSort(new HashMap<>());  // No sorting needed
//        reader.setTargetType(Item.class);  // Specify the target entity class
//        return reader;
//    }
//}
