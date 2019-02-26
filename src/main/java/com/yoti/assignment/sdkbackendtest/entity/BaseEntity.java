package com.yoti.assignment.sdkbackendtest.entity;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.time.Clock;
import java.time.Instant;

@EqualsAndHashCode(of = {"id"})
public class BaseEntity implements Serializable {

    @Id
    protected String id;

    protected String createdBy = who();
    protected Instant createdDate = when();

    protected String updatedBy; // Note: use who from service layer.
    protected Instant updatedDate; // Note: use when from service layer.

    @Version
    protected Long version;

    // Note: soft deletion.
    protected Boolean delete = Boolean.FALSE;

    public static String who() {
        return ManagementFactory.getRuntimeMXBean().getName();
    }

    private static Clock clock = Clock.systemUTC();
    public static Instant when() {
        return Instant.now(clock);
    }
}
