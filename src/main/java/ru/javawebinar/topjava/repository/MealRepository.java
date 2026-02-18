package ru.javawebinar.topjava.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public interface MealRepository {

    Meal save(int userId, Meal meal);

    Meal get(int userId, int id);
    
    Collection<Meal> getAll(int userId);

    List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate);

    boolean delete(int userId, int id);
}
