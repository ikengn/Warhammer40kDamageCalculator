

//initialize function which includes setting the navBar and populating the dropdown
function initialize(){
    activate();
    onStart();
}


//var passIn = "<%=PassingData %>"
//var units = passIn.split(",")
var numberOfSuccessfulHits = 0
var numberOfSuccessfulWounds = 0
var numberOfFailedSaves = 0
var dicePerUnit = 0
//var units = ["Captain","Captain on Bike","imperial Space Marine", "Space Marine"];

//makes the current page's equivalent part on the navBar active (different color)
function activate() {
    var homeNav = document.getElementById("damageCalculatorNav");
    homeNav.classList.add("active");
    //var myRelay = document.getElementById("myRelay");
    //myRelay.innerHTML = "activated";
}

//onStart is the function that will kick off populating the unit dropdowns.  Needs to call DB through HTML request
function onStart(){
    HTMLRequest("User","getUnits","attackName","weaponName","rolls","defendingName");
}

//populates the attack and defending dropdown with the names of the units
// @param passIn, a comma separated string of all of the units.  Comes from HTML request
function populateUnits(passIn){
    var units = passIn.split(",")
    var unit1Drop = document.getElementById("Unit1");
    var unit2Drop = document.getElementById("Unit2");
    var i = 0
    var length = unit1Drop.options.length
    while(length>1){ //removes all , but the --please ... -- option
        length = length-1
        unit1Drop.remove(length);
    }
    var length = unit2Drop.options.length
    while(length>1){ //removes all , but the --please ... -- option
        length = length-1
        unit2Drop.remove(length);
    }
    while(i<units.length-1){ //adds all of the unit names to the dropdown
        var op1 = document.createElement("option");
        var op2 = document.createElement("option");
        op1.value = units[i]
        op2.value = units[i]
        op1.innerHTML = units[i]
        op2.innerHTML = units[i]
        unit1Drop.appendChild(op1)
        unit2Drop.appendChild(op2)
        i = i+1
    }
}

//populates the weapon dropdown with the names of the weapons given a unit
// @param passIn, a comma separated string of all of the weapons given a unit.  Comes from HTML request
function populateWeapons(passIn){
    var weapons = passIn.split(",")
    var weaponDropdown = document.getElementById("Weapon1");
    var i = 0
    var length = weaponDropdown.options.length
    while(length>1){ //removes all , but the --please ... -- option
        length = length-1
        weaponDropdown.remove(length);
    }
    while(i<weapons.length-1){ //adds all of the unit names to the dropdown
        var opt = document.createElement("option");
        opt.value = weapons[i]
        opt.innerHTML = weapons[i]
        weaponDropdown.appendChild(opt)
        i = i+1
    }
    if(weapons.length==2){
        weaponDropdown.value=weapons[0]
    }
    valid();
}

//when attacking unit is changed, will repopulate the weapon dropdown to the correct values
function unit1Change(){
    var unit1Drop = document.getElementById("Unit1");
    
    var weaponDrop = document.getElementById("weaponDiv");
    if(unit1Drop.value!="first"){
        HTMLRequest("User","getWeaponBasedOnUnit",unit1Drop.value,"weaponName","rolls","defendingName");
        weaponDrop.style.display="block";
    }
    else{
        weaponDrop.style.display="none";
    }
    valid();
}

//checks if the user has added all of the base components
function valid(){
    var unit1 = document.getElementById("Unit1");
    var weapon1 = document.getElementById("Weapon1");
    var unit2 = document.getElementById("Unit2");
    var attackNumber = document.getElementById("numberOfUnits");
    var hitButton = document.getElementById("hitRollButton");
    var hitPhase = document.getElementById("hitPhase");
    var woundPhase = document.getElementById("woundPhase");
    var damagePhase = document.getElementById("damagePhase");
    var endScreen = document.getElementById("endScreen");
    var typeOfCalc = getTypeOfCalc();
    //always hide this if a change occurs, which is always when this is called
    hitPhase.style.display='none'
    woundPhase.style.display="none"
    damagePhase.style.display="none"
    endScreen.style.display="none"
    if(unit1.value!="first"  && weapon1.value!="first" && unit2.value!="first" && attackNumber.value!=null && attackNumber.value!="" && typeOfCalc!="Invalid"){
        hitButton.style.display='block' //show
    }
    else{
        hitButton.style.display='none' //hide
    }
}

