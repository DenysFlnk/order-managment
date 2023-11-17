<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<jsp:include page="fragments/header.jsp"/>
<script type="text/javascript" src="resources/js/common.js" defer></script>

<body class="d-flex flex-column h-100">
<jsp:include page="fragments/body-header.jsp"/>
<section>
    <div class="container mt-5 pt-5">
        <div class="row">
            <div class="col-12 col-sm-7 col-md-6 m-auto">
                <div class="card border-0 shadow">
                    <div class="card-body">
                        <div class="text-center">
                            <h2>
                                <spring:message code="login.hello"/>
                            </h2>
                            <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                            </svg>
                        </div>
                        <form action="security-check" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="text" name="username" id="username" class="form-control my-4 py-2" placeholder="Username" />
                            <input type="password" name="password" id="password" class="form-control my-4 py-2" placeholder="Password" />
                            <div class="text-center mt-3">
                                <button class="btn btn-primary" value="Log in" type="submit">
                                    <spring:message code="login.button"/></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <br>
        <div class="row">
            <div class="col-12 col-sm-7 col-md-6 m-auto">
                <div class="card border-0 shadow">
                    <div class="card-body">
                        <div class="text-center">
                            <h5>
                                <spring:message code="login.admin.credentials"/>
                            </h5>
                        </div>
                        <h6><spring:message code="login.name"/>admin</h6>
                        <h6><spring:message code="login.password"/>some_admin_password</h6>
                        <br>
                        <div class="text-center">
                            <h5>
                                <spring:message code="login.user.credentials"/>
                            </h5>
                        </div>
                        <h6><spring:message code="login.name"/>normal user</h6>
                        <h6><spring:message code="login.password"/>some_password</h6>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
    <c:if test="${param.error}">
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;<br>" +
            `${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}`,
        type: "error",
        layout: "center"
    });
    failedNote.show()
    </c:if>
</script>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>