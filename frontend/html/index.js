


function initialize(){
    window.location.href="home.jsp";
    activate();
    populate();
}
var units = ["Captain","Captain on Bike","imperial Space Marine", "Space Marine"];
function activate() {
    var homeNav = document.getElementById("homeNav");
    homeNav.classList.add("active");
    //var myRelay = document.getElementById("myRelay");
    //myRelay.innerHTML = "activated";
}

function populate(){
    var unit1Drop = document.getElementById("Unit1");
    var i = 0
    var length = unit1Drop.options.length
    while(length>1){ //removes all , but the --please ... -- option
        length = length-1
        unit1Drop.remove(length);
    }
    while(i<units.length){
        var opt = document.createElement("option");
        opt.value = units[i]
        opt.innerHTML = units[i]
        unit1Drop.appendChild(opt)
        i = i+1
    }

}

//var button = document.getElementById("testButton");
//button.addEventListener("click",activate);
function submit() {
    //var e1 = document.getElementById("Unit1");
    //var unit1 = e1.options[e.selectedIndex].text;
    //var e2 = document.getElementById("Unit2");
    //var unit2 = e2.options[e.selectedIndex].text;
    var hiddenDiv = document.getElementById("hiddenDiv");
    hiddenDiv.style.display='block'
    //document.getElementById('damageOutput').innerHTML = ;
}

function hide() {
    //var e1 = document.getElementById("Unit1");
    //var unit1 = e1.options[e.selectedIndex].text;
    //var e2 = document.getElementById("Unit2");
    //var unit2 = e2.options[e.selectedIndex].text;
    var hiddenDiv = document.getElementById("hiddenDiv");
    hiddenDiv.style.display='none'
    //document.getElementById('damageOutput').innerHTML = ;
}


document.addEventListener("DOMContentLoaded",function set(){
    initialize()
})
//initialize()