package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, ADMIN_ID);
        assertMatch(meal, testMeal);
    }

    @Test
    public void getOther() {
        assertThrows(NotFoundException.class, () -> service.get(testMeal.getId(), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(testMeal.getId(), ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(testMeal.getId(), ADMIN_ID));
    }

    @Test
    public void deleteOther() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> testMeals = getBetweenDatesInclusive();
        List<Meal> meals = service.getBetweenInclusive(START_DATE, END_DATE, USER_ID);
        assertMatch(meals, testMeals);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(updated.getId(), ADMIN_ID), getUpdated());
    }

    @Test
    public void updateOther() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(
                        new Meal(testMeal.getDateTime(), "New test breakfast", 3000),
                        ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> testMealList = getFullMealTestList();
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertMatch(meals, testMealList);
    }
}