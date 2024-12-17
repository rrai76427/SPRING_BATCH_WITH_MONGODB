package com.mongo.mongo.domain.enums;

import lombok.Getter;

@Getter
public enum ExecutionContextKey {

    //@// @formatter:off
    TRIP_TOTAL("trip.total");
    // @formatter:on

    private final String key;

    ExecutionContextKey(String key) {
        this.key = key;
    }

}
