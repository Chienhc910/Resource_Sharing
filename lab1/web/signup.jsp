<%-- 
    Document   : signup
    Created on : May 15, 2021, 9:20:31 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up</title>
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <link rel="stylesheet" href="./css/signup.css">
    </head>
    <body>
        <div class="form-wrapper">
            <form id="SignUpForm" class="form signup-form" action="MainController" method="POST" autocomplete="off">
                <h2>Fill your information</h2>
                <div>
                    <p>Email:</p> 
                    <input type="email" name="usernamesu" value="${(not empty requestScope.EMAIL) ? requestScope.EMAIL : param.usernamesu}" readonly autocomplete="off"/>
                </div>

                <div>           
                    <p>Fullname:</p>
                    <input type="text" name="fullnamesu" value="${(not empty requestScope.FULLNAME)?requestScope.FULLNAME:param.fullnamesu}" required/>
                </div>

                <div>           
                    <p>Address:</p>
                    <input type="text" name="address" value="${param.address}" required/>
                </div>

                <div>           
                    <p>Phone:</p>
                    <input type="tel" name="phone" value="${param.phone}" required/>
                </div>
                <span class="text-danger">${requestScope.INVALID.phone}</span>

                <div class="g-recaptcha" name="reCAPTCHA" data-sitekey="6Lf6ztQaAAAAACbG6Ks5r9g_DZ3Cz-qhjMfUriZ-"></div>

                <span class="text-danger">${requestScope.INVALID_RECAPTCHA}</span>
                <input type="submit" class="btn btn-success" name="action" value="Submit Info" />

            </form>
        </div>

        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    </body>
</html>

