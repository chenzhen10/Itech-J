'use strict';
//phone
function isDuplicate(countryCode,operatorCode,number) {
    var numbers = document.getElementsByClassName("phoneNym");
    var fullNum = '+' + countryCode.value + '(' + operatorCode.value  + ')' + number.value;
    var foundDuplicated = false;

    var checkboxes = document.getElementsByName("phones");
    var currentIndex ;


    for (var i = 0; i < checkboxes.length;i++){
        if(checkboxes[i].checked){
            currentIndex = i;
        }
    }

    var index  = document.querySelector('#phoneTable').rows[currentIndex].rowIndex;

    for (var i = 0; i < numbers.length;i++){
        if(numbers[i].textContent.replace(" \" " , "") === fullNum && index !== 1 + i){
            return !foundDuplicated;
        }
    }
    return foundDuplicated;
}
function isDuplicateForAddPhone(countryCode,operatorCode,number) {
    var numbers = document.getElementsByClassName("phoneNym");
    var fullNum = '+' + countryCode.value + '(' + operatorCode.value  + ')' + number.value;
    var foundDuplicated = false;

    for (var i = 0; i < numbers.length;i++){
        if(numbers[i].textContent.replace(" \" " , "") === fullNum ){
            return !foundDuplicated;
        }
    }
    return foundDuplicated;
}



function isValidNumber(countryCode,operatorCode,number) {
    return  0 < countryCode.value.length && countryCode.value.length < 4
        && 0 < operatorCode.value.length && operatorCode.value.length < 4
        && 0 < number.value.length && number.value.length < 8;
}

function isDigitsOnly(countryCode,operatorCode,number) {
    return countryCode.value.toString().trim().match('(^\\d{0,10}$)') && operatorCode.value.toString().trim().match('(^\\d{0,10}$)')
        && number.value.toString().trim().match('(^\\d{0,10}$)');
}



//user
function checkDate(str) {
    if (str.match(/(^$)/)) return true;
    var matches = str.toString().match(/(\d{4})[- \.](\d{1,2})[- \.](\d{1,2})/);
    if (!matches) return;

    var isCorrectDate = true;

    var day = parseInt(matches[3], 10);
    var month = parseInt(matches[2], 10);
    var year = parseInt(matches[1], 10);
    var date = new Date(year, month - 1, day);


    if (date.getMonth() + 1 !== month ||
        date.getFullYear() !== year ||
        date.getDate() !== day) {
        return  false;
    }
    return isCorrectDate;
}

function validateDate(date) {
    return moment(date).isAfter(moment());
}

function isValidUserForm(name, surname, lastName,citizenship,country,city,email , index) {
    var re = /(^$)|^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return name.toString().match('^[A-Za-zА-Яа-яёЁ]+$') && surname.toString().match('^[A-Za-zА-Яа-яёЁ]+$')
        && lastName.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && citizenship.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && country.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && city.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && email.toString().match(re)
        && index.toString().match('(^$)|(^\\d{0,10}$)');
}

function isValidSearchForm(name, surname, lastName,citizenship,country,city , index) {
    return name.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && surname.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && lastName.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && citizenship.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && country.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)')
        && city.toString().match('(^$)|(^[A-Za-zА-Яа-яёЁ]+$)') && index.toString().match('(^$)|(^\\d{0,10}$)');
}

//attachment
function isValidAttachment(name) {
    return name.value.length > 0 && name.value.length < 20;
}


function isValidName(name){
    return name.length > 0 && name.length < 20 ;
}


