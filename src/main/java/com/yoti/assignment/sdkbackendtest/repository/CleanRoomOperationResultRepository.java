package com.yoti.assignment.sdkbackendtest.repository;

import com.yoti.assignment.sdkbackendtest.entity.CleanRoomOperationResult;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanRoomOperationResultRepository extends ReactiveCrudRepository<CleanRoomOperationResult, String> {
}
