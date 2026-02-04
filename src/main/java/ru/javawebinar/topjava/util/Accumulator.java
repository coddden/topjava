package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.javawebinar.topjava.model.UserMealWithExcess;

public class Accumulator {
    
    Map<LocalDate, DayState> daysStates = new HashMap<>();
    List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
}
