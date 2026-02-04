package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import ru.javawebinar.topjava.util.DayState;

public class UserMealWithExcess {
    
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final DayState state;
    
    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, DayState state) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess {" +
                "dateTime = " + dateTime +
                ", description = '" + description + '\'' +
                ", calories = " + calories +
                ", excess = " + state.isExcess() +
                '}';
    }
}
