package com.yoti.assignment.sdkbackendtest.domain;

import lombok.Getter;
import org.javatuples.Pair;

import java.util.List;

@Getter
public class Room {

    private final int xMax;
    private final int yMax;

    // Note: bottom left corner is defined by X: 0 and Y: 0
    private final Location[][] locations;

    public Room(int xMax, int yMax,
                List<Pair<Integer, Integer>> dirtPatches) {
        this.xMax = xMax;
        this.yMax = yMax;

        this.locations = new Location[xMax][yMax];

        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {

                Pair<Integer, Integer> locationCoordinates = Pair.with(x, y);
                boolean isDirty = dirtPatches.contains(locationCoordinates);

                this.locations[x][y] = new Location(x, y, isDirty ? LocationState.DIRTY : LocationState.CLEAN);
            }
        }
    }

}
