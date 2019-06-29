'use strict';

addUser.addEventListener("click",function () {
    var showAddUser = document.querySelector('.addUsr');


    history.pushState(null,null,'client/add/user');

    showAddUser.style.display = "block";

    hideAllTables();
});



document.forms.add.onsubmit = function (e) {
    e.preventDefault();

    var name = document.forms.add.name.value;
    var surname = document.forms.add.surname.value;
    var lastName = document.forms.add.lastName.value;
    var date = document.forms.add.date.value;
    var gender = document.forms.add.gender.value;
    var citizenship = document.forms.add.citizenship.value;
    var maritalStatus = document.forms.add.maritalStatus.value;
    var webSite = document.forms.add.webSite.value;
    var email = document.forms.add.email.value;
    var workplace = document.forms.add.workplace.value;
    var country = document.forms.add.country.value;
    var city = document.forms.add.city.value;
    var street = document.forms.add.street.value;
    var house = document.forms.add.house.value;
    var numOfFlat = document.forms.add.numOfFlat.value;
    var index = document.forms.add.index.value;


    var options = {
        method : 'POST',
        headers : {'Content-Type' : 'application/x-www-form-urlencoded'},
        body : '&name=' + name + '&' + 'surname=' + surname + '&' + 'lastName=' + lastName + '&' + 'date=' + date
        + '&' + 'gender=' + gender + '&' + 'citizenship=' + citizenship + '&' + 'maritalStatus=' + maritalStatus  + '&' + 'webSite=' + webSite
        + '&' + 'email=' + email + '&' + 'workplace=' + workplace + '&' + 'country=' + country + '&' + 'city=' +  city + '&' + 'street=' +  street
        + '&' + 'house=' +  house + '&' + 'numOfFlat=' +  numOfFlat + '&' + 'index=' +  index
    };

    var currentUrl = window.location.href.split('/');
    var requestUrl = currentUrl[4] + currentUrl[5].substring(0,1).toUpperCase() + currentUrl[5].substring(1);

    var req = new Request(requestUrl,options);
    fetch(req).then(function (value) {
        var hideAddUsr = document.querySelector('.addUsr');
        hideAddUsr.style.display = 'none';
        // document.querySelector('.usrAdd').reset();

        history.back();
        refreshUserTable(0,5);
        showUserTable();

    });


};
var backBtn = document.querySelector('.backUser');
backBtn.addEventListener("click",function () {
    history.back();
    showUserTable();
    var showAddUser = document.querySelector('.addUsr');
    showAddUser.style.display = "none";
});

// delete user

deleteUser.addEventListener("click",function (ev) {
    var checkboxes = document.getElementsByName("users");
    var checked = [];
    var values = [];
    for (var i = 0; i < checkboxes.length;i++){
        if(checkboxes[i].checked){
            checked.push(checkboxes[i]);
        }
    }

    checked.forEach(function (value,index) {
        values.push(checked[index].value);
    });

    if(values.length !== 0){
        var options = {
            method : 'DELETE',
            headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
        };
        var req = new Request('client/delete/user?id=' + values ,options);
        fetch(req).then(function (value) {
            refreshUserTable(0,5);
        });

    }else{
        alert("You should choose user to delete");
    }
});


//edit user
var userEditForm = document.querySelector('#editUsr');
var userApplyButton = document.querySelector('#userEdit');

var table = document.querySelector('#dataTable');

var counter;
var userId;


var input = document.querySelector('#img');


input.addEventListener("change",function (ev) {
     toDataURL(input);
});

