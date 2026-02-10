package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

@FunctionalInterface
public interface MealFilterStrategy {
    
    boolean execute(Meal meal);
}