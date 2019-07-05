'use strict';



addUser.addEventListener("click",function () {
    hideSearchForm();

    var showAddUser = document.querySelector('.addUsr');


    history.pushState(null,null,'client/add/user');

    showAddUser.style.display = "block";

    hideAllTables();
});


//add user
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



    if (isValidUserForm(name,surname,lastName,citizenship,country,city,email,index) && !validateDate(date.toString())) {


        fetch('',options).then(function (value) {
            var hideAddUsr = document.querySelector('.addUsr');
            hideAddUsr.style.display = 'none';
            resetUserAddForm();

            history.back();
            refreshUserTable(0,5);
            showUserTable();

            if (value.status === 500) {
                return value.json();
            }
        }).then(function(res) {
            showError(res);
        }).catch(err => console.log(err));
    }else {
        alert('Name, surname, lastName,citizenship,maritalStatus,country,city should contain only letters and index should contain only digits ');
    }


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
    hideSearchForm();
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

            if (value.status === 500) {
                return value.json();
            }
        }).then(function(res) {
            showError(res);
        }).catch(err => console.log(err));

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
    hideSearchForm();
    history.pushState(null,null,'client/edit/user');
    showAllTablesExceptUser();
    refreshAllTableByUserId(userId);
    showUserImage();

    userEditForm.style.display = 'block';

    document.forms.edit.name.value = result[1].children[0].children[1].innerHTML.trim();
    document.forms.edit.surname.value = result[1].children[0].children[0].innerHTML.trim();
    document.forms.edit.lastName.value = result[1].children[0].children[2].innerHTML.trim();
    document.forms.edit.date.value = result[2].innerHTML.trim();
    document.forms.edit.gender.value = result[3].innerHTML.trim();
    document.forms.edit.citizenship.value =  result[4].innerHTML.trim();
    document.forms.edit.maritalStatus.value = result[5].innerHTML.trim();
    document.forms.edit.webSite.value = result[6].innerHTML.trim();
    document.forms.edit.email.value = result[7].innerHTML.trim();
    document.forms.edit.country.value = result[8].innerHTML.trim();
    document.forms.edit.city.value = result[9].innerHTML.trim();
    document.forms.edit.street.value = result[10].innerHTML.trim();
    document.forms.edit.house.value = result[11].innerHTML.trim();
    document.forms.edit.numOfFlat.value = result[12].innerHTML.trim();
    document.forms.edit.index.value = result[13].innerHTML.trim();
    document.forms.edit.workplace.value = result[14].innerHTML.trim();
}
var result;

table.addEventListener("mouseover",function (evt) {

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

    var result = true;

    var data = new FormData();
    if (typeof photoPath.files[0] !== 'undefined') {
        var imgOnly = photoPath.files[0].type.split('/')[0];
        result = imgOnly === 'image' || imgOnly === 'undefined';

    }
    data.append('Photo', photoPath.files[0]);
    data.append('name',name.trim());
    data.append('surname',surname.trim());
    data.append('lastName',lastName.trim());
    data.append('birthDate',date);
    data.append('gender',gender);
    data.append('citizenship',citizenship.trim());
    data.append('maritalStatus',maritalStatus.trim());
    data.append('webSite',webSite.trim());
    data.append('email',email.trim());
    data.append('country', country.trim());
    data.append('city',city.trim());
    data.append('street',street.trim());
    data.append('house',house.trim());
    data.append('numOfFlat',numOfFlat.trim());
    data.append('index',index);
    data.append('workplace',workplace.trim());
    data.append('userId',userId);



    var options = {
        method : 'POST',
        body : data
    };


    if (isValidUserForm(name,surname,lastName,citizenship,country,city,email, index) && result  && !validateDate(date.toString())) {
        userEditForm.style.display = 'none';

        fetch('',options).then(function (value) {
            if (value.status === 500) {
                return value.json();
            }
        }).then(function(res) {
            showError(res);
        }).then(function (value) {
            if(typeof pickedPhone !== 'undefined'){
                history.back();
                history.pushState(null,null,'client/edit/phone');
                indexOfUpdatedPhone.forEach(function (value,index) {
                    savePhoneUpdate(value);
                })
                sendUpdatedPhone();
            }
        }).then(function (value) {
            if(typeof pickedAttachment !== 'undefined'){
                history.back();
                history.pushState(null,null,'client/edit/attachment');
                indexOfUpdatedAttachment.forEach(function (value,index) {
                    saveAttachmentUpdate(value);
                })
                sendUpdatedAttachment();
            }
        }).then(function (value) {
            refreshUserTable(0,5);
            hideAllTables();
            showUserTable();
            history.back();

            resetUserEditFileElement();

            refreshAttachmentFields();
            refreshPhoneFields();
            //refresh arrays of edit indixes
            indexOfUpdatedPhone = [];
            indexOfUpdatedAttachment = [];
            phoneIdAcclumulator = [];
            attachmentIdAccumulator = [];

        }).catch(err => console.log(err));
    }else if (validateDate(date)) {
        alert('Your birthday cant be in the future please choose correct date');
    }else {
        alert('Name, surname, lastName,citizenship,maritalStatus,country,city should contain only letters and index should contain only digits and you can upload only image ');
    }


});

