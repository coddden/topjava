package ru.javawebinar.topjava.repository.inmemory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

@Repository
public class InMemoryMealRepository implements MealRepository {
    
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, Map<Integer, Meal>> usersMealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal);
        Map<Integer, Meal> mealsMap = usersMealsMap.computeIfAbsent(
                meal.getUserId(), userId -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            return mealsMap.putIfAbsent(meal.getId(), meal) == null ? meal : null;
        }
        return mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {}", id);
        Map<Integer, Meal> mealsMap = usersMealsMap.get(userId);
        return mealsMap == null ? null : mealsMap.get(id);
    }
    
    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        Map<Integer, Meal> mealsMap = usersMealsMap.get(userId);
        return mealsMap == null ?
                Collections.emptyList() :
                mealsMap.values().stream()
                    .sorted(Comparator.comparing(Meal::getDate).reversed())
                    .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAll filtered by date");
        Map<Integer, Meal> mealsMap = usersMealsMap.get(userId);
        return mealsMap == null ?
                Collections.emptyList() :
                mealsMap.values().stream()
                    .filter(meal ->
                        !meal.getDate().isBefore(startDate) &&
                        !meal.getDate().isAfter(endDate))
                    .sorted(Comparator.comparing(Meal::getDate).reversed())
                    .collect(Collectors.toList());
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", id);
        Map<Integer, Meal> mealsMap = usersMealsMap.get(userId);
        return mealsMap != null && mealsMap.remove(id) != null;
    }
}