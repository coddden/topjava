package ru.javawebinar.topjava.util.custom;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CustomDateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<CustomDateTimeFormat> {

    @Override
    public @NonNull Set<Class<?>> getFieldTypes() {
        return new HashSet<>(List.of(LocalDate.class, LocalTime.class));
    }

    @Override
    public @NonNull Printer<?> getPrinter(@NonNull CustomDateTimeFormat annotation, @NonNull Class<?> fieldType) {
        return Objects.requireNonNull(getFormatter(annotation, fieldType));
    }

    @Override
    public @NonNull Parser<?> getParser(@NonNull CustomDateTimeFormat annotation, @NonNull Class<?> fieldType) {
        return Objects.requireNonNull(getFormatter(annotation, fieldType));
    }

    private Formatter<?> getFormatter(CustomDateTimeFormat annotation, Class<?> fieldType) {
        switch (annotation.type()) {
            case DATE -> {
                return new CustomDateFormatter();
            }
            case TIME -> {
                return new CustomTimeFormatter();
            }
        }
        return null;
    }
}
