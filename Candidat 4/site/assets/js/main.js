inside = document.getElementsByClassName('body')[0];

function body(cw) {
    const request = new XMLHttpRequest();
    request.open('POST', 'pages/'+cw+'.php', true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.send('');
    request.onreadystatechange = function()
    {
        if (request.readyState === 4) {
            inside.innerHTML = request.responseText;
        }
    };
}

body("accueil")
