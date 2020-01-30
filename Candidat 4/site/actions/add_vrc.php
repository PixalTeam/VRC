<?php
session_start();
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');

if(isset($_POST['addvrc'])) {
    if(isset($_SESSION['id'])) {
        if(!empty($_POST['name']) AND !empty($_POST['digit'])) {
            $name = htmlspecialchars($_POST['name']);
            $digit = htmlspecialchars($_POST['digit']);

            $reqdigit = $bdd->prepare("SELECT * FROM vrc WHERE id = ?");
            $reqdigit->execute(array($digit));
            $digitexist = $reqdigit->rowCount();

            if ($digitexist == 0) {
                $insertmbr = $bdd->prepare('INSERT INTO vrc(name, digit, user_id, date) VALUES(?, ?, ?, UNIX_TIMESTAMP())');
                $insertmbr->execute(array($_POST['name'], $_POST['digit'], $_SESSION['id']));
                echo "ok";
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