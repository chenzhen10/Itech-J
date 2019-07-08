'use strict';

var pic = document.querySelector('.mainPage');
pic.addEventListener("click", function (evt) {
    hideAllTablesExceptUser();
    showUserTable();

    hideEditUserForm();
    hideAddPhoneForm();
    hideAddUserForm();
    hideEmailForm();
    hideSearchForm();
    hideAttachmentForm();
});