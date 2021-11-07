package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    private final CrudUserRepository userRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository crudUserRepository) {
        this.userRepository = crudUserRepository;
        this.crudRepository = crudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Meal repoMeal;
        if(meal.isNew()) {
            meal.setUser(userRepository.getById(userId));
            return crudRepository.save(meal);
        } else if((repoMeal = get(meal.getId(), userId)) != null) {
            repoMeal.setDateTime(meal.getDateTime());
            repoMeal.setDescription(meal.getDescription());
            repoMeal.setCalories(meal.getCalories());
            return crudRepository.save(repoMeal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.findById(id).orElse(null);
        return meal != null && meal.getUser().getId() == userId ? meal : null ;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserId(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.findAllByDateTimeRange(userId, startDateTime, endDateTime);
    }
}
