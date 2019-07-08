'use strict';

var indexOfUpdatedPhone = [];
var backAddPhone = document.querySelector('.backAddPhone');

var countryCode = document.querySelector('#countryCode');
var operatorCode = document.querySelector('#operatorCode');
var number = document.querySelector('#number');

var cancelButton = document.querySelector('.cancel');
var confirmButton = document.querySelector('.confirm');
var pickedPhone ;
var phoneId ;
var phoneIdAcclumulator = [];
var modal = document.querySelector('.modalPhone');

var countryCodeUpdatePhone = [];
var operatorCodeUpdatedPhone = [] ;
var numberUpdatedPhone = [] ;
var type = [] ;
var commentary = [];

addPhone.addEventListener("click",function (evt) {
    hideAllTablesExceptUser();
    showAddPhoneForm();
    hideEditUserForm();
});


document.forms.addPhone.onsubmit = function(e){
    e.preventDefault();

    var  countryCode = document.forms.addPhone.countryCode.value;
    var  operatorCode = document.forms.addPhone.operatorCode.value;
    var  number = document.forms.addPhone.number.value;
    var  type = document.forms.addPhone.type.value;
    var  commentary = document.forms.addPhone.commentary.value;


    var countryC = document.getElementsByName('countryCode')[0];
    var operatorC = document.getElementsByName('operatorCode')[0];
    var numberP = document.getElementsByName('number')[0];


    if(isValidNumber(countryC,operatorC,numberP) && isDigitsOnly(countryC,operatorC,numberP) && !isDuplicateForAddPhone(countryC,operatorC,numberP)){

        var options = {
            method : 'POST',
            headers :  {'Content-Type' : 'application/x-www-form-urlencoded'},
            body :'countryCode=' + countryCode + '&operatorCode=' + operatorCode  + '&number='
            + number + '&type=' + type + '&commentary=' + commentary + '&idclient=' + userId
        };

        fetch('client/add/phone',options).then(function (value) {
            showAllTablesExceptUser();
            refreshAllTableByUserId(userId);
            hideAddPhoneForm();

            showEditUserForm();

            resetPhoneAddForm();

            if (value.status === 500) {
                return value.json();
            }
        }).then(function(res) {
            showError(res);
        }).catch(err => console.log(err));

    }else if (isDuplicate(countryC,operatorC,numberP)) {
        alert("Your number , can't duplicate");
    }else if (!isDigitsOnly(countryC,operatorC,numberP)) {
        alert("Your number , should contain only digits");
    }else {
        alert("Your number , country code and operator code shouldn't be empty or be too long");
    }
};


backAddPhone.addEventListener("click",function (evt) {
    showAllTablesExceptUser();
    showEditUserForm();
    hideAddPhoneForm();
});

//delete phone
deletePhone.addEventListener("click",function (ev) {

    var checkboxes = document.getElementsByName("phones");
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


    if(values.length !== 0 ){

        var options = {
            method : 'DELETE',
            headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
        };

        fetch('client/delete/phone?id=' + values,options).then(function (value) {
            refreshPhoneTable(userId);
            if (value.status === 500) {
                return value.json();
            }
        }).then(function(res) {
            showError(res);
        }).catch(err => console.log(err));
    }else{
        alert('You should choose phone to delete');
    }
});

//edit phone

function closeModal(){
    modal.style.display = 'none';
    backdrop.style.display = 'none';
}


