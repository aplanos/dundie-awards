package com.ninjaone.dundieawards.organization.domain.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Map;

public class EventDeserializer<T> extends JsonDeserializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Integer, Class<? extends T>> versionMapping;

    public EventDeserializer(Map<Integer, Class<? extends T>> versionMapping) {
        this.versionMapping = versionMapping;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {

        final ObjectNode root = jsonParser.readValueAsTree();
        int version = root.has("version") ? root.get("version").asInt() : -1;

        Class<? extends T> eventClass = versionMapping.get(version);

        if (eventClass != null) {
            return objectMapper.treeToValue(root, eventClass);
        }

        throw new IllegalArgumentException("Unknown event version: " + version);
    }
}
