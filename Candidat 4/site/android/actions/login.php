<?php
session_start();
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');
if(isset($_GET['login'])){
    if(!empty($_GET['username']) AND !empty($_GET['password'])) {
        $username = $_GET['username'];
        $password = md5($_GET['password']);

        $requser = $bdd->prepare("SELECT * FROM users WHERE username = ? AND password = ?");
        $requser->execute(array($username, $password));
        $userexist = $requser->rowCount();
        $reqmail = $bdd->prepare("SELECT * FROM users WHERE mail = ? AND password = ?");
        $reqmail->execute(array($username, $password));
        $mailexist = $reqmail->rowCount();
        if($userexist == 1 OR $mailexist == 1) {
            $userinfo = $requser->fetch();
            $_SESSION['id'] = $userinfo['id'];
            $requser = $bdd->prepare("UPDATE users SET ip = ? WHERE id = ?");
            $requser->execute(array($_SERVER['REMOTE_ADDR'], $userinfo['id']));

            $requsernamefr = $bdd->prepare("SELECT * FROM users WHERE username = ?");
            $requsernamefr->execute(array($username));
            
            $usernameexistfr = $requsernamefr->fetch();

            $_SESSION['id'] = $usernameexistfr['id'];
            if(isset($_SESSION['id'])) {
                echo 'yes/'.$_SESSION['id'];
            } else {
                echo "Erreur de connexion";
            }
        } else {
            echo "Vous n'êtes pas enregistré dans nos bases de données. Veuillez vous inscrire s'il vous plaît.";
        }
    } else {
        echo "Tous les champs doivent être remplis.";
    }
} else { 
    echo "Erreur";
}
?>
