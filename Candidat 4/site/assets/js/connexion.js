//NE FONCTIONNE PAS, A REGLER DONC
form_bouton = document.getElementsByClassName('form');

form_bouton[0].onclick = function testt() {
    alert('hello')
}

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