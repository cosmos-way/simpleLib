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
                    <a>Добавить книгу</a>
                    <a>Список книг</a>
                    <a href="/bookRequest/all">Запросы на книги</a>
                    <a>Пользователи</a>
                </div>


            </div>
            <div class="reg-form-right">


                <c:if test="${numAccountsDeleted != null}">
                    <p>Accounts deleted: ${numAccountsDeleted}</p>
                </c:if>
                <div class="reg">
                </div>

                <div class="reg">
                </div>

                <div class="reg">

                    <div class="strip"></div>
                </div>

                <p>Inspect User Account</p>

                <div class="reg">

                    <div class="strip"></div>
                </div>

                <p>Inspect Order</p>

                <div class="reg">

                </div>

                <div class="strip"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<jsp:include page="_footer.jsp"/>