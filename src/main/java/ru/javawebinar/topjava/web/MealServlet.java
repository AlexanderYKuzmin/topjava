package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryMemory;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository repositoryMemory;

    @Override
    public void init() {
        repositoryMemory = new MealRepositoryMemory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException {
        log.debug("redirect to meals");
        String action = req.getParameter("action");
        Integer id;
        if(req.getParameter("id") == null || req.getParameter("id").isEmpty()) {
            id = null;
        } else {
            id = Integer.parseInt(req.getParameter("id"));
        }
        if(action != null) {
            log.debug("edition processing");
            switch (action) {
                case "edit":
                    req.setAttribute("meal", repositoryMemory.getById(id));
                    req.setAttribute("title", "Edit meal");
                    req.getRequestDispatcher("/editMeals.jsp").forward(req, resp);
                case "add":
                    req.setAttribute("title", "Add meal");
                    req.getRequestDispatcher("/editMeals.jsp").forward(req, resp);
                case "delete":
                    repositoryMemory.delete(id);
            }
        }
        showMealTable(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("store data attempt");
        req.setCharacterEncoding("UTF-8");
        try {
            Integer id = !req.getParameter("id").isEmpty() ? Integer.parseInt(req.getParameter("id")) : null;
            LocalDateTime dateTime = !req.getParameter("date").isEmpty()
                    ? LocalDateTime.parse(req.getParameter("date")) : LocalDateTime.now();
            String description = req.getParameter("description") != null ? req.getParameter("description") : "";
            int calories = !req.getParameter("calories").isEmpty() ? Integer.parseInt(req.getParameter("calories")) : 0;

            if(id == null) {
                log.debug("add meal data if not canceled");
                if(req.getParameter("cancel-btn") == null) {
                    repositoryMemory.add(new Meal(dateTime, description, calories));
                }
            } else {
                log.debug("update meal data");
                Meal meal = repositoryMemory.getById(id);
                meal.setDateTime(dateTime);
                meal.setDescription(description);
                meal.setCalories(calories);
                repositoryMemory.update(meal);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        showMealTable(req, resp);
    }

    private void showMealTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("show main table");
        List<MealTo> mealsTo = filteredByStreams(repositoryMemory.getAll(), LocalTime.MIN, LocalTime.MAX,
                repositoryMemory.getCaloriesPerDay());
        req.setAttribute("mealsTo", mealsTo);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    private List<MealTo> filteredByStreams(Map<Integer, Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collection<Meal> mealList = meals.values();
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        return mealList.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
