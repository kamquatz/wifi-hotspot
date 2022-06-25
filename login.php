<?php 
    session_start();
    if($_SESSION['logged_user']) {
        header("Location: c");
    }
?>
<html>
    <head>
<?php 
   include_once './constants.php'; 
   include_once './template/meta.php'; 
   include_once './template/css.php';  
   
   $error = '';
   
    $mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
    if (mysqli_connect_errno()) {
        printf("Connect failed: %s\n", mysqli_connect_error());
        exit();
    } 
    if (isset($_REQUEST["btn_login"])) {
        $login_username = filter_input(INPUT_POST, 'username'); 
        $login_password= trim(filter_input(INPUT_POST, 'password'));
        $sql = <<<SQL
                SELECT `id`, `username`, `password`,SHA2(?,256)
                    FROM `users` 
                        WHERE username=?
SQL;
    
        if ($stmt = $mysqli->prepare($sql)) {
            $error = '';
            $stmt->bind_param("ss",$login_password,$login_username);
            $stmt->execute();
            $stmt->bind_result($id, $username, $password, $login_hash_password);
            while ($stmt->fetch()) { 
                if($login_hash_password===$password){
                    $_SESSION['logged_user'] = $username;
                    header("Location: c");
                }else{
                    $error = 'Wrong User Password!';
                }
            }
        }
     } 
     $mysqli->close();
?>
    </head>
    <body>
        <div class="main-body">
            <div class="page-wrapper">
               <div class="page-body">
                    <div class="row">
                        <div class="col-sm-3"></div>
                     <div class="col-sm-6">
                        <div class="card">
                           <div class="card-block">
                               <b>M-Tech Admin Login</b>
                               <hr />
                                <form id="my_form" name="my_form" method="POST">
                                        <div class="form-group row">
                                            <label class="col-sm-5 col-form-label">Username / Email</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="Username"
                                                       name="username" id="username" required="required">
                                            </div>
                                        </div> 
                                        <div class="form-group row">
                                            <label class="col-sm-5 col-form-label">Password</label>
                                            <div class="col-sm-7">
                                                <input type="password" class="form-control" placeholder="Password"
                                                       name="password" id="password" required="required">
                                            </div>
                                        </div> 
                                    <h6 class="text-danger text-center"><?php echo $error; ?></h6>
                                    <div class="form-group row">
                                        <div class="col-sm-6">
                                            <button type="button" name="cancel_changes" id="cancel_changes" class="btn btn-sm btn-danger waves-effect pull-left">Cancel</button>
                                        </div>
                                        <div class="col-sm-6">
                                            <button type="submit" name="btn_login" id="btn_login" value="btn_submit" class="btn btn-sm btn-primary waves-effect waves-light pull-right">Login</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
                           </div>
        </div>
        <?php include_once 'template/js.php'; ?>
    </body>
</html>
