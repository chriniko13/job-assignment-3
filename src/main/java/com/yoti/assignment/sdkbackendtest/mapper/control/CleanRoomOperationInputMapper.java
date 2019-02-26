package com.yoti.assignment.sdkbackendtest.mapper.control;

import com.yoti.assignment.sdkbackendtest.dto.CleanRoomCommand;
import com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationInput;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CleanRoomOperationInputMapper implements Mapper<CleanRoomCommand, CleanRoomOperationInput> {

    @Override
    public CleanRoomOperationInput apply(CleanRoomCommand source) {

        final CleanRoomOperationInput destination = new CleanRoomOperationInput();

        destination.setRoomSize(new int[]{source.getRoomSize().getXMax(), source.getRoomSize().getYMax()});
        destination.setCoords(new int[]{source.getCoords().getX(), source.getCoords().getY()});
        destination.setPatches(
                source.getPatches()
                        .stream()
                        .map(p -> new int[]{p.getX(), p.getY()})
                        .collect(Collectors.toList())
        );
        destination.setInstructions(source.getInstructions());

        return destination;
    }

    @Override
    public boolean canApply(Class<?> source, Class<?> destination) {
        return CleanRoomCommand.class.equals(source)
                && CleanRoomOperationInput.class.equals(destination);
    }
}
