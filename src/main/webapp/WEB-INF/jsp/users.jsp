<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/header.jsp"/>

<body onload="loadContent()" class="d-flex flex-column h-100">
<jsp:include page="fragments/body-header.jsp"/>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/users.js" defer></script>
<br/>
<h2 class="text-center"><spring:message code="users.list"/></h2>
<br/>
<div class="container">
    <button class="btn btn-success btn-lg" onclick="openModal('userModal')">
        <span class="fas fa-plus fa-lg"></span>
        <spring:message code="users.add"/>
    </button>
</div>
<br/>
<div class="container">
    <table class="table table-bordered table-inc-font" id="userTable">
        <thead>
        <tr>
            <th><spring:message code="users.enabled"/></th>
            <th><spring:message code="common.name"/></th>
            <th><spring:message code="users.roles"/></th>
            <th><span class="fa fa-ellipsis"></span></th>
            <th><span class="fa fa-ellipsis"></span></th>
        </tr>
        </thead>
        <tbody id="userTableBody">
        </tbody>
    </table>
</div>
<div class="modal fade" tabindex="-1" id="userModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="users.user"/></h4>
                <button type="button" class="close" onclick="closeUserModal()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="userForm">
                    <input type="hidden" id="userId" name="id">
                    <input type="hidden" id="enabled" name="enabled">
                    <div class="form-group">
                        <label for="userName" class="col-form-label">
                            <spring:message code="common.name"/> *
                        </label>
                        <input type="text" class="form-control" id="userName" name="name"
                               placeholder="John">
                    </div>
                    <div class="form-group">
                        <label for="userPassword" class="col-form-label">
                            <spring:message code="users.password"/> *
                        </label>
                        <input type="text" class="form-control" id="userPassword" name="password">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label"><spring:message code="users.roles"/></label>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="roleAdmin" name="roles" value="ADMIN">
                            <label class="form-check-label" for="roleAdmin">
                                <spring:message code="users.admin"/>
                            </label>
                        </div>
                    </div>
                </form>
                <h6><spring:message code="common.required"/></h6>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeUserModal()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="saveUser()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/i18n.jsp"/>
<jsp:include page="fragments/footer.jsp"/>
</body>