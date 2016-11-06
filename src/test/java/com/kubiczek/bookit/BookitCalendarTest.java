package com.kubiczek.bookit;

import com.kubiczek.bookit.model.BookingBatch;
import com.kubiczek.bookit.model.BookingRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.net.URISyntaxException;
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
        assertThat(calendar.toString()).isEqualTo("" +
                "2016-11-06\n" +
                "15:30 17:30 EMP001");
    }

    @Test
    public void shouldCreateCalendar_whenBatchIsProvidedAsString() {
        // given
        BookingBatch batch = BookingBatchFactory.newInstance(new StringReader("" +
                "0900 1730" + '\n' +
                "2015-08-17 10:17:06 EMP001 2015-08-21 09:00 2" + '\n' +
                "2015-08-16 12:34:56 EMP002 2015-08-21 09:00 2" + '\n' +
                "2015-08-16 09:28:23 EMP003 2015-08-22 14:00 2" + '\n' +
                "2015-08-17 11:23:45 EMP004 2015-08-22 16:00 1" + '\n' +
                "2015-08-15 17:29:12 EMP005 2015-08-21 16:00 3"
        ));
        // when
        calendar = new BookitCalendar(batch);
        // then
        assertThat(calendar.toString()).isEqualTo("" +
                "2015-08-21\n" +
                "09:00 11:00 EMP002\n" +
                "2015-08-22\n" +
                "14:00 16:00 EMP003\n" +
                "16:00 17:00 EMP004");
    }

    @Test
    public void shouldCreateCalendar_whenBatchIsProvidedAsFile() throws FileNotFoundException, URISyntaxException {
        // given
        BookingBatch batch = BookingBatchFactory.newInstance(new FileReader(
               getClass().getClassLoader().getResource("booking-requests.bookit").getFile()));
        // when
        calendar = new BookitCalendar(batch);
        // then
        assertThat(calendar.toString()).isEqualTo("" +
                "2015-08-21\n" +
                "09:00 11:00 EMP002\n" +
                "2015-08-22\n" +
                "14:00 16:00 EMP003\n" +
                "16:00 17:00 EMP004");
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
