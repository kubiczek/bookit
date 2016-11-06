package com.kubiczek.bookit;

import com.kubiczek.bookit.model.BookingRequest;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookitCalendarTest {
    BookitCalendar calendar;

    @Before
    public void setup() {
        this.calendar = new BookitCalendar(LocalTime.of(9, 0), LocalTime.of(17, 30));
    }

    @Test
    public void shouldAddOneBookingToTheCalendar() {
        // given
        BookingRequest booking = new BookingRequest(LocalDateTime.of(2016, 11, 5, 8, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 15, 30, 0), Duration.ofHours(2));
        // when
        calendar.add(booking);
        // then
        assertThat(calendar.get(LocalDate.of(2016, 11, 6))).hasSize(1);
        assertThat(calendar.get(LocalDate.of(2016, 11, 6))).contains(booking);
    }

    @Test
    public void shouldThrowException_whenMeetingsOverlap() {
        // given
        BookingRequest booking = new BookingRequest(LocalDateTime.of(2016, 11, 5, 8, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 13, 0, 0), Duration.ofHours(2));
        BookingRequest overlapBooking = new BookingRequest(LocalDateTime.of(2016, 11, 5, 8, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 14, 0, 0), Duration.ofHours(2));
        // when
        calendar.add(booking);
        Throwable thrown = catchThrowable(() ->
                calendar.add(overlapBooking)
        );
        // then
        assertThat(calendar.get(LocalDate.of(2016, 11, 6))).hasSize(1);
        assertThat(calendar.get(LocalDate.of(2016, 11, 6))).contains(booking);
        assertThat(thrown).isInstanceOf(BookitException.class);
        assertThat(thrown).hasMessageStartingWith("Booking conflict");
    }

    @Test
    public void shouldThrowException_whenMeetingFallsOutsideOfTheOfficeHours() {
        // given
        BookingRequest booking = new BookingRequest(LocalDateTime.of(2016, 11, 5, 8, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 15, 45, 0), Duration.ofHours(2));
        // when
        Throwable thrown = catchThrowable(() ->
                calendar.add(booking)
        );
        // then
        assertThat(thrown).isInstanceOf(BookitException.class);
        assertThat(thrown).hasMessageStartingWith("Booking request falls outside of the office hours");
    }

    @Test
    public void shouldThrowException_whenOfficeHoursStartIsAfterEnd() {
        // when
        Throwable thrown = catchThrowable(() ->
                new BookitCalendar(LocalTime.of(9, 0), LocalTime.of(8, 0))
        );
        // then
        assertThat(thrown).isInstanceOf(AssertionError.class);
    }
}
