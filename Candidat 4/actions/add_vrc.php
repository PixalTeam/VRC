<?php
session_start();
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');

if(isset($_GET['submit'])) {
    if(isset($_SESSION['id'])) {
        if(!empty($_GET['name']) AND !empty($_GET['digit'])) {
            $insertmbr = $bdd->prepare('INSERT INTO vrc(name, digit, user_id, date) VALUES(?, ?, ?, UNIX_TIMESTAMP())');
            $insertmbr->execute(array($_GET['name'], $_GET['digit'], $_SESSION['id']));
        } 
        else {
            echo "Formulaire incomplet";
        }
    }
    else {
        echo "Vous n'êtes pas connecté";
    }
}
else {
    echo "Erreur";
}
?>