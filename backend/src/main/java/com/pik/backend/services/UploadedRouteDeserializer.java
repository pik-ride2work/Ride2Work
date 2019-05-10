package com.pik.backend.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;


public class UploadedRouteDeserializer extends JsonDeserializer<UploadedRoute> {
    private static final String ROUTE_NODE = "route";
    private static final String RESPONSE_NODE = "response";
    private static final String LEG_NODE = "leg";

    @Override

    public UploadedRoute deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode responseNode = rootNode.get(RESPONSE_NODE);
        Integer userId = rootNode.get("userId").asInt();
        //Parsing only the first route in the "route" array.
        JsonNode routeNode = responseNode.get(ROUTE_NODE).get(0);
        //Parsing only the first leg in the "leg" array.
        JsonNode legNode = routeNode.get(LEG_NODE).get(0);

        return null;
    }
}
