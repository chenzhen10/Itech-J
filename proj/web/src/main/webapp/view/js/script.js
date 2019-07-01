'use strict';

var addUser = document.querySelector('#addUser');
var deleteUser = document.getElementById("delete_user");

var attach = document.getElementById("attachment");
var downloadAttachment = document.getElementById("download");
var deleteAttachment = document.getElementById("deleteAttachment");
var deletePhone = document.getElementById("deletePhone");
var editPhone = document.getElementById("editPhone");
var searchUser = document.getElementById("searchUser");
var countOfUserPerPage = document.querySelector("#recordsPerPage");
var addAttachment = document.querySelector('#addAttachment');
var addPhone = document.querySelector('#addPhone');
var email = document.querySelector('#send');
var sendEmailForm = document.querySelector('#sendEmail');
var searchUsrButton = document.querySelector('#searchUsr');

var back = document.querySelector('#back');

function loadData(url) {
    return fetch(url).then(res => res.json()).catch(err => console.log(err));
}
function render(data,templ,table) {
    var template = document.getElementById(templ).innerHTML;
    var renderContext = document.getElementById(table);
    data.forEach(function (contact) {
        renderContext.innerHTML += Mustache.render(template, contact);
    });
}

var backdrop = document.querySelector('.backdrop');

function  refreshUserTable(start,total) {
    startLinks();
    var userTable = document.querySelector('#dataTable');
    userTable.innerHTML = '';
    loadData('client?command=pagination&page=' + start + '&total='+total).then(res => render(res,'users','dataTable')
    ).then(function () {
        fetchCountOFUser();
    }).catch(err => console.log(err));
}
function refreshPhoneTable(userId) {
    var phoneTable = document.querySelector('#phoneTable');
    phoneTable.innerHTML = '';
    loadData('client?command=allUserPhones&id=' + userId).then(res => render(res,'phones','phoneTable'))
    .catch(err => console.log(err));
}
function refreshAttachmentTable(userId) {
    var attachmentTable = document.querySelector('#attachmentTable');
    attachmentTable.innerHTML = '';
    loadData('client?command=allUserAttachments&id=' + userId).then(res => render(res,'attachments','attachmentTable'))
    .catch(err => console.log(err));
}


function refreshAllTableByUserId(userID) {
    var attachmentTable = document.querySelector('#attachmentTable');
    var phoneTable = document.querySelector('#phoneTable');
    attachmentTable.innerHTML = '';
    phoneTable.innerHTML = '';
    loadData('client?command=allUserAttachments&id=' + userID).then(res => render(res,'attachments','attachmentTable'))
    .catch(err => console.log(err));
    loadData('client?command=allUserPhones&id=' + userID).then(res => render(res,'phones','phoneTable'))
    .catch(err => console.log(err));
}
function hideAllTablesExceptUser() {
    var userTable = document.querySelector('#dataTable');
    var attachment = document.querySelector('.attachment');
    var phone = document.querySelector('.phone');
    attachment.style.display = 'none';
    phone.style.display = 'none';
    userTable.style.display = '';
}
function hideAllTables(){
    var attachment = document.querySelector('.attachment');
    var phone = document.querySelector('.phone');
    var user = document.querySelector('.userInfo');
    attachment.style.display = 'none';
    phone.style.display = 'none';
    user.style.display = 'none';
}
function showUserTable() {
    var user = document.querySelector('.userInfo');
    user.style.display = 'block';
}


function showAllTablesExceptUser(){
    var attachment = document.querySelector('.attachment');
    var phone = document.querySelector('.phone');
    var user = document.querySelector('.userInfo');
    attachment.style.display = 'block';
    phone.style.display = 'block';
    user.style.display = 'none';
}


history.replaceState(null,null,"client");
refreshUserTable(0,5);
hideAllTablesExceptUser();



//user count
var countOfUsers = 0;
function fetchCountOFUser(){
   fetch('client?command=countOfUsers').then(res => res.json()).then(res => extractData(res))
    .catch(err => console.log(err));

    function extractData(data) {
        countOfUsers = data;
    }
}


