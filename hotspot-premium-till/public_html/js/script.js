var radius = 'http://185.203.117.51:8081/account';
var push = 'http://185.203.117.51/hotspot-premium/push.php';
/*
var radius = 'http://localhost:8080/account';
var push = 'http://localhost/hotspot-premium/push.php';
*/
var town = 'meru';
var station = 'makutano';
var adphone = document.getElementById("adphone");
var notice = document.getElementById("notice");

adphone.onkeyup = function() {
    let phoneNumber = adphone.value.trim().replaceAll(' ','');
    adphone.value = phoneNumber;
    if(phoneNumber.length===10){
        let phone = '254'+phoneNumber.slice(-9);
        notice.innerHTML = ('');
        $.post(radius,{
            phone: phone,
            town: town,
            station: station
        },
        function(data, status){    
            account = data;
            if(account>0){
                document.sendin.username.value = account;
                document.sendin.password.value = account;
                document.sendin.submit();
            }else {
                notice.innerHTML = ('MPESA Confirmation not Received. Kindly pay for a package then Enter the Phone Number that made the MPESA payment');
                adphone.value = '';
                let ref = town+'-'+station;
               // stk(phone, 20, ref);
            }
        });
    }else notice.innerHTML = ('Enter Valid Phone Number');
};

function stk(phone, amount, ref){
    $.post(push,{
        phone: phone,
        amount: amount,
        ref: ref
    },
    function(data, status){    
        console.log(data);
    });
}
