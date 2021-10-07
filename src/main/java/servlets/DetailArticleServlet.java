package servlets;

import props.Product;
import utils.DB;
import utils.DBUtil;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DetailArticleServlet", value = "/detail-article-servlet")
public class DetailArticleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int xPid = 0;
        try {
            xPid = Integer.parseInt(req.getParameter("product"));
            DBUtil util = new DBUtil();
            Product product = util.getSingleProductAllInfoToDetailPage(xPid);

            if (product.getTitle() != null) {
                req.setAttribute("productInfo", product);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/detailarticle.jsp");
                dispatcher.forward(req, resp);
            } else {
                //index numarasına ait veri yok.
                resp.sendRedirect(Util.base_url + "index.jsp");
            }
        } catch (Exception e) {
            System.err.println("DetailArticleServlet doGet Error : " + e);
            System.err.println("Muhtemelen url kısmında product ifadesi hatalı.");
            resp.sendRedirect(Util.base_url + "index.jsp");
        }
    }
}
