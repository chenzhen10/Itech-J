'use strict';

var indexOfUpdatedAttachment = [];
var nameLengthChecker;

var editAttachment = document.querySelector('#editAttachment');
var modalAttachment = document.querySelector('.modalAttachment');
var pickedAttachment ;
var attachmentId;
var attachmentIdAccumulator = [];

var attachmentsBack = document.querySelector(".backAttachment");

var cancelBtn = document.querySelector('.cancelAttachment');
var confirmBtn = document.querySelector('.confirmAttachment');


var attachmentName = [];
var attachmentCommentary = [];
var attachmentID = [];


addAttachment.addEventListener("click",function (evt) {
    hideAllTablesExceptUser();
    showAttachmentForm();
    hideEditUserForm();
});

//attachment
attach.addEventListener("click",function (ev) {
    ev.preventDefault();
    var input = document.querySelector('#file');
    var fileName = document.querySelector('#fName');
    var comment = document.querySelector('#comment');

    if(input.files.length !== 0 && isValidAttachment(fileName)) {

        var data = new FormData();

        data.append('file', input.files[0]);
        data.append(fileName.value, fileName.value);
        data.append(comment.value, comment.value);
        data.append(userId,  userId);


        var options = {
            method: 'POST',
            body: data
        };

        fetch('client/add/attachment',options).then(function(value) {
            if (value.status === 500) {
                return value.json();
            }

            hideAttachmentForm();
            resetAttachmentAddForm();
            showAllTablesExceptUser();
            refreshAllTableByUserId(userId);
            showEditUserForm();

        }).then(function(res) {
            showError(res);
        }).catch(err => console.log(err));
    }else if(!isValidAttachment(fileName)){
        alert('Your name should be less then 20 and not be empty');
    }else{
        alert("You should choose 1 file and name should be not empty");
    }

});


attachmentsBack.addEventListener("click",function (evt) {
    resetAttachmentAddForm();
    refreshUserTable(0,5);
    showAllTablesExceptUser();
    hideAttachmentForm();
    showEditUserForm();
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
        //file name which we will see
        var fileL = path[count].innerHTML.split("\\").length;
        var fileExt = path[count].innerHTML.split("\\")[fileL-1].split('.')[1];
        var fileName = path[count].innerHTML.split("\\")[fileL-1].split('.')[0];


        var fullFileName =  fName[count].innerHTML.split('.')[0]    +  '.'  + fileExt ;
        var downloadLink = fileName + '.' + fileExt;

        window.location.href = 'client?command=downloadFile&fName=' + fullFileName + '&userId=' + userId[count].innerHTML + '&downloadLink=' + downloadLink  ;
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
        var options = {
            method : 'DELETE',
            headers : {'Content-Type' : 'application/x-www-form-urlencoded'},
            body : path
        };

        fetch('client/delete/attachment?&id=' + values + '&fName=' + names ,options).then(function (value) {
           refreshAttachmentTable(userId);
            if (value.status === 500) {
                return value.json();
            }
        }).then(function(res) {
            showError(res);
        }).catch(err => console.log(err));
    }else{
        alert("You should choose file to delete");
    }
});

//edit attachment

editAttachment.addEventListener("click",function (ev) {

    var checkboxes = document.getElementsByName("files");
    var checked = [];
    var values = [];

    for (var i = 0; i < checkboxes.length;i++){
        if(checkboxes[i].checked){
            indexOfUpdatedAttachment.push(i);
            pickedAttachment = i;
            checked.push(checkboxes[i]);
        }
    }
    checked.forEach(function (value,index) {
        values.push(checked[index].value);
    });

    if(values.length === 1 ){
        attachmentId = values;
        attachmentIdAccumulator.push(attachmentId);

        document.forms.update_attachment.attachmentName.value = document.getElementsByClassName('filName')[pickedAttachment].innerHTML;
        document.forms.update_attachment.commentary.value = document.getElementsByClassName('attachmentComment')[pickedAttachment].innerHTML;

        modalAttachment.style.display = 'block';
        backdrop.style.display = 'block';
    }else{
        alert("You should choose only one attachment to edit");
    }
});


function closeModalAttachment(){
    modalAttachment.style.display = 'none';
    backdrop.style.display = 'none';
}

cancelBtn.addEventListener("click",function (evt) {
    closeModalAttachment();
});



confirmBtn.addEventListener("click",function () {

    nameLengthChecker = document.getElementsByClassName('filName')[pickedAttachment].innerHTML
                = document.forms.update_attachment.attachmentName.value;
            document.getElementsByClassName('attachmentComment')[pickedAttachment].innerHTML
                = document.forms.update_attachment.commentary.value;
    if (isValidName(nameLengthChecker)) {
            closeModalAttachment();
            nameLengthChecker = '';
        }else {
            alert('Name should be less then 20 and more then 0');
        }
});


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
        headers : {'Content-Type' : 'application/json; charset=utf-8;'},
        body : JSON.stringify(result)
    };

    fetch('client/edit/attachment',options).then(function (value) {
        if (value.status === 500) {
            return value.json();
        }
    }).then(function(res) {
        showError(res);
        pickedAttachment = '';
    }).catch(err => console.log(err));;
}

function refreshAttachmentFields() {
    attachmentName = [];
    attachmentCommentary = [];
    attachmentID = [];
}

function resetAttachmentAddForm() {
 document.querySelector('#form').reset();
}

function showAttachmentForm() {
    var attachmentForm = document.querySelector('.makeAttachment');
    attachmentForm.style.display = 'block';
}

function hideAttachmentForm() {
    var attachmentForm = document.querySelector('.makeAttachment');
    attachmentForm.style.display = 'none';
}