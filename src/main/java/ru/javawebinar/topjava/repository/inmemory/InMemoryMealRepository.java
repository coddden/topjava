package ru.javawebinar.topjava.repository.inmemory;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

@Repository
public class InMemoryMealRepository implements MealRepository {
    
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> usersMealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", meal);
        Map<Integer, Meal> mealsMap = usersMealsMap.computeIfAbsent(
                userId, ui -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealsMap.put(meal.getId(), meal);
            return meal;
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
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId, null, null);
    }
    
    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllFiltered(userId, startDate, endDate);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", id);
        Map<Integer, Meal> mealsMap = usersMealsMap.get(userId);
        return mealsMap != null && mealsMap.remove(id) != null;
    }
    
    private List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAll filtered");
        Map<Integer, Meal> mealsMap = usersMealsMap.get(userId);
        return mealsMap.isEmpty() ?
                Collections.emptyList() :
                mealsMap.values().stream()
                    .filter(meal -> DateTimeUtil.isWithinDateRange(meal, startDate, endDate))
                    .sorted(Comparator.comparing(Meal::getDate).reversed())
                    .collect(Collectors.toList());
    }
}