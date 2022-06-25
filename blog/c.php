<?php 
    session_start();
    if(empty($_SESSION['logged_user'])) {
        header("Location: login.php");
        exit();
    }
?>
<html>
    <head>
<?php 
   include_once './constants.php'; 
   include_once './template/meta.php'; 
   include_once './template/css.php';  
   
$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}

$customer_filter = filter_input(INPUT_POST, 'customer_filter'); 

?>
    </head>
    <body style="font-size: smaller">
        <div class="main-body">
            <div class="page-wrapper">
               <div class="page-body">
                    <div class="row">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-8">
                            <div class="card">
                                <div class="card-block">
                                    <h6 class="text-center text-primary">
                                        <small class="pull-left">SurfCode Technologies LTD - County WIFI Admin</small>
                                        <small class="pull-right"><u><?php echo ucfirst(($_SESSION['logged_user'])); ?></u></small>
                                    </h6>
                                    <br />
                                    <a href="javascript:window.location.href=window.location.href" class="btn btn-sm btn-success waves-effect waves-light pull-right">Refresh</a>
                                    
                                    <form id="my_form_2" name="my_form" method="POST">
                                       <div class="form-group row">
                                           <div class="col-sm-5" style="font-weight: bolder">
                                               <select name="customer_filter" class="form-control form-control-sm" onchange="this.form.submit()">
                                                    <option value="all">All Users</option>
                                                    <option <?php echo $customer_filter=='today' ? 'selected' : ''; ?> value="today">Today Only</option>
                                                    <option <?php echo $customer_filter=='month' ? 'selected' : ''; ?> value="month">This Month</option>                                                    
                                                    <option <?php echo $customer_filter=='active' ? 'selected' : ''; ?> value="active">Active Users Only</option>
                                               </select>
                                            </div>
                                        </div>                                         
                                   </form>
                                    
                                    <div class="dt-responsive table-responsive">
                                        <table id="alt-pg-dt" class="table table-striped table-bordered nowrap" style="font-size: smaller">
                                                   <thead>
                                                      <tr>
                                                            <th class="text-right"></th>
                                                            <th class="text-center">Phone</th>
                                                            <th class="text-center">Status</th>
                                                            <th class="text-center">Videos Watched</th>
                                                      </tr>
                                                   </thead>
                                                   <tbody>
<?php 
$filter = '';
if (isset($_REQUEST["customer_filter"])) {    
    switch($customer_filter){
        case 'today': $filter = ' WHERE DATE(updated_at) = DATE(CURRENT_DATE()) '; break;
        case 'month': $filter = ' WHERE MONTH(updated_at) = MONTH(CURRENT_DATE()) AND YEAR(updated_at) = YEAR(CURRENT_DATE()) '; break;
        case 'active': $filter = ' WHERE expiry_time > NOW() '; break;
        default: $filter = ''; break;
    }
    
}

    $sql = 
        'SELECT id, phone,connections, updated_at, expiry_time, NOW()
        FROM `'.$_SESSION['logged_user'].'` '.$filter.' ORDER BY `device` ASC, `connections` DESC';
    $counter = $ttl_amt = $ttl_active = 0;
    if ($stmt = $mysqli->prepare($sql)) {
          $stmt->execute();
          $stmt->bind_result($id, $phone, $connections, $created_at, $expiry_time, $now);
          while ($stmt->fetch()) { 
              $counter++;
              if($expiry_time<=$now){
                  $status = '<b style="color: red;">expired</b>';
                }else{
                    $status = '<b style="color: green;">active</b>';
                    $ttl_active++;
                }
              ?>
                                                    <tr>
                                                        <td class="text-right"><?php echo $counter; ?>.</td>
                                                        <td class="text-center"><?php echo $phone; ?></td>
                                                        <td class="text-center"><?php echo $status; ?></td>
                                                        <td class="text-center"><?php echo $connections; ?></td>
                                                    </tr>
<?php  }
}
           
    $mysqli->close();
          ?>
                                                </tbody>
                                                <tfoot>
                                                      <tr>
                                                            <th class="text-right" colspan="2">Active Users</th>
                                                            <th class="text-center" colspan="2"><?php echo number_format($ttl_active); ?></th>
                                                      </tr>
                                                </tfoot>
                                                </table>
                                             </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-2"></div>
                    </div>
               </div>
            </div>
        </div>
        <?php include_once 'template/js.php'; ?>
        <script>
            
        </script>
    </body>
</html>