//set extension
function showUserImage() {

    var path  =  document.getElementsByClassName('photoPath');

    var fileL = path[counter].innerHTML.split("\\").length;
    var extension = path[counter].innerHTML.split('\\')[fileL - 1].split('.')[1];


    var img = document.querySelector('#currentImage');
    img.src = 'client?command=showUserPhoto&userId=' + userId + '&extension=' + extension  + '&refresh=' + Math.random() ;
}


// search user by params
searchUser.addEventListener("click",function (ev) {
    hideUserTable();
    hidePagination();
    showSearchPagination();
    var searchForm = document.querySelector('#search');
    searchForm.style.display = 'block';
});

var startValue = 0;
var totalValue = countOfUserPerPage.value;

searchUsrButton.addEventListener("click",function (evt) {
    evt.preventDefault();
    var name = document.forms.search.name.value.trim();
    var surname = document.forms.search.surname.value.trim();
    var lastName = document.forms.search.lastName.value.trim();
    var date = document.forms.search.date.value;
    var gender = document.forms.search.gender.value;
    var citizenship = document.forms.search.citizenship.value.trim();
    var maritalStatus = document.forms.search.maritalStatus.value.trim();
    var country = document.forms.search.country.value.trim();
    var city = document.forms.search.city.value.trim();
    var street = document.forms.search.street.value.trim();
    var house = document.forms.search.house.value.trim();
    var numOfFlat = document.forms.search.numOfFlat.value.trim();
    var index = document.forms.search.zipCode.value.trim();


    var options = {
        method : 'GET',
        headers : {'Content-Type' : 'application/json;charset=UTF-8;'}
    };

    if  (isValidSearchForm(name,surname,lastName, citizenship,country, city,index)){
        var userTable = document.querySelector('#dataTable');
        userTable.innerHTML = '';

        history.pushState(null,null,'client?command=findUser&name=' + name + '&surname=' + surname + '&lastName='+ lastName + '&date=' + date
            + '&gender=' + gender + '&citizenship=' + citizenship + '&maritalStatus=' + maritalStatus + '&country=' + country
            + '&city=' + city + '&street=' + street + '&house=' + house + '&numOfFlat=' + numOfFlat + '&index=' + index);

        loadData('client?command=findUser&name=' + name + '&surname=' + surname + '&lastName='+ lastName + '&date=' + date
            + '&gender=' + gender + '&citizenship=' + citizenship + '&maritalStatus=' + maritalStatus + '&country=' + country
            + '&city=' + city + '&street=' + street + '&house=' + house  + '&numOfFlat=' + numOfFlat + '&index=' + index
            + '&start=' + startValue  + '&total=' +  totalValue,options)
            .then(res => render(res,'users','dataTable')).then(function () {
            return fetch('client?command=countOfFoundUsers&name=' + name + '&surname=' + surname + '&lastName='+ lastName + '&date=' + date
                + '&gender=' + gender + '&citizenship=' + citizenship + '&maritalStatus=' + maritalStatus + '&country=' + country
                + '&city=' + city + '&street=' + street + '&house=' + house  + '&numOfFlat=' + numOfFlat + '&index=' + index);
        }).then(res => res.json()).then(function (res) {
            paginationForSearch(res,name,surname,lastName,date,gender,citizenship,maritalStatus,country,city,street,house,numOfFlat,index);
        }).catch(err => console.log(err));
        showUserTable();
    }else {
        alert("Name , surname , patronymic shouldn't contain digits and index shouldn't contain letters ");
    }

});

back.addEventListener("click",function () {
    showPagination();
    hideSearchPagination();
    history.replaceState(null,null,'client');
    hideSearchForm();
    refreshUserTable(0,5);

});

function isValidSearchForm(name, surname, lastName,citizenship,country,city , index) {
    return name.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && surname.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && lastName.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && citizenship.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && country.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && city.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && index.toString().match('(^$)|(^\\d{0,10}$)');
}


function hideSearchForm() {
    var searchForm = document.querySelector('#search');
    searchForm.style.display = 'none';
    resetUserSearchForm();
}

function resetUserAddForm() {
    document.querySelector('.usrAdd').reset();
}

function resetUserEditFileElement() {
    document.querySelector('#img').value = "";
}

function resetUserSearchForm() {
    document.querySelector('#userSearch').reset();
}

function hidePagination() {
    document.querySelector('.buttons').style.display = 'none';
    document.querySelector('#recordsPerPage').style.display = 'none';
}

function showPagination() {
    document.querySelector('.buttons').style.display = 'inline-block';
    document.querySelector('#recordsPerPage').style.display = 'inline-block';
}

function hideSearchPagination() {
    document.querySelector('#recordsPerPageS').style.display = 'none';
}

function showSearchPagination() {
    document.querySelector('.buttons').style.display = 'inline-block';
    document.querySelector('#recordsPerPageS').style.display = 'inline-block';
}


function isValidUserForm(name, surname, lastName,citizenship,country,city,email , index) {
    var re = /(^$)|^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return name.toString().match('^[A-Za-zА-Яа-яёЁ]+$') && surname.toString().match('^[A-Za-zА-Яа-яёЁ]+$')
        && lastName.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && citizenship.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && country.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && city.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && email.toString().match(re)
        && index.toString().match('(^$)|(^\\d{0,10}$)');
}

function validateDate(date) {
   return moment(date).isAfter(moment());
}