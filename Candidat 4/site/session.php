<?php
session_start();
if (isset($_SESSION['id'])) {
    $_SESSION = array();
    session_destroy();
}
else {
    $_SESSION['id'] = 9;
}
?>