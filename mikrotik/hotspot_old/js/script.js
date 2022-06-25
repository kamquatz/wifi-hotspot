document.stk.phone.value = sessionStorage.getItem("phone");

document.stk.phone.onkeyup = function() {   
    sessionStorage.setItem("phone",document.stk.phone.value);
};

function setAmount(amt){
    document.stk.amount.value = amt;
    sessionStorage.setItem("amount",amt);
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
        document.sendin.username.value = sessionStorage.getItem("phone");
        document.sendin.password.value = hexMD5('$(chap-id)' + sessionStorage.getItem("phone") + '$(chap-challenge)');
        document.sendin.submit();
        return false;
    });
};

var form = document.getElementById("stk");
form.addEventListener("submit", submit, true);
