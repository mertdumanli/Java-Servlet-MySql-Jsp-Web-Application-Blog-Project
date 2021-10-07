<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="props.Admin" %>
<%@page import="props.Product" %>
<%@page import="java.util.List" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<%Admin adm = util.isLogin(request, response); %>

<jsp:useBean id="dbUtil" class="utils.DBUtil"></jsp:useBean>
<%
    int aid = adm.getAid();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Management</title>
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
        <div class="col-sm-4">
            <h2>Yazı Ekle</h2>

            <%
                Object addErrorObject = request.getAttribute("addProductFeedBack");
                if (addErrorObject != null) {
                    String errorMessage = (String) addErrorObject;
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Hata!</strong> <%=errorMessage %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% } %>

            <form action="product-add-servlet" method="post">
                <div class="mb-3">
                    <input type="text" name="title" placeholder="Title" class="form-control" required/>
                </div>
                <div class="mb-3">
                    <input type="text" name="summary" placeholder="Summary" class="form-control" required/>
                </div>
                <div class="mb-3">
                    <textarea rows="10" cols="50" name="article" placeholder="Article" class="form-control"
                              required/></textarea>
                </div>
                <button class="btn btn-success"> Kaydet</button>
            </form>
        </div>
        <div class="col-sm-8">
            <h2>Yazılarım</h2>


            <%---------------------------------------Error Message----------------------------------------------------%>
            <%
                Object deleteProductObj = request.getAttribute("deleteProductFeedback");
                if (deleteProductObj != null) {
                    String message = (String) deleteProductObj;
            %>

            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Result!</strong> <%=message %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%
                }
            %>
            <%--------------------------------------------------------------------------------------------------------%>
            <%
                Object editProductObj = request.getAttribute("editProductFeedback");
                if (editProductObj != null) {
                    String messageEdit = (String) editProductObj;

            %>

            <div class="alert alert-warning">
                <strong>Result!</strong>
                <%=messageEdit%>
            </div>

            <%
                }
            %>

            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Pid</th>
                    <th scope="col">Başlık</th>
                    <th scope="col">Özet</th>
                    <th scope="col">Makale</th>
                    <th scope="col">Değiştir</th>
                </tr>
                </thead>
                <tbody>

                <%
                    Object number = request.getAttribute("numberObject");
                    List<Product> allProduct = dbUtil.allProduct(aid, number);
                    //System.out.println(allProduct);
                    if (allProduct.size() == 0) {

                %>
                <tr>
                    Yazı yok
                </tr>
                <%
                    }
                %>

                <%
                    if (allProduct.size() > 0) {

                        for (Product item : allProduct) {
                            String pid = Integer.toString(item.getPid());
                            //Makale kısmının tamamını değilde en fazla 100 karakterini göstererek daha güzel bir görüntü getirildi.
                            int maxChar = 0;
                            if (item.getArticle().length() > 100) {
                                maxChar = 100;
                            } else {
                                maxChar = item.getArticle().length();
                            }


                %>

                <tr>
                    <th scope="row"><%=item.getPid()%>
                    </th>
                    <td><%=item.getTitle()%>
                    </td>
                    <td><%=item.getSummary()%>
                    </td>
                    <td><%=item.getArticle().substring(0, maxChar)%>
                    </td>
                    <td>
                        <a href="product-edit-servlet?pid=<%=pid%>" class="btn btn-warning btn-sm">Düzenle</a>
                        <a onclick="return show_alert()" href="product-delete-servlet?pid=<%=pid%>"
                           class=" btn btn-danger btn-sm">Sil</a>
                    </td>
                </tr>

                <%
                        }
                    }
                %>


                </tbody>
            </table>

        </div>
    </div>


    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <script>
        function show_alert() {
            return confirm("Silmek istediğinizden emin misniz?")
        }
    </script>

</div>
</body>
</html>
