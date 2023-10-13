const dateFormat = "DD.MM.YYYY";
const orderPagination = $('#orderPagination');
const orderTable = $('#datatableBody');

let orderPage;
let objectsInPage = 10;

function loadContent(currentPage) {
/*    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
            failNoty(jqXHR);
    });*/
    orderPage = currentPage === undefined ? 0 : currentPage;
    $.ajax({
        url: ordersRestUrl + `?page=${orderPage}`,
        method: "GET",
        success: function (data) {
            orderTable.empty();
            data.forEach(order => {
                let newRow = $('<tr>');
                addStatusStyle(order, newRow);
                newRow.append(`<td>${order.formattedId}</td>
                               <td>${order.customerName}</td>   
                               <td>${order.customerPhone}</td> 
                               <td>${order.customerEmail}</td> 
                               <td>${order.prepaid}</td>`);

                let surcharge = order.surcharge === null ? "-" : order.surcharge;
                let summaryCost = order.summaryCost === null ? "-" : order.summaryCost;
                let creationDate = formatDate(getDateFromArray(order.creationDate), dateFormat);
                let deliveryDate = formatDate(getDateFromArray(order.deliveryDate), dateFormat);
                let detailsUrl = ordersUrl + `/${order.id}`;

                newRow.append(`<td>${surcharge}</td>
                               <td>${summaryCost}</td>   
                               <td>${creationDate}</td> 
                               <td>${deliveryDate}</td> 
                               <td>${order.note}</td>
                               <td><button type="button" class="btn btn-warning" 
                                onclick="redirectTo('${detailsUrl}')">
                                <span class="fa-solid fa-pencil"></span></button></td>
                               <td><button type="button" class="btn btn-danger" 
                                onclick="deleteAndUpdateTable(${order.id})">
                                <span class="fa-solid fa-xmark"></span></button></td>`);
                orderTable.append(newRow);
            });
            updatePagination(objectsInPage);
        }
    })
}

function updatePagination(objectsInPage) {
    orderPagination.empty();
    $.ajax({
        url: ordersRestUrl + "/count",
        method: "GET",
        success: function (data) {
            let pages = Math.ceil(data / objectsInPage);
            for (let i = 1; i <= pages; i++) {
                const isActive = i === orderPage + 1 ? 'active' : '';
                orderPagination.append(`<li class="page-item ${isActive}">
                                        <a class="page-link" data-page="${i - 1}" >${i}</a>
                                     </li>`);
            }

            $('.page-link').on('click', function () {
                orderPage = parseInt($(this).data('page'));
                loadContent(orderPage);
            });
        }
    })
}

function addStatusStyle(order, row) {
    if (order.orderStatus === "COMPLETED") {
        row.prop("class", "completed");
    }
}

function deleteAndUpdateTable(id) {
        doDelete(`${ordersRestUrl}/${id}`);
}

function save() {
    let form = $("#detailsForm");
    let json = convertFormToJson(form);
    json.orderStatus = "IN_WORK";
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: ordersRestUrl,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        data: JSON.stringify(json)
    }).done(function (data, textStatus, jqXHR) {
        closeModal("editRow");
        if (textStatus === "success") {
            redirectTo(jqXHR.getResponseHeader('Location'));
        }
    });
}
