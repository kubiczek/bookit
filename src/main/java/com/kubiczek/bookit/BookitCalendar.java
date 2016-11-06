package com.kubiczek.bookit;

import com.kubiczek.bookit.model.BookingRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookitCalendar {
    private final LocalTime officeHoursStart, officeHoursEnd;
    private Map<LocalDate, Set<BookingRequest>> calendar = new HashMap<>();

    public BookitCalendar(LocalTime officeHoursStart, LocalTime officeHoursEnd) {
        this.officeHoursStart = officeHoursStart;
        this.officeHoursEnd = officeHoursEnd;
    }

    public void add(BookingRequest request) {
        if (!isWithinOfficeHours(request)) {
            throw new BookitException("Booking request falls outside of the office hours: " + request);
        }
        LocalDate meetingDate = request.getMeetingStartTime().toLocalDate();
        if (!calendar.containsKey(meetingDate)) {
            calendar.put(meetingDate, new TreeSet<>(new BookingRequestComparator()));
        }
        boolean result = calendar.get(meetingDate).add(request);
        if (!result) {
            throw new BookitException("Booking conflict: " + request);
        }
    }

    private boolean isWithinOfficeHours(BookingRequest request) {
        return !request.getMeetingStartTime().toLocalTime().isBefore(officeHoursStart) &&
                !request.getMeetingEndTime().toLocalTime().isAfter(officeHoursEnd);

    }

    public Set<BookingRequest> get(LocalDate day) {
        return calendar.get(day);
    }

    public LocalTime getOfficeHoursStart() {
        return officeHoursStart;
    }

    public LocalTime getOfficeHoursEnd() {
        return officeHoursEnd;
    }
}
