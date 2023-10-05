let translatorPage = 0;
let translatorsInPage = 5;
const translatorPagination = $('#translatorPagination');

let orderUrl = window.location.pathname;
let orderId = parseOrderIdFromUrl(orderUrl);

const dateFormat = "YYYY-MM-DD";

function loadContent() {
    $('#orderNumber').prop("textContent", "Order #KP0" + orderId);
    $.ajax({
        url: `${ordersRestUrl}/${orderId}`,
        method: "GET",
        success: function (data) {
            $.each(data, function (key, value) {
                $('#orderForm').find("input[name='" + key + "']").val(value);
            });
            $('#orderStatus').val(data.orderStatus);
            $('#creationDate').val(formatDate(getDateFromArray(data.creationDate), dateFormat));
            $('#deliveryDate').val(formatDate(getDateFromArray(data.deliveryDate), dateFormat));

            loadDocuments(data);
            loadApostilles(data);
        }
    });
}

function parseOrderIdFromUrl(url) {
    const pattern = /\/orders\/(\d+)/;
    const match = url.match(pattern);

    return match[1];
}

function loadDocuments(order) {
    $('#docTableBody').empty();
    let documents = order.documentTos;
    documents.forEach(doc => {
        let newRow = $('<tr>');
        let isHardComplexity = doc.isHardComplexity;
        let checkBox = "";
        if (isHardComplexity) {
            newRow.prop("class", "hard-complexity");
            checkBox = "checked";
        }

        newRow.append(`<td hidden="hidden">${doc.id}</td>
                       <td><input id="complexity" type="checkbox" ${checkBox} onclick="changeDocumentComplexity($(this), 
                       ${doc.id})"></td>
                       <td id="language">${doc.language.toLowerCase()}</td>   
                       <td>${doc.officeRate}</td> 
                       <td>${doc.signsNumber === null ? "-" : doc.signsNumber}</td>
                       <td>${doc.notarizationCost}</td> 
                       <td>${doc.officeCost}</td>`);

        if (doc.translatorName === null) {
            newRow.append(`<td><button type="button" class="btn btn-success" 
                                onclick="showTranslatorsFor('${doc.id}')"><span class="fa fa-plus"></span></button>
                                </td>`);
        } else {
            newRow.append(`<td>${doc.translatorName}</td>`);
        }

        newRow.append(`<td>${doc.translatorRate === null || doc.translatorRate === "" ? "-" : doc.translatorRate}</td>
                       <td>${doc.translatorTax === null || doc.translatorTax === "" ? "-" : doc.translatorTax}</td>
                       <td><button type="button" class="btn btn-warning" 
                                onclick="editDocument('${doc.id}')"><span class="fa-solid fa-pencil"></span></button>
                                </td>
                       <td><button type="button" class="btn btn-danger" 
                                onclick="deleteDocument('${doc.id}')">
                                <span class="fa-solid fa-xmark"></span></button>
                                </td>`);

        $('#docTableBody').append(newRow);
    });
}

function deleteDocument(id) {
    doDelete(`${ordersRestUrl}/${orderId}/documents/${id}`);
}

function showTranslatorsFor(docId) {
    openModal('changeTranslator');
    translatorPage = 0;
    let orderDocument;
    let isHardComplexity;
    let documentLanguage;

    if (docId === "" || docId === null) {
        let form = $('#docForm');
        isHardComplexity = form.find('#isHardComplexity').is(":checked") ? "true" : "false";
        documentLanguage = form.find('#documentLanguage').val();

        orderDocument = {
            id: null,
            language: documentLanguage,
            isHardComplexity: isHardComplexity
        }
    } else {
        isHardComplexity = $('#complexity').val() === "on" ? "true" : "false";
        documentLanguage = $('#language').text().toUpperCase();

        orderDocument = {
            id: docId,
            language: documentLanguage,
            isHardComplexity: isHardComplexity
        };
    }

    getPaginatedTranslators(orderDocument, translatorPage);
}

function getPaginatedTranslators(orderDocument, currentPage) {
    $.ajax({
        url: translatorsRestUrl + `/document?language=${orderDocument.language}`
            + `&isHardComplexity=${orderDocument.isHardComplexity}`
            + `&page=${currentPage}`,
        method: 'GET',
        success: function (data) {
            $('#translatorTableBody').empty();
            data.forEach(object => {
                const newRow = `<tr>
                              <td hidden="hidden">${object.id}</td>
                              <td>${object.name}</td>
                              <td>${object.email}</td>
                              <td>${object.language}</td>
                              <td>${object.rate}</td>
                              <td><button type="button" class="btn btn-success" 
                              onclick="updateTranslator(${orderDocument.id}, ${object.id}, '${object.name}', 
                              '${object.rate}')">
                                    <span class="fa fa-plus"></span>
                                  </button>
                              </td>
                            </tr>`;
                $('#translatorTableBody').append(newRow);
            });
            updatePagination(orderDocument, translatorsInPage);
        }
    });
}

function updatePagination(orderDocument, objectsInPage) {
    translatorPagination.empty();
    $.ajax({
        url: translatorsRestUrl + `/document/count?language=${orderDocument.language}`,
        method: 'GET',
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
                getPaginatedTranslators(orderDocument, translatorPage);
            });
        }
    })
}

