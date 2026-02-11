package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

public class MealsUtil {
    
    public static final int CALORIES_PER_DAY = 2000;

    public static List<MealTo> filteredByStreams(
            List<Meal> meals, int caloriesPerDay, MealFilterStrategy strategy) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        return meals.stream()
                .filter(meal -> strategy.execute(meal))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
    
    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(
                meal.getId(),
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories(),
                excess);
    }
}
