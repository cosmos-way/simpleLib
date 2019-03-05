<jsp:include page="_header.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="currentSessionOrder" class="com.itmoshop.data.ItemOrder" scope="session"/>



<!-- content-section-starts-here -->

<div class="container">
    <div class="main-content">
        <div class="online-strip">
            <div class="col-md-4 follow-us">
                <h3>follow us : <a class="twitter" href="https://twitter.com/spbifmo"></a><a class="facebook" href="https://ru-ru.facebook.com/spbifmo/"></a></h3>
            </div>
            <div class="col-md-4 shipping-grid">
                <div class="shipping">
                    <img src="../images/shipping.png" alt=""/>
                </div>
                <div class="shipping-text">
                    <h3>Free Shipping</h3>

                    <p>on orders over 9000$</p>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="col-md-4 online-itemOrder">
                <p>Order online</p>

                <h3>Tel:+7 999 999 99 99</h3>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="products-grid">
            <header>
                <h3 class="head text-center">Recently added books</h3>
            </header>

            <%--Books grid starts here--%>

            <c:forEach items="${recentBooks}" var="book">
                <div class="col-md-4 product simpleCart_shelfItem  text-center">
                    <a href="/single/${book.id}"><img src="/resources/images/book_covers/${book.coverPicFileName}"
                                                      alt=""
                                                      style="width: auto; height: 250px; position: relative"/></a>

                    <div class="mask">
                        <a href="/single/${book.id}">Quick View</a>
                    </div>
                    <a class="like_name" href="/single/${book.id}">${book.title}</a>

                    <div class="simpleCart_shelfItem">
                        <form action="/order/addItem" method="post">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="hidden" name="txtUrl" value="">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" class="item_quantity" name="bookQuantity" value="1">

                            <sec:authorize access="isAuthenticated()">
                                <p><a class="item_add" href="#" onclick="postToDbCart2();">
                                    <i></i><span class="item_price">${book.price}$</span></a></p>
                            </sec:authorize>
                            <sec:authorize access="isAnonymous()">
                                <p><a href="/login">
                                    <i></i><span class="item_price">${book.price}$</span></a></p>
                            </sec:authorize>
                        </form>
                    </div>
                </div>
            </c:forEach>

            <%--Books grid ends here--%>

            <div class="clearfix"></div>
        </div>
    </div>

</div>


<jsp:include page="_booksSlider.jsp"/>



<jsp:include page="_footer.jsp"/>