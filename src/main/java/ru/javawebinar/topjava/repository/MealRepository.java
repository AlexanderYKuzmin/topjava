package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import java.util.Map;

public interface MealRepository {
    Map<Integer, Meal> getAll();

    Meal getById(Integer id);

    Meal add(Meal meal);

    Meal update(Meal meal);

    void delete(Integer id);

    int getCaloriesPerDay();
}
