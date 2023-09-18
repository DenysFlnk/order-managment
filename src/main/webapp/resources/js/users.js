const userTable = $('#userTableBody');
const userForm = $('#userForm');
const roleAdmin = $('#roleAdmin');

function loadContent() {
    $.ajax({
        url: usersRestUrl,
        method: "GET",
        success: function (data) {
            userTable.empty();
            data.forEach(user => {
                let newRow = $('<tr>');
                newRow.attr("available", user.enabled);
                let checkBox = user.enabled ? "checked" : "";
                newRow.append(`<td><input type="checkbox" ${checkBox} 
                               onclick="enable($(this), ${user.id})"></td>
                               <td>${user.name}</td>  
                               <td>${user.roles}</td> 
                                <span class="fa-solid fa-angle-down"></span></button></td>
                               <td><button type="button" class="btn btn-warning" 
                                onclick="editUser('${user.id}')">
                                <span class="fa-solid fa-pencil"></span></button></td>
                               <td><button type="button" class="btn btn-danger" 
                                onclick="doDelete(usersRestUrl + '/' + ${user.id})">
                                <span class="fa-solid fa-xmark"></span></button></td>`);
                userTable.append(newRow);
            });
        }
    });
}

function enable(checkBox, id) {
    let enable = checkBox.is(":checked");
    $.ajax({
        url: usersRestUrl + `/${id}?isEnabled=${enable}`,
        method: "PATCH",
        success: function () {
            loadContent();
        }
    });
}

function editUser(id) {
    $.ajax({
        url: usersRestUrl + `/${id}`,
        method: "GET",
        success: function (data) {
            openModal("userModal");
            $.each(data, function (key, value) {
                userForm.find("input[name='" + key + "']").val(value);
            });
            if (data.roles.includes("ADMIN")) {
                roleAdmin.prop("checked", true);
            }
        }
    })
}

function saveUser() {
    let userId = userForm.find('#userId').val();
    let json = convertFormToJson(userForm);
    if (roleAdmin.is(":checked")) {
        json.roles = ["USER", "ADMIN"];
    } else {
        json.roles = ["USER"]
    }
    json.enabled = "true";

    let method = "POST";
    let url = usersRestUrl;

    if (userId !== "" && userId !== null) {
        method ="PUT";
        url = url + `/${userId}`;
        json.enabled = userForm.find("#enabled").val();
    }

    $.ajax({
        url: url,
        contentType: "application/json; charset=utf-8",
        method: method,
        data: JSON.stringify(json),
        success: function () {
            roleAdmin.prop("checked", false);
            closeModal("userModal");
            loadContent();
        }
    });
}

function closeUserModal() {
    closeNoty();
    roleAdmin.prop("checked", false);
    closeModal("userModal");
}
