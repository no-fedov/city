package org.javaacademy.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateFormatter {
    private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static String formatDate(LocalDate date) {
        return date.format(PATTERN);
    }
}
