package ru.javawebinar.topjava.web;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

public class UserServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(UserServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userId");
        switch (userId == null ? "toUsers" : userId) {
            case "1":
                log.debug("Redirect to admin meals");
                SecurityUtil.setAuthUserId(Integer.parseInt(userId));
                response.sendRedirect("meals");
                break;
            case "2":
                log.debug("Redirect to user meals");
                SecurityUtil.setAuthUserId(Integer.parseInt(userId));
                response.sendRedirect("meals");
                break;
            case "toUsers":
            default:
                log.debug("forward to users");
                request.getRequestDispatcher("/users.jsp").forward(request, response);
        }
    }
}