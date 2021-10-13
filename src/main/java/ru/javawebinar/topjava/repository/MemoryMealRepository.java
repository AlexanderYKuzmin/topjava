package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealRepository implements MealRepository {
    private final AtomicInteger countId = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MemoryMealRepository() {
        for (Meal meal: MealsUtil.meals) {
            add(meal);
        }
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(countId.incrementAndGet());
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        if(meals.containsKey(meal.getId())){
            meals.put(meal.getId(), meal);
            return meal;
        }
       return null;
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

}
