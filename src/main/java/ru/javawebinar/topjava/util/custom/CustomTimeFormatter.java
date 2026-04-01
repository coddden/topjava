package ru.javawebinar.topjava.util.custom;

import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomTimeFormatter implements Formatter<LocalTime> {

    @Override
    public @NonNull LocalTime parse(@NonNull String text, @NonNull Locale locale) {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    @Override
    public @NonNull String print(@NonNull LocalTime localTime, @NonNull Locale locale) {
        return localTime.toString();
    }
}
