<?php
header('Access-Control-Allow-Origin: *');
include './constants.php';

$ph = filter_input(INPUT_POST, 'phone');
$site = filter_input(INPUT_POST, 'site');
$phone =  '254'.substr($ph,-9);

$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}

$sql = 'INSERT INTO `'.$site.'`
                  (phone, expiry_time, created_at) 
                      VALUE(?,DATE_ADD(NOW(),INTERVAL 30 MINUTE),NOW())
                          ON DUPLICATE KEY UPDATE 
                          expiry_time=DATE_ADD(NOW(),INTERVAL 30 MINUTE),device=0';

if ($stmt = $mysqli->prepare($sql)) {
    $stmt->bind_param("s",$phone);
    $stmt->execute();
    $stmt->close();
    echo 'success';
}   
