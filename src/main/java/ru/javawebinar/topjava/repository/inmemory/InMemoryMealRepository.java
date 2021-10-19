package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
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
            repository.put(meal.getId(), meal);
            return meal;
        }
        return repository.computeIfPresent(meal.getId(),
                (id, oldMeal) -> oldMeal.getUserId() == userId
                        ? new Meal(meal.getId(), meal.getDateTime(),
                                    meal.getDescription(), meal.getCalories(), userId)
                        : oldMeal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        log.info("delete {}, authorized userId {}", meal, userId);
        if (meal != null && meal.getUserId() == userId) {
            return repository.remove(mealId) != null;
        }
        return false;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        return meal.getUserId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }

    /*@Override
    public List<Meal> getAllByDateAndTime(Predicate<Meal> dateAndTimePredicate) {
        return repository.values().stream().filter(dateAndTimePredicate).collect(Collectors.toList());
    }*/

    public List<Meal> getAllByDate(int userId, LocalDate dateStart, LocalDate dateEnd) {
        return repository.values().stream().filter(meal -> DateTimeUtil.isBetweenDates(meal.getDate(), dateStart, dateEnd))
                .collect(Collectors.toList());
    }
}

