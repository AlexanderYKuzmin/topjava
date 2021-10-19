package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, int userId);

    // false if not found
    boolean delete(int mealId, int userId);

    // null if not found
    Meal get(int mealId, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getAllByDate(int userId, LocalDate startDate, LocalDate endDate);
    //List<Meal> getAllByDateAndTime(Predicate<Meal> filter);
}
