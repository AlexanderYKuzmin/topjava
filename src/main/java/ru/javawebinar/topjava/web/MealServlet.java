package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MemoryMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private MealRepository mealRepository;

    @Override
    public void init() {
        mealRepository = new MemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException {
        log.debug("redirect to meals");
        List<MealTo> mealsTo = filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX,
                CALORIES_PER_DAY);
        req.setAttribute("mealsTo", mealsTo);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("post data");
        req.setCharacterEncoding("UTF-8");

        if (req.getParameter("action") != null) {
            switch (req.getParameter("action")) {
                case "update":
                    log.debug("update meal action, redirect to editMeal.jsp");
                    req.setAttribute("meal", mealRepository.getById(getId(req.getParameter("id"))));
                case "add":
                    log.debug("add meal action, redirect to editMeal.jsp");
                    req.getRequestDispatcher("/editMeal.jsp").forward(req, resp);
                    break;
                case "delete":
                    log.debug("delete meal from MealRepository");
                    mealRepository.delete(getId(req.getParameter("id")));
            }
        } else {
            Integer id = !req.getParameter("id").isEmpty() ? getId(req.getParameter("id")) : null;
            LocalDateTime dateTime = !req.getParameter("date").isEmpty()
                    ? LocalDateTime.parse(req.getParameter("date")) : null;
            String description = req.getParameter("description") != null ? req.getParameter("description") : "";
            int calories = !req.getParameter("calories").isEmpty() ? Integer.parseInt(req.getParameter("calories")) : 0;

            if (id == null) {
                log.debug("add meal to MealRepository");
                mealRepository.add(new Meal(dateTime, description, calories));
            } else {
                log.debug("update meal in MealRepository");
                mealRepository.update(new Meal(id, dateTime, description, calories));
            }
        }
        resp.sendRedirect(req.getContextPath() + "/meals");
    }

    private List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        MealTo mealTo = new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
        mealTo.setId(meal.getId());
        return mealTo;
    }

    private int getId(String idParameter) {
        return Integer.parseInt(idParameter);
    }
}
