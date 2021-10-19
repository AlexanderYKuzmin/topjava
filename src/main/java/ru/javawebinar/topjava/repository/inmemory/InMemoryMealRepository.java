package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            return repository.merge(userId, new HashMap<Integer, Meal>() {{
                                put(meal.getId(), meal);
                            }},
                            (prev, newVal) -> {
                                prev.put(meal.getId(), meal);
                                return prev;
                            })
                    .get(meal.getId());
        }
        Map<Integer, Meal> mealsByUserId = repository.computeIfPresent(userId, (k, v) -> {
            v.put(meal.getId(), meal);
            return v;
        });
        return mealsByUserId != null ? mealsByUserId.get(meal.getId()) : null;
    }

    @Override
    public boolean delete(int mealId, int userId) {
        return repository.get(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        return repository.get(userId).get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Meal> getAllByDate(int userId, LocalDate dateStart, LocalDate dateEnd) {
        return repository.get(userId).values().stream().filter(meal -> DateTimeUtil.isBetweenDates(meal.getDate(), dateStart, dateEnd))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

