package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController{

    /*@Autowired
    MealService service;*/

    @GetMapping
    public String getMeals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/create")
    public String createMeal(Model model) {
        model.addAttribute("meal",
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }
/*
    @GetMapping("/{id}")
    public String getMeal(Model model, @PathVariable(name="id") int id) {
        get(id);
        return getMeals(model);
    }*/

    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable(name = "id") int id) {
        delete(id);
        return "redirect:/meals";
    }

   @GetMapping("/update/{id}")
    public String updateMeal(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute("meal", get(id));
        return "mealForm";
   }

   @GetMapping("/filter")
    public String filerMeal(Model model, HttpServletRequest request) {
       LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
       LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
       LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
       LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
       model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
       return "meals";
   }

   @PostMapping("/save")
   public String saveMeal(HttpServletRequest request) throws UnsupportedEncodingException {
       //request.setCharacterEncoding("UTF-8");
       System.out.println(request.getParameter("description"));
       Meal meal = new Meal(
               LocalDateTime.parse(request.getParameter("dateTime")),
               request.getParameter("description"),
               Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.hasLength(request.getParameter("id"))) {
           update(meal, getId(request));
        } else {
            create(meal);
        }
        return "redirect:/meals";
   }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
