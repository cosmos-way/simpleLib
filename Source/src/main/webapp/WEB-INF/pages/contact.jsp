<jsp:include page="_header.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- contact-page -->
<div class="contact">
    <div class="container">
        <div class="dreamcrub">
            <ul class="breadcrumbs">
                <li class="home">
                    <a href="/index" title="Go to Home Page">Home</a>&nbsp;
                    <span>&gt;</span>
                </li>
                <li class="women">
                    Contact
                </li>
            </ul>
            <ul class="previous">
                <li><a href="/index">Back to Previous Page</a></li>
            </ul>
            <div class="clearfix"></div>
        </div>
        <div class="contact-info">
            <h2>FIND US HERE</h2>
        </div>
        <div class="contact-map">
            <iframe src="https://maps.google.com/maps?q=%D0%9A%D1%80%D0%BE%D0%B2%D0%B5%D0%BD%D1%80%D0%BA%D1%81%D0%BA%D0%B8%D0%B9%2014&t=&z=13&ie=UTF8&iwloc=&output=embed"
                    style="border:0"></iframe>
        </div>
        <div class="contact-form">
            <div class="contact-info">
                <h3>CONTACT FORM</h3>
            </div>
            <form>
                <div class="contact-left">
                    <input type="text" placeholder="Name" required>
                    <input type="text" placeholder="E-mail" required>
                    <input type="text" placeholder="Subject" required>
                </div>
                <div class="contact-right">
                    <textarea placeholder="Message" required></textarea>
                </div>
                <div class="clearfix"></div>
                <input type="submit" value="SUBMIT">
            </form>
        </div>
    </div>
</div>
<!-- //contact-page -->

<jsp:include page="_footer.jsp"/>