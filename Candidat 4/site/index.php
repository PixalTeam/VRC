<?php
session_start();
$bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');
include('pages/header.php');

echo '<div class="body">';
echo '</div>';

include('pages/footer.php');