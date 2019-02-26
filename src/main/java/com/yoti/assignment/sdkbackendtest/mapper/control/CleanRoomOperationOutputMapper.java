package com.yoti.assignment.sdkbackendtest.mapper.control;

import com.yoti.assignment.sdkbackendtest.dto.CleanRoomResult;
import com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationOutput;
import org.springframework.stereotype.Component;

@Component
public class CleanRoomOperationOutputMapper implements Mapper<CleanRoomResult, CleanRoomOperationOutput> {

    @Override
    public CleanRoomOperationOutput apply(CleanRoomResult source) {
        final CleanRoomOperationOutput destination = new CleanRoomOperationOutput();

        destination.setCoords(new int[]{source.getCoords().getX(), source.getCoords().getY()});
        destination.setPatches(source.getCleanedPatches());

        return destination;
    }

    @Override
    public boolean canApply(Class<?> source, Class<?> destination) {
        return CleanRoomResult.class.equals(source)
                && CleanRoomOperationOutput.class.equals(destination);
    }
}
