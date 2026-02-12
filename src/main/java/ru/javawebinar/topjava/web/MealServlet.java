package ru.javawebinar.topjava.web;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(MealServlet.class);
    private final MealStorage mealStorage = new InMemoryMealStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "update":
                log.debug("update meal");
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = mealStorage.get(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                return;
            case "delete":
                log.debug("delete meal");
                id = Integer.parseInt(request.getParameter("id"));
                mealStorage.delete(id);
                response.sendRedirect("meals");
                return;
            case "mealForm":
                log.debug("forward to mealForm");
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                return;
            default:
                log.debug("show meals list");
                List<MealTo> mealsTo = MealsUtil.filteredByStreams(
                        mealStorage.getAll(), MealsUtil.CALORIES_PER_DAY);
                request.setAttribute("mealsTo", mealsTo);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("save meal");
        String idStr = request.getParameter("id");
        Integer id = idStr.isEmpty() ? null : Integer.parseInt(idStr);
        mealStorage.save(
                new Meal(
                        id,
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories"))));
        response.sendRedirect("meals");
    }
}
