<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="registration-form">
    <div class="container">
        <div class="dreamcrub">
            <ul class="breadcrumbs">
                <li class="home">
                    <a href="/index" title="Go to Home Page">Home</a>&nbsp;
                    <span>&gt;</span>
                </li>
                <li class="women">
                    Registration
                </li>
            </ul>
            <ul class="previous">
                <li><a href="/index">Back to Previous Page</a></li>
            </ul>
            <div class="clearfix"></div>
        </div>


        <h2>Registration</h2>

        <div class="registration-grids">
            <div class="reg-form">
                <div class="reg">
                    <p>Enter the following details to continue.</p>
                    <p>If you have previously registered <a href="#">click here</a></p>
                    <jsp:useBean id="newAccount" class="com.itmoshop.data.Account" scope="request"/>
                    <form:form method="post" action="/register" modelAttribute="newAccount">
                        <ul>
                            <li class="text-info">First Name:</li>
                            <li><input type="text" name="firstName" value="${newAccount.firstName}" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Last Name:</li>
                            <li><input type="text" name="lastName" value="${newAccount.lastName}" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Email:</li>
                            <li><input type="text" name="email" value="${newAccount.email}" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Password:</li>
                            <li><input type="password" name="password" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Re-enter Password:</li>
                            <li><input type="password" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Mobile Number:</li>
                            <li><input type="text" name="mobile" value="${newAccount.mobile}" required></li>
                        </ul>
                        <input type="submit" value="REGISTER NOW">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <p class="click">By clicking this button, you are agree to my <a href="#">Policy Terms and
                            Conditions.</a></p>
                        </form:form>
                    <c:if test="${error != null}">
                        <p style="color: darkred">Can't register following email. Please enter another one.</p>
                    </c:if>
                </div>
            </div>
            <div class="reg-right">
                <h3>Greetings!</h3>

                <div class="strip"></div>
                <p>We are glad to welcome you in our shop.</p>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>


<jsp:include page="_footer.jsp"/>