function updateTranslator(documentId, translatorId, translatorName, translatorRate) {
    if (documentId === null) {
        const editForm = $('#docForm');
        editForm.find('input[name="translatorId"]').val(translatorId);
        editForm.find('input[name="translatorName"]').val(translatorName);
        editForm.find('input[name="translatorRate"]').val(translatorRate);
        closeModal("changeTranslator");
    } else {
        $.ajax({
            url: ordersRestUrl + `/${orderId}/documents/${documentId}/translator?translatorId=${translatorId}`,
            method: "PATCH",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function () {
                closeModal("changeTranslator");
                loadContent();
                successNotyBottomRight();
            }
        });
    }
}

function loadApostilles(order) {
    $('#aposTableBody').empty();
    let apostilles = order.apostilles;

    apostilles.forEach(apostille => {
        let newRow = `<tr>
                        <td hidden="hidden">${apostille.id}</td>
                        <td>${apostille.title}</td>
                        <td>${apostille.submissionCountry}</td>
                        <td>${apostille.submissionDepartment}</td>
                        <td>${apostille.cost}</td>
                        <td><button type="button" class="btn btn-warning" onclick="editApostille('${apostille.id}')">
                                    <span class="fa-solid fa-pencil"></span>
                            </button>
                        </td>
                        <td><button type="button" class="btn btn-danger" onclick="deleteApostille('${apostille.id}')">
                                    <span class="fa-solid fa-xmark"></span>
                            </button>
                        </td>
                       </tr>`;

        $('#aposTableBody').append(newRow);
    });

}

function deleteApostille(id) {
    doDelete(`${ordersRestUrl}/${orderId}/apostilles/${id}`);
}

function editApostille(apostilleId) {
    openModal("aposModal");
    const editForm = $('#aposForm');
    $.ajax({
        url: ordersRestUrl + `/${orderId}/apostilles/${apostilleId}`,
        method: "GET",
        success: function (data) {
            $.each(data, function (key, value) {
                editForm.find("input[name='" + key + "']").val(value);
            })
        }
    });
}

let checkBoxGroup;

function editDocument(documentId) {
    openModal("docModal");
    checkBoxGroup = $('#checkBoxGroup').detach();
    const editForm = $('#docForm');
    $.ajax({
        url: ordersRestUrl + `/${orderId}/documents/${documentId}`,
        method: "GET",
        success: function (data) {
            $.each(data, function (key, value) {
                editForm.find("input[name='" + key + "']").val(value);
            });
            $('#documentLanguage').val(data.language);
            $('#isHardComplexity').prop('checked', data.isHardComplexity);
        }
    });
}

function changeDocumentComplexity(checkbox, id) {
    let isHardComplexity = checkbox.is(":checked");
    let updateRate = false;

    if (confirm("Update translator rate?")) {
        updateRate = true;
    }

    $.ajax({
        url: ordersRestUrl +
            `/${orderId}/documents/${id}/complexity?isHardComplexity=${isHardComplexity}&updateRate=${updateRate}`,
        method: "PATCH",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function () {
            loadContent();
        }
    });
}

function saveDocument() {
    const editForm = $('#docForm');
    const documentId = editForm.find('#documentId').val();
    const translatorId = editForm.find('#translatorId').val();

    let json = convertFormToJson(editForm);
    json.isHardComplexity = editForm.find('#isHardComplexity').is(":checked") ? "true" : "false";
    json.documentLanguage = editForm.find('#documentLanguage').val();

    let method = "PUT";
    let url = ordersRestUrl + `/${orderId}/documents/${documentId}?translatorId=${translatorId}`;

    if (documentId === null || documentId === "") {
        method = "POST";
        url = ordersRestUrl + `/${orderId}/documents?translatorId=${translatorId}`;
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
            closeModal("docModal");
            loadContent();
        }
    });
}

function saveApostille() {
    const editForm = $('#aposForm');
    const apostilleId = editForm.find('#apostilleId').val();
    let method = "PUT";
    let url = ordersRestUrl + `/${orderId}/apostilles/${apostilleId}`;

    if (apostilleId === null || apostilleId === "") {
        method = "POST";
        url = ordersRestUrl + `/${orderId}/apostilles`;
    }

    $.ajax({
        url: url,
        contentType: "application/json; charset=utf-8",
        method: method,
        data: convertFormToJsonString(editForm),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function () {
            closeModal("aposModal");
            loadContent();
        }
    });
}

function saveOrder() {
    const editForm = $('#orderForm');
    $.ajax({
        url: ordersRestUrl,
        contentType: "application/json; charset=utf-8",
        method: "PUT",
        data: convertFormToJsonString(editForm),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function () {
            location.href = "orders";
        }
    })
}

function openDocumentModal() {
    openModal("docModal");
    if (checkBoxGroup !== undefined) {
        $('#appendPlaceCheckbox').append(checkBoxGroup);
        checkBoxGroup = undefined;
    }
    $('#isHardComplexity').prop('checked', false);
}