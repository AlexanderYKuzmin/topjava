package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
        if (req.getParameter("action") != null) {
            switch (req.getParameter("action")) {
                case "update":
                    log.debug("update meal action, redirect to editMeal.jsp");
                    req.setAttribute("meal", mealRepository.getById(getId(req)));
                case "add":
                    log.debug("add meal action, redirect to editMeal.jsp");
                    req.getRequestDispatcher("/editMeal.jsp").forward(req, resp);
                    break;
            }
        }
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX,
                CALORIES_PER_DAY);
        req.setAttribute("mealsTo", mealsTo);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException,
            NullPointerException{
        log.debug("post data");
        req.setCharacterEncoding("UTF-8");

        if (req.getParameter("action") != null) {
            if (req.getParameter("action").equals("delete")) {
                log.debug("delete meal from MealRepository");
                mealRepository.delete(getId(req));
            }
        } else {
            Integer id = getId(req);
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("date"));
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));

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

    private Integer getId(HttpServletRequest req) throws NumberFormatException, NullPointerException{
        if (req.getParameter("id").isEmpty()) {
            return null;
        }
        return Integer.parseInt(req.getParameter("id"));
    }
}
