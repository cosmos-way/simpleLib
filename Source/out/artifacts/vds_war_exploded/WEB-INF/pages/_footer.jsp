<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="footer">
    <div class="container">
    <div class="online-strip">
    <div class="col-md-4 follow-us">
    <h3>follow us : </h3>
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
        <div class="copyright text-center">
            <p>Simple library system | const Ltd</p>
        </div>
    </div>
</div>
</body>
<script>
    $(document).ready(function () {
        $(function () {
            var txtUrls = document.getElementsByName("txtUrl");
            for (var i = 0; i < txtUrls.length; i++) {
                txtUrls[i].value = window.location.href;
            }
        });
    });
</script>
</html>