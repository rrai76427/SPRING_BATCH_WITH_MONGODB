package com.mongo.mongo.repository;
import com.mongo.mongo.model.TrapDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrapDetailsRepository extends MongoRepository<TrapDetails, String> {
}
