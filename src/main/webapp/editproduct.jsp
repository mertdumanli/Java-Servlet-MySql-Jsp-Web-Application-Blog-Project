<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="props.Admin" %>
<%@page import="props.Product" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<% Admin adm = util.isLogin(request, response); %>

<jsp:useBean id="dbUtil" class="utils.DBUtil"></jsp:useBean>
<%
    int aid = adm.getAid();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Product Edit</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">

                <a class="nav-link" href="login-servlet">Management
                </a>

                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            Profile
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="change-password-servlet?aid=${aid}">Change Password</a>
                            </li>
                            <li><a class="dropdown-item" href="log-out-servlet">Logout</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true"><%=adm.getName()%>
                        </a>
                    </li>
                </ul>
                <form class="d-flex" action="product-search-servlet" method="get">
                    <input name="search" class="form-control" type="text" placeholder="Search">
                    <button class="btn btn-outline-success"> Search</button>
                </form>
            </div>
        </div>
    </nav>
    <hr/>

    <div class="row">
        <div class="col-sm-6">
            <h2>Ürün Düzenle</h2>

            <%
                Product pro = new Product();
                if (request.getAttribute("pro") != null) {
                    pro = (Product) request.getAttribute("pro");
                    System.out.println(pro.getSummary());
                }
            %>
            <form action="product-edit-servlet?aid=<%=aid%>" method="post">
                <div class="mb-3">
                    <input value="<%=pro.getTitle()%>" type="text" name="title" placeholder="Title" class="form-control"
                           required/>
                </div>
                <div class="mb-3">
                    <input value="<c:out value="${pro.getSummary()}"/>" type="text" name="summary" placeholder="Summary"
                           class="form-control" required/>
                </div>
                <div class="mb-3">
                    <textarea rows="10" cols="50" name="article" placeholder="Article"
                              class="form-control"
                              required/><%=pro.getArticle()%></textarea>
                </div>
                <button class="btn btn-success">Edit</button>
            </form>
        </div>

    </div>


    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

</div>
</body>
</html>
