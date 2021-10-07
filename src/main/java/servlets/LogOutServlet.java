package servlets;

import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogOutServlet", value = "/log-out-servlet")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LogOutServlet Called");

        // session items delete
        req.getSession().removeAttribute("aid");
        req.getSession().removeAttribute("name");

        // all session remove
        req.getSession().invalidate();

        // cookie delete
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        resp.sendRedirect(Util.base_url + "admin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LogOutServlet doPost Called after changing password.");

        // session items delete
        req.getSession().removeAttribute("aid");
        req.getSession().removeAttribute("name");

        // all session remove
        req.getSession().invalidate();

        // cookie delete
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
}
