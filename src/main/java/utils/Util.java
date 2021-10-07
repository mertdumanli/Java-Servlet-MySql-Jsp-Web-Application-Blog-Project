package utils;

import props.Admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Util {
    public static final String base_url = "http://localhost:8091/BlogProject_war_exploded/";

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public Admin isLogin(HttpServletRequest request, HttpServletResponse response) {

        if (request.getCookies() != null ) {
            Cookie[] cookies = request.getCookies();
            for ( Cookie cookie : cookies ) {
                if ( cookie.getName().equals("user") ) {
                    String vales = cookie.getValue();
                    try {
                        String[] arr = vales.split("_");
                        request.getSession().setAttribute("aid", Integer.parseInt(arr[0]) );
                        request.getSession().setAttribute("name",arr[1] + " " + arr[2] );
                    } catch (NumberFormatException e) {
                        Cookie cookie1 = new Cookie("user", "");
                        cookie1.setMaxAge(0);
                        response.addCookie(cookie1);
                    }
                    break;
                }
            }
        }

        Object sessionObj = request.getSession().getAttribute("aid");
        Admin adm = new Admin();
        if ( sessionObj == null ) {
            try {
                response.sendRedirect(base_url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            int aid = (int) request.getSession().getAttribute("aid");
            String name = (String) request.getSession().getAttribute("name");
            adm.setAid(aid);
            adm.setName(name);
        }
        return adm;
    }

    public static final String aboutString = "Hello, my name is Mert Dumanlı. I am a junior computer engineer (100% English Education), graduated at Manisa Celal Bayar University. I'm from Ödemiş, the district of İzmir. I create my own world by writing code. In this world, I am free on many issues, there are some restrictions, but it is up to me to create options for them. I believe I can do anything if I make the necessary effort. This profession is my childhood dream. I represented my school in a knowledge contest when I was a senior in secondary school. I played football in youth setup. I attended a nature summer camp activity. It is said that I always have good relations with people. I am interested in adapting easily to new technologies, doing technical research and problem solving. I am always open to learning and improving myself.";
}
