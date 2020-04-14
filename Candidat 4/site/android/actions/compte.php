<?php

if (isset($_GET['id'])) {
    if($_GET['id'] != 0 || $_GET['id'] != '0') {
        session_start();
        $bdd = new PDO('mysql:host=localhost;dbname=vrc', 'root', '');

        
        $requser = $bdd->prepare("SELECT * FROM users WHERE id = ?");
        $requser->execute(array($_GET['id']));
        $userexist = $requser->fetch();

        $reqdigit = $bdd->prepare("SELECT * FROM vrc WHERE user_id = ?");
        $reqdigit->execute(array($_GET['id']));
        $digitexist = $reqdigit->fetchAll();
        $digitnum = $reqdigit->rowCount();

        for ($i = 0; $i < $digitnum; $i++) {
            $vrcList .= $digitexist[$i]['name']." (ajoutée le ".date('d/m/y H:i', $digitexist[$i]['date'])."),";
        }

        echo $userexist['username']."%Inscrit depuis le ".date('d F Y', $userexist['date'])."%".substr($vrcList, 0, -1);
    }
}