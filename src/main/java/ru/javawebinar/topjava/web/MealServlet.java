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
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.MealStorageInMemory;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(MealServlet.class);
    private final MealStorage mealStorageInMemory = new MealStorageInMemory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealStorageInMemory.delete(id);
            response.sendRedirect("meals");
            return;
        }
        if ("mealForm".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/mealForm.jsp")
                    .forward(request, response);
            return;
        }
        if ("update".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealStorageInMemory.get(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
            return;
        }
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(
                mealStorageInMemory.getAll(), MealsUtil.CALORIES_PER_DAY, meal -> true);
        request.setAttribute("mealsTo", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("redirect to meals");
        String idStr = request.getParameter("id");
        Integer id = idStr.isEmpty() ? null : Integer.parseInt(idStr);
        mealStorageInMemory.save(
                new Meal(
                        id,
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories"))));
        response.sendRedirect("meals");
    }
}
