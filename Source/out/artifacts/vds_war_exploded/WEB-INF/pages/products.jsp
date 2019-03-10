<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:useBean id="currentSessionOrder" class="com.itmoshop.data.ItemOrder" scope="session"/>


<div class="container">
    <div class="products-page">
        <div class="products">
            <div class="product-listy">
                <h2>Books by years</h2>
                <ul class="product-list">
                    <c:forEach items="${booksPublishDates}" var="bookPublishDate">
                        <li><a href="/search?q=${bookPublishDate}">${bookPublishDate}</a></li>
                    </c:forEach>
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
        <div class="new-product">
            <div class="new-product-top">
                <ul class="product-top-list">
                    <li><a href="/index">Home</a>&nbsp;<span>&gt;</span></li>
                    <li><span class="act">Search</span>&nbsp;</li>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div id="cbp-vm" class="cbp-vm-switcher cbp-vm-view-grid">
                <div class="cbp-vm-options">
                    <a href="#" class="cbp-vm-icon cbp-vm-grid cbp-vm-selected" data-view="cbp-vm-view-grid"
                       title="grid">Grid View</a>
                    <a href="#" class="cbp-vm-icon cbp-vm-list" data-view="cbp-vm-view-list" title="list">List View</a>
                </div>
                <div class="clearfix"></div>
                <ul>
                    <c:forEach items="${booksFound}" var="book">
                    <li>

                            <div class="simpleCart_shelfItem">
                                <div class="view view-first">
                                    <a class="cbp-vm-image" href="/single/${book.id}">
                                    <div class="inner_content clearfix">
                                        <div class="product_image">
                                            <img src="${book.imageURLmedium}"
                                                 class="img-responsive" style="width: auto; height: 200px; " alt=""/>

                                            <div class="mask">
                                                <div class="info">Quick View</div>
                                            </div>
                                            <div class="product_container">
                                                <div class="cart-left">
                                                    <p class="title" style="width: 250px;
                                                          white-space: nowrap;
                                                          overflow: hidden;
                                                          text-overflow: ellipsis;">${book.title}</p>
                                                </div>

                                                <div class="clearfix"></div>
                                            </div>
                                        </div>
                                    </div>
                                    </a>
                                </div>


                        <div class="cbp-vm-details" style="width: 250px;
                                                          white-space: nowrap;
                                                          overflow: hidden;
                                                          text-overflow: ellipsis;">
                                ${book.author}
                        </div>
                    </div>
                        </a>
            </li>
            </c:forEach>
            </ul>
        </div>
        <script src="../js/cbpViewModeSwitch.js" type="text/javascript"></script>
        <script src="../js/classie.js" type="text/javascript"></script>
    </div>
    <div class="clearfix"></div>
</div>
<div class="clearfix"></div>
</div>


<jsp:include page="_booksSlider.jsp"/>


<jsp:include page="_footer.jsp"/>