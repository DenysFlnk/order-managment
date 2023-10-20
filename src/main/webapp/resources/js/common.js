const ordersRestUrl = "rest-api/orders";
const ordersUrl = "orders";
const usersRestUrl = "rest-api/users";
const translatorsRestUrl = "rest-api/translators";
const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxError(function (event, jqXHR, options, jsExc) {
    failNoty(jqXHR);
});

function doDelete(url) {
    if (confirm(i18n["noty.confirmDelete"])) {
        $.ajax({
            url: url,
            method: "DELETE",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function () {
                loadContent();
            }
        });
    }
}

function openModal(id) {
    let modalWindow = $("#" + id);
    modalWindow.find(":input").val("");
    modalWindow.modal();
}

function closeModal(id) {
    $("#" + id).modal("hide");
}

function redirectTo(url) {
    document.location.href = url;
}

function getDateFromArray(localDate) {
    let day = localDate[2];
    let month = localDate[1];
    let year = localDate[0];

    return new Date(Date.UTC(year, month, day));
}

function formatDate(date, format) {
    const year = date.getFullYear().toString();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');

    return format
        .replace('YYYY', year)
        .replace('YY', year.slice(-2))
        .replace('MM', month)
        .replace('M', parseInt(month, 10).toString())
        .replace('DD', day)
        .replace('D', parseInt(day, 10).toString());
}


let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNotyBottomRight(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1500
    }).show();
}

function reminderToEmailTranslator() {
    closeNoty();
    new Noty({
        text: "<h5>" + i18n["noty.email"] +
            "&nbsp; <span class='fa fa-sm fa-envelope'></span</h5>",
        type: 'warning',
        layout: "center",
        timeout: 2000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    let errorInfo = jqXHR.responseJSON;
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show()
}

function convertFormToJsonString(jqueryForm) {
    return JSON.stringify(convertFormToJson(jqueryForm));
}

function convertFormToJson(jqueryForm) {
    return jqueryForm.serializeArray().reduce(function (obj, item) {
        obj[item.name] = item.value;
        return obj;
    }, {});
}