package com.kubiczek.bookit;

import com.kubiczek.bookit.model.BookingRequest;

import java.util.Comparator;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookingRequestComparator implements Comparator<BookingRequest> {
    @Override
    public int compare(BookingRequest o1, BookingRequest o2) {
        if (o1.getMeetingEndTime().isBefore(o2.getMeetingStartTime())
                || o1.getMeetingEndTime().isEqual(o2.getMeetingStartTime())) {
            return -1;
        }
        if (o2.getMeetingEndTime().isBefore(o1.getMeetingStartTime()) ||
                o2.getMeetingEndTime().isEqual(o1.getMeetingStartTime())) {
            return 1;
        }
        return 0;
    }
}
