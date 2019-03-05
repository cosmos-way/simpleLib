<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="news-letter">
    <div class="container">
        <div class="join">
            <h6>SEARCH</h6>

            <div class="sub-left-right">
                <form action="/search" method="get">
                    <input type="text" name="q" value="Enter Book Name Or Book Author Here"
                           onfocus="this.value = '';"
                           onblur="if (this.value == '') {this.value = 'Enter Book Name Or Author Here';}"/>
                    <input type="submit" value="SEARCH"/>
                </form>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<div class="footer">
    <div class="container">

        <div class="copyright text-center">
            <p>ITMO UNIVERSITY</p>
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