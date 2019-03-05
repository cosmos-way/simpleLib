<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="currentSessionOrder" class="com.itmoshop.data.ItemOrder" scope="session"/>
<jsp:useBean id="oldInquiredOrder" class="com.itmoshop.data.ItemOrder" scope="request"/>
<jsp:useBean id="orderedBooks" class="java.util.HashMap" scope="request"/>



<div class="cart-items">
    <div class="container">
        <div class="dreamcrub">
            <ul class="breadcrumbs">
                <li class="home">
                    <a href="/index" title="Go to Home Page">Home</a>&nbsp;
                    <span>&gt;</span>
                </li>
                <li class="women">
                    Order Info
                </li>
            </ul>
            <div class="clearfix"></div>
        </div>

        <h2>ORDER INFO</h2>
        <a href="">Order Id: ${oldInquiredOrder.id}</a>

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
                       onclick="emptyCartOnSessionOrderDelete(${currentSessionOrder.id}, ${oldInquiredOrder.id})">>
            </form>
        </c:if>
        <div class="cart-gd">
            <c:forEach items="${orderedBooks}" var="orderedBook" varStatus="bookCount">
                <div class="simpleCart_shelfItem">
                    <span hidden class="item_price">${orderedBook.key.price}</span>
                    <span hidden class="item_quantity">-${orderedBook.value}</span>
                    <a hidden id="del${orderedBook.key.id}Item" href="#" class="item_add"></a>
                </div>
                <div class="cart-header${bookCount.count}">
                    <div class="cart-sec">
                        <div class="cart-item cyc">
                            <img src="/images/book_covers/${orderedBook.key.coverPicFileName}" class="img-responsive"
                                 alt=""
                                 style="width: auto; height: 250px; position: relative">
                        </div>
                        <div class="cart-item-info simpleCart_shelfItem">
                            <h3><a href="/single/${orderedBook.key.id}"> ${orderedBook.key.title}</a></h3>

                            <div class="delivery">
                                <p>${orderedBook.value} X ${orderedBook.key.price}
                                    = ${orderedBook.key.price * orderedBook.value} $</p>
                                <span>
                                </span>

                                <div class="clearfix"></div>
                            </div>
                        </div>
                        <div class="clearfix"></div>

                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>



<jsp:include page="_footer.jsp"/>