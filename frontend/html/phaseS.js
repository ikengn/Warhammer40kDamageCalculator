function initialize(){
    activate();
}

function activate() {
    var homeNav = document.getElementById("phaseSNav");
    homeNav.classList.add("active");
    var myRelay = document.getElementById("myRelay");
    myRelay.innerHTML = "activated";
}
//var button = document.getElementById("testButton");
//button.addEventListener("click",activate);
function submit() {
    var e1 = document.getElementById("Unit1");
    var unit1 = e1.options[e.selectedIndex].text;
    var e2 = document.getElementById("Unit2");
    var unit2 = e2.options[e.selectedIndex].text;
    //document.getElementById('damageOutput').innerHTML = ;
}

initialize()