function editUser() {

    history.pushState(null,null,'client/edit/user');
    showAllTablesExceptUser();
    refreshAllTableByUserId(userId);
    showUserImage();

    userEditForm.style.display = 'block';

    document.forms.edit.name.value = result[1].children[0].children[1].innerHTML;
    document.forms.edit.surname.value = result[1].children[0].children[0].innerHTML;
    document.forms.edit.lastName.value = result[1].children[0].children[2].innerHTML;
    document.forms.edit.date.value = result[2].innerHTML;
    document.forms.edit.gender.value = result[3].innerHTML;
    document.forms.edit.citizenship.value =  result[4].innerHTML;
    document.forms.edit.maritalStatus.value = result[5].innerHTML;
    document.forms.edit.webSite.value = result[6].innerHTML;
    document.forms.edit.email.value = result[7].innerHTML;
    document.forms.edit.country.value = result[8].innerHTML;
    document.forms.edit.city.value = result[9].innerHTML;
    document.forms.edit.street.value = result[10].innerHTML;
    document.forms.edit.house.value = result[11].innerHTML;
    document.forms.edit.numOfFlat.value = result[12].innerHTML;
    document.forms.edit.index.value = result[13].innerHTML;
    document.forms.edit.workplace.value = result[14].innerHTML;
}
var result;
table.addEventListener("click",function (evt) {

    var rows = document.querySelectorAll('.usersRow');
    var td = document.querySelectorAll('.fullName');
    for (var i = 0; i < rows.length ; i++) {
        var currentRow = table.rows[i];
        var t = td[i];
        var createClickHandler = function (row) {
            return function () {
                userId = row.getElementsByTagName("td")[0].children[0].getAttribute('value');
                result = row.getElementsByTagName("td");
                counter = row.rowIndex - 1;
                editUser();
            }
        };
        t.onclick = createClickHandler(currentRow);
    }

});

var bac2Btn = document.querySelector('.backEditUser');
bac2Btn.addEventListener("click",function (evt) {
    history.back();
    showUserTable();
    refreshUserTable(0,5);
    hideAllTablesExceptUser();
    userEditForm.style.display = 'none';
});

userApplyButton.addEventListener("click",function (evt) {
    userEditForm.style.display = 'none';


    var name = document.forms.edit.name.value;
    var surname =  document.forms.edit.surname.value;
    var lastName =  document.forms.edit.lastName.value;
    var date = document.forms.edit.date.value;
    var gender = document.forms.edit.gender.value;
    var citizenship = document.forms.edit.citizenship.value;
    var maritalStatus = document.forms.edit.maritalStatus.value;
    var webSite = document.forms.edit.webSite.value;
    var email = document.forms.edit.email.value;
    var workplace = document.forms.edit.workplace.value;
    var country = document.forms.edit.country.value;
    var city =  document.forms.edit.city.value;
    var street = document.forms.edit.street.value;
    var house = document.forms.edit.house.value;
    var numOfFlat = document.forms.edit.numOfFlat.value;
    var index = document.forms.edit.index.value;
    var photoPath = document.getElementById('img');



    var data = new FormData();
    data.append('Photo',photoPath.files[0]);
    data.append('name',name.trim());
    data.append('surname',surname.trim());
    data.append('lastName',lastName.trim());
    data.append('birthDate',date);
    data.append('gender',gender);
    data.append('citizenship',citizenship.trim());
    data.append('maritalStatus',maritalStatus.trim());
    data.append('webSite',webSite.trim());
    data.append('email',email.trim());
    data.append('workplace',workplace.trim());
    data.append('country', country.trim());
    data.append('city',city.trim());
    data.append('street',street.trim());
    data.append('house',house.trim());
    data.append('numOfFlat',numOfFlat.trim());
    data.append('index',index);
    data.append('userId',userId);



    var options = {
        method : 'POST',
        mode: 'cors',
        body : data
    };
    var currentUrl = window.location.href.split('/');
    var requestUrl = currentUrl[4] + currentUrl[5].substring(0,1).toUpperCase() + currentUrl[5].substring(1);
    //how to send photo with params


    var request = new Request(requestUrl ,options );
    fetch(request).then(function (value) {
        if(typeof pickedPhone !== 'undefined'){
            history.back();
            phoneIdAcclumulator.forEach(function (value,index) {
                console.log(phoneIdAcclumulator);
                saveUpdatedPhone(index);
            })
        }
    }).then(function (value) {
        if(typeof pickedAttachment !== 'undefined'){
            history.back();
            history.pushState(null,null,'client/edit/attachment');
            attachmentIdAccumulator.forEach(function (value,index) {
                console.log(attachmentIdAccumulator);
                saveAttachmentUpdate(index);
            })
        }
    }).then(function () {
        refreshUserTable(0,5);
        hideAllTables();
        showUserTable();
        history.back();
        //refresh arrays of edit indixes
        phoneIdAcclumulator = [];
        attachmentIdAccumulator = [];
    }).catch(err => console.log(err));

});
function showUserImage() {
    var img = document.querySelector('#currentImage');
    img.src = 'client?command=showUserPhoto&userId=' + userId +'&refresh='+Math.random() ;
}


