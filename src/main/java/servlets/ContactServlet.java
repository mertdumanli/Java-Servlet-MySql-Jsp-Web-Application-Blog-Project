package servlets;

import props.Contact;
import utils.DBUtil;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ContactServlet", value = "/contact-servlet")
public class ContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(Util.base_url + "contact.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String tel = req.getParameter("tel");
        String message = req.getParameter("message");

        Contact contact = new Contact();
        contact.setName(name);
        contact.setSurname(surname);
        contact.setEmail(email);
        contact.setTel(tel);
        contact.setMessage(message);

        DBUtil util = new DBUtil();
        int feedBack = util.contact(contact);
        if (feedBack > 0) {
            req.setAttribute("sendContact", "En kısa sürede size geri dönüş yapacağız.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contact.jsp");
            dispatcher.forward(req, resp);
        } else {
            req.setAttribute("sendContact", "Yükleme başarısız oldu. Lütfen tekrar deneyin.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contact.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
