package ru.javawebinar.topjava.repository;

import java.time.LocalDate;
import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public interface MealRepository {

    Meal save(int userId, Meal meal);

    Meal get(int userId, int id);
    
    List<Meal> getAll(int userId);

    List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate);

    boolean delete(int userId, int id);
}
