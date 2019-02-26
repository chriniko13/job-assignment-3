package com.yoti.assignment.sdkbackendtest.dto;

import lombok.Data;

import java.util.List;

@Data
public class CleanRoomCommand {

    private RoomSize roomSize;
    private HooverCoords coords;
    private List<DirtPatch> patches;
    private String instructions;
}
