package ru.javawebinar.topjava.web;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to meals");
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(
                MealsUtil.getMeals(), MealsUtil.CALORIES_PER_DAY, meal -> true);
        request.setAttribute("mealsTo", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
