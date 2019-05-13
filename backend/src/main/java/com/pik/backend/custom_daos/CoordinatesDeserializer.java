package com.pik.backend.custom_daos;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CoordinatesDeserializer extends JsonDeserializer<Coordinates> {

    private static final String COORDINATES_NODE = "coordinates";
    @Override
    public Coordinates deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode coordinatesNode = rootNode.get(COORDINATES_NODE);
        return new Coordinates(coordinatesNode.get(0).asDouble(), coordinatesNode.get(1).asDouble());
    }
}
