<?php
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');
session_start();
if(!empty($_SESSION['id'])) {
    $requsername = $bdd->prepare("SELECT * FROM vrc WHERE user_id = ?");
    $requsername->execute(array($_SESSION['id']));
    $usernameexist = $requsername->rowCount();
}