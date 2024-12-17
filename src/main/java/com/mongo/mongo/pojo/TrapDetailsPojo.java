package com.mongo.mongo.pojo;


import com.mongo.mongo.model.RawTrap;

public class TrapDetailsPojo {

    private RawTrap rawTrap;
    private String trapType;

    public TrapDetailsPojo(RawTrap rawTrap, String trapType) {
        this.rawTrap = rawTrap;
        this.trapType = trapType;
    }

    public TrapDetailsPojo() {
    }

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
}
