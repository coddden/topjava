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
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    
    @Autowired
    private MealService service;
    private int userId = UserTestData.USER_ID;
    private int adminId = UserTestData.ADMIN_ID;
    private int mealId = MealTestData.MEAL_ID;
    private int notFoundMealId = MealTestData.NOT_FOUND_MEAL_ID;
    private int adminMealId = MealTestData.ADMIM_MEAL_ID;
    private Meal newMeal = MealTestData.getNew();
    private Meal sameMealWithId = MealTestData.sameMealWithId;
    private List<Meal> userMeals = MealTestData.userMeals;
    private List<Meal> userMealsBetweenInclusive = MealTestData.userMealsBetweenInclusive;
    
    static {
        SLF4JBridgeHandler.install();
    }

    @Test
    public void get() {
        Meal returned = service.get(mealId, userId);
        MealTestData.assertMatch(returned, sameMealWithId);
    }
    
    public void getOtherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(adminMealId, userId));
    }
    
    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(notFoundMealId, userId));
    }

    @Test
    public void delete() {
        service.delete(mealId, userId);
        assertThrows(NotFoundException.class, () -> service.get(mealId, userId));
    }
    
    public void deleteOtherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(adminMealId, userId));
    }
    
    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(notFoundMealId, userId));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filtered = service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30), LocalDate.of(2020, Month.JANUARY, 30), userId);
        MealTestData.assertMatch(filtered, userMealsBetweenInclusive);
    }

    @Test
    public void getAll() {
        List<Meal> returned = service.getAll(userId);
        MealTestData.assertMatch(returned, userMeals);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, userId);
        MealTestData.assertMatch(
                service.get(mealId, userId),
                MealTestData.getUpdated());
    }
    
    @Test
    public void updateOtherUserMeal() {
        Meal updated = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, adminId));
    }

    @Test
    public void create() {
        Meal created = service.create(newMeal, userId);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MealTestData.assertMatch(service.get(newId, userId), newMeal);
    }
    
    @Test
    public void duplicateDateTimeCreate() {
        Meal existingMeal = MealTestData.userMeals.get(0);
        Meal mealWithSameDate = new Meal(existingMeal.getDateTime(), "User Завтрак", 500);
        assertThrows(DataAccessException.class, () -> service.create(mealWithSameDate, userId));
    }
}
