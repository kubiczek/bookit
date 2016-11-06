package com.kubiczek.bookit;

import com.kubiczek.bookit.model.BookingBatch;
import org.junit.Test;

import java.io.StringReader;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookingBatchFactoryTest {

    @Test
    public void shouldCreateBookingBatch() {
        // given
        String batchStr = "" +
                "0900 1730" + '\n' +
                "2015-08-17 10:17:06 EMP001 2015-08-21 09:00 2" + '\n' +
                "2015-08-16 12:34:56 EMP002 2015-08-21 09:00 2" + '\n' +
                "2015-08-16 09:28:23 EMP003 2015-08-22 14:00 2" + '\n' +
                "2015-08-17 11:23:45 EMP004 2015-08-22 16:00 1" + '\n' +
                "2015-08-15 17:29:12 EMP005 2015-08-21 16:00 3";
        // when
        BookingBatch batch = BookingBatchFactory.newInstance(new StringReader(batchStr));
        // then
        assertThat(batch.getOfficeHoursStart()).isEqualTo(LocalTime.of(9, 0));
        assertThat(batch.getOfficeHoursEnd()).isEqualTo(LocalTime.of(17, 30));
        assertThat(batch.getBookings()).hasSize(5);
    }
}
