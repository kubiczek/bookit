package com.kubiczek.bookit.model;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookingBatch {
    private final LocalTime officeHoursStart;
    private final LocalTime officeHoursEnd;
    private final List<BookingRequest> bookings;

    public BookingBatch(LocalTime officeHoursStart, LocalTime officeHoursEnd, List<BookingRequest> bookings) {
        this.officeHoursStart = officeHoursStart;
        this.officeHoursEnd = officeHoursEnd;
        this.bookings = bookings;
    }

    public LocalTime getOfficeHoursStart() {
        return officeHoursStart;
    }

    public LocalTime getOfficeHoursEnd() {
        return officeHoursEnd;
    }

    public List<BookingRequest> getBookings() {
        return bookings;
    }
}
