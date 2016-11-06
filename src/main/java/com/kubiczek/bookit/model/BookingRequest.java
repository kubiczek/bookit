package com.kubiczek.bookit.model;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookingRequest {
    private final LocalDateTime submissionTime;
    private final String employeeId;
    private final LocalDateTime meetingStartTime;
    private final LocalDateTime meetingEndTime;
    private final Duration meetingDuration;

    public BookingRequest(LocalDateTime submissionTime, String employeeId, LocalDateTime meetingStartTime, Duration meetingDuration) {
        this.submissionTime = submissionTime;
        this.employeeId = employeeId;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingStartTime.plus(meetingDuration);
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

    public LocalDateTime getMeetingEndTime() {
        return meetingEndTime;
    }

    public Duration getMeetingDuration() {
        return meetingDuration;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "submissionTime=" + submissionTime +
                ", employeeId='" + employeeId + '\'' +
                ", meetingStartTime=" + meetingStartTime +
                ", meetingEndTime=" + meetingEndTime +
                ", meetingDuration=" + meetingDuration +
                '}';
    }
}
