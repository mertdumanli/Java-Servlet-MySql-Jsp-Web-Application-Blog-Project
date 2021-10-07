<%@ page import="props.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="util" class="utils.Util"></jsp:useBean>

<jsp:useBean id="dbUtil" class="utils.DBUtil"></jsp:useBean>
<%
    List<Product> allProduct = dbUtil.getAllArticle();
%>
<html>
<head>
    <title>Home Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<br/>


<div class="container">
    <div class="row justify-content-md-end">
        <div class="col-md-4 offset-md-4">
            <a href="home-servlet" method="get" class="btn btn-primary" style="border-radius: 15px">HOME</a>
            <a href="about-servlet" method="get" class="btn btn-primary" style="border-radius: 15px">ABOUT</a>
            <a href="contact-servlet" method="get" class="btn btn-primary" style="border-radius: 15px">CONTACT</a>
        </div>
    </div>
</div>


<div style="text-align:center;margin-bottom: 10px;margin-top: 10px">

    <img width="80%" class="center"
         src="picture/indexImage.jpg">

</div>

<%
    if (allProduct.size() > 0) {
        //Collections.reverse(allProduct);
        for (Product item : allProduct) {
            String xTitle = item.getTitle();
            String xSummary = item.getSummary();
            String xArticle = item.getArticle();
            Date xDate = item.getDate();
            int xAid = item.getAid();//method ile yazanı bulacaksın.
            String nameToxAid = dbUtil.getAdminName(xAid);
%>

<div class="card border-primary text-center mx-auto"
     style="width: 60%; margin-bottom: 10px;font-family: Garamond">
    <a style="text-decoration: none" href="detail-article-servlet?product=<%=item.getPid()%>">
        <div class="card-header" style="background-color: floralwhite;border: none">
            <h2 style="color: darkred"><%=xTitle%>
            </h2>
        </div>
        <div class="card-body">
            <p class="card-text" style="color: black;font-size: 20px;border: none"><%=xSummary%>
            </p>
        </div>
        <div class="card-footer text-muted" style="background-color: white;border: none">
            <label style="color: darkblue; font-size: 16px">Posted by <%=nameToxAid%> on <%=xDate.toString()%>
            </label>
        </div>
    </a>
</div>

<%
        }
    }
%>

<div style="width: 50%; margin: 0 auto">

    <div class="navbar navbar-inverse navbar-fixed-bottom mb-3" style="width: 35%;margin: 0 auto">
        <div class="container">
            <p class="navbar-text" style="font-weight: bold; text-align: center">Copyright &#169;mertdumanlicse
                Website 2021</p>
        </div>
        <!-- target yeni sekme için, rel güvenlik için -->
        <a href="https://twitter.com/mertdumanli_cse/" target="_blank" rel="noopener noreferrer">
            <ion-icon name="logo-twitter"></ion-icon>
        </a>
        <a href="https://www.instagram.com/mertdumanli.cse/" target="_blank" rel="noopener noreferrer">
            <ion-icon name="logo-instagram"></ion-icon>
        </a>
        <a href="https://www.facebook.com/mertdumanli.cse/" target="_blank" rel="noopener noreferrer">
            <ion-icon name="logo-facebook"></ion-icon>
        </a>
        <!-- mailto genelde posta uygulamasını açıyor ama açmadığı durumlar olabilir. -->
        <a href="mailto:mertdumanli.cse@gmail.com" target="_blank" rel="noopener noreferrer">
            <ion-icon name="mail-open-outline"></ion-icon>
        </a>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>


</body>
</html>
