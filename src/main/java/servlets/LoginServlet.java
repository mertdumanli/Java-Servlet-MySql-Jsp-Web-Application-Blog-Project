package servlets;

import props.Product;
import utils.DBUtil;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet doPost Called");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");//işaretlendiğinde on, işaretlenmediğinde null

        DBUtil util = new DBUtil();
        boolean status = util.login(email, Util.MD5(password), remember, req, resp);

        if (status) {
            resp.sendRedirect(Util.base_url + "management.jsp");
            int aid = (int) req.getSession().getAttribute("aid");
            req.getSession().setAttribute("addProductAid", aid);
        } else {
            req.setAttribute("loginError", "Kullanıcı adı veya şifre hatalı.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet doPost Called");
        resp.sendRedirect(Util.base_url + "management.jsp");
    }
}