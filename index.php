<?php
session_start();
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');
include('pages/header.php');

include('pages/accueil.php');

include('pages/footer.php');