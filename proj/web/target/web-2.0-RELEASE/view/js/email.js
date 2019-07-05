'use strict';

function isEmailExists(array){
    var isEmailsEmpty = true;
    array.forEach(function (value,index) {
        if(array[index].length === 0){
            return isEmailsEmpty = false;
        }
    });
    return isEmailsEmpty;
}



var emails = [];
var receivers;
var recieversName = [];
var idTemplate;
var emailForm = document.querySelector('#email');
sendEmailForm.addEventListener("click",function () {
    emails = [];
    var checkboxes = document.getElementsByName("users");
    var allEmails = document.querySelectorAll('.email');
    var allUsers = document.querySelectorAll('.userName');
    var allDate = document.querySelectorAll('.birthDate');
    var allWorkplace = document.querySelectorAll('.workplace');
    var allMaritalStatus = document.querySelectorAll('.maritalStatus');
    var checked = [];
    var values = [];

    for (var i = 0; i < checkboxes.length;i++){
        if(checkboxes[i].checked){
            emails.push(allEmails[i].innerHTML);
            recieversName.push(allUsers[i].innerHTML);
            date.push(allDate[i].innerHTML);
            workplace.push(allWorkplace[i].innerHTML);
            maritalStatus.push(allMaritalStatus[i].innerHTML);
            checked.push(checkboxes[i]);
        }
    }

    checked.forEach(function (value,index) {
        values.push(checked[index].value);
    });

    if(values.length !== 0 && isEmailExists(emails) ){
        hideSearchForm();
        refreshTemplates();
        showTemplates();
        receivers = document.forms.emailForm.receivers.value = emails;

        emailForm.style.display = 'block';
        hideAllTables();
        history.pushState(null,null,'client/sendEmail');
    }else if (!isEmailExists(emails)){
        alert("You cant send because of email(s) don't exist");
        recieversName = [];
        date = [] ;
        maritalStatus = [];
        workplace = [];
    }else{
        alert("You should choose at least one receiver to send an email");
    }

});
//send email
var date = [] ;
var maritalStatus = [];
var workplace = [];

email.addEventListener("click",function () {
    var topic = document.forms.emailForm.topic.value;
    var message = document.forms.emailForm.message.value;


    var parameter = '';

    if (idTemplate === '1'){
        parameter =  maritalStatus ;
    } else  if (idTemplate === '2'){
        parameter = date ;
    } else if (idTemplate === '3'){
        parameter = workplace ;
    }


    if (message.length > 0) {
        var options = {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: '&message=' + message + '&topic=' + topic + '&receiverEmails=' + receivers + '&receiversName=' + recieversName
            + '&idTemplate=' + idTemplate + '&parameter=' + parameter
        };

        fetch('',options).then(function (value) {
            history.back();
            showUserTable();
            refreshUserTable(0, 5);

            receivers = '';
            recieversName = [];
            idTemplate = '';

            date = [] ;
            maritalStatus = [];
            workplace = [];

            resetEmailForm();
            emailForm.style.display = 'none';
        }).catch(err => console.log(err));
    }else{
        alert("You can't send empty email ");
    }
});

var sendBack = document.querySelector("#searchBack");
sendBack.addEventListener("click",function (evt) {
    recieversName = [];
    date = [] ;
    maritalStatus = [];
    workplace = [];
    history.back();
    refreshUserTable(0,5);
    showUserTable();
    emailForm.style.display = 'none';

});

function showTemplates() {
    loadData('client?command=templates').then(res => render(res,'templates','messageTemplate')).catch(err => console.log(err));;
}

function refreshTemplates() {
    var templateField = document.querySelector('#messageTemplate');
    templateField.innerHTML = '';

}

var selectBtn = document.querySelector('#messageTemplate');
var textField = document.querySelector('#message');
selectBtn.addEventListener("change",function () {

    for (var i = 0; i < selectBtn.length ; i++) {
        if (selectBtn.children[i].selected ){
            if (selectBtn.children[i].value !== "0") {
                idTemplate = selectBtn.children[i].value;
                textField.value = '';
                textField.disabled = true;
                textField.value = selectBtn.children[i].innerHTML;
            }
            else  {
                textField.disabled = false;
                textField.innerHTML = '';
            }
        }
    }
});


function resetEmailForm() {
    //reset form
    document.getElementById('emailForm').reset();
    textField.disabled = false;
    textField.innerHTML = '';
}