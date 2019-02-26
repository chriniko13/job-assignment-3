package com.yoti.assignment.sdkbackendtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CleanRoomOperationInput implements Serializable {

    private int[] roomSize;

    private int[] coords;

    private List<int[]> patches;

    private String instructions;

}
