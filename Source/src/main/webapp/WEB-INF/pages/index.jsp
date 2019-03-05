<jsp:include page="_header.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="currentSessionOrder" class="com.itmoshop.data.ItemOrder" scope="session"/>



<!-- content-section-starts-here -->

<div class="container">
    <div class="main-content">

        <div class="products-grid">
            <header>
                <h3 class="head text-center">Recently added books</h3>
            </header>

            <%--Books grid starts here--%>

            <c:forEach items="${recentBooks}" var="book">
                <div class="col-md-4 product simpleCart_shelfItem  text-center">
                    <a href="/single/${book.id}"><img src="${book.imageURLlarg}"
                                                      alt=""
                                                      style="width: auto; height: 250px; position: relative"/></a>
                    <a class="like_name" href="/single/${book.id}">${book.title}</a>
                    <p class="like_name">${book.author}</p>

                    <div class="simpleCart_shelfItem">
                        <form action="/order/addItem" method="post">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="hidden" name="txtUrl" value="">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" class="item_quantity" name="bookQuantity" value="1">

                            <%--<sec:authorize access="isAuthenticated()">--%>
                                <%--<p><a class="item_add" href="#" onclick="postToDbCart2();">--%>
                                    <%--<i></i><span class="item_price">${book.price}$</span></a></p>--%>
                            <%--</sec:authorize>--%>
                            <%--<sec:authorize access="isAnonymous()">--%>
                                <%--<p><a href="/login">--%>
                                    <%--<i></i><span class="item_price">${book.price}$</span></a></p>--%>
                            <%--</sec:authorize>--%>
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