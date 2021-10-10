package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MealServiceImpl implements MealService {
    List<Meal> meals = new CopyOnWriteArrayList<>(MealsUtil.meals);

    @Override
    public List<MealTo> getMealsTo(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }

    @Override
    public Meal getMealById(String idParam) {
        if(idParam == null) return null;
        int id = Integer.parseInt(idParam);
        return meals.stream().filter(meal -> meal.getId() == id).findFirst().get();
    }

    @Override
    public void addMeal(Map<String, Object> mealProperties) {
        meals.add(new Meal((LocalDateTime) mealProperties.get("date"), (String) mealProperties.get("description"),
                (int) mealProperties.get("calories")));
    }

    @Override
    public void updateMeal(String... params) {
        Map<String, Object> mealProperties = checkAndTransformParams(params);
        if((int)mealProperties.get("id") == 0) {
            addMeal(mealProperties);
        }

        meals = meals.stream().map(meal -> {
            if(meal.getId() == (int)mealProperties.get("id")) {
                meal.setDateTime((LocalDateTime) mealProperties.get("date"));
                meal.setDescription((String) mealProperties.get("description"));
                meal.setCalories((int) mealProperties.get("calories"));
            }
            return meal;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteMeal(String idParam) {
        int id = Integer.parseInt(idParam);
        meals = meals.stream().filter(meal -> meal.getId() != id).collect(Collectors.toList());
    }

    private Map<String, Object> checkAndTransformParams(String[] params) {
        int id = 0;
        LocalDateTime dateTime = LocalDateTime.now();
        String description = "";
        int calories = 0;

        Map<String, Object> mealProperties = new HashMap<>();

        if(params[0] != null && !params[0].isEmpty()) {
            id = Integer.parseInt(params[0]);
        }
        if(params[1] != null && !params[1].isEmpty()) {
            try{
                dateTime = LocalDateTime.parse(params[1]);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
        if(params[2] != null) {
            description = params[2];
        }
        if(params[3] != null && !params[3].isEmpty()) {
            try {
                calories = Integer.parseInt(params[3]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        mealProperties.put("id", id);
        mealProperties.put("date", dateTime);
        mealProperties.put("description", description);
        mealProperties.put("calories", calories);

        return mealProperties;
    }
}
