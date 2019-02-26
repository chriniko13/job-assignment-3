package com.yoti.assignment.sdkbackendtest.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

    private final int x;
    private final int y;
    private LocationState state;


    public Location(int x, int y, LocationState state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }
}
