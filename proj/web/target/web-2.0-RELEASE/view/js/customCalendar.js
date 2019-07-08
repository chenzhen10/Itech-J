'use strict';

function createYearSelect() {

    var select = document.querySelectorAll('.year');
    for (var j = 0; j < select.length ; j++) {
        var countOfYear = 200;
        var startYear = 1900;
        for (var i = 0; i < countOfYear; i++) {
            var option = document.createElement('option');
            option.setAttribute('value', startYear);
            option.innerHTML = startYear++;
            select[j].append(option);
        }
    }

}

function createMonthSelect() {

    var select = document.querySelectorAll('.month');

    for (var j = 0; j < select.length ; j++) {
        var countOfYear = 12;
        var startYear = 1;
        for (var i = 0; i < countOfYear; i++) {
            var option = document.createElement('option');
            option.setAttribute('value', startYear);
            option.innerHTML = startYear++;
            select[j].append(option);
        }
    }

}

function createDaySelect() {

    var select = document.querySelectorAll('.day');
    for (var j = 0; j < select.length ; j++) {
        var countOfYear = 31;
        var startYear = 1;
        for (var i = 0; i < countOfYear; i++) {
            var option = document.createElement('option');
            option.setAttribute('value', startYear);
            option.innerHTML = startYear++;
            select[j].append(option);
        }
    }


}

function dateCreator() {
    var day = document.querySelector('.day');
    var month = document.querySelector('.month');
    var year = document.querySelector('.year');


    var dayIndex = day.selectedIndex;
    var monthIndex = month.selectedIndex;
    var yearIndex = year.selectedIndex;

    if (dayIndex === 0 && monthIndex === 0 && yearIndex === 0) {
        return '';
    }

    var date = '';
    date += year.value;
    date += '-';
    date += month.value;
    date += '-';
    date += day.value;
    return date;
}


function dateCreatorForUpdateUser() {
    var day = document.querySelectorAll('.day')[1];
    var month = document.querySelectorAll('.month')[1];
    var year = document.querySelectorAll('.year')[1];


    var dayIndex = day.selectedIndex;
    var monthIndex = month.selectedIndex;
    var yearIndex = year.selectedIndex;

    if (dayIndex === 0 && monthIndex === 0 && yearIndex === 0) {
        return '';
    }

    var date = '';
    date += year.value;
    date += '-';
    date += month.value;
    date += '-';
    date += day.value;
    return date;
}

createYearSelect();
createMonthSelect();
createDaySelect();

function isNumeric(num){
    return !isNaN(num)
}