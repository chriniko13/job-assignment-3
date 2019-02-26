package com.yoti.assignment.sdkbackendtest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;

@Data
@AllArgsConstructor

// Note: for serialization
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder(value = {"xMax", "yMax"})

// Note: for deserialization
@JsonDeserialize(using = RoomSize.RoomSizeDeserializer.class)
public class RoomSize {

    private int xMax;

    private int yMax;


    public static class RoomSizeDeserializer extends JsonDeserializer<RoomSize> {
        @Override
        public RoomSize deserialize(JsonParser parser, DeserializationContext context) throws IOException {

            parser.nextValue(); // [
            int x = parser.getIntValue();

            parser.nextValue();
            int y = parser.getIntValue();
            parser.nextValue(); // ]

            return new RoomSize(x, y);
        }
    }
}
