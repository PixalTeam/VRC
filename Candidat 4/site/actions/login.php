<?php
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');
session_start();
if(isset($_GET['login'])){
    if(!empty($_GET['username']) AND !empty($_GET['password'])) {
        $username = htmlspecialchars($_GET['username']);
        $password = md5($_GET['password']);

        $requser = $bdd->prepare("SELECT * FROM users WHERE username = ? AND password = ?");
        $requser->execute(array($username, $password));
        $userexist = $requser->rowCount();
        if($userexist == 1) {
            $userinfo = $requser->fetch();
            $_SESSION['id'] = $userinfo['id'];
            $requser = $bdd->prepare("UPDATE users SET ip = ?, date = UNIX_TIMESTAMP() WHERE id = ?");
            $requser->execute(array($_SERVER['REMOTE_ADDR'], $userinfo['id']));
            echo $_SESSION['id'];
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
