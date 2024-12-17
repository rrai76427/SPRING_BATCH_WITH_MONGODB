package com.mongo.mongo.domain.model;

public record TripCsvLine(
        Integer bikeId,
        Integer age,
        String gender,
        String durationTime,
        String startStationName,
        String endStationName
) {
}



