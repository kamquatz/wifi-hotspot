var videoCount = 7;
var site = 'tharaka';
var station = 'tharaka';
var adphone = document.getElementById("adphone");
var notice = document.getElementById("notice");
var notice2 = document.getElementById("notice2");
var adconnect = document.getElementById("adconnectbutton");
var advideo = document.getElementById("advideo");
var source = document.createElement('source');
advideo.appendChild(source);
setVideo();

//adconnect.style.visibility = 'visible';
document.onclick = function() { 
    advideo.play();    
};

function setVideo(){
    let pos = new Date().getMinutes() % videoCount;
    let src = 'ads/vid_'+pos+'.mp4';
    //console.log(src);
    source.setAttribute('src', src);
    source.setAttribute('type', 'video/mp4');
    advideo.load();
    setTimeout(function(){
        adconnect.style.visibility = 'visible';
        notice2.style.visibility = 'hidden';
        connect();
    }, 30000);    
}

function connect(){
    let phone = adphone.value.trim();
    if(phone.length===10){
        let account = '254'+phone.slice(-9);
        document.sendin.username.value = account;
        document.sendin.password.value = account;
        setTimeout(function(){
            document.sendin.submit();
        }, 2000);
    }else notice.innerHTML = ('Enter Valid Phone Number');
    return false;
}

adphone.onkeyup = function() {
    let phone = adphone.value.trim();
    if(phone.length===10){
        notice.innerHTML = ('');
        let url = 'http://185.203.117.51:8080/add';
        $.post(url,{
            phone: '254'+phone.slice(-9),
            site: site,
            station: station
        },
        function(data, status){            
        });
    }else notice.innerHTML = ('Enter Valid Phone Number');
};
