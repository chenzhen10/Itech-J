'use strict';
//variables
var backBtn = document.querySelector('.backUser');
var userApplyButton = document.querySelector('#userEdit');
var table = document.querySelector('#dataTable');
var counter;
var userId;
var inputAdd = document.querySelectorAll('.img')[0];

var input = document.querySelectorAll('.img')[1];
var result;
var bac2Btn = document.querySelector('.backEditUser');


addUser.addEventListener("click",function () {

    var img = document.querySelectorAll('.currentImage')[0];
    img.src = 'client?command=showUserPhoto' +  '&refresh=' + Math.random() ;

    hideSearchForm();
    showAddUserForm();
    hideAllTables();
});


//add user
document.forms.add.onsubmit = function (e) {
    e.preventDefault();


    var name = document.forms.add.name.value;
    var surname = document.forms.add.surname.value;
    var lastName = document.forms.add.lastName.value;
    var date = dateCreator();
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
    var photoPath = document.querySelectorAll('.img')[0];


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


    var options = {
        method : 'POST',
        body : data
    };


    if (isValidUserForm(name,surname,lastName,citizenship,country,city,email,index) &&  result && checkDate(date) && !validateDate(new Date(date))) {

        fetch('client/add/user',options).then(function (value) {
            if (value.status === 500) {
                return value.json();
            }

            hideAddUserForm();
            resetUserAddForm();
            refreshUserTable(0,5);
            showUserTable();
            resetUseAddFileElement();

        }).then(function(res) {
            showError(res);
        }).catch(err => console.log(err));
    }else if (!result) {
        alert('You can upload only image');
    }else if(!checkDate(date)) {
        alert('You should correct date');
    }else if (validateDate(new Date(date))) {
        alert("Your birthday can't be in future please choose correct date");
    }else {
        alert('Name, surname, lastName,citizenship,maritalStatus,country,city should contain only letters and index should contain only digits ');
    }


};

