package com.kubiczek.bookit;

import com.kubiczek.bookit.model.BookingBatch;
import com.kubiczek.bookit.model.BookingRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookingBatchFactory {
    public static BookingBatch newInstance(Reader reader) {
        BufferedReader in = new BufferedReader(reader);
        try {
            String[] officeHoursTokens = in.readLine().split(" ");
            LocalTime officeHoursStart = LocalTime.of(
                    Integer.parseInt(officeHoursTokens[0].substring(0, 2)),
                    Integer.parseInt(officeHoursTokens[0].substring(2)));
            LocalTime officeHoursEnd = LocalTime.of(
                    Integer.parseInt(officeHoursTokens[1].substring(0, 2)),
                    Integer.parseInt(officeHoursTokens[1].substring(2)));

            List<BookingRequest> bookings = new LinkedList<>();
            DateTimeFormatter submissionTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter meetingStartTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String line;
            while ((line = in.readLine()) != null) {
                String[] bookingRequestTokens = line.split(" ");
                LocalDateTime submissionTime = LocalDateTime.parse(
                        bookingRequestTokens[0] + " " + bookingRequestTokens[1], submissionTimeFormatter);
                String employeeId = bookingRequestTokens[2];
                LocalDateTime meetingStartTime = LocalDateTime.parse(
                        bookingRequestTokens[3] + " " + bookingRequestTokens[4], meetingStartTimeFormatter);
                Duration meetingDuration = Duration.ofHours(
                        Integer.parseInt(bookingRequestTokens[5]));
                bookings.add(new BookingRequest(submissionTime, employeeId, meetingStartTime, meetingDuration));
            }
            return new BookingBatch(officeHoursStart, officeHoursEnd, bookings);
        } catch (IOException e) {
            throw new BookitException("IO exception", e);
        }
    }
}
