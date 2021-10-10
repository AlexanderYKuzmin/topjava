package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealService service = new MealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");
        if(action != null) {
            if (action.equals("edit")) {
                req.setAttribute("meal", service.getMealById(idParam));
                req.getRequestDispatcher("/edit-meals.jsp").forward(req, resp);
            } else if (action.equals("delete")) {
                service.deleteMeal(idParam);
            }
        }
        showMealTable(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        service.updateMeal(req.getParameter("id"), req.getParameter("date"), req.getParameter("description"),
                req.getParameter("calories"));
        req.removeAttribute("id");
        req.removeAttribute("date");
        req.removeAttribute("description");
        req.removeAttribute("calories");
        showMealTable(req, resp);
    }

    private void showMealTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealTo> mealsTo = service.getMealsTo(LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
        req.setAttribute("mealsTo", mealsTo);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
