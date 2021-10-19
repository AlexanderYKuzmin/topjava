package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.FilterDateAndTimeValuesStorage;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    @Autowired
    private FilterDateAndTimeValuesStorage dateAndTimeValuesStorage;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, meal.getId());
        assureIdConsistent(meal, meal.getId());
        service.update(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void save(Meal meal) {
        log.info("update {} with id={}", meal, meal.getId());
        if (meal.isNew()) {
            create(meal);
        } else {
            update(meal);
        }
    }

    public List<MealTo> getAllByDateAndTime(LocalDate dateStart, LocalDate dateEnd, LocalTime timeStart, LocalTime timeEnd) {
        log.info("getAll by Data and Time");
        /*Predicate<Meal> dateAndTimePredicate = meal -> meal.getUserId() == SecurityUtil.authUserId() &&
                DateTimeUtil.isBetweenDates(meal.getDate(), FilterDateAndTimeValuesStorage.getDateStart(),
                        FilterDateAndTimeValuesStorage.getDateEnd()) &&
                DateTimeUtil.isBetweenHalfOpen(meal.getTime(), FilterDateAndTimeValuesStorage.getTimeStart(),
                        FilterDateAndTimeValuesStorage.getTimeEnd());*/
        dateAndTimeValuesStorage.saveValues(dateStart, dateEnd, timeStart, timeEnd);
        List<Meal> mealsByDate = service.getAllByDate(SecurityUtil.authUserId(), dateStart, dateEnd);
        return MealsUtil.getFilteredTos(mealsByDate, SecurityUtil.authUserCaloriesPerDay(), timeStart, timeEnd );
    }

    public void clearFilterForm() {
        dateAndTimeValuesStorage.clear();
    }

    public void setUserId(int userId) {
        SecurityUtil.setAuthUserId(userId);
    }
}