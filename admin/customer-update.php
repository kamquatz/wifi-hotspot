<?php
header('Access-Control-Allow-Origin: *');
include_once './constants.php'; 
    
$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}

if (isset($_REQUEST["id"])){
    $id= filter_input(INPUT_POST, 'id');
    $site = filter_input(INPUT_POST, 'site');

    $sql = 'UPDATE `'.$site.'`
            SET `device`=1,`connections`=(`connections`+1)
            WHERE `id`=?';
SQL;
    if ($stmt = $mysqli->prepare($sql)) {
        $stmt->bind_param("i",$id);
        $stmt->execute();
        $stmt->close();
        echo '{ "status" : "success" }';
    }     
 } 