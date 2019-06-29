'use strict';

addAttachment.addEventListener("click",function (evt) {
    history.back();
    hideAllTablesExceptUser();
    history.pushState(null,null,'client/add/attachment');
    var attachmentForm = document.querySelector('.makeAttachment');
    attachmentForm.style.display = 'block';
    userEditForm.style.display = 'none';
});

//attachment
attach.addEventListener("click",function (ev) {
    var input = document.querySelector('#file');
    var fileName = document.querySelector('#fName');
    var comment = document.querySelector('#comment');

    if(input.files.length !== 0) {

        var data = new FormData();

        data.append('file', input.files[0]);
        data.append(fileName.value, fileName.value);
        data.append(comment.value, comment.value);
        data.append(userId,  userId);


        var options = {
            method: 'POST',
            mode: 'cors',
            body: data
        };

        var currentUrl = window.location.href.split('/');
        var requestUrl = currentUrl[4] + currentUrl[5].substring(0,1).toUpperCase() + currentUrl[5].substring(1);
        var req = new Request(requestUrl, options);

        fetch(req).then(function() {
            history.back();
            var attachmentForm = document.querySelector('.makeAttachment');
            attachmentForm.style.display = 'none';
            showUserTable();
            refreshAttachmentTable();
            refreshUserTable(0,5);
        });
    }
    else{
        alert("You should choose only one user and load 1 file");
    }

});

var attachmentsBack = document.querySelector(".backAttachment");
attachmentsBack.addEventListener("click",function (evt) {
    history.back();
    history.pushState(null,null,'client/edit/user');
    refreshUserTable(0,5);
    showAllTablesExceptUser();
    var attachmentForm = document.querySelector('.makeAttachment');
    attachmentForm.style.display = 'none';
    userEditForm.style.display = 'block';

});

//download
downloadAttachment.addEventListener("click",function(ev) {
    var fName = document.getElementsByClassName('filName');
    var userId = document.getElementsByClassName('userID');
    var checkboxes = document.getElementsByName("files");
    var path = document.getElementsByClassName('path');
    var checked = [];
    var values = [];
    var count;
    for (var i = 0; i < checkboxes.length;i++){
        if(checkboxes[i].checked){
            count = i;
            checked.push(checkboxes[i]);
        }
    }
    checked.forEach(function (value,index) {
        values.push(checked[index].value);
    });



    if (values.length === 1){
        var fileLength = fName[count].innerHTML.split('.').length;
        var fileL = path[count].innerHTML.split("\\").length;
        var fileName = path[count].innerHTML.split("\\")[fileL-1].split('.')[0];


        var fullFileName = fileName  + '.'  +  fName[count].innerHTML.split('.')[fileLength-1];


        window.location.href = 'client?command=download_file&fName=' + fullFileName + '&userId=' + userId[count].innerHTML  ;
    } else {
        alert('You can download only one file at a time');
    }

});

//delete attachments
deleteAttachment.addEventListener("click",function (ev) {

    var fName = document.getElementsByClassName('filName');
    var paths = document.getElementsByClassName('path');

    var names = [];
    var path =[];

    var checkboxes = document.getElementsByName("files");
    var checked = [];
    var values = [];
    for (var i = 0; i < checkboxes.length;i++){
        if(checkboxes[i].checked){
            checked.push(checkboxes[i]);
            names.push(fName[i].innerHTML);
            path.push(paths[i].innerHTML);
        }
    }
    checked.forEach(function (value,index) {
        values.push(checked[index].value);

    });

    if(values.length !== 0){

        history.back();

        var uri = 'client/delete/attachment?&id=' + values + '&fName=' + names  ;

        var options = {
            method : 'DELETE',
            headers : {'Content-Type' : 'application/x-www-form-urlencoded'},
            body : path
        };
        var req = new Request(uri,options);

        fetch(req).then(function () {
           refreshAttachmentTable(userId);
           history.forward();
        });
    }else{
        alert("You should choose file to delete");
    }
});

var editAttachment = document.querySelector('#editAttachment');
var modalAttachment = document.querySelector('.modalAttachment');
var pickedAttachment ;
var attachmentId;
var attachmentIdAccumulator = [];
editAttachment.addEventListener("click",function (ev) {

    var checkboxes = document.getElementsByName("files");
    var checked = [];
    var values = [];

    for (var i = 0; i < checkboxes.length;i++){
        if(checkboxes[i].checked){
            pickedAttachment = i;
            checked.push(checkboxes[i]);
        }
    }
    checked.forEach(function (value,index) {
        values.push(checked[index].value);
    });

    if(values.length === 1 ){
        history.back();
        attachmentId = values;
        attachmentIdAccumulator.push(attachmentId);
        history.pushState(null,null,'client/edit/attachment');


        document.forms.update_attachment.attachmentName.value = document.getElementsByClassName('filName')[pickedAttachment].innerHTML;
        document.forms.update_attachment.commentary.value = document.getElementsByClassName('attachmentComment')[pickedAttachment].innerHTML;


        modalAttachment.style.display = 'block';
        backdrop.style.display = 'block';
    }else{
        alert("You should choose only one attachment to edit");
    }
});

var cancelBtn = document.querySelector('.cancelAttachment');
var confirmBtn = document.querySelector('.confirmAttachment');

function closeModalAttachment(){
    modalAttachment.style.display = 'none';
    backdrop.style.display = 'none';
}

cancelBtn.addEventListener("click",function (evt) {
    history.back();
    history.pushState(null,null,'client/edit/user');
    closeModalAttachment();
});

function isValidName(){
    return true;
}

confirmBtn.addEventListener("click",function () {
    if(isValidName()){
        document.getElementsByClassName('filName')[pickedAttachment].innerHTML
            = document.forms.update_attachment.attachmentName.value;
        document.getElementsByClassName('attachmentComment')[pickedAttachment].innerHTML
            =  document.forms.update_attachment.commentary.value;

        history.back();
        history.pushState(null,null,'client/edit/user');
        closeModalAttachment();
    }else{
        alert('Your file name should be less then 30 ');
    }

});

var attachmentName = [];
var attachmentCommentary = [];
var attachmentID = [];

function saveAttachmentUpdate(index) {
    attachmentName.push(document.getElementsByClassName('filName')[index].innerHTML);
    attachmentCommentary.push(document.getElementsByClassName('attachmentComment')[index].innerHTML );
    attachmentID.push(attachmentIdAccumulator[index].toString());
}

function sendUpdatedAttachment() {
    var result = [] ;
    for (var i = 0; i < attachmentIdAccumulator.length ; i++) {
        var attachment = {
              id : attachmentID[i],
              name : attachmentName[i],
              commentary : attachmentCommentary[i]
            };
        result.push(attachment);
    }

    var options = {
        method : 'PUT',
        mode: 'cors',
        headers : {'Content-Type' : 'application/json; charset=utf-8;'},
        body : JSON.stringify(result)
    };

    var request = new Request('',options);
    fetch(request);
}

function refreshAttachmentFields() {
    attachmentName = [];
    attachmentCommentary = [];
    attachmentID = [];
}