<?php
echo'
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <link rel="stylesheet" href="assets/css/main.css">
    <title>VRC - PixalTeam</title>
</head>
<body>
    <div class="header">';
    if (!isset($_SESSION['id'])) {
        echo '
            <span first class="animated fadeInDown slow">
                <p class="nc">
                <a href="?page=inscription">
                    INSCRIPTION
                    </a>
                </p>
            </span>
            <a href="?page=accueil">
            <img src="assets/img/logo.png" class="nc" alt="logo">
            </a>
            <span second class="animated fadeInDown slow">
                <p class="nc">
                <a href="?page=connexion">
                    CONNEXION
                    </a>
                </p>
            </span>
        </div>';
    }
    else {
        echo '
        <span first class="animated fadeInDown slow">
        <p>
            <a href="https://github.com/PixalTeam" target="_blank">
            GITHUB</a>
        </p>
    </span>
    <span third class="animated fadeInDown slow">
                <p class="co">
                <a href="?page=add_vrc">
                    AJOUTER UNE VRC
                </a>
                </p>
            </span>
            <a href="?page=accueil">
            <img src="assets/img/logo.png" class="nc" alt="logo">
            </a>
            <span fourth class="animated fadeInDown slow">
                <p>
                <a href="actions/logout.php">
                    DECONNEXION
                    </a>
                </p>
            </span>
            <span second class="animated fadeInDown slow">
                <p class="co">
                <a href="?page=profil&id='.$_SESSION['id'].'">
                    MON COMPTE
                </a>
                </p>
            </span>

        </div>';
    }
?>