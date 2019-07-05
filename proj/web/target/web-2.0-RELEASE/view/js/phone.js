'use strict';

var indexOfUpdatedPhone = [];

addPhone.addEventListener("click",function (evt) {
    history.back();
    history.pushState(null,null,'client/add/phone');
    hideAllTablesExceptUser();
    var showAddPhoneForm = document.querySelector('#addPhon');
    showAddPhoneForm.style.display = 'block';

    var userEditForm = document.querySelector('#editUsr');
    userEditForm.style.display = 'none';


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


    if(isValidNumber(countryC,operatorC,numberP) ){

        var options = {
            method : 'POST',
            headers :  {'Content-Type' : 'application/x-www-form-urlencoded'},
            body :'countryCode=' + countryCode + '&operatorCode=' + operatorCode  + '&number='
            + number + '&type=' + type + '&commentary=' + commentary + '&idclient=' + userId
        };

        fetch('',options).then(function (value) {
            history.back();
            history.pushState(null,null,'client/edit/user');
            showAllTablesExceptUser();
            refreshAllTableByUserId(userId);
            var showAddPhoneForm = document.querySelector('#addPhon');
            showAddPhoneForm.style.display = 'none';

            var userEditForm = document.querySelector('#editUsr');
            userEditForm.style.display = 'block';

            resetPhoneAddForm();

            if (value.status === 500) {
                return value.json();
            }
        }).then(function(res) {
            showError(res);
        }).catch(err => console.log(err));

    }else {
        alert("Your number , country code and operator code shouldn't be empty");
    }
};

var backAddPhone = document.querySelector('.backAddPhone');
backAddPhone.addEventListener("click",function (evt) {
    history.back();
    history.pushState(null,null,'client/edit/user');
    showAllTablesExceptUser();
    userEditForm.style.display = 'block';
    var showAddPhoneForm = document.querySelector('#addPhon');
    showAddPhoneForm.style.display = 'none';
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
        history.back();
        var options = {
            method : 'DELETE',
            headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
        };

        var req = new Request('client/delete/phone?id=' + values,options);
        fetch(req).then(function (value) {
            refreshPhoneTable(userId);
            history.forward();

            if (value.status === 500) {
                return value.json();
            }
        }).then(function(res) {
            showError(res);
        }).catch(err => console.log(err));
    }else{
        alert('You should choose only one phone');
    }
});


//edit phone

var countryCode = document.querySelector('#countryCode');
var operatorCode = document.querySelector('#operatorCode');
var number = document.querySelector('#number');

function isValidNumber(countryCode,operatorCode,number) {
    return  0 < countryCode.value.length && countryCode.value.length < 4
        && 0 < operatorCode.value.length && operatorCode.value.length < 5
        && 0 < number.value.length && number.value.length < 8;
}



var cancelButton = document.querySelector('.cancel');
var confirmButton = document.querySelector('.confirm');



var pickedPhone ;
var phoneId ;
var phoneIdAcclumulator = [];
var modal = document.querySelector('.modalPhone');

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
        history.back();
        history.pushState(null,null,'client/edit/phone');


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
    history.back();
    history.pushState(null,null,'client/edit/user');
    closeModal();
});

confirmButton.addEventListener("click",function (evt) {
    if(isValidNumber(countryCode,operatorCode,number)) {
        document.getElementsByClassName('countryCode')[pickedPhone].innerHTML = document.forms.update_phone.countryCode.value;
        document.getElementsByClassName('operatorCode')[pickedPhone].innerHTML = document.forms.update_phone.operatorCode.value;
        document.getElementsByClassName('number')[pickedPhone].innerHTML = document.forms.update_phone.number.value;
        document.getElementsByClassName('phoneType')[pickedPhone].innerHTML = document.forms.update_phone.type.value;
        document.getElementsByClassName('phoneCommentary')[pickedPhone].innerHTML = document.forms.update_phone.commentary.value;
        history.back();
        history.pushState(null,null,'client/edit/user');
        closeModal();
    }else{
        alert("Your number shouldn't be empty");
    }
});

var countryCodeUpdatePhone = [];
var operatorCodeUpdatedPhone = [] ;
var numberUpdatedPhone = [] ;
var type = [] ;
var commentary = [];

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
    var request = new Request('',options);
    fetch(request).then(function (value) {
        if (value.status === 500) {
            return value.json();
        }
    }).then(function(res) {
        showError(res);
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

