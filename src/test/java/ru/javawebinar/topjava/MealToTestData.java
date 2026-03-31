package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;

public class MealToTestData {

    public static final MatcherFactory.Matcher<MealTo> MEALTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);

    public static final String START_DATE_TIME = "2020-01-31T09:00:00";
    public static final String END_DATE_TIME = "2020-01-31T14:00:00";

    public static final MealTo mealTo1 = new MealTo(MEAL1_ID, of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false);
    public static final MealTo mealTo2 = new MealTo(MEAL1_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, false);
    public static final MealTo mealTo3 = new MealTo(MEAL1_ID + 2, of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, false);
    public static final MealTo mealTo4 = new MealTo(MEAL1_ID + 3, of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, true);
    public static final MealTo mealTo5 = new MealTo(MEAL1_ID + 4, of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500, true);
    public static final MealTo mealTo6 = new MealTo(MEAL1_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000, true);
    public static final MealTo mealTo7 = new MealTo(MEAL1_ID + 6, of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510, true);

    public static final List<MealTo> mealTos = List.of(mealTo7, mealTo6, mealTo5, mealTo4, mealTo3, mealTo2, mealTo1);
    public static final List<MealTo> filteredMeals = List.of(mealTo6, mealTo5);
}