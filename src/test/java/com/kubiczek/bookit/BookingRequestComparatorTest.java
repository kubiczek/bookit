package com.kubiczek.bookit;

import com.kubiczek.bookit.model.BookingRequest;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookingRequestComparatorTest {
    private BookingRequestComparator comparator;

    @Before
    public void setup() {
        this.comparator = new BookingRequestComparator();
    }

    @Test
    public void shouldReturnNegativeValue() {
        // given
        BookingRequest booking1 = new BookingRequest(LocalDateTime.of(2016, 11, 6, 8, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 13, 0, 0), Duration.ofHours(2));
        BookingRequest booking2 = new BookingRequest(LocalDateTime.of(2016, 11, 6, 9, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 15, 0, 0), Duration.ofHours(1));
        // when
        int result = comparator.compare(booking1, booking2);
        // then
        assertThat(result).isEqualTo(-1);
    }

    @Test
    public void shouldReturnPositiveValue() {
        // given
        BookingRequest booking1 = new BookingRequest(LocalDateTime.of(2016, 11, 6, 8, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 13, 0, 0), Duration.ofHours(2));
        BookingRequest booking2 = new BookingRequest(LocalDateTime.of(2016, 11, 6, 9, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 12, 0, 0), Duration.ofHours(1));
        // when
        int result = comparator.compare(booking1, booking2);
        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void shouldReturnZero() {
        // given
        BookingRequest booking1 = new BookingRequest(LocalDateTime.of(2016, 11, 6, 8, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 13, 0, 0), Duration.ofHours(2));
        BookingRequest booking2 = new BookingRequest(LocalDateTime.of(2016, 11, 6, 9, 0, 0),
                "EMP001", LocalDateTime.of(2016, 11, 6, 14, 0, 0), Duration.ofHours(3));
        // when
        int result = comparator.compare(booking1, booking2);
        // then
        assertThat(result).isEqualTo(0);
    }
}
