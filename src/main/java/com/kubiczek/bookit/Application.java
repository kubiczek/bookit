package com.kubiczek.bookit;

import com.kubiczek.bookit.model.BookingBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;

/**
 * Created by mkubiczek on 11/6/2016.
 */
@SpringBootApplication
public class Application {
    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("Welcome to Bookit!");
            if (args.length != 2) {
                throw new IllegalArgumentException("Usage: Bookit <in> <out>");
            }

            File inFile = new File(args[0]);
            BookingBatch batch;
            try (Reader in = new FileReader(inFile)) {
                log.info("Reading a batch from the file: {}", inFile.getAbsolutePath());
                batch = BookingBatchFactory.newInstance(in);
            }
            log.info("Processing the batch of {} request(s)", batch.getBookings().size());
            BookitCalendar calendar = new BookitCalendar(batch);

            File outFile = new File(args[1]);
            try (Writer out = new FileWriter(outFile)) {
                out.write(calendar.toString());
            }
        };
    }
}
