package ru.javawebinar.topjava.service;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

@Service
public class MealService {

    private MealRepository repository;
    
    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }
    
    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }
    
    public List<MealTo> getAll(int userId, int calories) {
        Collection<Meal> allMeals = repository.getAll(userId);
        if (!allMeals.isEmpty()) {
            return MealsUtil.getTos(allMeals, calories);
        }
        return Collections.emptyList();
    }
    
    public List<MealTo> getAllFiltered(int userId, int calories,
            LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        Collection<Meal> allMeals = repository.getAllFiltered(userId, startDate, endDate);
        if (!allMeals.isEmpty()) {
            return MealsUtil.getFilteredTos(allMeals, calories, startTime, endTime);
        }
        return Collections.emptyList();
    }
    
    public void update(int userId, Meal meal) {
        checkNotFound(repository.save(userId, meal), meal.getId());
    }
    
    public void delete(int userId, int id) {
        checkNotFound(repository.delete(userId, id), id);
    }
}