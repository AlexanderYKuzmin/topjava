package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryMemory implements MealRepository {
    private final AtomicInteger countId = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MealRepositoryMemory() {
        for (Meal meal: MealsUtil.meals) {
            add(meal);
        }
    }

    @Override
    public Map<Integer, Meal> getAll() {
        return meals;
    }

    @Override
    public Meal getById(Integer id) {
        return meals.get(id);
    }

    @Override
    public Meal add(Meal meal) {
        meals.put(countId.incrementAndGet(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(Integer id) {
        meals.remove(id);
    }

    @Override
    public int getCaloriesPerDay() {
        return MealsUtil.CALORIES_PER_DAY;
    }
}
