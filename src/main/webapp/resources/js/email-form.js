const agencyEmail = "filonenko.denys94@gmail.com";
const emailUrl = "translators/email"

function openEmailModal(translatorId) {
    openModal("emailModal");
    $.ajax({
        url: translatorsRestUrl + `/${translatorId}`,
        method: "GET",
        success: function (data) {
            let emailForm = $('#emailForm');
            emailForm.find('#orderId').val(orderId);
            emailForm.find('#senderEmail').val(agencyEmail);
            emailForm.find('#translatorNameEmailForm').val(data.name);
            emailForm.find('#targetEmail').val(data.email);
            let subject = "Order #" + orderId;
            emailForm.find('#subject').val(subject);
        }
    });
}

function sendEmail() {
    let emailForm = $('#emailForm')[0];
    let formData = new FormData(emailForm);

    $.ajax({
        url: emailUrl,
        method: "POST",
        contentType: false,
        processData: false,
        data: formData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function () {
            closeModal("emailModal");
            successNotyBottomRight(i18n["noty.email.success"]);
        }
    });
}