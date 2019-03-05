<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:useBean id="oldInquiredOrder" class="com.itmoshop.data.ItemOrder" scope="request"/>


<div class="registration-form">
    <div class="container">
        <div class="dreamcrub">
            <ul class="breadcrumbs">
                <li class="home">
                    <a href="/index" title="Go to Home Page">Home</a>&nbsp;
                    <span>&gt;</span>
                </li>
                <li class="women">
                    Account
                </li>
            </ul>
            <div class="clearfix"></div>
        </div>


        <h2>Registrational information</h2>

        <div class="registration-grids">
            <div class="reg-form">
                <div class="reg">
                    <jsp:useBean id="existingAccount" class="com.itmoshop.data.Account" scope="request"/>
                    <form:form method="post" action="/account/change" modelAttribute="changedAccount">
                        <ul>
                            <li class="text-info">First Name:</li>
                            <li><input type="text" name="firstName" value="${existingAccount.firstName}" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Last Name:</li>
                            <li><input type="text" name="lastName" value="${existingAccount.lastName}" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Email:</li>
                            <li><input type="text" name="email" value="${existingAccount.email}" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Password:</li>
                            <li><input type="password" name="password" value="${existingAccount.password}"></li>
                        </ul>
                        <ul>
                            <li class="text-info">Re-enter Password:</li>
                            <li><input type="password" value="${existingAccount.password}"></li>
                        </ul>
                        <ul>
                            <li class="text-info">Mobile Number:</li>
                            <li><input type="text" name="mobile" value="${existingAccount.mobile}" required></li>
                        </ul>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <ul>
                                <li class="text-info">Current Role:</li>
                                <li><input type="text" name="role" value="${existingAccount.role}"></li>
                            </ul>
                        </sec:authorize>
                        <input type="submit" value="CHANGE ACCOUNT INFO">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <p class="click">By clicking this button, you are agree to my <a href="#">Policy Terms and
                            Conditions.</a></p>
                    </form:form>
                    <c:if test="${error != null}">
                        <p style="color: darkred">Cannot change to following email. Please enter another one.</p>
                    </c:if>
                </div>
            </div>
            <div class="reg-right">
                <h3>Your orders</h3>
                <c:if test="${ordersSet.size() == 0}">
                    <p>No orders were placed.</p>
                </c:if>
                <c:forEach items="${ordersSet}" var="oldInquiredOrder">
                    <div class="strip"></div>
                    <a href="/order/info?orderId=${oldInquiredOrder.id}">Order ${oldInquiredOrder.id}</a>

                    <p>Status:
                        <c:choose>
                            <c:when test="${oldInquiredOrder.newOrder == true}">
                                <c:out value="New, "/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="Old, "/>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${oldInquiredOrder.confirmedOrder == true}">
                                <c:out value="Confirmed"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="Not Confirmed"/>
                            </c:otherwise>
                        </c:choose>
                    </p>

                    <p>Order Time: ${oldInquiredOrder.orderDateTime}</p>

                    <p>Order Sum: ${oldInquiredOrder.orderSum} $</p>

                    <div class="strip"></div>
                    <c:if test="${oldInquiredOrder.confirmedOrder == false}">
                        <form action="/order/delete" method="post">
                            <input type="hidden" name="orderId" value="${oldInquiredOrder.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="submit" value="Delete order"
                                   onclick="emptyCartOnSessionOrderDelete(${currentSessionOrder.id}, ${oldInquiredOrder.id})">
                        </form>
                    </c:if>
                    <div class="strip"></div>
                </c:forEach>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<!-- registration-form -->

<jsp:include page="_footer.jsp"/>