//pagination
countOfUserPerPage.addEventListener("change",function () {
  linkRender();
});

function linkRender() {
    var countOfRecordsPerPage = countOfUserPerPage.value;
    var userDiv = document.querySelector('.buttons');
    var userTable = document.querySelector('#dataTable');
    userTable.innerHTML = '';

    var countOfRecords = countOfUsers;

    var currentUsers = 0;

    var currentPage = 1;

    var pages = Math.ceil( countOfRecords / countOfRecordsPerPage );

    userDiv.innerHTML = '';



    loadData('client?command=pagination' + '&page=' + currentUsers  +
        '&total=' + countOfRecordsPerPage).then(res => render(res,'users','dataTable')).catch(err => console.log(err));

    if (countOfRecords > countOfRecordsPerPage) {
        for (var i = 1; i <= pages; i++) {
            var a = document.createElement('button');
            a.setAttribute('class', 'userLink' );

            a.addEventListener("click", function (ev) {

                userTable.innerHTML = '';

                //prev
                if (currentPage > this.innerHTML) {
                    if (currentPage <= pages) {
                        for (var j = currentPage; j > this.innerHTML; j--) {
                            currentUsers -= Number(countOfRecordsPerPage);
                        }
                        currentPage = this.innerHTML;
                    }
                    //next
                } else if (currentPage < this.innerHTML) {
                    if (currentPage <= pages) {
                        for (var j = currentPage; j < this.innerHTML; j++) {
                            currentUsers += Number(countOfRecordsPerPage);
                        }
                        currentPage = this.innerHTML;
                    }
                }
                history.pushState(null, null, 'client?command=pagination' + '&page=' + this.innerHTML
                    + '&total=' + countOfRecordsPerPage);

                loadData('client?command=pagination'  + '&page=' + currentUsers +
                    '&total=' + countOfRecordsPerPage).then(res => render(res, 'users', 'dataTable'))
                    .catch(err => console.log(err));
            });
            a.innerHTML = i;
            a.style.marginLeft = '20px';
            userDiv.appendChild(a);
        }

    }
}

//pre check photo
function toDataURL(input){
    var img = document.querySelector('#currentImage');
    if (input.files && input.files[0]) {
        var fr = new FileReader();

        fr.onload = function(e){
            img.src = e.target.result;
        };
        fr.readAsDataURL(input.files[0]);
    }
}

function startLinks() {
    //render links
    var countOfRecordsPerPage = 5;
    var userDiv = document.querySelector('.buttons');
    var userTable = document.querySelector('#dataTable');
    userTable.innerHTML = '';

    var countOfRecords = countOfUsers;

    var currentUsers = 0;

    var currentPage = 1;

    var pages = Math.ceil( countOfRecords / countOfRecordsPerPage );

    userDiv.innerHTML = '';


    if (countOfRecords > countOfRecordsPerPage) {

        for (var i = 1; i <= pages; i++) {
            var a = document.createElement('button');
            a.setAttribute('class', 'userLink' );

            a.addEventListener("click", function (ev) {

                userTable.innerHTML = '';

                //prev
                if (currentPage > this.innerHTML) {
                    if (currentPage <= pages) {
                        for (var j = currentPage; j > this.innerHTML; j--) {
                            currentUsers -= Number(countOfRecordsPerPage);
                        }
                        currentPage = this.innerHTML;
                    }
                    //next
                } else if (currentPage < this.innerHTML) {
                    if (currentPage <= pages) {
                        for (var j = currentPage; j < this.innerHTML; j++) {
                            currentUsers += Number(countOfRecordsPerPage);
                        }
                        currentPage = this.innerHTML;
                    }
                }
                history.pushState(null, null, 'client?command=pagination' + '&page=' + this.innerHTML + '&total=' + countOfRecordsPerPage);

                loadData('client?command=pagination'  + '&page=' + currentUsers +
                    '&total=' + countOfRecordsPerPage).then(res => render(res, 'users', 'dataTable'))
                    .catch(err => console.log(err));;
            });
            a.innerHTML = i;
            a.style.marginLeft = '20px';
            userDiv.appendChild(a);
        }
    }
}
