<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/header.jsp"/>

<body class="d-flex flex-column h-100">
<jsp:include page="fragments/body-header.jsp"/>
<div class="jumbotron">
    <div class="container text-center">
        <br>
        <h4 class="my-3">${status}</h4>
        <h2><spring:message code="error.message"/></h2>
        <h4 class="my-5">${message}</h4>
        <a href="orders"><spring:message code="error.redirect"/></a>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
