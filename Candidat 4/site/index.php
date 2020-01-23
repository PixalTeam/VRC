<?php
session_start();
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');
include('pages/header.php');

if (isset($_GET['page'])) {
    switch ($_GET['page']) {
        case 'connexion':
        include('pages/connexion.php');
        break;
        case 'inscription':
        include('pages/inscription.php');
        break;
        case 'profil':
        include('pages/profil.php');
        break;
        case 'add_vrc':
        include('pages/add_vrc.php');
        break;
        default:
        include('pages/accueil.php');
        break;
    }
} else {
    include('pages/accueil.php');
}
include('pages/footer.php');