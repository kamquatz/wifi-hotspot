<?php
//LNM Credentials
$consumer_key = 'bAlBP8YJHzber5n8GDJaCr1jjN70aN9j';
$consumer_secret = 'yIHvI5zWMNeqHCpf';
$Passkey = '6a81f64bd454fa1ccd8bf1862731a4c884ad9a734bd83d6164d0068486215c6d';
$Business_Code = '8044637';

$Time_Stamp = date("Ymdhis");
$credentials = base64_encode($consumer_key . ':' . $consumer_secret);
$password = base64_encode($Business_Code . $Passkey . $Time_Stamp);

//LNM URLs
$Register_URL = 'https://api.safaricom.co.ke/mpesa/c2b/v2/registerurl';
$Token_URL = 'https://api.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials';
$LNM_Online_URL = 'https://api.safaricom.co.ke/mpesa/stkpush/v1/processrequest';

//RETURN PATH URLs
$OnlinePayment = 'https://multiplespacetechnologies.com/admin/kanyoton/onlinepayment';
$CallBackURL = 'https://multiplespacetechnologies.com/admin/kanyoton/callback';
$ConfirmationURL = 'https://multiplespacetechnologies.com/admin/kanyoton/confirmation?token=bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919';
$ValidationURL="https://multiplespacetechnologies.com/admin/kanyonton/validation";