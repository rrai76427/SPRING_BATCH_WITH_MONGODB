package com.mongo.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trap_details")
public class TrapDetails {

    @Id
    private String id;
    private RawTrap rawTrap;
    private String trapType;

    // Getters and Setters
    public RawTrap getRawTrap() {
        return rawTrap;
    }

    public void setRawTrap(RawTrap rawTrap) {
        this.rawTrap = rawTrap;
    }

    public String getTrapType() {
        return trapType;
    }

    public void setTrapType(String trapType) {
        this.trapType = trapType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
