<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <script>
        function postToDbCart() {
            document.activeElement.parentElement.submit();
        }

        function postToDbCart2() {
            document.activeElement.parentElement.parentElement.submit();
        }
        function emptyCart() {
            document.getElementById("emptyCart").click();
        }
        function emptyCartOnSessionOrderDelete(sessionOrderId, orderToDelId) {
            if (sessionOrderId == orderToDelId) {
                emptyCart();
            }
        }
    </script>
    <title>ITMO Shop</title>
    <link href="../css/bootstrap.css" rel='stylesheet' type='text/css'/>
    <script src="../js/jquery.min.js"></script>
    <link href="../css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="../css/component.css" rel='stylesheet' type='text/css'/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content=""/>
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);
    function hideURLbar() {
        window.scrollTo(0, 1);
    } </script>

    <script type="text/javascript" src="../js/bootstrap-3.1.1.min.js"></script>

    <script src="../js/simpleCart.min.js"></script>

    <link rel="stylesheet" href="../css/flexslider.css" type="text/css" media="screen"/>
</head>
<body>

<div class="header">
    <div class="header-top-strip">
        <div class="container">
            <div class="header-top-left">
                <ul>
                    <sec:authorize access="isAnonymous()">
                        <li><a href="/login"><span class="glyphicon glyphicon-user"> </span>Login</a></li>
                        <li><a href="/register"><span class="glyphicon glyphicon-lock"> </span>Create an Account</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li>
                            <a href="/account"><span class="glyphicon glyphicon-user"> </span>
                                <sec:authentication property="principal.username"/></a>
                        </li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <li>
                                <a href="/admin"><span class="glyphicon glyphicon-lock"> </span>Admin</a>
                            </li>
                        </sec:authorize>
                        <li>
                            <form action="/checkout/empty" method="post">
                                <a href="/logout" onclick="emptyCart()">
                                    <span class="glyphicon glyphicon-lock"> </span>
                                    Logout
                                </a>
                            </form>
                        </li>
                    </sec:authorize>
                </ul>
            </div>
            <div class="header-right">
                <div class="cart box_1">
                    <a href="/checkout">
                        <h3><span class="simpleCart_total"> $0.00 </span> (<span id="simpleCart_quantity"
                                                                                 class="simpleCart_quantity"> 0 </span>)<img
                                src="../images/bag.png" alt=""></h3>
                    </a>

                    <form action="/checkout/empty" method="post" style="float: right; ">
                        <input type="hidden" name="txtUrl" value="">
                        <p><a href="#" id="emptyCart" class="simpleCart_empty" onclick="postToDbCart2()">Empty cart</a></p>
                    </form>

                    <div class="clearfix"></div>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="inner-banner">
    <div class="container">
        <div class="banner-top inner-head">
            <nav class="navbar navbar-default" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <div class="logo">
                        <h1><a href="/index"><span>IT</span> -Shop</a></h1>
                    </div>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="/index">HOME</a></li>
                        <li><a href="/contact">CONTACT</a></li>
                    </ul>
                </div>
            </nav>
        </div>
    </div>
</div>