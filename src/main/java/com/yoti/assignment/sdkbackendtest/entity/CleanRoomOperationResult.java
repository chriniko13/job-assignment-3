package com.yoti.assignment.sdkbackendtest.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data

@Document(collection = "results")
public class CleanRoomOperationResult extends BaseEntity {

    private CleanRoomOperationInput input;
    private CleanRoomOperationOutput output;

}
