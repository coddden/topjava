package ru.javawebinar.topjava.storage;

import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public interface MealStorage {
    
    Meal save(Meal meal);
    
    Meal get(int id);
    
    List<Meal> getAll();
    
    void delete(int id);
}