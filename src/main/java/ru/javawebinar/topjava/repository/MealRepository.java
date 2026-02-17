package ru.javawebinar.topjava.repository;

import java.time.LocalDate;
import java.util.Collection;
import ru.javawebinar.topjava.model.Meal;

public interface MealRepository {

    Meal save(Meal meal);

    Meal get(int userId, int id);
    
    Collection<Meal> getAll(int userId);

    Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate);

    boolean delete(int userId, int id);
}
