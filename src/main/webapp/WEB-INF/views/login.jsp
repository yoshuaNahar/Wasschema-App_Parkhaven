<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="nl-NL">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="description" content="Wasrooster van de studentenflat Parkhaven in Rotterdam"/>
    <meta name="keywords" content=""/>
    <title>Parkhaven - Was Schema</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/login-and-signup-form.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/alert-box.css"/>
</head>
<body>

<div id="alert_div" class="alert alert-success">
    <a href="" class="close" data-dismiss="alert" aria-label="close">
        <span aria-hidden="true">&times;</span>
    </a> ${requestScope.message}
</div>

<div class="initial_page">
    <div class="initial_form">
        <a href="/WasSchema"><img id="logo"
                                  src="${pageContext.request.contextPath}/resources/img/parkhaven-logo.png"/></a>
        <form id="register_form" action="" method="post">
            <input name="email" type="email" placeholder="email address" required/>
            <input name="firstName" type="text" placeholder="firstname"/>
            <input name="lastName" type="text" placeholder="lastname"/>
            <input id="password" name="password" type="password" placeholder="password" required/>
            <input id="confirm_password" type="password" placeholder="repeat password" required
                   pattern=""
                   title="Password not identical"/>
            <input name="houseNumber" type="text" placeholder="housenumber" required/>
            <input name="sharedPassword" type="text" placeholder="shared code" required/>
            <input name="to_servlet" value="signup" style="display: none;"/>
            <button type="submit">create</button>
            <div class="message">
                <p class="float_right">
                    Already registered? <a href="#" class="switch_between_login_create_form">Sign
                    In</a>
                </p>
            </div>
        </form>
        <form id="login_form" action="" method="post">
            <input name="email" type="email" placeholder="email" required/>
            <input name="password" type="password" placeholder="password" required/>
            <input name="to_servlet" value="login" style="display: none;"/>
            <button id="login_button" type="submit">login</button>
            <div class="message">
                <p class="float_left">
                    <a class="goto_forgot">Forgot password?</a>
                </p>
                <p class="float_right">
                    <a href="#" class="switch_between_login_create_form">Create an account</a>
                </p>
            </div>
        </form>
        <form id="forgot_password_form" action="" method="post">
            <input name="email" type="email" placeholder="email" required/>
            <input name="to_servlet" value="forgotPassword" style="display: none;"/>
            <button type="submit">send mail</button>
            <div class="message">
                <p class="float_right">
                    <a href="#" class="goto_forgot">Sign In</a>
                </p>
            </div>
        </form>
    </div>
</div>

<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script>
  $(function () {
    // toggle the forms to appear and disappear when pressing the links
    $('.switch_between_login_create_form').click(function () {
      $('#login_form').animate({
        height: "toggle",
        opacity: "toggle"
      }, "slow");
      $('#register_form').animate({
        height: "toggle",
        opacity: "toggle"
      }, "slow");
    });
    $('.goto_forgot').click(function () {
      $('#forgot_password_form').animate({
        height: "toggle",
        opacity: "toggle"
      }, "slow");
      $('#login_form').animate({
        height: "toggle",
        opacity: "toggle"
      }, "slow");
    });

    // toggle alert message
    if ("${requestScope.message}" != "") {
      $('#alert_div').animate({
        height: "toggle",
        opacity: "toggle"
      }, "slow").delay(10000);
      $('#alert_div').animate({
        height: "toggle",
        opacity: "toggle"
      }, "slow");
    }
    $('.close').click(function () {
      $('#alert_div').animate({
        height: "toggle",
        opacity: "toggle"
      }, "slow");
    });

    // check password identical in signup form
    $('#password').focusout(function () {
      var pass = $('#password').val();
      $('#confirm_password').attr("pattern", pass);
    });
  });
</script>

</body>
</html>