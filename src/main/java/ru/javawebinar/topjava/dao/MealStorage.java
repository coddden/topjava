package ru.javawebinar.topjava.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import ru.javawebinar.topjava.model.Meal;

public class MealStorage implements Storage {
    
    protected Map<Integer, Meal> meals = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    
    @Override
    public void save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
        }
        meals.put(meal.getId(), meal);
    }
    
    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void delete(Integer id) {
        meals.remove(id);
    }
}