<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal fade" tabindex="-1" id="emailModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Email information</h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form action="translators/email" method="post" enctype="multipart/form-data" id="emailForm">
                    <input type="hidden" id="orderId" name="orderId">
                    <div class="form-group">
                        <label for="senderEmail" class="col-form-label">Sender email</label>
                        <input type="text" class="form-control" id="senderEmail" name="senderEmail" readonly>
                    </div>
                    <div class="form-group">
                        <label for="translatorNameEmailForm" class="col-form-label">Translator name</label>
                        <input type="text" class="form-control" id="translatorNameEmailForm" name="translatorName" required>
                    </div>
                    <div class="form-group">
                        <label for="targetEmail" class="col-form-label">Translator email</label>
                        <input type="email" class="form-control" id="targetEmail" name="targetEmail" required>
                    </div>
                    <div class="form-group">
                        <label for="subject" class="col-form-label">Email subject</label>
                        <input type="text" class="form-control" id="subject" name="subject" required>
                    </div>
                    <div class="form-group">
                        <label for="addInfo" class="col-form-label">Additional info</label>
                        <textarea id="addInfo" class="form-control" name="addInfo" rows="4"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="file" class="col-form-label">Attach Files:   </label>
                        <input type="file" id="file" name="file" accept=".pdf, .doc, .docx, .png, .jpeg, .txt" multiple required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Cancel
                </button>
                <button type="button" class="btn btn-primary" onclick="sendEmail()">
                    <span class="fa fa-check"></span>
                    Send
                </button>
            </div>
        </div>
    </div>
</div>
