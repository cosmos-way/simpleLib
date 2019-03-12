<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:useBean id="reqestedBooks" class="java.util.ArrayList" scope="request"/>

<script type="text/javascript">

    function requestSatusSelectChangedAjax(selectObject, requestID) {
        var data = {}
        data["id"] = requestID
        data["status"] = selectObject.value
        $.ajax({

            type: "POST",
            contentType: "application/json",
            url: "/bookRequest/requestSaveStatus",
            data: JSON.stringify(data),
            dataType: 'json',
            timeout: 600000,
            success: function (result) {
                // $.notify(
                //     "Успешно обновлен статус запроса  "+requestID, "success"
                // );

                $("#select_status_"+requestID).notify(
                    "Успешно обновлен статус запроса  "+requestID,
                    { position:"right", className:"success" }
                );
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#ajaxGetUserServletResponse').html("No!");
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    }


    function requestDateTillInputChangedAjax(sObject, requestID) {
        var data = {}
        data["id"] = requestID
        data["dateTill"] = sObject.value
        $.ajax({

            type: "POST",
            contentType: "application/json",
            url: "/bookRequest/requestSaveDateTill",
            data: JSON.stringify(data),
            dataType: 'json',
            timeout: 600000,
            success: function (result) {
                $.notify(
                    "Дата возврата обновлена, запрос "+requestID,
                    { className:"success" }
                );
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#ajaxGetUserServletResponse').html("No!");
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    }

    $('#allRequestsFilter').submit(function () {
        alert("hhh");
        $(this)
            .find('input[name]')
            .filter(function () {
                return !this.value;
            })
            .prop('name', '');
    });
</script>


<div class="registration-form">
    <div class="container">

        <form id="allRequestsFilter"  method="get" action="/bookRequest/all" novalidate>
            <div class="form-row">
                <div class="col-md-2 mb-5">
                    <label for="validationCustom01">id книги</label>
                    <input type="text" class="form-control" id="validationCustom01" name="bookId" value="${param["bookId"]}"  >
                </div>
                <div class="col-md-2 mb-5">
                    <label for="validationCustom02">id запроса</label>
                    <input type="text" class="form-control" id="validationCustom02" name="id" value="${param["id"]}" >
                </div>
                <div class="col-md-2 mb-5">
                    <label for="validationCustomUsername">id пользователя</label>
                    <input type="text" class="form-control" id="validationCustomUsername" name="userId"  aria-describedby="inputGroupPrepend" value="${param["userId"]}">

                </div>
                <div class="col-md-2 mb-4">
                    <label for="validationCustom03">Статус запроса</label>


                    <select class="form-control" id="validationCustom03" name="status">
                        <option value="">Все</option>
                        <option value="ISSUED" ${param["status"] == "ISSUED" ? 'selected="selected"' : ''}>ВЫДАНО</option>
                        <option value="ORDERED" ${param["status"] == "ORDERED" ? 'selected="selected"' : ''}>ЗАКАЗАНО</option>
                        <option value="ERROR" ${param["status"] == "ERROR" ? 'selected="selected"' : ''}>ОШИБКА</option>
                        <option value="DELIVERED" ${param["status"] == "DELIVERED" ? 'selected="selected"' : ''}>ВЕРНУЛИ</option>
                        <option value="READY" ${param["status"] == "READY" ? 'selected="selected"' : ''}>ГОТОВО</option>
                        <option value="CANCELED" ${param["status"] == "CANCELED" ? 'selected="selected"' : ''}>ОТМЕНЕНО</option>
                    </select>
                </div>
                <div class="col-md-2 mb-3">
                    <label for="validationCustom04">Дата заказа</label>

                    <div class="controls input-append date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                        <fmt:parseDate value="${param[\"dateFrom\"]}" pattern="yyyy-mm-dd" var="dateFrom"/>
                        <input name="dateFrom" id="validationCustom04" size="16" type="text" value="<fmt:formatDate value="${dateFrom}" type="date" pattern="yyyy-mm-dd"/>"  readonly>
                        <span class="add-on"><i class="icon-remove"></i></span>
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                </div>
                <div class="col-md-2 mb-3">
                    <label for="validationCustom05">Дата возврата</label>

                    <div class="controls input-append date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                        <fmt:parseDate value="${param[\"dateTill\"]}" pattern="yyyy-mm-dd" var="dateTill"/>
                        <input name="dateTill" id="validationCustom05" size="16" type="text" value="<fmt:formatDate value="${dateTill}" type="date" pattern="yyyy-mm-dd"/>"  readonly>
                        <span class="add-on"><i class="icon-remove"></i></span>
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-row">

                <div class="col-md-5 mb-3">
                    <div class="form-group">
                        <div class="form-check">
                            <input name="expired" class="form-check-input" type="checkbox" value="1" id="invalidCheck" required>
                            <label class="form-check-label" for="invalidCheck">
                                Просрочены
                            </label>
                        </div>
                    </div>
                </div>
                <div class="col-md-2 mb-3">
                    <label for="limit">Лимит</label>
                    <input name="limit" type="text" class="form-control" value="${param["limit"]}" id="limit">
                </div>
                <div class="col-md-5 mb-3">

                </div>


            </div>
            <input type="submit" class="btn btn-primary" value="Применить фильтр">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>




        <table class="table">
            <tr>
                <th>id</th>
                <th>Наименование книги</th>
                <th>Автор</th>
                <th>Статус запроса</th>
                <th>Дата запроса</th>
                <th>Выдача до</th>
                <th>Пользователь</th>
            </tr>
            <c:forEach items="${reqestedBooks}" var="request" varStatus="status">
                <tr>
                    <td>${request.id}</td>
                    <td>${request.book.title}</td>
                    <td>${request.book.author}</td>
                    <td>
                        <select id="select_status_${request.id}" onchange="requestSatusSelectChangedAjax(this,${request.id})">
                            <option value="ISSUED" ${request.status == "ISSUED" ? 'selected="selected"' : ''}>ВЫДАНО</option>
                            <option value="ORDERED" ${request.status == "ORDERED" ? 'selected="selected"' : ''}>ЗАКАЗАНО</option>
                            <option value="ERROR" ${request.status == "ERROR" ? 'selected="selected"' : ''}>ОШИБКА</option>
                            <option value="DELIVERED" ${request.status == "DELIVERED" ? 'selected="selected"' : ''}>ВЕРНУЛИ</option>
                            <option value="READY" ${request.status == "READY" ? 'selected="selected"' : ''}>ГОТОВО</option>
                            <option value="CANCELED" ${request.status == "CANCELED" ? 'selected="selected"' : ''}>ОТМЕНЕНО</option>
                        </select></td>
                    <td>${request.orderDateTime}</td>
                    <td>

                        <div class="control-group">
                            <div class="controls input-append date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                                <fmt:parseDate value="${request.dateTill}" pattern="yyyy-mm-dd" var="dateTill"/>
                                <input onchange="requestDateTillInputChangedAjax(this,${request.id})" size="16" type="text" value="<fmt:formatDate value="${dateTill}" type="date" pattern="yyyy-mm-dd"/>"  readonly>
                                <span class="add-on"><i class="icon-remove"></i></span>
                                <span class="add-on"><i class="icon-th"></i></span>
                            </div>
                            <input type="hidden" id="dtp_input2" value="" /><br/>
                        </div>


                        <%--<input type="date" class="form-control" value="<fmt:formatDate value="${request.dateTill}" type="date" pattern="yyyy-MM-dd"/>"/></td>--%>
                    <td>${request.account.firstName} ${request.account.lastName}</td>

                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<jsp:include page="_footer.jsp"/>

<script type="text/javascript" src="../js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="../js/locales/bootstrap-datetimepicker.ru.js" charset="UTF-8"></script>
<script type="text/javascript">

    $('.form_date').datetimepicker({
        language:  'ru',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });

</script>

<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function() {
        'use strict';
        window.addEventListener('load', function() {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function(form) {
                form.addEventListener('submit', function(event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();



</script>