backBtn.addEventListener("click",function () {
    resetUserAddForm();
    showUserTable();
    hideAddUserForm();
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
        fetch('client/delete/user?id=' + values,options).then(function (value) {
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

input.addEventListener("change",function (ev) {
     toDataURLForUserEdit(input);
});

inputAdd.addEventListener("change",function (ev) {
    toDataURL(inputAdd);
});
function editUser() {
    hideSearchForm();
    showAllTablesExceptUser();
    refreshAllTableByUserId(userId);
    showUserImage();

    showEditUserForm();

    var fullDate = result[2].innerHTML.trim().split('-');

    document.forms.edit.name.value = result[1].children[0].children[1].innerHTML.trim();
    document.forms.edit.surname.value = result[1].children[0].children[0].innerHTML.trim();
    document.forms.edit.lastName.value = result[1].children[0].children[2].innerHTML.trim();

    if (fullDate.length > 1) {
        document.forms.edit.year.value = fullDate[0];
        document.forms.edit.month.value = fullDate[1].replace('0', '');
        document.forms.edit.day.value = fullDate[2].replace('0', '');
    }
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

bac2Btn.addEventListener("click",function (evt) {
    showUserTable();
    refreshUserTable(0,5);
    hideAllTablesExceptUser();
    hideEditUserForm();
});

userApplyButton.addEventListener("click",function (evt) {

    var name = document.forms.edit.name.value;
    var surname =  document.forms.edit.surname.value;
    var lastName =  document.forms.edit.lastName.value;
    var date = dateCreatorForUpdateUser();
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
    var photoPath = document.querySelectorAll('.img')[1];
    var ext = document.querySelectorAll('.photoPath')[counter].innerHTML.split('.')[1];

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
    data.append('extension',ext);

    var options = {
        method : 'POST',
        body : data
    };

    if (isValidUserForm(name,surname,lastName,citizenship,country,city,email, index) && result && checkDate(date)  && !validateDate(new Date(date))) {
        hideEditUserForm();

        fetch('client/edit/user',options).then(function (value) {
            if (value.status === 500) {
                return value.json();
            }
        }).then(function(res) {
                showError(res);
        }).then(function (value) {
            if(typeof pickedPhone !== 'undefined' && pickedPhone !== ''){
                indexOfUpdatedPhone.forEach(function (value,index) {
                    savePhoneUpdate(value);
                })
                sendUpdatedPhone();
            }
        }).then(function (value) {
            if(typeof pickedAttachment !== 'undefined' && pickedAttachment !== ''){
                indexOfUpdatedAttachment.forEach(function (value,index) {
                    saveAttachmentUpdate(value);
                })
                sendUpdatedAttachment();
            }
        }).then(function (value) {

            refreshUserTable(0,5);
            hideAllTables();
            showUserTable();

            resetUserEditFileElement();

            refreshAttachmentFields();
            refreshPhoneFields();
            //refresh arrays of edit indixes
            indexOfUpdatedPhone = [];
            indexOfUpdatedAttachment = [];
            phoneIdAcclumulator = [];
            attachmentIdAccumulator = [];
        }).catch(err => console.log(err));
    }else if (!result) {
        alert('You can upload only image');
    }else if(!checkDate(date)) {
        alert('You should input correct date');
    }else if (validateDate(new Date(date))) {
        alert("Your birthday can't be in future please choose correct date");
    }else {
        alert('Name, surname, lastName,citizenship,maritalStatus,country,city should contain only letters and index should contain only digits');
    }

});

//set extension
function showUserImage() {

    var path  =  document.getElementsByClassName('photoPath');

    var fileL = path[counter].innerHTML.split("\\").length;
    var extension = path[counter].innerHTML.split('\\')[fileL - 1].split('.')[1];


    var img = document.querySelectorAll('.currentImage')[1];
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



searchUsrButton.addEventListener("click",function (evt) {
    evt.preventDefault();

    var name = document.forms.search.name.value.trim();
    var surname = document.forms.search.surname.value.trim();
    var lastName = document.forms.search.lastName.value.trim();
    var day = document.forms.search.day.value;
    var month = document.forms.search.month.value;
    var year = document.forms.search.year.value;

    var dayF = '' ;
    var monthF = '' ;
    var yearF = '' ;

    if (!isNumeric(year)) {
        year = '';
    }else{
        yearF += year;
    }
    if (!isNumeric(month)) {
        month = '';
    }else{
        monthF += month;
    }
    if (!isNumeric(day)) {
        day = '';
    }else{
        dayF += day;
    }


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

    if  (isValidSearchForm(name,surname,lastName, citizenship,country, city,index) ){
        var userTable = document.querySelector('#dataTable');
        userTable.innerHTML = '';

           fetch('client?command=countOfFoundUsers&name=' + name + '&surname=' + surname + '&lastName='+ lastName + '&year=' + yearF + '&month=' + monthF + '&day=' + dayF
                + '&gender=' + gender + '&citizenship=' + citizenship + '&maritalStatus=' + maritalStatus + '&country=' + country
                + '&city=' + city + '&street=' + street + '&house=' + house  + '&numOfFlat=' + numOfFlat + '&index=' + index,options)
            .then(res => res.json()).then(function (res) {
            paginationForSearch(res,name,surname,lastName,yearF,monthF,dayF,gender,citizenship,maritalStatus,country,city,street,house,numOfFlat,index);
        }).catch(err => console.log(err));
        showUserTable();
    }else {
        alert("Name , surname , patronymic shouldn't contain digits and index shouldn't contain letters ");
    }

});

back.addEventListener("click",function () {
    showPagination();
    hideSearchPagination();
    showUserTable();
    hideSearchForm();
    refreshUserTable(0,5);
});


function hideSearchForm() {
    var searchForm = document.querySelector('#search');
    searchForm.style.display = 'none';
    resetUserSearchForm();
}

function resetUserAddForm() {
    document.querySelector('.usrAdd').reset();
}

function resetUserEditFileElement() {
    document.querySelector('.img').value = "";
}

function resetUseAddFileElement() {
    document.querySelectorAll('.currentImage')[0].value = "";
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

function hideEditUserForm(){
    var userEditForm = document.querySelector('#editUsr');
    userEditForm.style.display = 'none';

}
function showEditUserForm() {
    var userEditForm = document.querySelector('#editUsr');
    userEditForm.style.display = 'block';
}

function hideAddUserForm() {
    var showAddUser = document.querySelector('.addUsr');
    showAddUser.style.display = "none";
}

function showAddUserForm() {
    var showAddUser = document.querySelector('.addUsr');
    showAddUser.style.display = "block";
}