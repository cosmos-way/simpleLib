<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:useBean id="reqestedBooks" class="java.util.ArrayList" scope="request"/>

<style>
    table {
        border-collapse: collapse; /* Убираем двойные линии */
        width: 100%; /* Ширина таблицы */
    }
    th {
        background: #dfebb7; /* Цвет фона ячейки */
        text-align: center; /* Выравнивание по левому краю */
    }
    td {
        text-align: center; /* Выравнивание по центру */
    }
    th, td {
        border: 1px solid black; /* Параметры рамки */
        padding: 4px; /* Поля вокруг текста */
    }
</style>

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
                // $('#ajaxGetUserServletResponse').html("YES!!!!!!!!!!!!!");
                // alert('At ' + result.time
                //     + ': ' + result.message);
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
</script>

<div id="ajaxGetUserServletResponse"></div>
<table>
    <tr>
        <th>id</th>
        <th>title</th>
        <th>author</th>
        <th>status</th>
        <th>Время заказа</th>
        <th>До</th>
        <th>ФИО</th>
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
                </select></td>
            <td>${request.orderDateTime}</td>
            <td>
                <div class="input-append date form_datetime">
                    <input size="16" type="text" value="<fmt:formatDate value="${request.dateTill}" type="date" pattern="yyyy-MM-dd"/>" readonly>
                    <span class="add-on"><i class="icon-th"></i></span>
                </div>

                <script type="text/javascript">
                    $(".form_datetime").datetimepicker({
                        format: "dd MM yyyy - hh:ii"
                    });
                </script>


                <div class="control-group">
                    <label class="control-label">Date Picking</label>
                    <div class="controls input-append date form_date" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                        <input size="16" type="text" value="" readonly>
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

<jsp:include page="_footer.jsp"/>

<script type="text/javascript" src="../js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="../js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
<script type="text/javascript">

    $('.form_date').datetimepicker({
        language:  'fr',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });

</script>