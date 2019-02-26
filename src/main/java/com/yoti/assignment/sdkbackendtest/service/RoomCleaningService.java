package com.yoti.assignment.sdkbackendtest.service;

import com.yoti.assignment.sdkbackendtest.domain.Environment;
import com.yoti.assignment.sdkbackendtest.domain.Hoover;
import com.yoti.assignment.sdkbackendtest.dto.CleanRoomCommand;
import com.yoti.assignment.sdkbackendtest.dto.CleanRoomResult;
import com.yoti.assignment.sdkbackendtest.dto.HooverCoords;
import com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationInput;
import com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationOutput;
import com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationResult;
import com.yoti.assignment.sdkbackendtest.error.RoomCleaningException;
import com.yoti.assignment.sdkbackendtest.mapper.boundary.MapperHandler;
import com.yoti.assignment.sdkbackendtest.repository.CleanRoomOperationResultRepository;
import lombok.extern.log4j.Log4j2;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Log4j2
@Service
public class RoomCleaningService {

    private final CleanRoomOperationResultRepository cleanRoomOperationResultRepository;
    private final MapperHandler mapperHandler;

    @Value("${print.cleaning.state}")
    private boolean printCleaningState;

    @Autowired
    public RoomCleaningService(CleanRoomOperationResultRepository cleanRoomOperationResultRepository,
                               MapperHandler mapperHandler) {

        this.cleanRoomOperationResultRepository = cleanRoomOperationResultRepository;
        this.mapperHandler = mapperHandler;
    }

    public Mono<CleanRoomResult> process(CleanRoomCommand command) {
        return execute(command) // Note: this is influenced by subscribeOn
                .map(cleanRoomResult -> Pair.with(command, cleanRoomResult))
                .publishOn(Schedulers.parallel())
                // Note: the rest is influenced by publishOn
                .flatMap(this::save);
    }

    private Mono<CleanRoomResult> execute(CleanRoomCommand command) {
        return Mono.create(sink -> {
            try {
                log.debug("execute method called!");

                Environment environment = mapperHandler
                        .getMapping(CleanRoomCommand.class, Environment.class)
                        .apply(command);

                Hoover hoover = environment.getHoover();
                hoover.clean(printCleaningState);

                HooverCoords currentCoords = new HooverCoords(hoover.getCurrentX(), hoover.getCurrentY());
                CleanRoomResult result = new CleanRoomResult(currentCoords, hoover.getCleanedDirtyPatchesCounter());

                sink.success(result);
            } catch (Exception error) {
                final String msg = "execute operation failed";
                log.error(msg, error);
                sink.error(new RoomCleaningException(msg, error));
            }
        });
    }

    private Mono<CleanRoomResult> save(Pair<CleanRoomCommand, CleanRoomResult> info) {
        return Mono.create(sink -> {
            try {
                log.debug("save method called!");

                CleanRoomOperationResult result = new CleanRoomOperationResult();

                CleanRoomOperationInput input = mapperHandler
                        .getMapping(CleanRoomCommand.class, CleanRoomOperationInput.class)
                        .apply(info.getValue0());

                CleanRoomOperationOutput output = mapperHandler
                        .getMapping(CleanRoomResult.class, CleanRoomOperationOutput.class)
                        .apply(info.getValue1());

                result.setInput(input);
                result.setOutput(output);

                cleanRoomOperationResultRepository
                        .save(result)
                        .subscribe(record -> sink.success(info.getValue1()));
            } catch (Exception error) {
                final String msg = "save operation failed";
                log.error(msg, error);
                sink.error(new RoomCleaningException(msg, error));
            }
        });
    }

}
