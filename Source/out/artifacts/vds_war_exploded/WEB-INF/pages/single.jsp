<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="book" class="com.itmoshop.data.Book" scope="request"/>
<jsp:useBean id="currentSessionOrder" class="com.itmoshop.data.ItemOrder" scope="session"/>
<jsp:useBean id="currentSessionRequest" class="com.itmoshop.data.BookRequest" scope="session"/>


<div class="container">
    <div class="dreamcrub">
        <ul class="breadcrumbs">
            <li class="home">
                <a href="/index" title="Go to Home Page">Home</a>&nbsp;
                <span>&gt;</span>
            </li>
            <li class="women">
                ${book.title}
            </li>
        </ul>
        <div class="clearfix"></div>
    </div>

    <div class="products-page">
        <div class="products">
            <div class="product-listy">
                <h2>MORE BOOKS</h2>
                <ul class="product-list">
                    <li><a href="/search?q=${book.author}">Same author</a></li>
                    <li><a href="/search?q=${book.publisher}">Same publisher</a></li>
                    <li><a href="/search?q=${book.publishDate}">Same publish year</a></li>
                </ul>
            </div>
            <div class="tags">
                <h4 class="tag_head">Top Rated</h4>
                <ul class="tags_links">
                    <c:forEach items="${featuredBooks}" var="book">
                        <li><a href="/single/${book.id}">${book.title}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>


        <%--Book info--%>
        <div class="new-product">
            <div class="col-md-5 zoom-grid">
                <div class="flexslider">
                    <ul class="slides">
                        <li data-thumb="${book.imageURLlarg}">
                            <div class="thumb-image"><img src="${book.imageURLlarg}"
                                                          class="img-responsive" alt=""/></div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col-md-7 book-info">
                <div class="book-name">
                    <h3>${book.title}</h3>

                    <div class="clearfix"></div>
                    <p>${book.description}</p>
                </div>
                <div class="span span1">
                    <p class="left">AUTHOR</p>

                    <p class="right crop">${book.author}</p>

                    <div class="clearfix"></div>
                </div>
                <div class="span span1">
                    <p class="left">PUBLISHER</p>

                    <p class="right crop">${book.publisher}</p>

                    <div class="clearfix"></div>
                </div>
                <div class="span span1">
                    <p class="left">PUBLISH YEAR</p>

                    <p class="right crop">${book.yearOfPublication}</p>

                    <div class="clearfix"></div>
                </div>
                <div class="span span1">
                    <p class="left">ISBN</p>

                    <p class="right crop">${book.isbn}</p>

                    <div class="clearfix"></div>
                </div>
                <%--<div class="span span1">--%>
                    <%--<p class="left">PAGES</p>--%>

                    <%--<p class="right crop">${book.numOfPages}</p>--%>

                    <%--<div class="clearfix"></div>--%>
                <%--</div>--%>
                <%--<div class="span span3">--%>
                    <%--<p class="left">LANGUAGE</p>--%>

                    <%--<p class="right crop">${book.language}</p>--%>

                    <%--<div class="clearfix"></div>--%>
                <%--</div>--%>
                <div class="purchase">

                    <div class="simpleCart_shelfItem">
                        <p hidden><a class="item_add" href="#" id="addToCartLink">
                            <span class="item_price">${book.price} $</span>
                        </a></p>

                        <script>
                            function addToJsCart() {
                                document.getElementById('addToCartLink').click();
                            }
                            function redirectLogin() {
                                window.location="/login";
                            }
                        </script>

                        <form action="/bookRequest/addItem" method="post">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="hidden" id="txtUrl" name="txtUrl" value=""/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <script>document.getElementById('txtUrl').value = window.location.href;</script>
                            <sec:authorize access="isAnonymous()">
                                <input  type="button" value="Заказать" onclick="redirectLogin()">
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <input type="submit" value="Заказать" name=name >
                                <%--onclick="addToJsCart(); postToDbCart();"--%>
                            </sec:authorize>

                        </form>
                        <br>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <form action="/single/remove" method="post">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="submit" value="Delete book">
                            <input type="hidden" name="" value="">
                        </form>
                        </sec:authorize>

                    </div>
                    <%--<div class="social-icons">--%>
                        <%--<ul>--%>
                            <%--<li><a class="facebook1" href="https://ru-ru.facebook.com/spbifmo/"></a></li>--%>
                            <%--<li><a class="twitter1" href="https://twitter.com/spbifmo"></a></li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                    <div class="clearfix"></div>
                </div>

                <script defer src="../js/jquery.flexslider.js"></script>
                <script>

                    $(window).load(function () {
                        $('.flexslider').flexslider({
                            animation: "slide",
                            controlNav: "thumbnails"
                        });
                    });
                </script>
            </div>
            <div class="clearfix"></div>

            <%--<div class="reviews-tabs">--%>

                <%--<ul class="nav nav-tabs responsive hidden-xs hidden-sm" id="myTab">--%>
                    <%--<li class="test-class active"><a class="deco-none misc-class" href="#how-to">Description</a>--%>
                    <%--</li>--%>
                    <%--<li class="test-class"><a class="deco-none" href="#source">Reviews (${bookReviews.size()})</a></li>--%>
                <%--</ul>--%>

                <%--<div class="tab-content responsive hidden-xs hidden-sm">--%>
                    <%--<div class="tab-pane active" id="how-to">--%>
                        <%--<p class="tab-text">${book.description}</p>--%>
                    <%--</div>--%>
                    <%--<div class="tab-pane" id="source">--%>
                        <%--<div class="response">--%>
                            <%--<c:forEach items="${bookReviews}" var="bookReview">--%>
                                <%--<div class="media response-info">--%>
                                    <%--<div class="media-left response-text-left">--%>
                                        <%--<h5><a href="#"--%>
                                               <%--style="pointer-events: none; cursor: default; word-wrap: normal">--%>
                                                <%--${bookReview.posterUsername}--%>
                                        <%--</a></h5>--%>
                                    <%--</div>--%>
                                    <%--<div class="media-body response-text-right">--%>
                                        <%--<p>${bookReview.reviewText}</p>--%>
                                        <%--<ul>--%>
                                            <%--<li>${bookReview.reviewDate}</li>--%>
                                        <%--</ul>--%>
                                    <%--</div>--%>
                                    <%--<div class="clearfix"></div>--%>
                                <%--</div>--%>
                                <%--<div class="clearfix"></div>--%>
                            <%--</c:forEach>--%>
                        <%--</div>--%>
                        <%--<div class="media response-info">--%>
                            <%--<div class="media-left response-text-left">--%>
                                <%--<h5><a href="#"></a></h5>--%>
                            <%--</div>--%>
                            <%--<div class="media-body response-text-right">--%>
                                <%--<textarea form="reviewForm" name="bookReviewText" maxlength="500" rows="4"--%>
                                          <%--style="width: 100%"></textarea>--%>

                                <%--<form id="reviewForm" action="/single/add" method="post">--%>
                                    <%--<input type="hidden" name="bookId" value="${book.id}">--%>
                                    <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                                    <%--<input type="submit" value="Add review">--%>
                                <%--</form>--%>
                            <%--</div>--%>
                            <%--<div class="clearfix"></div>--%>
                        <%--</div>--%>
                        <%--<div class="clearfix"></div>--%>

                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>


</div>
<div class="clearfix"></div>

</div>
</div>


<jsp:include page="_booksSlider.jsp"/>

<script src="../js/responsive-tabs.js"></script>
<script type="text/javascript">
    $('#myTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    $('#moreTabs a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    (function ($) {
        $('.js-alert-test').click(function () {
            alert('Button Clicked: Event was maintained');
        });
        fakewaffle.responsiveTabs(['xs', 'sm']);
    })(jQuery);

</script>

<jsp:include page="_footer.jsp"/>