package com.yoti.assignment.sdkbackendtest.domain;

public enum DirectionCommand {
       NORTH,

    WEST,  EAST,

       SOUTH;

    public static DirectionCommand from(Character input) {
        switch (input) {
            case 'N':
                return NORTH;
            case 'W':
                return WEST;
            case 'E':
                return EAST;
            case 'S':
                return SOUTH;
            default:
                throw new IllegalStateException();
        }
    }
}
