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

@WebServlet(name = "ProductAddServlet", value = "/product-add-servlet")
public class ProductAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(Util.base_url + "management.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Yeni yazı ekleme işleminde kontrol yapılacak yer.
        System.out.println("ProductAddServlet doPost called.");


        boolean aidStatus = req.getSession().getAttribute("addProductAid") != null;

        if (aidStatus) {

            String title = req.getParameter("title");
            String summary = req.getParameter("summary");
            String article = req.getParameter("article");

            int aid = (int) req.getSession().getAttribute("addProductAid");

            Product pro = new Product();

            pro.setTitle(title);
            pro.setSummary(summary);
            pro.setArticle(article);
            pro.setAid(aid);
            //pro.setPid(); it will be incremented by automatic sql.
            //pro.setDate(); It will be automatically added by sql.

            DBUtil util = new DBUtil();
            int feedback = util.addProduct(pro);

            if (feedback > 0) {

                //ekleme başarılı.
                resp.sendRedirect(Util.base_url + "management.jsp");

            } else {
                if (feedback == -1) {
                    //title benzersiz
                    req.setAttribute("addProductFeedBack", "Aynı başlıkta bir yazı zaten mevcut.");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/management.jsp");
                    dispatcher.forward(req, resp);
                } else if (feedback == 0) {
                    //bir hata meydana geldi.
                    req.setAttribute("addProductFeedBack", "Ekleme başarısız. Sistemsel bir hata meydana geldi.");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/management.jsp");
                    dispatcher.forward(req, resp);
                }
            }
        } else {
            resp.sendRedirect(Util.base_url + "admin.jsp");
        }
    }
}
