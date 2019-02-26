package com.yoti.assignment.sdkbackendtest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CleanRoomResult {
    private HooverCoords coords;

    @JsonProperty("patches")
    private int cleanedPatches;
}
