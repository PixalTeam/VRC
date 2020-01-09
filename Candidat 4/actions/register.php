<?php
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');
session_start();
if(isset($_GET['register'])) {
    if(!empty($_GET['username']) AND !empty($_GET['password']) AND !empty($_GET['password2'])) {
        $password = md5($_GET['password']);
        $password2 = md5($_GET['password2']);
        $username = $_GET['username'];
        if($password == $password2) {
            $requsername = $bdd->prepare("SELECT * FROM users WHERE username = ? AND password = ?");
            $requsername->execute(array($username, $pass));
            $usernameexist = $requsername->rowCount();
            if($usernameexist == 0) {
                $insertmbr = $bdd->prepare("INSERT INTO users(username, password, ip, date) VALUES (?, ?, ?, UNIX_TIMESTAMP())");
                $insertmbr->execute(array($username, $password, $_SERVER['REMOTE_ADDR']));

                $usernameexist = $requsername->fetch();
                $_SESSION['id'] = $userexist['id'];
                echo $_SESSION['id'];
            } else {
                echo "Vous avez déjà un compte. Veuillez vous connectez s'il vous plaît.";
            }
        } else {
            echo "Vos 2 mots de passe ne correspondent pas.";
        }
    } else {
        echo "Tous les champs doivent être remplis.";
    }
} else {
    echo "Erreur";
}
?>