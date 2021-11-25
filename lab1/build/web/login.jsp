FF<%-- 
    Document   : index
    Created on : May 14, 2021, 11:21:50 AM
    Author     : pc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta name="google-signin-scope" content="profile email" />
        <meta
            name="google-signin-client_id"
            content="953934748934-9r8kja4btcskanr7saldk1h92ruem59k.apps.googleusercontent.com" />

        <!--send email-->
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/emailjs-com@2/dist/email.min.js"></script>
        <script type="text/javascript">
            (function () {
                emailjs.init("user_IPz57UYggjwMZpkHZDP8h");
            })();
        </script>


        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <link rel="stylesheet" href="./css/index.css">
    </head>
    <body>
        <div class="form-wrapper">
            <form id="LoginForm" class="form" action="MainController" method="POST">
                <h2>Login</h2>
                <div>
                    <p>Username:</p> 
                    <input type="text" name="username" value="${param.username}" required/>
                </div>
                <div>           
                    <p>Password:</p>
                    <input type="password" name="password" value="${param.password}" required/>
                </div>
                <div class="g-recaptcha" name="reCAPTCHA" data-sitekey="6Lf6ztQaAAAAACbG6Ks5r9g_DZ3Cz-qhjMfUriZ-"></div>

                <span class="text-danger">${requestScope.INVALID_VALIDATION}</span>
                <input type="submit" class="btn btn-success" name="action" value="Login" />
                <span class="redirect-button" onclick="showSignUp()">Don't have account? Let sign up.</span>
                <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>

            </form>
            <form id="SignUpForm" class="form signup-form" action="MainController" method="POST" autocomplete="off">
                <h2>Sign Up</h2>
                <div>
                    <p>Email<span class="text-danger">*</span>:</p> 
                    <input type="email" name="usernamesu" value="${param.usernamesu}" autocomplete="off"/>
                </div>
                <span class="text-danger">${requestScope.INVALID_SIGNUP.username}</span>

                <div>           
                    <p>Password<span class="text-danger">*</span>:</p>
                    <input type="password" name="passwordsu" value="${param.passwordsu}" autocomplete="off"/>
                </div>
                <span class="text-danger">${requestScope.INVALID_SIGNUP.password}</span>

                <div>           
                    <p>Confirm<span class="text-danger">*</span>:</p>
                    <input type="password" name="rePasswordsu" value="${param.rePasswordsu}"/>
                </div>
                <span class="text-danger">${requestScope.INVALID_SIGNUP.confirmPassword}</span>
                <div>           
                    <p>Fullname<span class="text-danger">*</span>:</p>
                    <input type="text" name="fullnamesu" value="${param.fullnamesu}"/>
                </div>
                <span class="text-danger">${requestScope.INVALID_SIGNUP.fullname}</span>

                <div>           
                    <p>Address<span class="text-danger">*</span>:</p>
                    <input type="text" name="addresssu" value="${param.addresssu}"/>
                </div>
                <span class="text-danger">${requestScope.INVALID_SIGNUP.address}</span>

                <div>           
                    <p>Phone<span class="text-danger">*</span>:</p>
                    <input type="tel" name="phonesu" value="${param.phonesu}"/>
                </div>
                <span class="text-danger">${requestScope.INVALID_SIGNUP.phone}</span>

                <div class="g-recaptcha" name="reCAPTCHA" data-sitekey="6Lf6ztQaAAAAACbG6Ks5r9g_DZ3Cz-qhjMfUriZ-"></div>

                <span class="text-danger">${requestScope.INVALID_VALIDATION_SIGNUP}</span>
                <input type="submit" class="btn btn-success" name="action" value="Sign Up" />
                <span class="redirect-button" onclick="showLogin()">Already have account? Let login.</span>

            </form>
        </div>
        <form action="MainController" method="POST" style="display: none">
            <input type="hidden" id="gmail" name="gmail"/>
            <input type="hidden" id="fullname" name="fullname"/>
            <input type="submit" id="ggButton" name="action" value="LoginGoogle"/>
        </form>

        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

        <script>

                    const showSignUp = () => {
                        document.getElementById("SignUpForm").style.display = "flex";
                        document.getElementById("LoginForm").style.display = "none";

                    }

                    const showLogin = () => {
                        document.getElementById("SignUpForm").style.display = "none";
                        document.getElementById("LoginForm").style.display = "flex";
                    }

            <c:if test="${not empty requestScope.INVALID_VALIDATION_SIGNUP || not empty INVALID_SIGNUP}">
                    showSignUp();
            </c:if>
            <c:if test="${not empty requestScope.SIGNUP_SUCCESS}">
                    swal("Notification", "${requestScope.SIGNUP_SUCCESS}", "success");
            </c:if>

        </script>
        <script>
            var profile;
            function onSignIn(googleUser) {
                googleUser.disconnect();
                // Useful data for your client-side scripts:
                profile = googleUser.getBasicProfile();
                login();
            }

            const login = () => {
                document.getElementById("gmail").value = profile.getEmail();
                document.getElementById("fullname").value = nonAccentVietnamese(profile.getName()).toUpperCase();
                document.getElementById("ggButton").click();
            }

            function nonAccentVietnamese(str) {
                str = str.toLowerCase();
//     We can also use this instead of from line 11 to line 17
//     str = str.replace(/\u00E0|\u00E1|\u1EA1|\u1EA3|\u00E3|\u00E2|\u1EA7|\u1EA5|\u1EAD|\u1EA9|\u1EAB|\u0103|\u1EB1|\u1EAF|\u1EB7|\u1EB3|\u1EB5/g, "a");
//     str = str.replace(/\u00E8|\u00E9|\u1EB9|\u1EBB|\u1EBD|\u00EA|\u1EC1|\u1EBF|\u1EC7|\u1EC3|\u1EC5/g, "e");
//     str = str.replace(/\u00EC|\u00ED|\u1ECB|\u1EC9|\u0129/g, "i");
//     str = str.replace(/\u00F2|\u00F3|\u1ECD|\u1ECF|\u00F5|\u00F4|\u1ED3|\u1ED1|\u1ED9|\u1ED5|\u1ED7|\u01A1|\u1EDD|\u1EDB|\u1EE3|\u1EDF|\u1EE1/g, "o");
//     str = str.replace(/\u00F9|\u00FA|\u1EE5|\u1EE7|\u0169|\u01B0|\u1EEB|\u1EE9|\u1EF1|\u1EED|\u1EEF/g, "u");
//     str = str.replace(/\u1EF3|\u00FD|\u1EF5|\u1EF7|\u1EF9/g, "y");
//     str = str.replace(/\u0111/g, "d");
                str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
                str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
                str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
                str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
                str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
                str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
                str = str.replace(/đ/g, "d");
                // Some system encode vietnamese combining accent as individual utf-8 characters
                str = str.replace(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // Huyền sắc hỏi ngã nặng 
                str = str.replace(/\u02C6|\u0306|\u031B/g, ""); // Â, Ê, Ă, Ơ, Ư
                return str;
            }
            <c:if test="${not empty param.INVALID}">
            swal("Warning!", "${param.INVALID}");
            </c:if>
        </script>
        <script>

        </script>

        <!--        <script>
        <c:if test="${not empty requestScope.SIGNUP_USER}">
        const link = `Go to this link to activate your account:` + window.location.host + `/lab1/MainController?username=${requestScope.SIGNUP_USER.userid}&key=${requestScope.ACTIVE_CODE}&action=VerifyAccount`;
        const sendEmail = (params) => {
            var tempParams = {
                from_name: 'Manager Aplication',
                to_email: '${requestScope.SIGNUP_USER.userid}',
                to_name: '${requestScope.SIGNUP_USER.fullname}',
                message: link,
            }
            emailjs.send('service_4ygzp06', 'template_jgq569s', tempParams).then(res => {
                console.log(res.status);
            })
        }
        sendEmail();
        </c:if>

    </script>-->
    </body>
</html>
