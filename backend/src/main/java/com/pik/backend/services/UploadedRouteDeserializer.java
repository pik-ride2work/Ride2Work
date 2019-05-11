package com.pik.backend.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class UploadedRouteDeserializer extends JsonDeserializer<UploadedRoute> {
    //Node & field name constants
    private static final String TRACE_POINTS_NODE = "TracePoints";
    private static final String CORD_LON = "lon";
    private static final String CORD_LAT = "lat";
    private static final String CORD_ELE = "elevation";
    private static final String TIMESTAMP = "timestamp";
    private static final String USER_ID = "userId";

    @Override
    public UploadedRoute deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode tracePointsNode = rootNode.get(TRACE_POINTS_NODE);
        int userId = rootNode.get(USER_ID).asInt();
        List<RoutePoint> points = new ArrayList<>();
        for (JsonNode point : tracePointsNode) {
            points.add(deserializePoint(point));
        }
        return new UploadedRoute(points, userId);
    }

    private RoutePoint deserializePoint(JsonNode pointNode){
        double lat = pointNode.get(CORD_LAT).asDouble();
        double lon = pointNode.get(CORD_LON).asDouble();
        double ele = pointNode.get(CORD_ELE).asDouble();
        long unixTimestamp = pointNode.get(TIMESTAMP).asLong();
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(unixTimestamp));
        return new RoutePoint(timestamp, lon, lat, ele);
    }
}
