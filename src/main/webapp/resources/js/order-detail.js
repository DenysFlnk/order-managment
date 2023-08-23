let translatorPage = 0;
const translatorPagination = $('#translatorPagination');

let orderUrl = window.location.pathname;
let orderId = parseOrderIdFromUrl(orderUrl);

let lastSavedDoc;

function loadContent() {
    let orderNumberH2 = document.getElementById("orderNumber");
    orderNumberH2.textContent = "Order #KP0" + orderId;

    let order = JSON.parse(doGet(ordersRestUrl + '/' + orderId).responseText);

    document.getElementById("id").setAttribute("value", orderId);

    let customerName = document.getElementById("customerName");
    customerName.setAttribute("value", order.customerName);

    let customerPhone = document.getElementById("customerPhone");
    customerPhone.setAttribute("value", order.customerPhone);

    let customerEmail = document.getElementById("customerEmail");
    customerEmail.setAttribute("value", order.customerEmail);

    const dateFormat = "YYYY-MM-DD";

    let creationDate = document.getElementById("creationDate");
    creationDate.setAttribute("value", formatDate(getDateFromArray(order.creationDate), dateFormat));

    let deliveryDate = document.getElementById("deliveryDate");
    deliveryDate.setAttribute("value", formatDate(getDateFromArray(order.deliveryDate), dateFormat));

    let orderStatus = document.getElementById("orderStatus");
    orderStatus.value = order.orderStatus;

    let prepaid = document.getElementById("prepaid");
    prepaid.setAttribute("value", order.prepaid);

    let surcharge = document.getElementById("surcharge");
    surcharge.setAttribute("value", order.surcharge);

    let summaryCost = document.getElementById("summaryCost");
    summaryCost.setAttribute("value", order.summaryCost);

    let note = document.getElementById("note");
    note.setAttribute("value", order.note);

    loadDocuments(order);
    loadApostilles(order);
}

function parseOrderIdFromUrl(url) {
    const pattern = /\/orders\/(\d+)/;
    const match = url.match(pattern);

    return match[1];
}

function loadDocuments(order) {
    let documentTable = document.getElementById("docTableBody");
    documentTable.innerHTML = "";

    let documents = order.documentTos;

    for (let i = 0; i < documents.length; i++) {
        let tr = document.createElement("tr");
        if (documents[i].isHardComplexity) {
            tr.classList.add("hard-complexity");
        }

        let td1 = document.createElement("td");
        td1.setAttribute("hidden", 'hidden');
        td1.appendChild(document.createTextNode(documents[i].id));
        tr.appendChild(td1);

        let td2 = document.createElement("td");
        td2.appendChild(document.createTextNode(documents[i].language.toLowerCase()));
        tr.appendChild(td2);

        let td3 = document.createElement("td");
        td3.appendChild(document.createTextNode(documents[i].officeRate));
        tr.appendChild(td3);

        let td4 = document.createElement("td");
        td4.appendChild(document.createTextNode(documents[i].signsNumber === null ? "-" : documents[i].signsNumber));
        tr.appendChild(td4);

        let td5 = document.createElement("td");
        td5.appendChild(document.createTextNode(documents[i].notarizationCost));
        tr.appendChild(td5);

        let td6 = document.createElement("td");
        td6.appendChild(document.createTextNode(documents[i].officeCost));
        tr.appendChild(td6);


        let td7 = document.createElement("td");
        if (documents[i].translatorName === null) {
            let addTranslatorButton = document.createElement("button");
            addTranslatorButton.setAttribute("type", "button");
            addTranslatorButton.setAttribute("class", "btn btn-success");
            addTranslatorButton.addEventListener("click", function () {
                showTranslatorsFor(documents[i]);
            });
            let spanPlus = document.createElement("span");
            spanPlus.setAttribute("class", "fa fa-plus");
            addTranslatorButton.appendChild(spanPlus);
            td7.appendChild(addTranslatorButton);
        } else {
            td7.appendChild(document.createTextNode(documents[i].translatorName));
        }
        tr.appendChild(td7);

        let td8 = document.createElement("td");
        td8.appendChild(document.createTextNode(documents[i].translatorRate === null ? "-" : documents[i].translatorRate));
        tr.appendChild(td8);

        let td9 = document.createElement("td");
        td9.appendChild(document.createTextNode(documents[i].translatorTax === null ? "-" : documents[i].translatorTax));
        tr.appendChild(td9);

        let editButton = document.createElement("button");
        editButton.setAttribute("type", "button");
        editButton.setAttribute("class", "btn btn-warning");
        editButton.appendChild(createSpanEllipsis());
        editButton.addEventListener("click", function () {
            editDocument(documents[i].id);
        });

        let deleteButton = document.createElement("button");
        deleteButton.setAttribute("type", "button");
        deleteButton.setAttribute("class", "btn btn-danger");
        deleteButton.appendChild(createSpanMinus());
        deleteButton.addEventListener("click", function () {
            if (confirm("Are you sure?")) {
                doDelete(ordersRestUrl + "/" + orderId + "/documents/" + documents[i].id);
                loadContent();
            }
        });

        let td10 = document.createElement("td");
        td10.appendChild(editButton);
        tr.appendChild(td10);

        let td11 = document.createElement("td");
        td11.appendChild(deleteButton);
        tr.appendChild(td11);

        documentTable.appendChild(tr);
    }

}

