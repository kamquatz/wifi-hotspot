<?php 
    session_start();
    if(isset($_SESSION["logged_user"])){
        header("Location: c.php");
        exit();
    }else{
        header("Location: login.php");
    }
