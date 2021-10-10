package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface MealService {
    List<MealTo> getMealsTo(LocalTime startTime, LocalTime endTime, int caloriesPerDay);

    Meal getMealById(String id);

    void addMeal(Map<String, Object> mealProperties);

    void updateMeal(String... params);

    void deleteMeal(String id);
}
