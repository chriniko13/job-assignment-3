package com.yoti.assignment.sdkbackendtest.validator;

import com.yoti.assignment.sdkbackendtest.dto.CleanRoomCommand;
import com.yoti.assignment.sdkbackendtest.dto.DirtPatch;
import com.yoti.assignment.sdkbackendtest.dto.HooverCoords;
import com.yoti.assignment.sdkbackendtest.dto.RoomSize;
import com.yoti.assignment.sdkbackendtest.error.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.javatuples.Pair;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.paukov.combinatorics.CombinatoricsFactory.createPermutationWithRepetitionGenerator;
import static org.paukov.combinatorics.CombinatoricsFactory.createVector;

@Log4j2
@Component
public class CleanCommandValidator implements Validator<CleanRoomCommand> {

    private static final int MAX_COORD = 30;

    private final Pattern instructionsPattern;

    public CleanCommandValidator() {
        instructionsPattern = Pattern.compile("[NEWS]*");
    }

    @Override
    public void validate(CleanRoomCommand input) {

        log.debug("validate method executed");

        validateRoomSize(input);

        final Set<Pair<Integer, Integer>> validCoords = calculateValidCoords(input);

        validateHooverCoords(input, validCoords);

        validateDirtPatchCoords(input, validCoords);

        validateInstructions(input);
    }

    private void validateRoomSize(CleanRoomCommand input) {
        RoomSize roomSize = input.getRoomSize();
        int roomSizeXMax = roomSize.getXMax();
        int roomSizeYMax = roomSize.getYMax();

        if (roomSizeXMax <= 0 || roomSizeXMax > MAX_COORD) {
            throw new ValidationException("xMax room size provided is not valid");
        }

        if (roomSizeYMax <= 0 || roomSizeYMax > MAX_COORD) {
            throw new ValidationException("yMax room size provided is not valid");
        }
    }

    // Note: extract valid coords for our environment == room in order to proceed with other validations.
    private Set<Pair<Integer, Integer>> calculateValidCoords(CleanRoomCommand input) {
        int maxCoord = Math.max(input.getRoomSize().getXMax(), input.getRoomSize().getYMax());

        List<Integer> possibleSelectionsForCoord = IntStream
                .rangeClosed(0, maxCoord)
                .boxed()
                .collect(Collectors.toList());

        ICombinatoricsVector<Integer> vector = createVector(possibleSelectionsForCoord);

        Generator<Integer> generator = createPermutationWithRepetitionGenerator(vector, 2);

        final Set<Pair<Integer, Integer>> validCoords = new LinkedHashSet<>();

        for (ICombinatoricsVector<Integer> permutation : generator) {
            Pair<Integer, Integer> validCoord = Pair.with(permutation.getValue(0), permutation.getValue(1));
            validCoords.add(validCoord);
        }
        return validCoords;
    }

    private void validateHooverCoords(CleanRoomCommand input, Set<Pair<Integer, Integer>> validCoords) {
        HooverCoords hooverCoords = input.getCoords();
        if (!validCoords.contains(Pair.with(hooverCoords.getX(), hooverCoords.getY()))) {
            throw new ValidationException("hoover coords provided are not valid");
        }
    }

    private void validateDirtPatchCoords(CleanRoomCommand input, Set<Pair<Integer, Integer>> validCoords) {
        for (DirtPatch dirtPatch : input.getPatches()) {
            if (!validCoords.contains(Pair.with(dirtPatch.getX(), dirtPatch.getY()))) {
                throw new ValidationException("dirt patch: " + dirtPatch + " provided is not valid");
            }
        }
    }

    private void validateInstructions(CleanRoomCommand input) {
        final String instructionsErrorMsg = "instructions provided are not valid";
        String instructions = input.getInstructions();
        if (instructions == null || instructions.isEmpty()) {
            throw new ValidationException(instructionsErrorMsg);
        }

        if (!instructionsPattern.matcher(instructions).matches()) {
            throw new ValidationException(instructionsErrorMsg);
        }
    }

}