//checks if the die is a valid number of units 1+ int
function validInt(){
    var attackNumber = document.getElementById("numberOfUnits");
    if((!Number.isInteger(attackNumber.valueAsNumber))||attackNumber.valueAsNumber<.5){ //integer 1 or more
        attackNumber.value = "";
    }
    valid()
}

//checks if the die is a valid int 1-6 inclusive
// @param dieID, the id of the die I need to check
function checkValidDie(dieID){
    var dieToCheck = document.getElementById(dieID);
    try{
        if((!Number.isInteger(dieToCheck.valueAsNumber))||dieToCheck.valueAsNumber<.5||dieToCheck.valueAsNumber>6.5){ //integer 1 thru 6
            dieToCheck.value = "";
        }
    }
    catch{
        dieToCheck.value="";
    }
    checkValidField(dieID);
}

//checks to see if all of the dice are filled to show the next box
// @param dieID, the id of the dice field I need to check
function checkValidField(dieID){
    //ex diceRollWound
    //   diceRollHit
    var getChar = dieID.charAt(8);
    var diceRollArea = document.getElementById("numberOfHits");
    var valid = 0;
    var nextShowCalcButton = document.getElementById("numberOfHits");
    if (getChar=="W"){
        //wound phase check
        diceRollArea = document.getElementById("diceRollAreaWound");
        nextShowCalcButton = document.getElementById("calcWounds");
        valid = 1;
    }
    else if (getChar=="H"){
        //hit phase check
        diceRollArea = document.getElementById("diceRollAreaHit");
        nextShowCalcButton = document.getElementById("calcHits");
        valid = 1
    }
    else if (getChar=="D"){
        diceRollArea = document.getElementById("diceRollAreaDamage");
        nextShowCalcButton = document.getElementById("calcDamage");
        valid = 1
    }
   else {
    //invalid, show an error
        valid = 0
   }
   if(valid==1){
        var currentDie = diceRollArea.childElementCount;
        var allValid = 1;
        while(currentDie>0){
            currentDie = currentDie-1
            var dieToCheck = diceRollArea.children[currentDie];
            if((!Number.isInteger(dieToCheck.valueAsNumber))||dieToCheck.valueAsNumber<.5||dieToCheck.valueAsNumber>6.5){ //integer 1 thru 6
                allValid = 0;
                currentDie = -1
            }
        }
        if(allValid==1){
            nextShowCalcButton.style.display="block"
        }
        else{
            nextShowCalcButton.style.display="none"
        }
   }
   else {
    diceRollArea.innerHTML = "Invalid die, dieID=" + dieID + " , getChar=" + getChar;
   }
}

//resets the dice if between the toNextPhase button and calculation is performed
// @param phase, which dice phase I need to reset
function resetDice(phase){
    if(phase=="hitRoll"){
        numberOfDice = dicePerUnit*document.getElementById("numberOfUnits").value;
        var dieNumber = 1;
        while(dieNumber<=numberOfDice){
            var die = document.getElementById("diceRollHit"+dieNumber); //grabs the die and set the value
            die.disabled = false;
            die.classList = [];
            die.style.backgroundColor = "white"
            dieNumber = dieNumber+1
        }
    }
    else if(phase=="woundRoll"){
        numberOfDice = numberOfSuccessfulHits;
        var dieNumber = 1;
        while(dieNumber<=numberOfDice){
            var die = document.getElementById("diceRollWound"+dieNumber); //grabs the die and set the value
            die.disabled = false;
            die.classList = [];
            die.style.backgroundColor = "white"
            dieNumber = dieNumber+1
        }
    }
    else if(phase=="damageRoll"){
        numberOfDice = numberOfSuccessfulWounds;
        var dieNumber = 1;
        while(dieNumber<=numberOfDice){
            var die = document.getElementById("diceRollDamage"+dieNumber); //grabs the die and set the value
            die.disabled = false;
            die.classList = [];
            die.style.backgroundColor = "white"
            dieNumber = dieNumber+1
        }
    }
}

