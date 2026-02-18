package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import ru.javawebinar.topjava.model.Meal;

public class DateTimeUtil {
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return !lt.isBefore(startTime) && lt.isBefore(endTime);
    }
    
    public static boolean isWithinDateRange(Meal meal, LocalDate startDate, LocalDate endDate) {
        return (startDate == null || !meal.getDate().isBefore(startDate)) &&
                (endDate == null || !meal.getDate().isAfter(endDate));
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
