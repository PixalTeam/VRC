inside = document.getElementsByClassName('body')[0];
header_co_bouton = document.getElementsByClassName('co');

header_nc_bouton = document.getElementsByClassName('nc');

function body(cw) {
    const request = new XMLHttpRequest();
    request.open('POST', 'pages/'+cw+'.php', true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.send('');
    request.onreadystatechange = function()
    {
        if (request.readyState === 4) {
            switch(request.responseText) {
                case 'session':

                    toastr["warning"]("Vous êtes déjà connectés. Vous n'avez pas accès à cette page")
                    toastr.options = {
                        "closeButton": false,
                        "debug": false,
                        "newestOnTop": true,
                        "progressBar": false,
                        "positionClass": "toast-top-right",
                        "preventDuplicates": false,
                        "onclick": null,
                        "showDuration": "300",
                        "hideDuration": "1000",
                        "timeOut": "5000",
                        "extendedTimeOut": "1000",
                        "showEasing": "swing",
                        "hideEasing": "linear",
                        "showMethod": "fadeIn",
                        "hideMethod": "fadeOut"
                    }
                default:
                    inside.innerHTML = request.responseText;
            }
        }
    };
}

body('accueil')


header_co_bouton[0].onclick = function() {
    body('add_vrc')
}
header_co_bouton[1].onclick = function() {
    body('accueil')
}
header_co_bouton[2].onclick = function() {
    body('profil')
}
header_nc_bouton[0].onclick = function() {
    body('inscription')
}
header_nc_bouton[1].onclick = function() {
    body('accueil')
}
header_nc_bouton[2].onclick = function() {
    body('connexion')
}

//NE FONCTIONNE PAS, A REGLER DONC

bouton = document.getElementById('form');

bouton.onclick = function habopn() {
    alert('hey')
    /*
        input = document.getElementsByTagName('input')
        var formData = new FormData();
        formData.set("login", "TRUE");
        formData.set("username", input[0]);
        formData.set("password", input[0]);

        var request = new XMLHttpRequest();
        request.open('GET', 'actions/login.php', true);
        request.send(formData);
        request.onreadystatechange = function()
        {
            if (request.readyState === 4) {
                if(request.responseText == "ok") {
                    toastr["success"]("Vous allez être connecté")
                    toastr.options = {
                        "closeButton": false,
                        "debug": false,
                        "newestOnTop": true,
                        "progressBar": false,
                        "positionClass": "toast-top-right",
                        "preventDuplicates": false,
                        "onclick": null,
                        "showDuration": "300",
                        "hideDuration": "1000",
                        "timeOut": "5000",
                        "extendedTimeOut": "1000",
                        "showEasing": "swing",
                        "hideEasing": "linear",
                        "showMethod": "fadeIn",
                        "hideMethod": "fadeOut"
                    }
                }
                else {
                    toastr["warning"](request.responseText)
                    toastr.options = {
                        "closeButton": false,
                        "debug": false,
                        "newestOnTop": true,
                        "progressBar": false,
                        "positionClass": "toast-top-right",
                        "preventDuplicates": false,
                        "onclick": null,
                        "showDuration": "300",
                        "hideDuration": "1000",
                        "timeOut": "5000",
                        "extendedTimeOut": "1000",
                        "showEasing": "swing",
                        "hideEasing": "linear",
                        "showMethod": "fadeIn",
                        "hideMethod": "fadeOut"
                    }
                }
            }
        }
    */

}