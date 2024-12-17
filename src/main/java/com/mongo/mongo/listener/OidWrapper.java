package com.mongo.mongo.listener;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.snmp4j.smi.OID;

public class OidWrapper {
    private boolean valid;
    private int syntax;
    private int berlength;
    private boolean dynamic;
    private boolean exception;
    private String syntaxString;
    private int berpayloadLength;

    @JsonDeserialize(using = OIDDeserializer.class)
    private OID oid;

    public OID getOid() {
        return oid;
    }

    public void setOid(OID oid) {
        this.oid = oid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getSyntax() {
        return syntax;
    }

    public void setSyntax(int syntax) {
        this.syntax = syntax;
    }

    public int getBerlength() {
        return berlength;
    }

    public void setBerlength(int berlength) {
        this.berlength = berlength;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isException() {
        return exception;
    }

    public void setException(boolean exception) {
        this.exception = exception;
    }

    public String getSyntaxString() {
        return syntaxString;
    }

    public void setSyntaxString(String syntaxString) {
        this.syntaxString = syntaxString;
    }

    public int getBerpayloadLength() {
        return berpayloadLength;
    }

    public void setBerpayloadLength(int berpayloadLength) {
        this.berpayloadLength = berpayloadLength;
    }
}