//disables the dice between the calculation and the toNextPhase button
// @param phase, which dice phase I need to freeze
function freezeDice(phase){
    if(phase=="hitRoll"){
        numberOfDice = dicePerUnit*document.getElementById("numberOfUnits").value;
        var dieNumber = 1;
        while(dieNumber<=numberOfDice){
            var die = document.getElementById("diceRollHit"+dieNumber); //grabs the die and set the value
            die.disabled = true;
            dieNumber = dieNumber+1
        }
    }
    else if(phase=="woundRoll"){
        numberOfDice = numberOfSuccessfulHits;
        var dieNumber = 1;
        while(dieNumber<=numberOfDice){
            var die = document.getElementById("diceRollWound"+dieNumber); //grabs the die and set the value
            die.disabled = true;
            dieNumber = dieNumber+1
        }
    }
    else if(phase=="damageRoll"){
        numberOfDice = numberOfSuccessfulWounds;
        var dieNumber = 1;
        while(dieNumber<=numberOfDice){
            var die = document.getElementById("diceRollDamage"+dieNumber); //grabs the die and set the value
            die.disabled = true;
            dieNumber = dieNumber+1
        }
    }
}

//transitions to the next sequence.  Will populate the hitPhase dice after calculating how many dice are needed to be rolled
function toHitPhase(){
    var attackName = document.getElementById("Unit1").value;
    var defendingName = document.getElementById("Unit2").value;
    var rolls = document.getElementById("numberOfUnits").value;
    var weaponName = document.getElementById("Weapon1").value;
    var miniPhase = "dicePerUnit";
    var typeOfCalc = getTypeOfCalc();
    var woundPhase = document.getElementById("woundPhase");
    var damagePhase = document.getElementById("damagePhase");
    var endScreen = document.getElementById("endScreen");
    //always hide this if a change occurs, which is always when this is called
    woundPhase.style.display="none"
    damagePhase.style.display="none"
    endScreen.style.display="none"
    var numberOfHitsShowcase = document.getElementById("numberOfHitsShowcase")
    numberOfHitsShowcase.innerHTML = "The number of Hits: "
    var calcHits = document.getElementById("calcHits")
    calcHits.style.display="block"
    var toWoundPhase = document.getElementById("toWoundPhase")
    toWoundPhase.style.display="none"
    //make a call to get the numberOfDicePerUnit, based on weapon
    HTMLRequest(typeOfCalc,miniPhase,attackName,weaponName,rolls,defendingName);
    checkValidField("diceRollHit1")
}

//this function adds the correct number of dice to the hit phase
// @param diceperunit, comes from HTML request, number of dice one unit can throw
function hitPhaseRolls(diceperunit){
    var unit1 = document.getElementById("Unit1");
    var unit2 = document.getElementById("Unit2");
    var attackNumber = document.getElementById("numberOfUnits");
    var hitPhase = document.getElementById("hitPhase");
    dicePerUnit = diceperunit;//assign the global variable the number
    //one last check for value input
    if(unit1.value!="first" && unit2.value!="first" && attackNumber.value!=null){
        hitPhase.style.display='block'
        var diceRollArea = document.getElementById("diceRollAreaHit");
        numberOfDice = attackNumber.value*dicePerUnit
        currentDie = diceRollArea.childElementCount

        //removes any extra dice
        while(currentDie>numberOfDice){
            currentDie = currentDie-1
            diceRollArea.removeChild(diceRollArea.children[currentDie])
        }
        //want to just remove dice if needed
        //diceRollArea.innerHTML="";    

        //creates the new dice
        while(currentDie<numberOfDice){
            currentDie=currentDie+1
            var die = document.createElement("input");
            die.type = "number"
            die.id = "diceRollHit"+currentDie
            die.placeholder = "Dice Roll "+currentDie
            //styling
            if(currentDie==1){
                die.style="margin-right: 20px; margin-left: 20px"
            }
            else{
            die.style="margin-right: 20px"
            }
            die.min=1
            die.max=6
            var onChangeEvent = "checkValidDie('"+die.id+"')";
            //add before setting attribute for timing purposes.  Else error will occur
            diceRollArea.append(die);
            //to confirm valid input
            die.setAttribute("onchange",onChangeEvent);            
        }
    }
    else{
        hitPhase.style.display='none'
    }
}

