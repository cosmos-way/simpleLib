<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- registration-form -->
<div class="registration-form">
    <div class="container">
        <div class="dreamcrub">
            <ul class="breadcrumbs">
                <li class="home">
                    <a href="/index" title="Go to Home Page">Home</a>&nbsp;
                    <span>&gt;</span>
                </li>
                <li class="women">
                    Admin Page
                </li>
            </ul>
            <div class="clearfix"></div>
        </div>


        <h2>Add books to server</h2>

        <div class="registration-grids">
            <div class="reg-form">
                <div class="reg">
                    <p>Add one book manually</p>



                    <c:if test="${addedBookId != null}">
                        <p>Book was successfully added with id #${addedBookId},
                            <a href="/single/${addedBookId}">visit book page</a></p>
                    </c:if>

                    <form:form method="post" action="/admin/addSingle" modelAttribute="coverPicFile"
                               enctype="multipart/form-data">
                        <ul>
                            <li class="text-info">Title :</li>
                            <li><input type="text" name="title" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Publisher :</li>
                            <li><input type="text" name="publisher" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Author :</li>
                            <li><input type="text" name="author" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">ISBN number :</li>
                            <li><input type="text" name="isbn" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Publish Date (ex. YYYY-MM-DD):</li>
                            <li><input type="text" name="publishDate" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Number of pages :</li>
                            <li><input type="text" name="numOfPages" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Language :</li>
                            <li><input type="text" name="language" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Description :</li>
                            <li><input type="text" name="description" value="" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Price (ex. 9.99):</li>
                            <li><input type="text" name="price" value="${newAccount.mobile}" required></li>
                        </ul>
                        <ul>
                            <li class="text-info">Cover picture (.jpg, .png, .gif etc):</li>
                            <li><input type="file" name="coverPicFile" value="" required></li>
                        </ul>
                        <input type="submit" value="Upload Book">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    </form:form>
                </div>

                <div class="reg">
                    <p>Add bundle of books as single .zip file containing .json and image files</p>
                    <c:if test="${addedBooks != null}">
                        <c:forEach items="${addedBooks}" var="book">
                            <p>Book was successfully added with id #${book.id},
                                <a href="/single/${book.id}">visit book page</a></p>
                        </c:forEach>
                    </c:if>

                    <form:form method="post" action="/admin/addMany" modelAttribute="zipArchive"
                               enctype="multipart/form-data">
                        <ul>
                            <li class="text-info">Zip Archive:</li>
                            <li><input type="file" name="zipArchive" value=""></li>
                        </ul>
                        <input type="submit" value="Upload Books">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form:form>
                </div>
            </div>
            <div class="reg-form-right">


                <c:if test="${numAccountsDeleted != null}">
                    <p>Accounts deleted: ${numAccountsDeleted}</p>
                </c:if>
                <div class="reg">
                    <form action="/admin/dropAccounts" method="post">
                        <input type="submit" value="Delete All Accounts">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <div class="strip"></div>
                </div>
                <c:if test="${numBooksDeleted != null}">
                    <p>Books deleted: ${numBooksDeleted}</p>
                </c:if>
                <div class="reg">
                    <form action="/admin/dropBooks" method="post">
                        <input type="submit" value="Delete All Books">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <div class="strip"></div>
                </div>
                <c:if test="${numOrdersDeleted != null}">
                    <p>Orders deleted: ${numOrdersDeleted}</p>
                </c:if>
                <div class="reg">
                    <form action="/admin/dropOrders" method="post">
                        <input type="submit" value="Delete All Orders">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <div class="strip"></div>
                </div>

                <p>Inspect User Account</p>

                <div class="reg">
                    <form action="/admin/account" method="get">
                        <ul>
                            <li class="text-info">User Email:</li>
                            <li><input type="text" name="userEmail" value="" required></li>
                        </ul>
                        <input type="submit" value="Find">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <div class="strip"></div>
                </div>

                <p>Inspect Order</p>

                <div class="reg">
                    <form action="/admin/order" method="get">
                        <ul>
                            <li class="text-info">Order Id:</li>
                            <li><input type="text" name="orderId" value="" required></li>
                        </ul>
                        <input type="submit" value="Find">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
                <%--<div class="reg">
                    <ul>
                        <li class="text-info">Web Root Folders:</li>
                        <li><select name="Events">
                            <c:forEach items="${webRootPathFolders}" var="folder">
                                <option value="${folder}">${folder}</option>
                            </c:forEach>
                        </select></li>
                    </ul>
                    </form>
                </div>--%>
                <div class="strip"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<!-- registration-form -->

<jsp:include page="_footer.jsp"/>