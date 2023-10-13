const userTable = $('#userTableBody');
const userForm = $('#userForm');
const roleAdmin = $('#roleAdmin');

function loadContent() {
/*    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
            failNoty(jqXHR);
    });*/
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
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
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

    function invalidPasswordNoty() {
        closeNoty();
        failedNote = new Noty({
            text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;<br>" + "Password length must be >= 6" +
                " and not contain blank characters",
            type: "error",
            layout: "bottomRight"
        });
        failedNote.show()
    }

    let method;
    let url;
    if (userId === "" || userId === undefined) {
        method = "POST";
        url = usersRestUrl;
        if (!isValidPassword(json.password)) {
            invalidPasswordNoty();
            return;
        }
        json.password = enc(json.password);
    } else {
        method ="PUT";
        url = usersRestUrl + `/${userId}`;
        json.enabled = userForm.find("#enabled").val();
        if (json.password !== "" && json.password !== undefined) {
            if (!isValidPassword(json.password)) {
                invalidPasswordNoty();
                return;
            }
            json.password = enc(json.password);
        } else {
            json.password = null;
        }
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

function isValidPassword(password) {
    if (password.includes(" ")) {
        return false;
    }
    return password.length >= 6;

}

function enc(msg) {
    function rotate_left(n,s) {
        let t4 = ( n<<s ) | (n>>>(32-s));
        return t4;
    }
    function lsb_hex(val) {
        let str='';
        let i;
        let vh;
        let vl;
        for( i=0; i<=6; i+=2 ) {
            vh = (val>>>(i*4+4))&0x0f;
            vl = (val>>>(i*4))&0x0f;
            str += vh.toString(16) + vl.toString(16);
        }
        return str;
    }

    function cvt_hex(val) {
        let str='';
        let i;
        let v;
        for( i=7; i>=0; i-- ) {
            v = (val>>>(i*4))&0x0f;
            str += v.toString(16);
        }
        return str;
    }
    function Utf8Encode(string) {
        string = string.replace(/\r\n/g,'\n');
        let utftext = '';
        for (let n = 0; n < string.length; n++) {
            let c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            }
            else if((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            }
            else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }
        }
        return utftext;
    }
    let blockstart;
    let i, j;
    let W = new Array(80);
    let H0 = 0x67452301;
    let H1 = 0xEFCDAB89;
    let H2 = 0x98BADCFE;
    let H3 = 0x10325476;
    let H4 = 0xC3D2E1F0;
    let A, B, C, D, E;
    let temp;
    msg = Utf8Encode(msg);
    let msg_len = msg.length;
    let word_array = new Array();
    for( i=0; i<msg_len-3; i+=4 ) {
        j = msg.charCodeAt(i)<<24 | msg.charCodeAt(i+1)<<16 |
            msg.charCodeAt(i+2)<<8 | msg.charCodeAt(i+3);
        word_array.push( j );
    }
    switch( msg_len % 4 ) {
        case 0:
            i = 0x080000000;
            break;
        case 1:
            i = msg.charCodeAt(msg_len-1)<<24 | 0x0800000;
            break;
        case 2:
            i = msg.charCodeAt(msg_len-2)<<24 | msg.charCodeAt(msg_len-1)<<16 | 0x08000;
            break;
        case 3:
            i = msg.charCodeAt(msg_len-3)<<24 | msg.charCodeAt(msg_len-2)<<16 | msg.charCodeAt(msg_len-1)<<8 | 0x80;
            break;
    }
    word_array.push( i );
    while( (word_array.length % 16) !== 14 ) word_array.push( 0 );
    word_array.push( msg_len>>>29 );
    word_array.push( (msg_len<<3)&0x0ffffffff );
    for ( blockstart=0; blockstart<word_array.length; blockstart+=16 ) {
        for( i=0; i<16; i++ ) W[i] = word_array[blockstart+i];
        for( i=16; i<=79; i++ ) W[i] = rotate_left(W[i-3] ^ W[i-8] ^ W[i-14] ^ W[i-16], 1);
        A = H0;
        B = H1;
        C = H2;
        D = H3;
        E = H4;
        for( i= 0; i<=19; i++ ) {
            temp = (rotate_left(A,5) + ((B&C) | (~B&D)) + E + W[i] + 0x5A827999) & 0x0ffffffff;
            E = D;
            D = C;
            C = rotate_left(B,30);
            B = A;
            A = temp;
        }
        for( i=20; i<=39; i++ ) {
            temp = (rotate_left(A,5) + (B ^ C ^ D) + E + W[i] + 0x6ED9EBA1) & 0x0ffffffff;
            E = D;
            D = C;
            C = rotate_left(B,30);
            B = A;
            A = temp;
        }
        for( i=40; i<=59; i++ ) {
            temp = (rotate_left(A,5) + ((B&C) | (B&D) | (C&D)) + E + W[i] + 0x8F1BBCDC) & 0x0ffffffff;
            E = D;
            D = C;
            C = rotate_left(B,30);
            B = A;
            A = temp;
        }
        for( i=60; i<=79; i++ ) {
            temp = (rotate_left(A,5) + (B ^ C ^ D) + E + W[i] + 0xCA62C1D6) & 0x0ffffffff;
            E = D;
            D = C;
            C = rotate_left(B,30);
            B = A;
            A = temp;
        }
        H0 = (H0 + A) & 0x0ffffffff;
        H1 = (H1 + B) & 0x0ffffffff;
        H2 = (H2 + C) & 0x0ffffffff;
        H3 = (H3 + D) & 0x0ffffffff;
        H4 = (H4 + E) & 0x0ffffffff;
    }
    temp = cvt_hex(H0) + cvt_hex(H1) + cvt_hex(H2) + cvt_hex(H3) + cvt_hex(H4);

    return temp.toLowerCase();
}
