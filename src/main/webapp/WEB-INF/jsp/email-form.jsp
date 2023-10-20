<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="modal fade" tabindex="-1" id="emailModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="email.info"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form action="translators/email" method="post" enctype="multipart/form-data" id="emailForm">
                    <input type="hidden" id="orderId" name="orderId">
                    <div class="form-group">
                        <label for="senderEmail" class="col-form-label">
                            <spring:message code="email.sender"/>
                        </label>
                        <input type="text" class="form-control" id="senderEmail" name="senderEmail" readonly>
                    </div>
                    <div class="form-group">
                        <label for="translatorNameEmailForm" class="col-form-label">
                            <spring:message code="order-detail.translatorName"/>
                        </label>
                        <input type="text" class="form-control" id="translatorNameEmailForm" name="translatorName" required>
                    </div>
                    <div class="form-group">
                        <label for="targetEmail" class="col-form-label">
                            <spring:message code="email.translatorEmail"/>
                        </label>
                        <input type="email" class="form-control" id="targetEmail" name="targetEmail" required>
                    </div>
                    <div class="form-group">
                        <label for="subject" class="col-form-label">
                            <spring:message code="email.subject"/>
                        </label>
                        <input type="text" class="form-control" id="subject" name="subject" required>
                    </div>
                    <div class="form-group">
                        <label for="addInfo" class="col-form-label">
                            <spring:message code="email.addInfo"/>
                        </label>
                        <textarea id="addInfo" class="form-control" name="addInfo" rows="4"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="file" class="col-form-label">
                            <spring:message code="email.attach"/></label>
                        <input type="file" id="file" name="file" accept=".pdf, .doc, .docx, .png, .jpeg, .txt" multiple required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="sendEmail()">
                    <span class="fa fa-check"></span>
                    <spring:message code="email.send"/>
                </button>
            </div>
        </div>
    </div>
</div>
