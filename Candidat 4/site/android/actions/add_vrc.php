<?php
session_start();
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');

if(isset($_GET['addvrc'])) {
    if(isset($_GET['id'])) {
        if(!empty($_GET['name']) AND !empty($_GET['digit'])) {
            $name = htmlspecialchars($_GET['name']);
            $digit = htmlspecialchars($_GET['digit']);

            $reqdigit = $bdd->prepare("SELECT * FROM vrc WHERE digit = ?");
            $reqdigit->execute(array($digit));
            $digitexist = $reqdigit->rowCount();

            if ($digitexist == 0) {
                $insertmbr = $bdd->prepare('INSERT INTO vrc(name, digit, user_id, date) VALUES(?, ?, ?, UNIX_TIMESTAMP())');
                $insertmbr->execute(array($_GET['name'], $_GET['digit'], $_GET['id']));
                echo "yes";
            }
            else {
                echo "L'ID de cette VRC est déjà inscrite. Veuillez choisir un autre ID";
            }
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