//grabs the die and set the value, used for the random case
// @param diceRolls, comes from HTML request, the randomized dice rolls

function setHitPhaseRolls(diceRolls){
    var rolls = diceRolls.split(",");
    var dieNumber = 1
    while(dieNumber<rolls.length){
        var die = document.getElementById("diceRollHit"+dieNumber); //grabs the die and set the value
        die.value = rolls[dieNumber-1]
        die.disabled = true;
        dieNumber = dieNumber+1
    }
}

//assumes all dice have proper input, then perform the getHit Calc
function calcHits(){
    var attackName = document.getElementById("Unit1").value;
    var defendingName = document.getElementById("Unit2").value;
    var rollNumber = document.getElementById("numberOfUnits").value*dicePerUnit;
    var rolls = [];
    var dieNumber = 1;
    while(dieNumber<=rollNumber){
        var getDie = document.getElementById("diceRollHit"+dieNumber);
        var dieValue = getDie.valueAsNumber;
        rolls.push(dieValue);
        dieNumber = dieNumber+1;
    }
    var weaponName = document.getElementById("Weapon1").value;
    var miniPhase = "hitRoll";
    var typeOfCalc = "User";
    freezeDice("hitRoll");
    HTMLRequest(typeOfCalc,miniPhase,attackName,weaponName,rolls,defendingName);
}

//sets the function for the toWound button onclick
function toWoundPhase(){
    var attackName = document.getElementById("Unit1").value;
    var defendingName = document.getElementById("Unit2").value;
    var weaponName = document.getElementById("Weapon1").value;
    var typeOfCalc = getTypeOfCalc();
    var damagePhase = document.getElementById("damagePhase");
    var endScreen = document.getElementById("endScreen");
    //always hide this if a change occurs, which is always when this is called
    damagePhase.style.display="none"
    endScreen.style.display="none"
    woundPhaseRolls() //creates the dice for the woundPhase
    var numberOfWoundsShowcase = document.getElementById("numberOfWoundsShowcase")
    numberOfWoundsShowcase.innerHTML = "The number of Wounds: "
    //default the calcWounds to show and the damagePhase to hide
    var calcWounds = document.getElementById("calcWounds")
    calcWounds.style.display="block"
    var toDamagePhase = document.getElementById("toDamagePhase")
    toDamagePhase.style.display="none"
    resetDice("woundRoll");
    //if random, then run the calculation
    if(typeOfCalc=="Random"){
    var miniPhase = "woundRoll";
    HTMLRequest(typeOfCalc,miniPhase,attackName,weaponName,numberOfSuccessfulHits,defendingName);
    }
    checkValidField("diceRollWound1")
}

//this function adds the correct number of dice to the wound phase
function woundPhaseRolls(){
    var unit1 = document.getElementById("Unit1");
    var unit2 = document.getElementById("Unit2");
    var attackNumber = document.getElementById("numberOfUnits");
    var woundPhase = document.getElementById("woundPhase");
    //one last check for value input
    if(unit1.value!="first" && unit2.value!="first" && attackNumber.value!=null){
        woundPhase.style.display='block'
        var diceRollArea = document.getElementById("diceRollAreaWound");
        //TODO: need an if 0 case
        numberOfDice = numberOfSuccessfulHits
        currentDie = diceRollArea.childElementCount

        //removes any extra dice
        while(currentDie>numberOfDice){
            currentDie = currentDie-1
            diceRollArea.removeChild(diceRollArea.children[currentDie])
        }
        //creates the new dice
        while(currentDie<numberOfDice){
            currentDie=currentDie+1
            var die = document.createElement("input");
            die.type = "number"
            die.id = "diceRollWound"+currentDie
            die.placeholder = "Dice Roll "+currentDie
            //styling
            if(currentDie==1){
                die.style="margin-right: 20px; margin-left: 20px"
            }
            else{
            die.style="margin-right: 20px"
            }
            die.min=1
            die.max=6
            var onChangeEvent = "checkValidDie('"+die.id+"')";
            //add before setting attribute for timing purposes.  Else error will occur
            diceRollArea.append(die);
            die.setAttribute("onchange",onChangeEvent);            
        }
    }
    else{
        hitPhase.style.display='none'
    }
}

