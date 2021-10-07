package servlets;

import utils.DBUtil;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangePasswordServlet", value = "/change-password-servlet")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ChangePasswordServlet doGet Called");

        int aid = Integer.parseInt(req.getParameter("aid"));
        req.getSession().setAttribute("changePasswordAid", aid);
        resp.sendRedirect(Util.base_url + "changepassword.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //şifre değiştir ekranında değiştirme işleminin kontrolü burada olacak.
        System.out.println("ChangePasswordServlet doPost called.");

        boolean aidStatus = req.getSession().getAttribute("changePasswordAid") != null;

        if (aidStatus) {

            String oldPassword = req.getParameter("oldPassword");
            String newPassword = req.getParameter("newPassword");
            String newPasswordr = req.getParameter("newPasswordr");


            int aid = (int) req.getSession().getAttribute("changePasswordAid");


            DBUtil util = new DBUtil();
            int feedback = util.changePassword(oldPassword, newPassword, newPasswordr, aid);

            if (feedback > 0) {

                //req.getSession().removeAttribute("changePasswordAid");

                LogOutServlet los = new LogOutServlet();
                los.doPost(req, resp);

                //şifre değiştirildi.
                req.setAttribute("changeFeedBack", "Şifre başarıyla güncellendi.");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin.jsp");
                dispatcher.forward(req, resp);

            } else if (feedback == -1) {
                //şifre değiştirilmedi. eski şifre hatalı
                req.setAttribute("changeFeedback", "Şifre değiştirilemedi. Eski şifre hatalı.");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/changepassword.jsp");
                dispatcher.forward(req, resp);
            } else if (feedback == -2) {
                //şifre değiştirilemedi. yeni şifreler birbirinden farklı.
                req.setAttribute("changeFeedback", "Şifre değiştirilemedi. Yeni şifreler birbirinden farklı.");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/changepassword.jsp");
                dispatcher.forward(req, resp);
            } else {
                //bir hata meydana geldi.
                req.setAttribute("changeFeedback", "Şifre değiştirilemedi. Sistemsel bir hata meydana geldi.");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/changepassword.jsp");
                dispatcher.forward(req, resp);
            }
        } else {
            System.out.println(aidStatus);
            resp.sendRedirect(Util.base_url);
        }
    }
}