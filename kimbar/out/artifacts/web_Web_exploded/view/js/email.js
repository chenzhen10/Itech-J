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
var emailForm = document.querySelector('#email');
sendEmailForm.addEventListener("click",function () {
    emails = [];
    var checkboxes = document.getElementsByName("users");
    var allEmails = document.querySelectorAll('.email');
    var checked = [];
    var values = [];

    for (var i = 0; i < checkboxes.length;i++){
        if(checkboxes[i].checked){
            emails.push(allEmails[i].innerHTML);
            checked.push(checkboxes[i]);
        }
    }

    checked.forEach(function (value,index) {
        values.push(checked[index].value);
    });

    if(values.length !== 0 && isEmailExists(emails) ){

        receivers = document.forms.emailForm.receivers.value = emails;

        emailForm.style.display = 'block';
        hideAllTables();
        history.pushState(null,null,'client/sendEmail/user');
    }else if (!isEmailExists(emails)){
        alert("You cant send because of email(s) don't exist");
    }else{
        alert("You should choose at least one receiver to send an email");
    }

});
//send email
email.addEventListener("click",function () {
    var topic = document.forms.emailForm.topic.value;
    var message = document.forms.emailForm.message.value;

    var options = {
        method : 'POST',
        headers : {'Content-Type' : 'application/x-www-form-urlencoded'},
        body : '&message=' + message + '&topic=' + topic + '&receivers=' + receivers
    };
    var currentUrl = window.location.href.split('/');
    var requestUrl = currentUrl[4] ;

    var request = new Request(requestUrl,options);
    fetch(request).then(function (value) {
        history.back();
        showUserTable();
        refreshUserTable(0,5);
        emailForm.style.display = 'none';
    });
});

var sendBack = document.querySelector("#searchBack");
sendBack.addEventListener("click",function (evt) {
    history.back();
    refreshUserTable(0,5);
    showUserTable();
    emailForm.style.display = 'none';

});