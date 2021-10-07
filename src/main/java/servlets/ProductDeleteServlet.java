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

@WebServlet(name = "ProductDeleteServlet", value = "/product-delete-servlet")
public class ProductDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pid = req.getParameter("pid");//get product Id

        try {
            int cpid = Integer.parseInt(pid);//pid boş olursa hata verecektir. try catch
            //converted id
            int aid = (int) req.getSession().getAttribute("aid");
            DBUtil util = new DBUtil();
            if (util.isProductItem(cpid, aid)) {
                System.out.println("Yetki mevcut.");
                int status = util.productDelete(cpid);
                if (status > 0) {
                    //başarıyla silindi sayfa yenilenecek.
                    resp.sendRedirect(Util.base_url + "management.jsp");
                } else {
                    req.setAttribute("deleteProductFeedback", "Hata oldu. Sql kaynaklı.");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/management.jsp");
                    dispatcher.forward(req, resp);
                }
            } else {
                //Yetkisi olmayan bir ID yi silmeye kalkışmış demektir.
                //bunu yapan kullanıcı dışarıya atıyorum.
                LogOutServlet los = new LogOutServlet();
                los.doPost(req, resp);
                resp.sendRedirect(Util.base_url + "admin.jsp");
            }
        } catch (Exception e) {
            System.err.println("ProductDeleteServlet Error : " + e);
            if (e.toString().contains("java.lang.NullPointerException")) {
                //Açık oturum yok bu durumda da giriş yapması için admin.jsp ye yönlendirme var.
                resp.sendRedirect(Util.base_url + "admin.jsp");
            } else {//id numarası girilmemiş ve login olunmamış ise "http://localhost:8091/BlogProject_war_exploded/product-delete-servlet?pid=..."
                resp.sendRedirect(Util.base_url);
            }
        }
    }
}
