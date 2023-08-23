const ordersRestUrl = "rest-api/orders"
const ordersUrl = "orders"

const translatorsRestUrl = "rest-api/translators";

function doGet(url) {
    let Httpreq = new XMLHttpRequest();
    Httpreq.open("GET", url, false);
    Httpreq.send(null);
    return Httpreq;
}

function doDelete(url) {
    let Httpreq = new XMLHttpRequest();
    Httpreq.open("DELETE", url, false);
    Httpreq.send(null);
    return Httpreq;
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

    const formattedDate = format
        .replace('YYYY', year)
        .replace('YY', year.slice(-2))
        .replace('MM', month)
        .replace('M', parseInt(month, 10).toString())
        .replace('DD', day)
        .replace('D', parseInt(day, 10).toString());

    return formattedDate;
}


let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNotyBottomRight() {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;",
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function successNotyCenter() {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;",
        type: 'success',
        layout: "center",
        timeout: 1000
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