package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

/**
 * Created by Валерий on 24.01.2017.
 */
@Controller
@RequestMapping(value = "/meals")
public class MealController extends AbstractMealController{
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

/*    @RequestMapping(method = RequestMethod.GET)
    public String meals(Model model){
        model.addAttribute("meals", super.getAll());
        return "meals";
    }*/

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request){
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request, Model model){
        model.addAttribute("meal", super.get(getId(request)));
        return "meal";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateOrCreate(HttpServletRequest request){
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (meal.isNew()){
            super.create(meal);
        } else {
            super.update(meal, meal.getId());
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween(HttpServletRequest request, Model model){
        LocalDate startDate = parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = parseLocalTime(resetParam("endTime", request));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    private String resetParam(String param, HttpServletRequest request){
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

    private int getId(HttpServletRequest request){
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
