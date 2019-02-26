package com.yoti.assignment.sdkbackendtest.domain;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2

@Getter
public class Hoover {

    private static final String HOOVER_LEGEND = "H";
    private static final String DIRT_PATCH_LEGEND = "!";
    private static final String CLEAN_LEGEND = ".";
    private static final String SPACER = "  ";

    private int currentX;
    private int currentY;

    private final Queue<DirectionCommand> directionCommands;

    private int cleanedDirtyPatchesCounter;

    private final Room room;

    public Hoover(int currentX, int currentY, List<DirectionCommand> directionCommands, Room room) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.directionCommands = new LinkedList<>(directionCommands);
        this.cleanedDirtyPatchesCounter = 0;
        this.room = room;
    }

    public void clean(boolean print) {

        if (print) {
            printCurrentStateOfRoom();
        }
        // Note: in case of hoover is deployed on dirty patch.
        cleanDirtyPatchIfExists();

        DirectionCommand cmd;

        while ((cmd = getDirectionCommands().poll()) != null) {
            navigate(cmd);
            cleanDirtyPatchIfExists();
            if (print) {
                printCurrentStateOfRoom();
            }
        }
    }

    private void cleanDirtyPatchIfExists() {
        Optional.of(room.getLocations()[currentX][currentY])
                .filter(currentLocation -> currentLocation.getState() == LocationState.DIRTY)
                .ifPresent(currentLocation -> {
                    currentLocation.setState(LocationState.CLEAN);
                    cleanedDirtyPatchesCounter++;
                });
    }

    private void navigate(DirectionCommand directionCommand) {
        switch (directionCommand) {
            case NORTH:
                if (currentY < room.getYMax() - 1) {
                    currentY++;
                }
                break;

            case WEST:
                if (currentX > 0) {
                    currentX--;
                }
                break;

            case EAST:
                if (currentX < room.getXMax() - 1) {
                    currentX++;
                }
                break;

            case SOUTH:
                if (currentY > 0) {
                    currentY--;
                }
                break;
        }
    }

    private void printCurrentStateOfRoom() {
        Location[][] locations = room.getLocations();

        StringBuilder stateSnapshot = new StringBuilder();

        stateSnapshot
                .append("\n")
                .append("Pending direction commands: ")
                .append(this.directionCommands
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","))
                )
                .append("\n")
                .append("Next which will be executed: ")
                .append(this.directionCommands
                        .stream()
                        .findFirst()
                        .map(Enum::name)
                        .orElse("---EMPTY---")
                )
                .append("\n")
                .append("\n");

        Deque<StringBuilder> stack = new ArrayDeque<>();

        for (int y = 0; y < room.getYMax(); y++) {
            StringBuilder sb = new StringBuilder();

            for (int x = 0; x < room.getXMax(); x++) {

                if (currentX == x && currentY == y) {
                    sb.append(HOOVER_LEGEND).append(SPACER);
                } else {
                    if (locations[x][y].getState() == LocationState.DIRTY) {
                        sb.append(DIRT_PATCH_LEGEND).append(SPACER);
                    } else {
                        sb.append(CLEAN_LEGEND).append(SPACER);
                    }
                }

            }
            sb.append("\n");
            stack.push(sb);
        }

        while (!stack.isEmpty()) {
            stateSnapshot.append(stack.pop());
        }

        log.debug(stateSnapshot.toString());
    }
}
