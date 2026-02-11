package ru.javawebinar.topjava.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class MealTo {
    
    private int id;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean excess;

    public MealTo(int id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }
    
    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public Date getDateTimeAsDate() {
        return Timestamp.valueOf(dateTime);
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }
    
    public boolean getExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo {" +
                "dateTime = " + dateTime +
                ", description = '" + description + '\'' +
                ", calories = " + calories +
                ", excess = " + excess +
                '}';
    }
}
