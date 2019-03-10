<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="currentSessionOrder" class="com.itmoshop.data.ItemOrder" scope="session"/>
<jsp:useBean id="orderedBooks" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="reqestedBooks" class="java.util.ArrayList" scope="request"/>

<!-- checkout -->

<script>
    function deleteItem(itemRef) {
        document.getElementById(itemRef).click();
    }
</script>

<div class="cart-items">
    <div class="container">
        <div class="dreamcrub">
            <ul class="breadcrumbs">
                <li class="home">
                    <a href="/index" title="Go to Home Page">Home</a>&nbsp;
                    <span>&gt;</span>
                </li>
                <li class="women">
                    Cart
                </li>
            </ul>
            <div class="clearfix"></div>
        </div>
        <h2>MY SHOPPING CART (<%=reqestedBooks.size()%>)</h2>

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
                                    <form action="/checkout/delItem" method="post">
                                        <input type="hidden" name="bookId" id="bookId" value="${orderedBook.key.id}">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="submit" value="Remove Item"
                                               onclick="deleteItem('del${orderedBook.key.id}Item')">
                                    </form>
                                </span>

                                <div class="clearfix"></div>
                            </div>
                        </div>
                        <div class="clearfix"></div>

                    </div>
                </div>
            </c:forEach>
            <c:if test="${confirmed == null && orderedBooks.size() != 0}">
                <form action="/checkout/confirm" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div align="right"><input type="submit" value="Confirm order" onclick="emptyCart()"></div>
                </form>
            </c:if>
            <c:if test="${confirmed != null}">
                <p>Thank you!</p>

                <p>Your order has been confirmed!</p>

                <p>Manager will contact you shortly.</p>

                <p>Now you can see the order status in your account or place new order.</p>
            </c:if>
        </div>
    </div>
</div>

<!-- //checkout -->

<jsp:include page="_footer.jsp"/>