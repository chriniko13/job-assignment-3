package com.yoti.assignment.sdkbackendtest.mapper.control;

import com.yoti.assignment.sdkbackendtest.domain.DirectionCommand;
import com.yoti.assignment.sdkbackendtest.domain.Environment;
import com.yoti.assignment.sdkbackendtest.domain.Hoover;
import com.yoti.assignment.sdkbackendtest.domain.Room;
import com.yoti.assignment.sdkbackendtest.dto.CleanRoomCommand;
import com.yoti.assignment.sdkbackendtest.dto.HooverCoords;
import com.yoti.assignment.sdkbackendtest.dto.RoomSize;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnvironmentMapper implements Mapper<CleanRoomCommand, Environment> {

    @Override
    public Environment apply(CleanRoomCommand command) {

        RoomSize roomSize = command.getRoomSize();

        HooverCoords hooverCoords = command.getCoords();

        List<Pair<Integer, Integer>> dirtPatches = command
                .getPatches()
                .stream()
                .map(patch -> Pair.with(patch.getX(), patch.getY()))
                .collect(Collectors.toList());

        List<DirectionCommand> directionCommands = command
                .getInstructions()
                .chars()
                .mapToObj(instruction -> DirectionCommand.from((char) instruction))
                .collect(Collectors.toList());

        Room room = new Room(roomSize.getXMax(), roomSize.getYMax(), dirtPatches);
        Hoover hoover = new Hoover(hooverCoords.getX(), hooverCoords.getY(), directionCommands, room);

        return new Environment(room, hoover);
    }

    @Override
    public boolean canApply(Class<?> source, Class<?> destination) {
        return CleanRoomCommand.class.equals(source)
                && Environment.class.equals(destination);
    }
}
