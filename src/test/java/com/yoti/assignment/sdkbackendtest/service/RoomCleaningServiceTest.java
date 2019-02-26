package com.yoti.assignment.sdkbackendtest.service;

import com.yoti.assignment.sdkbackendtest.domain.Environment;
import com.yoti.assignment.sdkbackendtest.domain.Hoover;
import com.yoti.assignment.sdkbackendtest.domain.Room;
import com.yoti.assignment.sdkbackendtest.dto.*;
import com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationInput;
import com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationOutput;
import com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationResult;
import com.yoti.assignment.sdkbackendtest.mapper.boundary.MapperHandler;
import com.yoti.assignment.sdkbackendtest.mapper.control.Mapper;
import com.yoti.assignment.sdkbackendtest.repository.CleanRoomOperationResultRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class RoomCleaningServiceTest {

    private RoomCleaningService roomCleaningService;

    @Mock
    private MapperHandler mapperHandler;

    @Mock
    private CleanRoomOperationResultRepository cleanRoomOperationResultRepository;

    @Mock
    private Mapper<CleanRoomCommand, Environment> toEnvironmentMapper;

    @Mock
    private Mapper<CleanRoomCommand, CleanRoomOperationInput> toOperationInputMapper;

    @Mock
    private Mapper<CleanRoomResult, CleanRoomOperationOutput> toOperationOutputMapper;

    @Mock
    private Room room;

    @Mock
    private Hoover hoover;

    @Mock
    private CleanRoomOperationResult cleanRoomOperationResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        roomCleaningService = new RoomCleaningService(
                cleanRoomOperationResultRepository,
                mapperHandler
        );
    }

    @Test
    public void process_method_works_as_expected() {

        // given
        CleanRoomCommand command = new CleanRoomCommand();
        command.setRoomSize(new RoomSize(5, 5));
        command.setCoords(new HooverCoords(1, 2));
        command.setPatches(
                Arrays.asList(
                        new DirtPatch(1, 0),
                        new DirtPatch(2, 2),
                        new DirtPatch(2, 3)
                )
        );
        command.setInstructions("NNESEESWWSSWWE");

        CleanRoomResult cleanRoomResult = new CleanRoomResult(
                new HooverCoords(1, 0), 3
        );

        Mockito.when(mapperHandler.getMapping(CleanRoomCommand.class, Environment.class))
                .thenReturn(toEnvironmentMapper);

        Mockito.when(toEnvironmentMapper.apply(command))
                .thenReturn(new Environment(room, hoover));

        Mockito.when(hoover.getCurrentX()).thenReturn(1);
        Mockito.when(hoover.getCurrentY()).thenReturn(0);
        Mockito.when(hoover.getCleanedDirtyPatchesCounter()).thenReturn(3);

        Mockito.when(mapperHandler.getMapping(CleanRoomCommand.class, CleanRoomOperationInput.class))
                .thenReturn(toOperationInputMapper);

        Mockito.when(mapperHandler.getMapping(CleanRoomResult.class, CleanRoomOperationOutput.class))
                .thenReturn(toOperationOutputMapper);

        Mockito.when(cleanRoomOperationResultRepository.save(Mockito.any(CleanRoomOperationResult.class)))
                .thenReturn(Mono.just(cleanRoomOperationResult));

        // when
        Mono<CleanRoomResult> result = roomCleaningService.process(command);

        // then
        assertNotNull(result);

        StepVerifier
                .create(result)
                .expectNext(cleanRoomResult)
                .verifyComplete();

    }

}