package com.yoti.assignment.sdkbackendtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CleanRoomOperationOutput implements Serializable {

    private int[] coords;

    private int patches;

}
