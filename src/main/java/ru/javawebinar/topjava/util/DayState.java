package ru.javawebinar.topjava.util;

public class DayState {
    
    private int caloriesLimit;
    private int caloriesTotal;
    
    public DayState(int caloriesLimit) {
        this.caloriesLimit = caloriesLimit;
    }
    
    public void addCalories(int calories) {
        caloriesTotal += calories;
    }
    
    public boolean isExcess() {
        return caloriesTotal > caloriesLimit;
    }
}
