package utils;

import props.Contact;
import props.Product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBUtil {

    public boolean login(String email, String password, String remember, HttpServletRequest req, HttpServletResponse resp) {

        boolean status = false;
        DB db = new DB();

        try {
            String sql = "SELECT * FROM admin where email = ? and password = ?";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, password);
            ResultSet rs = pre.executeQuery();
            status = rs.next();

            if (status) {
                //session create
                int aid = rs.getInt("aid");
                String name = rs.getString("name");

                req.getSession().setAttribute("aid", aid);
                req.getSession().setAttribute("name", name);

                //cookie create
                if (remember != null) {
                    name = name.replaceAll(" ", "_");
                    String val = aid + "_" + name;
                    Cookie cookie = new Cookie("user", val);
                    cookie.setMaxAge(60 * 60 * 30);
                    resp.addCookie(cookie);
                }
            }
        } catch (Exception e) {
            System.err.println("Login Error: " + e);
        } finally {
            db.close();
        }
        return status;
    }

    //all product result
    public List<Product> allProduct(Integer aid, Object isSearch) {
        List<Product> ls = new ArrayList<>();
        DB db = new DB();
        if (isSearch == null || isSearch.toString().isEmpty()) {
            try {
                String sql = "Select * from product where aid=?";
                PreparedStatement pre = db.conn.prepareStatement(sql);
                pre.setInt(1, aid);
                ResultSet rs = pre.executeQuery();
                while (rs.next()) {
                    int pid = rs.getInt("pid");
                    String title = rs.getString("title");
                    String summary = rs.getString("summary");
                    String article = rs.getString("article");
                    Date date = rs.getDate("date");

                    Product pr = new Product();
                    pr.setPid(pid);
                    pr.setTitle(title);
                    pr.setSummary(summary);
                    pr.setArticle(article);
                    pr.setDate(date);
                    ls.add(pr);
                }
            } catch (Exception e) {
                System.err.println("no: 0 - allProduct Error: " + e);
            } finally {
                db.close();
            }
        } else {
            try {
                String sql = "Select * from product where aid=? and title like ?";

                PreparedStatement pre = db.conn.prepareStatement(sql);
                pre.setInt(1, aid);
                pre.setString(2, '%' + isSearch.toString() + '%');
                ResultSet rs = pre.executeQuery();
                while (rs.next()) {
                    int pid = rs.getInt("pid");
                    String title = rs.getString("title");
                    String summary = rs.getString("summary");
                    String article = rs.getString("article");
                    Date date = rs.getDate("date");

                    Product pr = new Product();
                    pr.setPid(pid);
                    pr.setTitle(title);
                    pr.setSummary(summary);
                    pr.setArticle(article);
                    pr.setDate(date);
                    ls.add(pr);
                }
            } catch (Exception e) {
                System.err.println("no: 1 - allProduct Error: " + e);
            } finally {
                db.close();
            }
        }
        return ls;
    }

    //product item single delete
    public int productDelete(int pid) {
        int status = 0;
        DB db = new DB();
        try {
            String sql = "delete from product where pid=?";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setInt(1, pid);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("productDelete Error: " + e);
        } finally {
            db.close();
        }
        return status;
    }

    public int contact(Contact contact) {
        int feedBack = 0;

        DB db = new DB();

        try {
            String sql = "INSERT INTO contact values (null, ?, ?, ?, ?, ?, now())";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setString(1, contact.getName());
            pre.setString(2, contact.getSurname());
            pre.setString(3, contact.getEmail());
            pre.setString(4, contact.getTel());
            pre.setString(5, contact.getMessage());
            feedBack = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("contact Error: " + e);
        } finally {
            db.close();
        }
        return feedBack;
    }

    public String getEmail(int aid) {
        String email = "";

        DB db = new DB();
        try {
            String sql = "SELECT email from admin where aid=?";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setInt(1, aid);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (Exception e) {
            System.err.println("getEmail error: " + e);
        } finally {
            db.close();
        }
        return email;
    }

    public int changePassword(String oldPassword, String newPassword, String newPasswordr, int aid) {
        int status = 0;
        DB db = new DB();
        if (newPassword.equals(newPasswordr)) {
            try {
                String sql = "UPDATE admin set password = ?  where password = ? and aid = ? ";
                PreparedStatement pre = db.conn.prepareStatement(sql);
                pre.setString(1, Util.MD5(newPassword));
                pre.setString(2, Util.MD5(oldPassword));
                pre.setInt(3, aid);
                status = pre.executeUpdate();
                if (status == 0) {
                    status = -1;
                }
            } catch (Exception e) {
                System.err.println("changePassword Error : " + e);
            } finally {
                db.close();
            }

        } else {//yeni şifreler farklı
            status = -2;
        }

        return status;
    }

    public int addProduct(Product pro) {
        int feedBack = 0;
        DB db = new DB();

        try {
            String sql = "insert into product values ( null, ?, ?, ?, now(), ? ) ";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setString(1, pro.getTitle());
            pre.setString(2, pro.getSummary());
            pre.setString(3, pro.getArticle());
            pre.setInt(4, pro.getAid());
            feedBack = pre.executeUpdate();

        } catch (Exception e) {
            if (e.toString().contains("SQLIntegrityConstraintViolationException")) {
                feedBack = -1;
            }
            System.err.println("addProduct Error : " + e);
        } finally {
            db.close();
        }
        return feedBack;
    }

    public boolean isProductItem(int cpid, int aid) {
        boolean isItem = false;
        System.out.println("cpid: " + cpid);
        System.out.println("aid: " + aid);

        DB db = new DB();

        try {
            String sql = "select * from product where pid = ? and aid = ?";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setInt(1, cpid);
            pre.setInt(2, aid);
            ResultSet rs = pre.executeQuery();
            isItem = rs.next();
        } catch (Exception e) {
            System.err.println("isProductItem Error : " + e);
        } finally {
            db.close();
        }
        return isItem;
    }

    public Product getSingleProductInfo(int pid) {
        Product product = new Product();
        DB db = new DB();
        try {
            String sql = "select * from product where pid = ?";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setInt(1, pid);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int pidNumber = rs.getInt("pid");
                String title = rs.getString("title");
                String summary = rs.getString("summary");
                String article = rs.getString("article");
                Date date = rs.getDate("date");

                product.setPid(pidNumber);
                product.setTitle(title);
                product.setSummary(summary);
                product.setArticle(article);
                product.setDate(date);
                return product;
            }
        } catch (Exception e) {
            System.err.println("getSingleProductInfo Error: " + e);
        } finally {
            db.close();
        }
        return product;
    }

    public int editProduct(Product product) {
        int feedBack = 0;
        DB db = new DB();

        try {
            String sql = "UPDATE product set title = ?, summary= ?, article = ?, date = now()  where pid = ? and aid=?";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setString(1, product.getTitle());
            pre.setString(2, product.getSummary());
            pre.setString(3, product.getArticle());
            pre.setInt(4, product.getPid());
            pre.setInt(5, product.getAid());
            feedBack = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("editProduct Error: " + e);
            if (e.toString().contains("java.sql.SQLIntegrityConstraintViolationException")) {
                feedBack = -1;
            }
        } finally {
            db.close();
        }

        return feedBack;
    }
    //getAllArticle Tüm yazıların bilgisini alma.

    public List<Product> getAllArticle() {
        List<Product> ls = new ArrayList<>();
        DB db = new DB();
        try {
            String sql = "Select * from product order by date DESC";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int pid = rs.getInt("pid");
                String title = rs.getString("title");
                String summary = rs.getString("summary");
                String article = rs.getString("article");
                Date date = rs.getDate("date");
                int aid = rs.getInt("aid");

                Product pr = new Product();
                pr.setPid(pid);
                pr.setTitle(title);
                pr.setSummary(summary);
                pr.setArticle(article);
                pr.setDate(date);
                pr.setAid(aid);
                ls.add(pr);
            }
        } catch (Exception e) {
            System.err.println("getAllArticle Error : " + e);
        } finally {
            db.close();
        }
        return ls;
    }

    public String getAdminName(int xAid) {
        String adminName = "";
        DB db = new DB();
        try {
            String sql = "select name from admin where aid=?";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setInt(1, xAid);
            ResultSet rs = pre.executeQuery();
            rs.next();
            adminName = rs.getString("name");
        } catch (Exception e) {
            System.err.println("getAdminName Error : " + e);
        } finally {
            db.close();
        }
        return adminName;
    }

    public Product getSingleProductAllInfoToDetailPage(int pid) {
        Product product = new Product();
        DB db = new DB();
        try {
            String sql = "select * from product where pid = ?";
            PreparedStatement pre = db.conn.prepareStatement(sql);
            pre.setInt(1, pid);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int aidNumber = rs.getInt("aid");
                int pidNumber = rs.getInt("pid");
                String title = rs.getString("title");
                String summary = rs.getString("summary");
                String article = rs.getString("article");
                Date date = rs.getDate("date");

                product.setAid(aidNumber);
                product.setPid(pidNumber);
                product.setTitle(title);
                product.setSummary(summary);
                product.setArticle(article);
                product.setDate(date);
                return product;
            }
        } catch (Exception e) {
            System.err.println("getSingleProductAllInfoToDetailPage Error: " + e);
        } finally {
            db.close();
        }
        return product;
    }
}