package ru.javawebinar.topjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public class MealTestData {
    
    public static final int NOT_FOUND = -1;
    public static final List<Meal> userMeals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "User Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "User Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "User Ужин", 500),
            new Meal(LocalDateTime.of(
                    2020, Month.JANUARY, 31, 0, 0), "User Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "User Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "User Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "User Ужин", 410)
    );
    
    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "New", 500);
    }
    
    public static Meal getUpdated() {
        Meal updated = getNew();
        updated.setDateTime(LocalDateTime.of(2022, Month.FEBRUARY, 2, 2, 0));
        updated.setDescription("Updated");
        updated.setCalories(222);
        return updated;
    }
    
    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
    
    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(expected);
    }
}