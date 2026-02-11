package ru.javawebinar.topjava.dao;

import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public interface Storage {
    
    void save(Meal meal);
    
    List<Meal> getAll();
    
    void delete(Integer id);
}