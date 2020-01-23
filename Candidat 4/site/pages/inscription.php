<?php
if (!isset($_SESSION['id'])) {
    echo '
        <div class="center">
            <div class="box animated fadeInLeft fast">
                <h1>INSCRIPTION</h1>
                <input type="text" placeholder="Identifiant"><br>
                <input type="email" placeholder="Email"><br>
                <input type="password" placeholder="Mot de passe"><br>
                <input type="password" placeholder="Répéter mot de passe"><br>
                <input type="submit" class="form" onclick="register(\'Inscription\')" value="Inscription">
            </div>
        </div>';
}
else {
    header('Location: index.php');
}
?>