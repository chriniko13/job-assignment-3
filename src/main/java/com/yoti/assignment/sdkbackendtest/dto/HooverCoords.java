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
@JsonPropertyOrder({"x", "y"})

// Note: for deserialization
@JsonDeserialize(using = HooverCoords.HooverCoordsDeserialization.class)
public class HooverCoords {

    private int x;
    private int y;

    public static class HooverCoordsDeserialization extends JsonDeserializer<HooverCoords> {

        @Override
        public HooverCoords deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            parser.nextValue(); // [
            int x = parser.getIntValue();

            parser.nextValue();
            int y = parser.getIntValue();
            parser.nextValue(); // ]

            return new HooverCoords(x, y);
        }
    }
}