//sets the wound phase rolls, random only
// @param diceRolls, comes from HTML request, the randomized dice rolls

function setWoundPhaseRolls(diceRolls){
    var rolls = diceRolls.split(",");
    var dieNumber = 1
    while(dieNumber<rolls.length){
        var die = document.getElementById("diceRollWound"+dieNumber);
        die.value = rolls[dieNumber-1]
        die.disabled = true;
        dieNumber = dieNumber+1
    }
}

// performs the getWound Calculation with the user inputted values
function calcWounds(){
    var attackName = document.getElementById("Unit1").value;
    var defendingName = document.getElementById("Unit2").value;
    var rollNumber = numberOfSuccessfulHits;
    var rolls = [];
    var dieNumber = 1;
    while(dieNumber<=rollNumber){
        var getDie = document.getElementById("diceRollWound"+dieNumber);
        var dieValue = getDie.valueAsNumber;
        rolls.push(dieValue);
        dieNumber = dieNumber+1;
    }
    var weaponName = document.getElementById("Weapon1").value;
    var miniPhase = "woundRoll";
    var typeOfCalc = "User";
    freezeDice("woundRoll");
    HTMLRequest(typeOfCalc,miniPhase,attackName,weaponName,rolls,defendingName);

}

//from andy below

//triggers the damage sequence.  Will call the function to populate the dice for the damage section and if random will perform the simulation
function toDamagePhase(){
    var attackName = document.getElementById("Unit1").value;
    var defendingName = document.getElementById("Unit2").value;
    var weaponName = document.getElementById("Weapon1").value;
    var typeOfCalc = getTypeOfCalc();
    var endScreen = document.getElementById("endScreen");
    //always hide this if a change occurs, which is always when this is called
    endScreen.style.display="none"
    damagePhaseRolls() //creates the dice for the woundPhase
    var numberOfDamageShowcase = document.getElementById("numberOfDamageShowcase")
    numberOfDamageShowcase.innerHTML = "The number of Damage Hits: "
    //default the calcWounds to show and the damagePhase to hide
    resetDice("damageRoll");
    var calcDamage = document.getElementById("calcDamage")
    calcDamage.style.display="block"
    var toEndScreen = document.getElementById("toEndScreen")
    toEndScreen.style.display="none"
    //if random, then run the calculation
    if(typeOfCalc=="Random"){
    var miniPhase = "damageRoll";
    HTMLRequest(typeOfCalc,miniPhase,attackName,weaponName,numberOfSuccessfulWounds,defendingName);
    }
    checkValidField("diceRollDamage1")
}

//creates the damage phase dice and the properties that go with them
function damagePhaseRolls(){
    var unit1 = document.getElementById("Unit1");
    var unit2 = document.getElementById("Unit2");
    var attackNumber = document.getElementById("numberOfUnits");
    var damagePhase = document.getElementById("damagePhase");
    //one last check for value input
    if(unit1.value!="first" && unit2.value!="first" && attackNumber.value!=null){
        damagePhase.style.display='block'
        var diceRollArea = document.getElementById("diceRollAreaDamage");
        //TODO: need an if 0 case
        numberOfDice = numberOfSuccessfulWounds
        currentDie = diceRollArea.childElementCount

        //removes any extra dice
        while(currentDie>numberOfDice){
            currentDie = currentDie-1
            diceRollArea.removeChild(diceRollArea.children[currentDie])
        }
        //creates the new dice
        while(currentDie<numberOfDice){
            currentDie=currentDie+1
            var die = document.createElement("input");
            die.type = "number"
            die.id = "diceRollDamage"+currentDie
            die.placeholder = "Dice Roll "+currentDie
            //styling
            if(currentDie==1){
                die.style="margin-right: 20px; margin-left: 20px"
            }
            else{
            die.style="margin-right: 20px"
            }
            die.min=1
            die.max=6
            var onChangeEvent = "checkValidDie('"+die.id+"')";
            //add before setting attribute for timing purposes.  Else error will occur
            diceRollArea.append(die);
            die.setAttribute("onchange",onChangeEvent);            
        }
    }
    else{
        damagePhase.style.display='none'
    }
}

