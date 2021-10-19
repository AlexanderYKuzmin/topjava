package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("forward to meals");
        String userSelection = request.getParameter("user");
        if (userSelection != null) {
            SecurityUtil.setAuthUserId(Integer.parseInt(userSelection));
            response.sendRedirect("meals");
        } else {
            response.sendRedirect("users.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.info("Do Post");
    }
}
