package com.example.casehandlingsystem.helpers;

import com.example.casehandlingsystem.exceptions.ApiError;
import org.springframework.http.HttpStatus;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class TimeParser {
    private final boolean now;
    private LocalDateTime time = null;

    public TimeParser(String year, String month, String day, String hour, String minute, String second, String nanosecond) {
        now = year == null
                && month == null
                && day == null
                && hour == null
                && minute == null
                && second == null
                && nanosecond == null;
        if (!now) {
            try {
                time = LocalDateTime.of(
                        Integer.parseInt(year),
                        Integer.parseInt(month),
                        Integer.parseInt(day),
                        parseTimeParam(hour),
                        parseTimeParam(minute),
                        parseTimeParam(second),
                        parseTimeParam(nanosecond)
                );
                if (time.isAfter(LocalDateTime.now()))
                    throw new ApiError(HttpStatus.BAD_REQUEST.value(), "Future time provided.");
            } catch (NumberFormatException | DateTimeException e) {
                throw new ApiError(HttpStatus.BAD_REQUEST.value(), "Invalid date/time provided.");
            }

        }
    }

    public boolean getNow() {
        return now;
    }

    public LocalDateTime getTime() {
        return time;
    }

    private Integer parseTimeParam(String param) throws NumberFormatException {
        if (param == null)
            return 0;
        else
            return Integer.parseInt(param);
    }

}