package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    ConfigurableApplicationContext appCtx;
    private MealRestController mealController;
    //private FilterDateAndTimeValuesStorage filterDateAndTime = appCtx.getBean(FilterDateAndTimeValuesStorage.class);

    @Override
    public void init() throws ServletException {
        super.init();
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                null);
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealController.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user"));
        mealController.setUserId(userId);


        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action)
                        ? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000)
                        : mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("Filtering meal by date and time");
                LocalDate dateStart = LocalDate.parse(request.getParameter("dateStart"));
                LocalDate dateEnd =   LocalDate.parse(request.getParameter("dateEnd"));
                LocalTime timeStart = LocalTime.parse(request.getParameter("timeStart"));
                LocalTime timeEnd = LocalTime.parse(request.getParameter("timeEnd")) ;

                List<MealTo> mealsTo = mealController.getAllByDateAndTime(
                    dateStart, dateEnd, timeStart, timeEnd);

                request.setAttribute("meals", mealsTo);
                request.setAttribute("filterDateStart", dateStart);
                request.setAttribute("filterDateEnd", dateEnd);
                request.setAttribute("filterTimeStart", timeStart);
                request.setAttribute("filterTimeEnd", timeEnd);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "clearForm":
                mealController.clearFilterForm();

            case "all":
            default:
                log.info("getAll");
                /*request.setAttribute("filterDateStart", FilterDateAndTimeValuesStorage.getDateStart());
                request.setAttribute("filterDateEnd", FilterDateAndTimeValuesStorage.getDateEnd());
                request.setAttribute("filterTimeStart", FilterDateAndTimeValuesStorage.getTimeStart());
                request.setAttribute("filterTimeEnd", FilterDateAndTimeValuesStorage.getTimeEnd());*/
                request.setAttribute("meals", mealController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
