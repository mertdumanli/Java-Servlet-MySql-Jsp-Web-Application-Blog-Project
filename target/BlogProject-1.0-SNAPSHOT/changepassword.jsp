<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="props.Admin" %>
<%@page import="props.Product" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<%Admin adm = util.isLogin(request, response); %>

<jsp:useBean id="dbUtil" class="utils.DBUtil"></jsp:useBean>
<%
    int aid = adm.getAid();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <br/>
    <div class="row">
        <div class="col-sm-4 translate-middle"></div>
        <div class="col-sm-4">
            <h3>Change Password</h3>
            <%---------------------------------------Error Message----------------------------------------------------%>
            <%
                Object changePasswordObj = request.getAttribute("changeFeedback");
                if (changePasswordObj != null) {
                    String message = (String) changePasswordObj;
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
                String email = dbUtil.getEmail(adm.getAid());
            %>
            <form action="change-password-servlet" method="post">
                <div class="mb-3">
                    <input type="email" disabled class="form-control" name="email" value="<%=email%>" required>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control" name="oldPassword" placeholder="Old Password" required>
                </div>

                <div class="mb-3">
                    <input type="password" class="form-control" name="newPassword" placeholder="New Password" required>
                </div>

                <div class="mb-3">
                    <input type="password" class="form-control" name="newPasswordr" placeholder="New Password Repeat"
                           required>
                </div>
                <input class="btn btn-primary" type="submit" value="Change">
            </form>
        </div>
    </div>

</div>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>