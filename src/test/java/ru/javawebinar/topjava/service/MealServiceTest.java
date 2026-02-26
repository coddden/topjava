package ru.javawebinar.topjava.service;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
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
import ru.javawebinar.topjava.util.exception.NotFoundException;

@ContextConfiguration({
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-web.xml",
        "classpath:spring/spring-repository-jdbc.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    
    @Autowired
    private MealService service;
    
    static {
        SLF4JBridgeHandler.install();
    }

    @Test
    public void get() {
        Meal returned = service.get(MealTestData.MEAL_ID, UserTestData.USER_ID);
        MealTestData.assertMatch(returned, MealTestData.sameMealWithId);
    }
    
    public void getOtherUserMeal() {
        assertThrows(NotFoundException.class,
                () -> service.get(MealTestData.ADMIM_MEAL_ID, UserTestData.USER_ID));
    }
    
    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.get(MealTestData.NOT_FOUND_MEAL_ID, UserTestData.USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MealTestData.MEAL_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.MEAL_ID, UserTestData.USER_ID));
    }
    
    public void deleteOtherUserMeal() {
        assertThrows(NotFoundException.class,
                () -> service.delete(MealTestData.ADMIM_MEAL_ID, UserTestData.USER_ID));
    }
    
    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.delete(MealTestData.NOT_FOUND_MEAL_ID, UserTestData.USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filtered = service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30),
                UserTestData.USER_ID);
        MealTestData.assertMatch(filtered, MealTestData.userMealsBetweenInclusive);
    }

    @Test
    public void getAll() {
        List<Meal> returned = service.getAll(UserTestData.USER_ID);
        MealTestData.assertMatch(returned, MealTestData.userMeals);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, UserTestData.USER_ID);
        MealTestData.assertMatch(
                service.get(MealTestData.MEAL_ID, UserTestData.USER_ID),
                MealTestData.getUpdated());
    }
    
    @Test
    public void updateOtherUserMeal() {
        Meal updated = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, UserTestData.ADMIN_ID));
    }

    @Test
    public void create() {
        Meal meal = MealTestData.getNew();
        Meal created = service.create(meal, UserTestData.USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }
    
    @Test
    public void duplicateDateTimeCreate() {
        Meal existingMeal = MealTestData.userMeals.get(0);
        Meal mealWithSameDate = new Meal(existingMeal.getDateTime(), "User Завтрак", 500);
        assertThrows(DataAccessException.class, () -> service.create(mealWithSameDate, UserTestData.USER_ID));
    }
}
