<?php
session_start();
if (isset($_SESSION['id'])) {
    echo $_SESSION['id'];
}
else {
    $_SESSION['id'] = 9;
}
?>