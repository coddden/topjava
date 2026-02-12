package ru.javawebinar.topjava.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import ru.javawebinar.topjava.model.Meal;

public class InMemoryMealStorage implements MealStorage {
    
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    
    @Override
    public Meal save(Meal meal) {
        Integer id = meal.getId();
        if (id == null) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        if (id != null && meals.containsKey(id)) {
            meals.put(id, meal);
            return meal;
        }
        return null;
    }
    
    @Override
    public Meal get(int id) {
        return meals.get(id);
    }
    
    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }
}