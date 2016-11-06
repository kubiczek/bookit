package com.kubiczek.bookit.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookingRequest {
    private final LocalDateTime submissionTime;
    private final String employeeId;
    private final LocalDateTime meetingStartTime;
    private final Duration meetingDuration;

    public BookingRequest(LocalDateTime submissionTime, String employeeId, LocalDateTime meetingStartTime, Duration meetingDuration) {
        this.submissionTime = submissionTime;
        this.employeeId = employeeId;
        this.meetingStartTime = meetingStartTime;
        this.meetingDuration = meetingDuration;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getMeetingStartTime() {
        return meetingStartTime;
    }

    public Duration getMeetingDuration() {
        return meetingDuration;
    }
}
