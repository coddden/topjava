package ru.javawebinar.topjava.util.custom;

import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomDateFormatter implements Formatter<LocalDate> {

    @Override
    public @NonNull LocalDate parse(@NonNull String text, @NonNull Locale locale) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public @NonNull String print(LocalDate localDate, @NonNull Locale locale) {
        return localDate.toString();
    }
}
