package com.yoti.assignment.sdkbackendtest.resource;

import com.yoti.assignment.sdkbackendtest.dto.CleanRoomCommand;
import com.yoti.assignment.sdkbackendtest.dto.CleanRoomResult;
import com.yoti.assignment.sdkbackendtest.error.ValidationException;
import com.yoti.assignment.sdkbackendtest.service.RoomCleaningService;
import com.yoti.assignment.sdkbackendtest.validator.Validator;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Log4j2

@RestController
@RequestMapping("/api/cleaning")
public class RoomCleaningResource {

    private final RoomCleaningService roomCleaningService;
    private final Validator<CleanRoomCommand> cleanRoomCommandValidator;

    @Autowired
    public RoomCleaningResource(RoomCleaningService roomCleaningService,
                                Validator<CleanRoomCommand> cleanRoomCommandValidator) {
        this.roomCleaningService = roomCleaningService;
        this.cleanRoomCommandValidator = cleanRoomCommandValidator;
    }

    @GetMapping(path = "/hello")
    public Mono<String> hello() {
        return Mono.just("hello");
    }

    @PostMapping(
            path = "/rooms",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public @ResponseBody
    Publisher<CleanRoomResult> createEntry(@RequestBody CleanRoomCommand cleanRoomCommand) {

        final Mono<CleanRoomCommand> validationMono = Mono.create(s -> {
            try {
                cleanRoomCommandValidator.validate(cleanRoomCommand);
                s.success(cleanRoomCommand);
            } catch (ValidationException e) {
                log.warn("validation error occurred during processing of clean room command");
                s.error(e);
            }
        });

        return validationMono
                .flatMap(roomCleaningService::process)
                .subscribeOn(Schedulers.elastic());
    }

}
