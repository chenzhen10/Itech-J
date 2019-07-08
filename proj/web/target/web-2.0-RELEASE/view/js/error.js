'use strict';
function showError(res) {
    if (res) {
        var context = document.querySelector('#errorMsg');
        var template = document.querySelector('#error').innerHTML;
        var elem = document.createElement('span');
        elem.style.color = 'red';
        elem.style.fontSize = '20px';
        elem.innerHTML = Mustache.render(template, res);
        context.appendChild(elem);
        setTimeout(function () {
            context.innerHTML = ''
        }, 10000);
    }
}