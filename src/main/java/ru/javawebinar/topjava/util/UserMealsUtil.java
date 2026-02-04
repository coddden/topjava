package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

public class UserMealsUtil {
    
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                        "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(
                meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }
    
    public static List<UserMealWithExcess> filteredByCycles(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, DayState> daysStates = new HashMap<>();
        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            DayState state = daysStates.computeIfAbsent(date, d -> new DayState(caloriesPerDay));
            state.addCalories(meal.getCalories());
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsWithExcess.add(createUserMealWithExcess(meal, daysStates.get(date)));
            }
        }
        return mealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(Collector.of(
                        Accumulator::new,
                        (accumulator, meal) -> {
                            LocalDate date = meal.getDateTime().toLocalDate();
                            DayState state = accumulator.daysStates.computeIfAbsent(
                                    date, d -> new DayState(caloriesPerDay));
                            state.addCalories(meal.getCalories());
                            if (TimeUtil.isBetweenHalfOpen(
                                    meal.getDateTime().toLocalTime(), startTime, endTime)) {
                                accumulator.mealsWithExcess.add(createUserMealWithExcess(
                                        meal, accumulator.daysStates.get(date)));
                            }
                        },
                        (a1, a2) -> {
                            throw new UnsupportedOperationException();
                        },
                        accumulator -> accumulator.mealsWithExcess));
    }
    
    private static UserMealWithExcess createUserMealWithExcess(UserMeal meal, DayState state) {
        return new UserMealWithExcess(
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories(),
                state);
    }
}