function showTranslatorsFor(doc) {
    openModal('changeTranslator');
    translatorPage = 0;

    let orderDocument;
    if (doc === "" || doc == null) {
        const editForm = $('#docForm');
        let isHardComplexity = editForm.find('#isHardComplexity').is(":checked") ? "true" : "false";
        let documentLanguage = editForm.find('#documentLanguage').val();

        orderDocument = {
            id: null,
            language: documentLanguage,
            isHardComplexity: isHardComplexity
        }
    } else {
        orderDocument = doc;
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
            console.log(orderDocument)
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
            updatePagination(orderDocument, data.length);
        }
    });
}

function updatePagination(orderDocument, objectsInPage) {
    translatorPagination.empty();
    $.ajax({
        url: translatorsRestUrl + `/document/count?language=${orderDocument.language}`,
        method: 'GET',
        success: function (data) {
            let pages = data / objectsInPage;
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
            success: function () {
                closeModal("changeTranslator");
                loadContent();
            }
        });
    }
}

function loadApostilles(order) {
    let apostilleTable = document.getElementById("aposTableBody");
    apostilleTable.innerHTML = "";

    let apostilles = order.apostilles;

    for (let i = 0; i < apostilles.length; i++) {
        let tr = document.createElement("tr");

        let td1 = document.createElement("td");
        td1.setAttribute("hidden", 'hidden');
        td1.appendChild(document.createTextNode(apostilles[i].id));
        tr.appendChild(td1);

        let td2 = document.createElement("td");
        td2.appendChild(document.createTextNode(apostilles[i].title));
        tr.appendChild(td2);

        let td3 = document.createElement("td");
        td3.appendChild(document.createTextNode(apostilles[i].submissionCountry));
        tr.appendChild(td3);

        let td4 = document.createElement("td");
        td4.appendChild(document.createTextNode(apostilles[i].submissionDepartment));
        tr.appendChild(td4);

        let td5 = document.createElement("td");
        td5.appendChild(document.createTextNode(apostilles[i].cost));
        tr.appendChild(td5);

        let editButton = document.createElement("button");
        editButton.setAttribute("type", "button");
        editButton.setAttribute("class", "btn btn-warning");
        editButton.appendChild(createSpanEllipsis());
        editButton.addEventListener("click", function () {
            editApostille(apostilles[i].id);
        });

        let deleteButton = document.createElement("button");
        deleteButton.setAttribute("type", "button");
        deleteButton.setAttribute("class", "btn btn-danger");
        deleteButton.appendChild(createSpanMinus());
        deleteButton.addEventListener("click", function () {
            if (confirm("Are you sure?")) {
                doDelete(ordersRestUrl + "/" + orderId + "/apostilles/" + apostilles[i].id);
                loadContent();
            }
        });

        let td6 = document.createElement("td");
        td6.appendChild(editButton);
        tr.appendChild(td6);

        let td7 = document.createElement("td");
        td7.appendChild(deleteButton);
        tr.appendChild(td7);

        apostilleTable.appendChild(tr);
    }
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

function editDocument(documentId) {
    openModal("docModal");
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
        success: function () {
            closeModal("docModal");
            clearDocumentForm();
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
        success: function () {
            closeModal("aposModal");
            editForm.empty();
            loadContent();
        }
    });
}

function saveOrder() {
    const editForm = $('#orderForm');
    console.log(convertFormToJson(editForm));
    $.ajax({
        url: ordersRestUrl,
        contentType: "application/json; charset=utf-8",
        method: "PUT",
        data: convertFormToJsonString(editForm),
        success: function () {
            location.href = "orders";
        }
    })
}

function createSpanEllipsis() {
    let spanEllipsis = document.createElement("span");
    spanEllipsis.setAttribute("class", "fa fa-ellipsis");
    return spanEllipsis;
}

function createSpanMinus() {
    let spanMinus = document.createElement("span");
    spanMinus.setAttribute("class", "fa fa-minus");
    return spanMinus;
}

function openDocumentModal() {
    openModal("docModal");
    $('#isHardComplexity').prop('checked', false);
}