// sets the dice with the values given from random into the webpage
// @param diceRolls, comes from HTML request, the randomized dice rolls

function setDamagePhaseRolls(diceRolls){
    var rolls = diceRolls.split(",");
    var dieNumber = 1
    while(dieNumber<rolls.length){
        var die = document.getElementById("diceRollDamage"+dieNumber);
        die.value = rolls[dieNumber-1]
        die.disabled = true;
        dieNumber = dieNumber+1
    }
}

//calculates the amount of damage dealt based on the provided rolls
function calcDamage(){
    var attackName = document.getElementById("Unit1").value;
    var defendingName = document.getElementById("Unit2").value;
    var weaponName = document.getElementById("Weapon1").value;
    var rollNumber = numberOfSuccessfulWounds;
    var rolls = [];
    var dieNumber = 1;
    while(dieNumber<=rollNumber){
        var getDie = document.getElementById("diceRollDamage"+dieNumber);
        var dieValue = getDie.valueAsNumber;
        rolls.push(dieValue);
        dieNumber = dieNumber+1;
    }
    var miniPhase = "damageRoll";
    var typeOfCalc = "User";
    freezeDice("damageRoll");
    HTMLRequest(typeOfCalc,miniPhase,attackName,weaponName,rolls,defendingName);
}

//from andy above
//shows the end value damage and whether or not overkill happened
function toEndScreen(){
    var attackName = document.getElementById("Unit1").value;
    var defendingName = document.getElementById("Unit2").value;
    var weaponName = document.getElementById("Weapon1").value;
    var typeOfCalc = getTypeOfCalc();
    var miniPhase = "finalDamage";
    HTMLRequest(typeOfCalc,miniPhase,attackName,weaponName,numberOfFailedSaves,defendingName);
}

//gets the type of calculation from the radio button
function getTypeOfCalc(){
    var rollA = document.getElementById("RollA").checked; //User radio button option
    var rollB = document.getElementById("RollB").checked; //Random radio button option
    if(rollA){
        return("User");
    }
    else if(rollB){
        return("Random");
    }
    else{
        return("Invalid");
    }
}

