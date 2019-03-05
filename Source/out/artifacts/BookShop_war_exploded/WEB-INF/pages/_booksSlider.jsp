<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="other-products">
    <div class="container">
        <h3 class="like text-center">Featured books</h3>

        <ul id="flexiselDemo3">

            <c:forEach items="${featuredBooks}" var="book">
                <li><a href="/single/${book.id}"><img src="/resources/images/book_covers/${book.coverPicFileName}"
                                                      class="img-responsive"
                                                      style="margin: auto; width: auto; height: 300px; " alt=""/></a>

                    <div class="product liked-product simpleCart_shelfItem">
                        <a class="like_name" href="/single/${book.id}">${book.title}</a>

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
                </li>
            </c:forEach>
        </ul>

        <script type="text/javascript">
            $(window).load(function () {
                $("#flexiselDemo3").flexisel({
                    visibleItems: 4,
                    animationSpeed: 1000,
                    autoPlay: true,
                    autoPlaySpeed: 3000,
                    pauseOnHover: true,
                    enableResponsiveBreakpoints: true,
                    responsiveBreakpoints: {
                        portrait: {
                            changePoint: 480,
                            visibleItems: 1
                        },
                        landscape: {
                            changePoint: 640,
                            visibleItems: 2
                        },
                        tablet: {
                            changePoint: 768,
                            visibleItems: 3
                        }
                    }
                });

            });
        </script>
        <script type="text/javascript" src="../js/jquery.flexisel.js"></script>
    </div>
</div>