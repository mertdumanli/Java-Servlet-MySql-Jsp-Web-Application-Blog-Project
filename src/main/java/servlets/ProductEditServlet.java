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

@WebServlet(name = "ProductEditServlet", value = "/product-edit-servlet")
public class ProductEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int pid = Integer.parseInt(req.getParameter("pid"));
        req.getSession().setAttribute("editProductPid", pid);
        DBUtil util = new DBUtil();
        Product pro = util.getSingleProductInfo(pid);

        req.setAttribute("pro", pro);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/editproduct.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean pidStatus = req.getSession().getAttribute("editProductPid") != null;

        if (pidStatus) {

            String title = req.getParameter("title");
            String summary = req.getParameter("summary");
            String article = req.getParameter("article");
            int pid = (int) req.getSession().getAttribute("editProductPid");
            req.getSession().removeAttribute("editProductPid");
            int aid = Integer.parseInt(req.getParameter("aid"));

            System.out.println("ProductEditServlet doPost pid: " + pid);
            System.out.println("ProductEditServlet doPost aid: " + aid);

            Product product = new Product();
            product.setPid(pid);
            product.setTitle(title);
            product.setSummary(summary);
            product.setArticle(article);
            product.setAid(aid);

            DBUtil util = new DBUtil();
            int status = util.editProduct(product);
            if (status > 0) {
                resp.sendRedirect(Util.base_url + "management.jsp");
            } else if (status == -1) {
                req.setAttribute("editProductFeedback", "Aynı isimli bir başlık başka bir yazıya ait.");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/management.jsp");
                dispatcher.forward(req, resp);
            } else {
                req.setAttribute("editProductFeedback", "Veritabanında hata oluştu lütfen tekrar deneyin.");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/management.jsp");
                dispatcher.forward(req, resp);
            }
        }
    }
}
