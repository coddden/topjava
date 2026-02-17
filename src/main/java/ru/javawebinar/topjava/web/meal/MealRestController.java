package ru.javawebinar.topjava.web.meal;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

@Controller
public class MealRestController {
    
    @Autowired
    private MealService service;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    
    public Meal create(Meal meal) {
        log.info("create {}", meal);
        meal.setUserId(authUserId());
        return service.create(meal);
    }
    
    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll(authUserId());
    }
    
    public List<MealTo> getAll(
            LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getAll filtered by date");
        return service.getAll(
                authUserId(), startDate, startTime, endDate, endTime);
    }
    
    public void update(Meal meal) {
        log.info("update {} with id={}", meal, meal.getId());
        service.update(meal);
    }
    
    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(authUserId(), id);
    }
}