document.stk.phone.focus();
document.stk.phone.value = sessionStorage.getItem("phone");

document.stk.phone.onkeyup = function() {   
    sessionStorage.setItem("phone",document.stk.phone.value);
};

function setAmount(amt){
    document.stk.amount.value = amt;
    sessionStorage.setItem("amount",amt);
}

function connect(){
    document.sendin.username.value = document.stk.phone.value;
    document.sendin.password.value =  document.stk.phone.value;
    document.sendin.submit();
    return false;
}
function connect2(){
    document.sendin.username.value = document.getElementById("account").value;
    document.sendin.password.value = document.getElementById("account").value;
    document.sendin.submit();
    return false;
}

function connect3(){
    document.sendin.username.value = document.getElementById("adphone").value;
    document.sendin.password.value = document.getElementById("adphone").value;
    document.sendin.submit();
    return false;
}
var submit = function(event) {
    event.preventDefault();
    let url = 'http://multiplespacetechnologies.com/admin/kanyonton/push.php';
    $.post(url,
    {
        phone: '254'+sessionStorage.getItem("phone").slice(-9),
        amount: sessionStorage.getItem("amount"),        
        account: sessionStorage.getItem("phone")
    },
    function(data, status){
        sessionStorage.setItem("pushed",1);
        connect();
    });
};

function connectDefault(){
    let url = 'http://multiplespacetechnologies.com/admin/kanyonton/push.php';
    let amount = sessionStorage.getItem("amount") !==null ? sessionStorage.getItem("amount") : 20;
    if(!sessionStorage.getItem("pushed")) connect();
    else{
        $.post(url,{
            phone: '254'+sessionStorage.getItem("phone").slice(-9),
            amount: amount,        
            account: sessionStorage.getItem("phone")
        },
        function(data, status){
            connect();
        });
    } 
}

var form = document.getElementById("stk");
form.addEventListener("submit", submit, true);

var ad_video = document.getElementById("ad_video");
ad_video.addEventListener("click", () => {
  ad_video.play();
}, { once: true });
document.getElementById("watchad").addEventListener("click", () => {
  ad_video.play();
}, { once: true });
ad_video.onended = function() {
    document.getElementById("adconnectbutton").style.visibility = 'visible';
    document.getElementById("notice2").style.visibility = 'hidden';
};

var adphone = document.getElementById("adphone");
var notice = document.getElementById("notice");
adphone.onkeyup = function() {
    let phone = adphone.value.trim();
    if(phone.length===10){
        notice.innerHTML = ('');
        let url = 'http://multiplespacetechnologies.com/admin/kanyonton/ad.php';
        $.post(url,
        {
            phone: phone,
            amount: 0,        
            account: phone
        },
        function(data, status){
            
        });
    }else notice.innerHTML = ('Enter Valid Phone Number');
};
