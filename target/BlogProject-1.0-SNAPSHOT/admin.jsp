<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<div class="container">
    <br/>
    <div class="row">
        <div class="col-sm-4 translate-middle"></div>
        <div class="col-sm-4">
            <h3>Admin Login</h3>


            <%---------------------------------------Error Message----------------------------------------------------%>
            <%
                Object loginObj = request.getAttribute("loginError");
                if (loginObj != null) {
                    String errorMessage = (String) loginObj;
            %>

            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Hata!</strong> <%=errorMessage %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%
                }
            %>
            <%--------------------------------------------------------------------------------------------------------%>

            <%---------------------------------------ChangePasswordMessage--------------------------------------------%>

            <%
                Object changeFeedBack = request.getAttribute("changeFeedBack");
                if (changeFeedBack != null) {
                    String strChangeFeedBack = (String) changeFeedBack;

            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <strong>Result!</strong> <%=strChangeFeedBack %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>

            <%
                }%>
            <%--------------------------------------------------------------------------------------------------------%>

            <form action="login-servlet" method="post">
                <div class="mb-3">
                    <input type="email" class="form-control" name="email" placeholder="E-Mail" required>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control" name="password" placeholder="Password" required>
                </div>

                <div class="mb-3 form-check">
                    <input name="remember" type="checkbox" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1">Remember</label>
                </div>

                <input class="btn btn-primary" type="submit" value="Login">
            </form>
        </div>
    </div>
    <div class="col-sm-4"></div>

</div>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>