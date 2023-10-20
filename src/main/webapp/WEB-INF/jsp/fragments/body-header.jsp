<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<nav class="navbar navbar-expand-md navbar-dark bg-dark py-0">
    <div class="d-flex justify-content-start align-content-center">
        <h2 class="text-white">
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor" class="bi bi-book"
                 viewBox="0 0 16 16">
                <path d="M1 2.828c.885-.37 2.154-.769 3.388-.893 1.33-.134 2.458.063 3.112.752v9.746c-.935-.53-2.12-.603-3.213-.493-1.18.12-2.37.461-3.287.811V2.828zm7.5-.141c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
            </svg>
            <spring:message code="header.name"/>
        </h2>
    </div>

    <sec:authorize access="isAuthenticated()">
        <div class="container d-flex justify-content-end">
            <header class="d-flex flex-wrap py-3">
                <ul class="nav nav-pills">
                    <li class="nav-item">
                        <a href="orders" class="nav-link"><spring:message code="header.orders"/></a>
                    </li>
                    <li class="nav-item">
                        <a href="translators" class="nav-link"><spring:message code="header.translators"/></a>
                    </li>
                    <sec:authorize access="hasRole('ADMIN')">
                        <li class="nav-item">
                            <a href="users" class="nav-link"><spring:message code="header.users"/></a>
                        </li>
                    </sec:authorize>
                    <li class="nav-item">
                        <form method="post" action="logout"
                              onsubmit="return confirm('<spring:message code="header.logout"/>');">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button class="btn btn-primary my-1" type="submit"><span class="fa fa-sign-out"></span>
                            </button>
                        </form>
                    </li>
                </ul>
            </header>
        </div>
    </sec:authorize>

    <ul class="navbar-nav ml-auto d-flex justify-content-end">
        <li class="nav-item dropdown">
            <a class="dropdown-toggle nav-link my-1 ml-2" data-toggle="dropdown">
                <spring:message code="common.language"/></a>
            <div class="dropdown-menu">
                <a class="dropdown-item" onclick="changeLanguage('en')">English</a>
                <a class="dropdown-item" onclick="changeLanguage('uk')">Українська</a>
            </div>
        </li>
    </ul>
</nav>
<script type="text/javascript">
    function getCurrentUrl() {
        const fullUrl = window.location.href;
        const split = fullUrl.split("?");
        return split[0];
    }

    function changeLanguage(language) {
        const currentUrl = getCurrentUrl();
        const newUrl = currentUrl + "?lang=" + language;
        window.location.href = newUrl;
    }
</script>

