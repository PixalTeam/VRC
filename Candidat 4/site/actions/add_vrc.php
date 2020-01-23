<?php
session_start();
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');

if(isset($_POST['addvrc'])) {
    if(isset($_SESSION['id'])) {
        if(!empty($_POST['name']) AND !empty($_POST['digit'])) {
            $insertmbr = $bdd->prepare('INSERT INTO vrc(name, digit, user_id, date) VALUES(?, ?, ?, UNIX_TIMESTAMP())');
            $insertmbr->execute(array($_POST['name'], $_POST['digit'], $_SESSION['id']));
            echo "ok";
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