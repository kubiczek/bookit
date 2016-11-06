package com.kubiczek.bookit;

import com.kubiczek.bookit.model.BookingBatch;
import com.kubiczek.bookit.model.BookingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookitCalendar {
    private static final Logger log = LoggerFactory.getLogger(BookitCalendar.class);

    private final LocalTime officeHoursStart, officeHoursEnd;
    private Map<LocalDate, Set<BookingRequest>> calendar = new HashMap<>();

    public BookitCalendar(LocalTime officeHoursStart, LocalTime officeHoursEnd) {
        assert officeHoursStart.isBefore(officeHoursEnd);
        this.officeHoursStart = officeHoursStart;
        this.officeHoursEnd = officeHoursEnd;
    }

    public BookitCalendar(BookingBatch batch) {
        this(batch.getOfficeHoursStart(), batch.getOfficeHoursEnd());
        List<BookingRequest> bookings = batch.getBookings();
        Collections.sort(bookings,
                (o1, o2) -> o1.getSubmissionTime().compareTo(o2.getSubmissionTime()));
        bookings.forEach(request -> {
            try {
                this.add(request);
            } catch (BookitException e) {
                log.warn("Request ignored: {}", e.getMessage());
            }
        });

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

    @Override
    public String toString() {
        List<LocalDate> days = calendar.keySet().stream().collect(toList());
        Collections.sort(days);

        return days.stream().map(day -> {
                    StringBuffer result = new StringBuffer();
                    result.append(day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .append('\n')
                            .append(calendar.get(day).stream()
                                    .map(request -> "" +
                                            request.getMeetingStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " " +
                                            request.getMeetingEndTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " " +
                                            request.getEmployeeId()
                                    )
                                    .collect(joining("\n")));
                    return result.toString();
                }
        ).collect(joining("\n"));
    }
}
