package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.text.SimpleDateFormat;
import java.util.*;

public class UsersUtil {
    public static final List<User> users = Arrays.asList(
            new User(1, "User-1", "user01@yandex.ru", "Password01", Role.ADMIN, new Role[]{Role.ADMIN, Role.USER}),
            new User(2, "User-2", "user02@mail.ru", "Password02", Role.USER, new Role[]{Role.USER}),
            new User(3, "User-3", "user01@rambler.ru", "Password03", Role.USER, new Role[]{Role.USER}),
            new User(4, "User-4", "user01@gmail.com", "Password04", Role.ADMIN, new Role[]{Role.ADMIN, Role.USER})
    );

   /* public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }*/
}
