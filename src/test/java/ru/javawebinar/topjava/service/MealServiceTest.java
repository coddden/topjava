package ru.javawebinar.topjava.service;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    
    @Autowired
    private MealService service;
    private int userId = UserTestData.USER_ID;
    
    static {
        SLF4JBridgeHandler.install();
    }

    @Test
    public void get() {
        Meal created = service.create(MealTestData.getNew(), userId);
        Meal returned = service.get(created.getId(), userId);
        MealTestData.assertMatch(returned, created);
    }
    
    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, userId));
    }

    @Test
    public void delete() {
        Meal created = service.create(MealTestData.getNew(), userId);
        int id = created.getId();
        service.delete(id, userId);
        assertThrows(NotFoundException.class, () -> service.get(id, userId));
    }
    
    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.delete(MealTestData.NOT_FOUND, userId));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filtered = service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30),
                userId);
        List<Meal> meals = MealTestData.userMeals.stream()
                .filter(m -> Util.isBetweenHalfOpen(
                        m.getDate(),
                        LocalDate.of(2020, Month.JANUARY, 30),
                        LocalDate.of(2020, Month.JANUARY, 31)))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        MealTestData.assertMatch(filtered, meals);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(userId);
        List<Meal> meals = MealTestData.userMeals.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        MealTestData.assertMatch(all, meals);
    }

    @Test
    public void update() {
        Meal created = service.create(MealTestData.getNew(), userId);
        Meal updated = MealTestData.getUpdated();
        int id = created.getId();
        updated.setId(id);
        service.update(updated, userId);
        MealTestData.assertMatch(service.get(id, userId), updated);
    }

    @Test
    public void create() {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, userId);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, userId), newMeal);
    }
    
    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                        "User Завтрак", 500), userId));
    }
}