editPhone.addEventListener("click",function (ev) {
    var checkboxes = document.getElementsByName("phones");
    var checked = [];
    var values = [];

    for (var i = 0; i < checkboxes.length;i++){
        if(checkboxes[i].checked){
            indexOfUpdatedPhone.push(i);
            pickedPhone = i;
            checked.push(checkboxes[i]);
        }
    }
    checked.forEach(function (value,index) {
        values.push(checked[index].value);
    });

    if(values.length === 1 ){

        phoneId = values;
        phoneIdAcclumulator.push(phoneId);

        document.forms.update_phone.countryCode.value = document.getElementsByClassName('countryCode')[pickedPhone].innerHTML;
        document.forms.update_phone.operatorCode.value = document.getElementsByClassName('operatorCode')[pickedPhone].innerHTML;
        document.forms.update_phone.number.value = document.getElementsByClassName('number')[pickedPhone].innerHTML;
        document.forms.update_phone.type.value = document.getElementsByClassName('phoneType')[pickedPhone].innerHTML;
        document.forms.update_phone.commentary.value = document.getElementsByClassName('phoneCommentary')[pickedPhone].innerHTML;

        modal.style.display = 'block';
        backdrop.style.display = 'block';
    }else{
        alert("You should choose only one phone to edit");
    }
});

cancelButton.addEventListener("click",function (evt) {
    closeModal();
});

confirmButton.addEventListener("click",function (evt) {
    if(isValidNumber(countryCode,operatorCode,number)  && isDigitsOnly(countryCode,operatorCode,number) &&  !isDuplicate(countryCode,operatorCode,number)) {
        document.getElementsByClassName('countryCode')[pickedPhone].innerHTML = document.forms.update_phone.countryCode.value;
        document.getElementsByClassName('operatorCode')[pickedPhone].innerHTML = document.forms.update_phone.operatorCode.value;
        document.getElementsByClassName('number')[pickedPhone].innerHTML = document.forms.update_phone.number.value;
        document.getElementsByClassName('phoneType')[pickedPhone].innerHTML = document.forms.update_phone.type.value;
        document.getElementsByClassName('phoneCommentary')[pickedPhone].innerHTML = document.forms.update_phone.commentary.value;
        closeModal();
    }else if (isDuplicate(countryCode,operatorCode,number)) {
        alert("Your number , can't duplicate");
    }else if (!isDigitsOnly(countryCode,operatorCode,number)) {
        alert("Your number , should contain only digits");
    }else{
        alert("Your number shouldn't be empty or be too long");
    }
});



function savePhoneUpdate(index) {
     countryCodeUpdatePhone.push(document.getElementsByClassName('countryCode')[index].innerHTML) ;
     operatorCodeUpdatedPhone.push(document.getElementsByClassName('operatorCode')[index].innerHTML) ;
     numberUpdatedPhone.push(document.getElementsByClassName('number')[index].innerHTML );
     type.push(document.getElementsByClassName('phoneType')[index].innerHTML) ;
     commentary.push(document.getElementsByClassName('phoneCommentary')[index].innerHTML) ;
}
function sendUpdatedPhone() {
    var result = [];

    for (var i = 0; i < phoneIdAcclumulator.length ; i++) {

        var phone = {
            countryCode :countryCodeUpdatePhone[i],
            operatorCode : operatorCodeUpdatedPhone[i],
            number : numberUpdatedPhone[i],
            commentary : commentary[i],
            type : type[i],
            id : phoneIdAcclumulator[i].toString()
        };
        result.push(phone);
    }


    var options = {
        method : 'PUT',
        mode: 'cors',
        headers : {'Content-Type' : 'application/json; charset=utf-8;'},
        body : JSON.stringify(result)
    };
    fetch('client/edit/phone',options).then(function (value) {
        if (value.status === 500) {
            return value.json();
        }
    }).then(function(res) {
        showError(res);
        pickedPhone = '';
    }).catch(err => console.log(err));
}

function refreshPhoneFields() {
    countryCodeUpdatePhone = [];
    operatorCodeUpdatedPhone = [] ;
    numberUpdatedPhone = [] ;
    type = [] ;
    commentary = [];
}

function resetPhoneAddForm() {
    document.querySelector('#phoneAdd').reset();
}


function hideAddPhoneForm() {
    var showAddPhoneForm = document.querySelector('#addPhon');
    showAddPhoneForm.style.display = 'none';
}
function showAddPhoneForm() {
    var showAddPhoneForm = document.querySelector('#addPhon');
    showAddPhoneForm.style.display = 'block';
}