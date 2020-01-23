<?php
if (!isset($_SESSION['id'])) {
    echo '
        <div class="center">
            <div class="box animated fadeInRight fast">
                <h1>CONNEXION</h1>
                <input type="text" placeholder="Identifiant ou Email"><br>
                <input type="password" placeholder="Mot de passe"><br>
                <input type="submit" class="form" onclick="connexion(\'Connexion\')" value="Connexion">
            </div>
        </div>';
}
else {
    header('Location: index.php');
}
?>