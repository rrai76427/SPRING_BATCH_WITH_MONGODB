package com.mongo.mongo.model;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import java.util.ArrayList;


public  class RawTrap {
        private OID oid;
        private String source;
        private byte type;
        private ArrayList<VariableBinding>vb;
        private short genericValue;
        private long specificValue;

    public RawTrap(OID oid, String source, byte type, ArrayList<VariableBinding> vb, short genericValue, long specificValue) {
        this.oid = oid;
        this.source = source;
        this.type = type;
        this.vb = vb;
        this.genericValue = genericValue;
        this.specificValue = specificValue;
    }

    public RawTrap() {
    }

    public OID getOid() {
        return oid;
    }

    public void setOid(OID oid) {
        this.oid = oid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public ArrayList<VariableBinding> getVb() {
        return vb;
    }

    public void setVb(ArrayList<VariableBinding> vb) {
        this.vb = vb;
    }

    public short getGenericValue() {
        return genericValue;
    }

    public void setGenericValue(short genericValue) {
        this.genericValue = genericValue;
    }

    public long getSpecificValue() {
        return specificValue;
    }

    public void setSpecificValue(long specificValue) {
        this.specificValue = specificValue;
    }
}

