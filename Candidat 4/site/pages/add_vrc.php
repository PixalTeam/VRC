<?php
session_start();
if (!isset($_SESSION['id'])) {
    echo '
        <div class="center">
            <div class="box animated fadeInRight fast">
                <h1>AJOUTER UNE VRC</h1>
                <input type="text" placeholder="Nom de la VRC"><br>
                <input type="password" placeholder="ID de la VRC"><br>
                <input type="submit" class="form" value="Ajouter une VRC">
            </div>
        </div>';
}
else {
    echo 'session';
}
?>