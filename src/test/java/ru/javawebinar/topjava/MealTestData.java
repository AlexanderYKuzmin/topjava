package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_ID = 1000;
    public static final int NOT_FOUND = 10;

    public static final LocalDate START_DATE = LocalDate.of(2021, Month.JANUARY, 31);
    public static final LocalDate END_DATE = LocalDate.of(2021, Month.JANUARY, 31);

    public static final Meal testMeal = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак",500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "Test breakfast", 2000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal();
        updated.setId(MEAL_ID);
        updated.setDateTime(LocalDateTime.of(2021, Month.OCTOBER, 25, 23,0));
        updated.setDescription("Updated description");
        updated.setCalories(10000);
        return updated;
    }

    public static List<Meal> getBetweenDatesInclusive() {
        return Arrays.asList(
                new Meal(1009, LocalDateTime.of(2021, Month.JANUARY, 31, 8, 0), "Завтрак", 800),
                new Meal(1010, LocalDateTime.of(2021, Month.JANUARY, 31, 14, 0), "Обед", 900),
                new Meal(1011, LocalDateTime.of(2021, Month.JANUARY, 31, 21, 0), "Ужин", 700)
        );
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }
}