/*sends the post request to the damageHelper jsp file
* @param typeOfCalc - User or Random
* @param miniPhase - What type of phase is the damageHelper running (essentially which function to call)
* @param attackName - Attacking Unit Name
* @param weaponName - Weapon Name
* @param rolls - CAN BE either the number of rolls that random will need to generate or the values of the rolls in a string format (1,2,3,4,5)
* @param defendingName - Defending Unit Name
*/
function HTMLRequest(typeOfCalc,miniPhase,attackName,weaponName,rolls,defendingName){
    var http = new XMLHttpRequest();
    http.open("POST","damageHelper.jsp", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "type=" + typeOfCalc +  "&miniPhase=" + miniPhase +  "&attackName=" + attackName + "&rolls=" + rolls + "&weaponName=" + weaponName + "&defendingName=" + defendingName;
    //var numberOfHits = document.getElementById("numberOfHits");
    //actually sends the information
    http.send(params);
    http.onload = function() {
        //alert(http.responseText);
        //will need parsing here once actual information is transferred back :)
        nextStep(http.responseText,typeOfCalc,miniPhase,attackName,weaponName,rolls,defendingName);
    }
}

/*evaluates the return from the damageHelper jsp file and updates the webpage
* @param responseText - the response from the post request above
* @param typeOfCalc - User or Random
* @param miniPhase - What type of phase is the damageHelper running (essentially which function to call)
* @param attackName - Attacking Unit Name
* @param weaponName - Weapon Name
* @param rolls - CAN BE either the number of rolls that random will need to generate or the values of the rolls in a string format (1,2,3,4,5)
* @param defendingName - Defending Unit Name
*/
function nextStep(responseText,typeOfCalc,miniPhase,attackName,weaponName,rolls,defendingName){
    //var numberOfHits = document.getElementById("numberOfHits");
    var hitPhase = document.getElementById("hitPhaseTest");
    var holder = document.getElementById("holder");
    holder.innerHTML = "";
    hitPhase.style.display='block';
    //numberOfHits.innerHTML = responseText; //this is how I show anything on the webpage for debugging, will be removed in end product (?)
    var splitTheResponse = responseText.split("|");
    var errorCondition = splitTheResponse[3];
    if(errorCondition=="true"){
        holder.innerHTML = splitTheResponse[2];
    }
    //rest is valid
    else{
        //set the units in the dropdown
        if(miniPhase=="getUnits"){
            var passIn = splitTheResponse[2]
            populateUnits(passIn)
        }
        //set the weapons in the dropdown
        else if(miniPhase=="getWeaponBasedOnUnit"){
            var passIn = splitTheResponse[2]
            populateWeapons(passIn)
        }
        else if(miniPhase=="dicePerUnit"){ //to get the number of dice per unit that they throw.  Used for the hitRoll initialization
            var dicePerunit = parseInt(splitTheResponse[2]);
            dicePerUnit = dicePerunit
            if(Number.isInteger(dicePerunit)){
                hitPhaseRolls(dicePerunit);
                if(typeOfCalc=="Random"){ //if random, then I do not need to wait till the user inputs, so run hitRoll calculation and populate with random dice
                    //rolls at this point is an int of the number of attackers there are
                    HTMLRequest(typeOfCalc,"hitRoll",attackName,weaponName,(rolls*dicePerunit),defendingName);
                }
                else{ //User
                    checkValidField("rollDiceHit1");
                    resetDice("hitRoll");
                }
            }
            else{
                var holder = document.getElementById("holder");
                holder.innerHTML = "dicePerUnit errored out.  Above might give you some insight.";
            }
        }
        else if(miniPhase=="hitRoll"){ //main hitRoll population
            var calcHitsButton = document.getElementById("calcHits");
            calcHitsButton.style.display="none";
            var toWoundPhase = document.getElementById("toWoundPhase");
            toWoundPhase.style.display="block";
            var splitTheResponse = responseText.split("|"); //dice string is part 2, dice results part 3 ... based on weird jsp return
            //numberOfHits.innerHTML = splitTheResponse[2];
            var rollResults = splitTheResponse[2];
            if(typeOfCalc=="Random"){
            setHitPhaseRolls(splitTheResponse[1]);
            }
            var splitRollResult = rollResults.split(",");
            var rollNumber = 1;
            numberOfSuccessfulHits = 0;//change this number to represent actual number of wounds
            while(rollNumber<splitRollResult.length){
                var getDie = document.getElementById("diceRollHit"+rollNumber);
                getDie.style.color = 'black'
                if(splitRollResult[rollNumber-1]=="true"){
                    numberOfSuccessfulHits = numberOfSuccessfulHits+1;
                    getDie.style.backgroundColor = "green"
                }
                else{
                    getDie.style.backgroundColor = "#FF7000"
                }
                rollNumber=rollNumber+1;
            } 
            var numberOfHitsShowcase = document.getElementById("numberOfHitsShowcase");
            numberOfHitsShowcase.innerHTML = "The number of Hits: "+numberOfSuccessfulHits;
            if(numberOfSuccessfulHits==0){
                toWoundPhase.style.display="none";
                toEndScreen();
            }
        }
        else if(miniPhase=="woundRoll"){ //basically a copy and paste job from hitRoll phase
            var calcWounds = document.getElementById("calcWounds");
            calcWounds.style.display="none";
            var toDamagePhase = document.getElementById("toDamagePhase");
            toDamagePhase.style.display="block";
            var splitTheResponse = responseText.split("|"); //dice string is part 2, dice results part 3
            //numberOfHits.innerHTML = splitTheResponse[2];
            var rollResults = splitTheResponse[2];
            if(typeOfCalc=="Random"){
            setWoundPhaseRolls(splitTheResponse[1]);
            }
            var splitRollResult = rollResults.split(",");
            var rollNumber = 1;
            numberOfSuccessfulWounds = 0;//change this number to represent actual number of wounds
            while(rollNumber<splitRollResult.length){
                var getDie = document.getElementById("diceRollWound"+rollNumber);
                getDie.style.color = 'black';
                if(splitRollResult[rollNumber-1]=="true"){
                    numberOfSuccessfulWounds = numberOfSuccessfulWounds+1;
                    getDie.style.backgroundColor = "green";
                }
                else{
                    getDie.style.backgroundColor = "#FF7000";
                }
                rollNumber=rollNumber+1;
            }        
            var numberOfWoundsShowcase = document.getElementById("numberOfWoundsShowcase");
            numberOfWoundsShowcase.innerHTML = "The number of Wounds: "+numberOfSuccessfulWounds;
            if(numberOfSuccessfulWounds==0){
                toDamagePhase.style.display="none";
                toEndScreen();
            }
        }
        else if(miniPhase=="damageRoll"){
            var calcDamage = document.getElementById("calcDamage");
            calcDamage.style.display="none";
            var tES = document.getElementById("toEndScreen");
            tES.style.display="block";
            var splitTheResponse = responseText.split("|"); //dice string is part 2, dice results part 3
            //numberOfHits.innerHTML = splitTheResponse[2];
            var rollResults = splitTheResponse[2];
            if(typeOfCalc=="Random"){
            setDamagePhaseRolls(splitTheResponse[1]);
            }
            var splitRollResult = rollResults.split(",");
            var rollNumber = 1;
            numberOfFailedSaves = 0;//change this number to represent actual number of fails
            while(rollNumber<splitRollResult.length){
                var getDie = document.getElementById("diceRollDamage"+rollNumber);
                getDie.style.color = 'black'
                if(splitRollResult[rollNumber-1]=="false"){
                    numberOfFailedSaves = numberOfFailedSaves+1;
                    getDie.style.backgroundColor = "green"
                }
                else{
                    getDie.style.backgroundColor = "#FF7000";
                }
                rollNumber=rollNumber+1;
            }         
            var numberOfDamageShowcase = document.getElementById("numberOfDamageShowcase");
            numberOfDamageShowcase.innerHTML = "The number of Damage Hits: "+numberOfFailedSaves;
            //HTMLRequest(typeOfCalc,"finalDamage",attackName,weaponName,numberOfFailedSaves,defendingName);
        }
        else if(miniPhase=="finalDamage"){
            var splitTheResponse = responseText.split("|");
            //numberOfHits.innerHTML = splitTheResponse[1] +"|" + splitTheResponse[2];
            var damageValue = splitTheResponse[1]
            var overkillValue = splitTheResponse[2]
            var finalDamage = document.getElementById("finalDamage");
            var overkillText = document.getElementById("overkill");
            var ES = document.getElementById("endScreen");
            ES.style.display = "block"
            finalDamage.innerHTML = "Final Damage: " + damageValue;
            if(overkillValue=="false"){
                overkillText.style.display="none"
            }
            else{
                overkillText.style.display="block"
            }
        }
    }
}

/*placeholder for future work.  Would of worked with the history page.  Never developed
*/
function saveCalc(){
    //do nothing right now
    //var numberOfHits = document.getElementById("numberOfHits");
    //numberOfHits.innerHTML = "You have clicked the Save Calc Button";
}


//once all of the elements are loaded in, then run the initialize function to prevent unknown element errors
document.addEventListener("DOMContentLoaded",function set(){
    initialize()
})
//initialize()