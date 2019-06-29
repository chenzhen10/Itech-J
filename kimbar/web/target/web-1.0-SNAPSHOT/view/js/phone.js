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


    function isValidNumber() {
        return countryC.value.length < 5 && operatorC.value.length < 5 && numberP.value.length < 9;
    }

    if(isValidNumber() ){

        var options = {
            method : 'POST',
            headers :  {'Content-Type' : 'application/x-www-form-urlencoded'},
            body :'countryCode=' + countryCode + '&operatorCode=' + operatorCode  + '&number='
            + number + '&type=' + type + '&commentary=' + commentary + '&idclient=' + userId
        };


        var currentUrl = window.location.href.split('/');
        var requestUrl = currentUrl[4] + currentUrl[5].substring(0,1).toUpperCase() + currentUrl[5].substring(1);

        var request = new Request(requestUrl,options);
        fetch(request).then(function (value) {
            history.back();
            showAllTablesExceptUser();
            refreshAllTableByUserId(userId);
            var showAddPhoneForm = document.querySelector('#addPhon');
            showAddPhoneForm.style.display = 'none';

            var userEditForm = document.querySelector('#editUsr');
            userEditForm.style.display = 'block';
        });

    }else if(!isValidNumber()){
        alert('Your country code should contain less then 4 digits as well as operator code and number should be less then 9 digits');
    }else{
        alert('You should choose only one user who will own this phone number');
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
    history.back();
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

        var req = new Request('client/delete/phone?id=' + values,options);
        fetch(req).then(function () {
            refreshPhoneTable(userId);
            history.forward();
        });
    }else{
        alert('You should choose only one phone');
    }
});


//edit phone

var countryCode = document.querySelector('#countryCode');
var operatorCode = document.querySelector('#operatorCode');
var number = document.querySelector('#number');

function isValidNumber() {
    return countryCode.value.length < 5 && operatorCode.value.length < 5 && number.value.length < 9;
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
    if(isValidNumber()) {
        document.getElementsByClassName('countryCode')[pickedPhone].innerHTML = document.forms.update_phone.countryCode.value;
        document.getElementsByClassName('operatorCode')[pickedPhone].innerHTML = document.forms.update_phone.operatorCode.value;
        document.getElementsByClassName('number')[pickedPhone].innerHTML = document.forms.update_phone.number.value;
        document.getElementsByClassName('phoneType')[pickedPhone].innerHTML = document.forms.update_phone.type.value;
        document.getElementsByClassName('phoneCommentary')[pickedPhone].innerHTML = document.forms.update_phone.commentary.value;
        closeModal();
        history.back();
        history.pushState(null,null,'client/edit/user');
    }else{
        alert('Your country code should contain less then 4 digits as well as operator code and number should be less then 9 digits');
    }
});

var countryCodeUpdatePhone = [];
var operatorCodeUpdatedPhone = [] ;
var numberUpdatedPhone = [] ;
var type = [] ;
var commentary = [];

function saveUpdatedPhone(index) {
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

    var request = new Request('client/edit/phone',options );
    fetch(request);
}

function refreshPhoneFields() {
    countryCodeUpdatePhone = [];
    operatorCodeUpdatedPhone = [] ;
    numberUpdatedPhone = [] ;
    type = [] ;
    commentary = [];
}