package ru.javawebinar.topjava.repository;

import java.util.Collection;
import ru.javawebinar.topjava.model.Meal;

public interface MealRepository {

    Meal save(Meal meal);

    Meal get(int userId, int id);

    Collection<Meal> getAll(int userId);

    boolean delete(int userId, int id);
}
