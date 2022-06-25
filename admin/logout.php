<?php 
    session_start();
    session_destroy();
    
    if (isset($_REQUEST["site"])) {
        $site = filter_input(INPUT_GET, 'site'); 
    }
    header("Location: login.php?site=".$site);
?>?>