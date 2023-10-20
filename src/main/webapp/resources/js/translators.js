let translatorPage;
let objectsInPage = 10;
let currentTranslator;

const translatorTable = $('#translatorTableBody');
const rateTable = $('#rateTableBody');
const translatorPagination = $('#translatorPagination');

function loadContent(currentPage) {
    translatorPage = currentPage === undefined ? 0 : currentPage;
    $.ajax({
        url: translatorsRestUrl + `?page=${translatorPage}&size=${objectsInPage}`,
        method: "GET",
        success: function (data) {
            translatorTable.empty();
            data.forEach(translator => {
                let newRow = $('<tr>');
                newRow.attr("available", translator.available);
                let checkBox = translator.available ? "checked" : "";
                newRow.append(`<td><input type="checkbox" ${checkBox} 
                               onclick="changeAvailability($(this), ${translator.id})"></td>
                               <td>${translator.name}</td>
                               <td>${translator.email}</td>   
                               <td>${translator.phoneNumber}</td> 
                               <td>${translator.languages}</td> 
                               <td><button type="button" class="btn btn-light" 
                                onclick="loadRates('${translator.id}')">
                                <span class="fa-solid fa-angle-down"></span></button></td>
                               <td><button type="button" class="btn btn-warning" 
                                onclick="editTranslator('${translator.id}')">
                                <span class="fa-solid fa-pencil"></span></button></td>
                               <td><button type="button" class="btn btn-danger" 
                                onclick="deleteTranslator('${translator.id}')">
                                <span class="fa-solid fa-xmark"></span></button></td>`);
                translatorTable.append(newRow);
            });
            updatePagination(objectsInPage);
        }
    });
}

function updatePagination(objectsInPage) {
    translatorPagination.empty();
    $.ajax({
        url: translatorsRestUrl + "/count",
        method: "GET",
        success: function (data) {
            let pages = Math.ceil(data / objectsInPage);
            for (let i = 1; i <= pages; i++) {
                const isActive = i === translatorPage + 1 ? 'active' : '';
                translatorPagination.append(`<li class="page-item ${isActive}">
                                        <a class="page-link" data-page="${i - 1}" >${i}</a>
                                     </li>`);
            }

            $('.page-link').on('click', function () {
                translatorPage = parseInt($(this).data('page'));
                loadContent(translatorPage);
            });
        }
    });
}

function changeAvailability(checkBox, translatorId) {
    let isAvailable = checkBox.is(":checked");
    $.ajax({
       url: translatorsRestUrl + `/${translatorId}?isAvailable=${isAvailable}`,
       method: "PATCH",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
       success: function () {
           loadContent(translatorPage);
       }
    });
}

function editTranslator(translatorId) {
    openModal("translatorModal");
    const translatorForm = $('#translatorForm');
    $.ajax({
        url: translatorsRestUrl + `/${translatorId}`,
        method: "GET",
        success: function (data) {
            $.each(data, function (key, value) {
                translatorForm.find("input[name='" + key + "']").val(value);
            });
        }
    });
}

function saveTranslator() {
    const translatorForm = $('#translatorForm');
    let translatorId = translatorForm.find('#translatorId').val();

    let json = convertFormToJson(translatorForm);
    json.available = "true";

    let method = "POST";
    let url = translatorsRestUrl;

    if (translatorId !== "" && translatorId !== null) {
        method = "PUT";
        url = url + `/${translatorId}`;
        json.available = translatorForm.find("#available").val();
    }

    $.ajax({
        url: url,
        contentType: "application/json; charset=utf-8",
        method: method,
        data: JSON.stringify(json),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function () {
            closeModal("translatorModal");
            loadContent(translatorPage);
        }
    });
}

function deleteTranslator(translatorId) {
    if (confirm(i18n["noty.confirmDelete"])) {
        $.ajax({
           url: translatorsRestUrl + `/${translatorId}`,
           method: "DELETE",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
           success: function () {
               loadContent(translatorPage);
           }
        });
    }
}

function loadRates(translatorId) {
    currentTranslator = translatorId;
    $.ajax({
        url: translatorsRestUrl + `/${translatorId}/language-rates`,
        method: "GET",
        success: function (data) {
            openModal("rateModal");
            rateTable.empty();
            data.forEach(rate => {
                let newRow = $('<tr>');
                newRow.append(`<td>${rate.language}</td>
                               <td>${rate.commonRate}</td>   
                               <td>${rate.hardRate}</td> 
                               <td>${rate.signs}</td> 
                               <td><button type="button" class="btn btn-warning" 
                                onclick="editRate('${rate.id}')">
                                <span class="fa-solid fa-pencil"></span></button></td>
                               <td><button type="button" class="btn btn-danger" 
                                onclick="deleteRate('${rate.id}')">
                                <span class="fa-solid fa-xmark"></span></button></td>`);
                rateTable.append(newRow);
            });
        }
    });
}

function editRate(rateId) {
    openModal("editRate");
    let rateForm = $('#rateForm');
    $.ajax({
        url: translatorsRestUrl + `/${currentTranslator}/language-rates/${rateId}`,
        method: "GET",
        success: function (data) {
            $.each(data, function (key, value) {
                rateForm.find("input[name='" + key + "']").val(value);
            });
            $('#rateLanguage').val(data.language);
        }
    });
}

function deleteRate(rateId) {
    if (confirm(i18n["noty.confirmDelete"])) {
        $.ajax({
            url: translatorsRestUrl + `/${currentTranslator}/language-rates/${rateId}`,
            method: "DELETE",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function () {
                loadRates(currentTranslator);
                loadContent(translatorPage);
            }
        });
    }
}

function saveRate() {
    const rateForm = $('#rateForm');
    let rateId = rateForm.find('#rateId').val();

    let json = convertFormToJson(rateForm);
    json.language = rateForm.find('#rateLanguage').val();

    let method = "POST";
    let url = translatorsRestUrl + `/${currentTranslator}/language-rates`;

    if (rateId !== "" && rateId !== null) {
        method = "PUT";
        url = url + `/${rateId}`;
    }

    $.ajax({
        url: url,
        contentType: "application/json; charset=utf-8",
        method: method,
        data: JSON.stringify(json),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function () {
            closeModal("editRate");
            loadRates(currentTranslator);
            loadContent(translatorPage);
        }
    });
}

