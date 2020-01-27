<?php
session_start();
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');
if(isset($_GET['register'])) {
    if(!empty($_GET['username']) AND !empty($_GET['password']) AND !empty($_GET['password2'])) {
        $password = md5($_GET['password']);
        $password2 = md5($_GET['password2']);
        $username = htmlspecialchars($_GET['username']);
        if($password == $password2) {
            $requsername = $bdd->prepare("SELECT * FROM users WHERE username = ?");
            $requsername->execute(array($username, $password));
            $usernameexist = $requsername->rowCount();

            $reqmail = $bdd->prepare("SELECT * FROM users WHERE mail = ?");
            $reqmail->execute(array($mail, $password));
            $mailexist = $reqmail->rowCount();
            if($usernameexist == 0 OR $mailexist == 0) {
                $insertmbr = $bdd->prepare("INSERT INTO users(username, password, ip, date, perm, mail) VALUES (?, ?, ?, UNIX_TIMESTAMP(), 0, NULL)");
                $insertmbr->execute(array($username, $password, $_SERVER['REMOTE_ADDR']));

                $requsernamefr = $bdd->prepare("SELECT * FROM users WHERE username = ?");
                $requsernamefr->execute(array($username));
                
                $usernameexistfr = $requsernamefr->fetch();

                $_SESSION['id'] = $usernameexistfr['id'];
                if(isset($_SESSION['id'])) {
                    echo 'ok';
                } else {
                    echo "Erreur de connexion";
                }
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