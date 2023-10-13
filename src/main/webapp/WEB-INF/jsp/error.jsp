<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/header.jsp"/>

<body>
<jsp:include page="fragments/body-header.jsp"/>
<div class="jumbotron">
    <div class="container text-center">
        <br>
        <h4 class="my-3">${status}</h4>
        <h2>Access denied</h2>
        <h4 class="my-5">${message}</h4>
        <a href="orders">back to Orders</a>
    </div>
</div>
</body>
</html>
