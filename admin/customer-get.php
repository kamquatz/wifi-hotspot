<?php
include_once './constants.php';
$site = filter_input(INPUT_GET, 'site');
$logical = filter_input(INPUT_GET, 'logical'); //=='less' ? '<' : '>';
$limit = filter_input(INPUT_GET, 'limit');


$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}

$sql = 'SELECT `id`, `phone`
        FROM `'.$site.'`
        WHERE `device`=0
        ORDER BY id ASC
        LIMIT ?';

if($stmt = $mysqli->prepare($sql)) {
    $stmt->bind_param("i",$limit);
    $stmt->execute();
    $res = $stmt->get_result(); // returns mysqli_result same as mysqli::query()
    $rows = $res->fetch_all(MYSQLI_ASSOC);
    print json_encode($rows);
}

$mysqli->close();
