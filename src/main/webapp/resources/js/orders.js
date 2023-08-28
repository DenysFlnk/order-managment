const dateFormat = "DD.MM.YYYY";
const orderPagination = $('#orderPagination');
const orderTable = $('#datatableBody');

let orderPage;
let objectsInPage = 10;

function loadContent(currentPage) {
    orderPage = currentPage;
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
                               <td><button type="button" class="btn btn-warning btn-lg" 
                                onclick="redirectTo('${detailsUrl}')">Details</button></td>
                               <td><button type="button" class="btn btn-danger btn-lg" 
                                onclick="deleteAndUpdateTable(${order.id})">Delete</button></td>`);
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
    } else {
        row.prop("class", "in-work");
    }
}

/*function loadContent() {

    let orders = JSON.parse(doGet(ordersRestUrl).responseText);
    let table = document.getElementById("datatableBody");
    table.innerHTML = "";
    // createPaging(document.getElementById("limit").value, playersCount, currentPage);

    for (let i = 0; i < orders.length; i++) {

        let tr = document.createElement("tr");
        tr.classList.add(orders[i].orderStatus === "COMPLETED" ? "completed" : "in-work");

        let td1 = document.createElement("td");
        td1.appendChild(document.createTextNode(orders[i].formattedId));
        tr.appendChild(td1);

        let td2 = document.createElement("td");
        td2.appendChild(document.createTextNode(orders[i].customerName));
        tr.appendChild(td2);

        let td3 = document.createElement("td");
        td3.appendChild(document.createTextNode(orders[i].customerPhone));
        tr.appendChild(td3);

        let td4 = document.createElement("td");
        td4.appendChild(document.createTextNode(orders[i].customerEmail));
        tr.appendChild(td4);

        let td5 = document.createElement("td");
        td5.appendChild(document.createTextNode(orders[i].prepaid));
        tr.appendChild(td5);

        let td6 = document.createElement("td");
        td6.appendChild(document.createTextNode(orders[i].surcharge === null ? "-" : orders[i].surcharge));
        tr.appendChild(td6);

        let td7 = document.createElement("td");
        td7.appendChild(document.createTextNode(orders[i].summaryCost === null ? "-" : orders[i].summaryCost));
        tr.appendChild(td7);

        const dateFormat = "DD.MM.YYYY";

        let td8 = document.createElement("td");
        let creationDate = getDateFromArray(orders[i].creationDate);
        td8.appendChild(document.createTextNode(formatDate(creationDate, dateFormat)));
        tr.appendChild(td8);

        let td9 = document.createElement("td");
        let deliveryDate = getDateFromArray(orders[i].deliveryDate);
        td9.appendChild(document.createTextNode(formatDate(deliveryDate, dateFormat)));
        tr.appendChild(td9);

        let td10 = document.createElement("td");
        td10.appendChild(document.createTextNode(orders[i].note));
        tr.appendChild(td10);

        let editButton = document.createElement("button");
        editButton.setAttribute("type", "button");
        editButton.setAttribute("class", "btn btn-warning btn-lg");
        editButton.addEventListener("click", function () {
            redirectTo(ordersUrl + '/' + orders[i].id);
        });
        editButton.appendChild(document.createTextNode("Details"));

        let deleteButton = document.createElement("button");
        deleteButton.setAttribute("type", "button");
        deleteButton.setAttribute("class", "btn btn-danger btn-lg");
        deleteButton.appendChild(document.createTextNode("Delete"));
        deleteButton.addEventListener("click", function () {
            deleteAndUpdateTable(orders[i].id);
        });

        let td11 = document.createElement("td");
        td11.appendChild(editButton);
        tr.appendChild(td11);

        let td12 = document.createElement("td");
        td12.appendChild(deleteButton);
        tr.appendChild(td12);

        table.appendChild(tr);
    }
    window.scrollTo(500, 100);
}*/

function deleteAndUpdateTable(id) {
    if (confirm("Are you sure?")) {
        doDelete(ordersRestUrl + '/' + id);
        loadContent(orderPage);
    }
}

function save() {
    let form = $("#detailsForm");
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: ordersRestUrl,
        data: convertFormToJsonString(form)
    }).done(function (data, textStatus, jqXHR) {
        closeModal("editRow");
        if (textStatus === "success") {
            redirectTo(jqXHR.getResponseHeader('Location'));
        }
    });
}
