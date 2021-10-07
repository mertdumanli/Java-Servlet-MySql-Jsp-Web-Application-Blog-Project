<html>
<head>
    <title>Contact</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>

<div class="container mt-3">
    <div class="row justify-content-md-end">
        <div class="col-md-4 offset-md-4">
            <a href="home-servlet" method="get" class="btn btn-primary">HOME</a>
            <a href="about-servlet" method="get" class="btn btn-primary">ABOUT</a>
            <a href="contact-servlet" method="get" class="btn btn-primary">CONTACT</a>
        </div>
    </div>


    <div style="text-align:center" class="m-3">

        <img width="80%" class="center"
             src="picture/contactImage.jpg">

    </div>

    <div style="text-align: center" class="m-3">
        <label class="text">Can you share your requests and complaints with us? We will get back to you as soon as
            possible.</label>
    </div>


    <div style="width: 50%; margin: 0 auto;">

        <form action="contact-servlet" method="post">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="">First and Last Name</span>
                </div>
                <input name="name" type="text" class="form-control" placeholder="Name" required>
                <input name="surname" type="text" class="form-control" placeholder="Last Name" required>
            </div>

            <div class="mb-3">
                <input name="email" type="email" class="form-control" placeholder="E-Mail" required/>
            </div>
            <div class="mb-3">
                <input name="tel" type="tel" class="form-control" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
                       placeholder="Tel (xxx-xxx-xxxx)" required>
            </div>
            <div class="mb-3">
                <textarea rows="4" cols="50" name="message" class="form-control" placeholder="Message"
                          required/></textarea>
            </div>
            <input class="btn btn-success" type="submit" value="Send"/>
        </form>

        <%
            Object sendContact = request.getAttribute("sendContact");
            if (sendContact != null) {
                String answerContact = (String) sendContact;
                if (!answerContact.contains("başarısız")) {
        %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>Result!</strong> <%=answerContact %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% } else {
        %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Result!</strong> <%=answerContact %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <%
                }
            }
        %>

        <div class="navbar navbar-inverse navbar-fixed-bottom mb-3" style="width: 40%;margin: 0 auto">
            <div class="container">
                <p class="navbar-text" style="font-weight: bold; text-align: center">Copyright &#169;mertdumanlicse
                    Website
                    2021</p>
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
</div>


</body>
</html>
