function showUserInfo() {
    $("#userPageLink").addClass("active")
    $("#adminPageLink").removeClass("active")
    $("#adminPanel").hide()
    $("#userPanel").show()
}

function showAdminPanel() {
    $("#userPageLink").removeClass("active")
    $("#adminPageLink").addClass("active")
    $("#userPanel").hide()
    $("#adminPanel").show()
}


// получить текущего пользователя
function loadCurrentUser() {
    $.ajax({
        url: "http://localhost:8080/currentUser"
    }).then(function(data) {
        $('#navbar-email').append(data.username);
        let roles = "";
        $.each(data['roles'], function ( k, v ) {
            let cut = v.role_name.substring(5);
            roles = roles + cut + " "
            $('#navbar-roles').append(cut + " ");
        });

        let tableBody = $('#userinfo-table-body');
        tableBody.append(
            "<tr class=\"align-middle myInfoTableRow\"> <td>" + data.id +
            "</td> <td>" + data.username +
            "</td> <td>" + data.name +
            "</td> <td>" + roles +
            "</td> </tr>");



    });




}

// загр таблицу
function loadTable() {
    clearTable();

    $.ajax({
        url: "http://localhost:8080/admin/getAllUsers"
    }).then(function (data) {
        $.each(data, function (rowIndex, user) {
            let tableBody = $('#my-table-body');
            let roles = "";

            $.each(user['roles'], function ( index, role ) {
                let cut = role.role_name.substring(5);
                roles = roles + cut + " ";
            });

            tableBody.append(
                "<tr class=\"align-middle myTableRow\"> <td>" + user.id +
                "</td> <td>" + user.username +
                "</td> <td>" + user.name +
                "</td> <td>" + roles +
                "</td> <td>" + "<button " +
                "onclick=\"showEditModal(" + user.id + ")\"" +
                "class=\"btn btn-success\">Edit</button>" +
                "</td> <td>" + "<button " +
                "onclick=\"showDeleteModal(" + user.id + ")\"" +
                "class=\"btn btn-danger\">Delete</button>" +
                "</td> </tr>");
        });
    });
}

function clearTable() {
    $('.myTableRow').remove();
}

function showEditModal(id) {
    let modal = $('#editModal');
    updateModal(id);

    modal.show();
}

function showDeleteModal(id) {
    let modal = $('#deleteModal');
    updateModal(id);


    modal.show();
}
// обновить окна
function updateModal(id) {
    const rolefield = $('.modalRolesField');
    $('.modalRoleOption').remove()

    // подгрузить информацию о юзере
    $.ajax({
        url: ("http://localhost:8080/admin/getOne/" + id)
    }).then(function (data) {
        $('.modalIdField').val(data.id);
        $('.modalEmailField').val(data.username);
        $('.modalPasswordField').val(data.password);
        $('.modalUsernameField').val(data.name);
    });

    // подгрузить все роли
    $.ajax({
        url: ("http://localhost:8080/admin/getAllRoles/")
    }).then( function (data) {

        data.forEach( function ( k, v ) {
            let cut = k.role_name.substring(5);
            rolefield.append( "<option value='" +
                k.id +
                "' class='modalRoleOption'> " +
                cut +
                " </option>");
        })
    });
}

function hideModals() {
    $('#editModal').hide();
    $('#deleteModal').hide();
}

function submitNewUser() {
    let email = $('#newUserEmail')
    let name = $('#newUserName')
    let psw = $('#newUserPassword')
    let roles = [];

    $('#newUserRoles option:selected').each( function(i) {
        console.log("a selected role: " + $(this).val())
        roles.push( { "id": $(this).val() })
    })

    let newuser = [ {"password": psw.val(), "username" : email.val(), "name": name.val(), "roles" : roles} ];

    $.ajax({
        type: "POST",
        url: ("http://localhost:8080/admin/add"),
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        data: JSON.stringify(newuser),
        processData: false,
        failure: function(errMsg) {
            alert(errMsg);
        }
    });


    document.activeElement.blur()
    email.val('')
    psw.val('')
    name.val('')

    $('#home-tab').trigger('click')



    // загрузить с задержкой, нужно менять в зависимости от скорости db
    setTimeout(function () {loadTable()}, 2000);

}

function submitUserEdit() {
    let id = $('.modalIdField').val();
    let email = $('.modalEmailField').val();
    let psw = $('.modalPasswordField').val();
    let name = $('.modalUsernameField').val();
    let roles = [];

    $('.editModalRolesField option:selected').each( function(i) {
        console.log("a selected role: " + $(this).val())
        roles.push( { "id": $(this).val()})
    })

    let user = [ {"id": id, "password": psw, "username": email, "name": name, "roles": roles} ];
    $.ajax({
        type: "POST",
        url: ("http://localhost:8080/admin/update"),
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        data: JSON.stringify(user),
        processData: false,
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
    setTimeout( function () {loadTable()}, 1000);
    hideModals();
}
function submitDeleteUser() {
    let id = $('.modalIdField').val();
    $.ajax({
        url: ("http://localhost:8080/admin/delete/" + id),
    });
    setTimeout( function () {loadTable()}, 1000);
    hideModals();
}

function loadRolesForNewUser() {
    let rolesSelect = $("#newUserRoles")


    $.ajax({
        url: ("http://localhost:8080/admin/getAllRoles/")
    }).then( function (data) {
        data.forEach( function ( k, v ) {
            let cut = k.role_name.substring(5);
            rolesSelect.append( "<option value='" +
                k.id +
                "' class='newUserOption'> " +
                cut +
                " </option>");
        })
    });
}

function loadUserInfo() {
    $.ajax({
        url: "http://localhost:8080/currentUser"
    }).then(function(data) {
        $('#navbar-email').append(data.username);
        $('#userAndAdmin a').remove()
        let sidetable = $('#userAndAdmin')
        let tableBody = $('#my-table-body');

        let roles = "";

        $.each(data['roles'], function ( k, v ) {
            let cut = v.role_name.substring(5);

            roles = roles + cut + " ";
            $('#navbar-roles').append(cut + " ");
        });


        tableBody.append(
            "<tr class=\"align-middle myTableRow\"> <td>" + data.id +
            "</td> <td>" + data.username +
            "</td> <td>" + data.name +
            "</td> <td>" + roles +
            "</td> </tr>");
    });
}