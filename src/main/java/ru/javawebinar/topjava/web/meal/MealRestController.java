package ru.javawebinar.topjava.web.meal;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;
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
import ru.javawebinar.topjava.web.SecurityUtil;

@Controller
public class MealRestController {
    
    @Autowired
    private MealService service;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    
    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        return service.create(authUserId(), meal);
    }
    
    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll(authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }
    
    public List<MealTo> getAllFiltered(
            LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        if (startDate == null && startTime == null && endDate == null && endTime == null) {
            return getAll();
        }
        log.info("getAll filtered");
        return service.getAllFiltered(authUserId(), SecurityUtil.authUserCaloriesPerDay(),
                startDate, startTime, endDate, endTime);
    }
    
    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(authUserId(), meal);
    }
    
    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(authUserId(), id);
    }
}