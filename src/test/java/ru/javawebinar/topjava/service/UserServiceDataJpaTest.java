package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class UserServiceDataJpaTest extends UserServiceTest{

    @Test
    public void getWithMeal() {
        User user = service.get(USER_ID);
        user.getMeals().sort((o1,o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        USER_MATCHER.assertMatch(user, UserTestData.getWithMealData());
        MEAL_MATCHER.assertMatch(user.getMeals(), UserTestData.getWithMealData().getMeals());
    }
}
