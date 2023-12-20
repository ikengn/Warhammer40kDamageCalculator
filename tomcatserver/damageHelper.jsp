<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import = "backend.*" import = "DB.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="java.util.zip.DataFormatException"%>
<%
//Below are the passed in values from diceSetter
String typeOfCalc = request.getParameter("type");
String miniPhase = request.getParameter("miniPhase");
String attackName = request.getParameter("attackName");
String weapon = request.getParameter("weaponName");
String rollsString = request.getParameter("rolls"); //could be an int of number of rolls or a string of the rolls
//creates instance of Williams Webhelper
Webhelper webHelp = new Webhelper();
//getUnitName
Boolean error = false;
//gets the names of the units
if(miniPhase.equals("getUnits")){
	ArrayList<String> unitNames = webHelp.getAllUnitslist();
    if (unitNames.remove(0).equals("false")){
        out.println("||error from getAllUnitslist: "+webHelp.getErrorMessage()+"|true|");
	}
    else{
	String outputUnitNameString = "||";
    for (String s : unitNames){
    outputUnitNameString += s + ",";
    }	
	out.println(outputUnitNameString+"|false|"); //how to return 
    }
}
//gets the names of the weapons based on the unit
else if(miniPhase.equals("getWeaponBasedOnUnit")){
	ArrayList<String> weaponNames = webHelp.getUnitWeapon(attackName);
    if (weaponNames.remove(0).equals("false")){
        out.println("||error from getUnitWeapon: "+webHelp.getErrorMessage()+"|true|");
	}
    else{
	String outputWeaponNamesString = "||";
		for (String s : weaponNames){
			outputWeaponNamesString += s + ",";
		}
	out.println(outputWeaponNamesString+"|false|"); //how to return results
    }
}
//dicePerUnit is the number of dice that each unit gets to roll for damage
else if(miniPhase.equals("dicePerUnit")){
	ArrayList<Integer> diceNumber = webHelp.getDiceOfWeapon(attackName, weapon);
	if (diceNumber.remove(0)==-1){
        out.println("||error from getDiceOfWeapon: "+webHelp.getErrorMessage()+"|true|");
	}
    else{
        String outputDiceString = "||";
        for (Integer s : diceNumber){
            outputDiceString += Integer.toString(s) + ",";
        }
        out.println(outputDiceString+"|false|");
    }
	 //how to return results
}
else if(miniPhase.equals("hitRoll")){ //the main hitRoll phase
	if(typeOfCalc.equals("Random")){ //if random, call to get dice rolls and call to get the results of the rolls
		int numberOfRolls = Integer.parseInt(rollsString); //string to int
		ArrayList<Integer> outputDice = webHelp.getRollDice(numberOfRolls); //calls backend to randomize dice via webhelper
		if (outputDice.remove(0)==-1){
            out.println("||error from hitRoll getRollDice: "+webHelp.getErrorMessage()+"|true|");
        }
        else{
            ArrayList<Boolean> outputResult = webHelp.getHitRollResult(attackName,weapon,outputDice); //calls backend to get results based on the randomized dice
            if (!outputResult.remove(0)){
                out.println("||error from Random getHitRollResult: "+webHelp.getErrorMessage()+"|true|");
            }
            else{
            //below is the conversion from ArrayList<Integer> and ArrayList<Boolean> to string as only jsp can return string
            String outputDiceString = "";
            for (Integer s : outputDice){
                outputDiceString += Integer.toString(s) + ",";
            }
            String outputResultString = "";
            for (Boolean s : outputResult){
                outputResultString += String.valueOf(s) + ",";
            }
            out.println("|"+outputDiceString+"|"+outputResultString+"|false|");
            }
        }
        
	}
	else if(typeOfCalc.equals("User")){ //if user, then only call results of the rolls based on the dice rolls
		//below is a big conversion phase to convert string into ArrayList<Integer>
		String[] rollsStringList = rollsString.split(",");
		int[] intArray = new int[rollsStringList.length];
		for(int i = 0; i < rollsStringList.length; i++) {
			intArray[i] = Integer.parseInt(rollsStringList[i]);
		}
		//need to convert int[] to ArrayList<Integer>
		ArrayList<Integer> rollsList = new ArrayList<Integer>(intArray.length);
		for (int i : intArray){
			rollsList.add(i);
		}

		ArrayList<Boolean> outputResult = webHelp.getHitRollResult(attackName, weapon,rollsList);//calls backend to get results based on the randomized dice via webhelper
		if (!outputResult.remove(0)){
            out.println("||error from User getHitRollResult: "+webHelp.getErrorMessage()+"|true|");
        }
        else{
            String outputResultString = "";
            for (Boolean s : outputResult){
                outputResultString += String.valueOf(s) + ",";
            }
            out.println("||"+outputResultString+"|false|");
        }
	}
}
//all future elements need the defending unit, for minor speed boost
else{
	String defendingName = request.getParameter("defendingName");
	//rest is essentially copy and paster job from hit roll except for wound and adding defending unit
	if(miniPhase.equals("woundRoll")){
		if(typeOfCalc.equals("Random")){
			int numberOfRolls = Integer.parseInt(rollsString);
			ArrayList<Integer> outputDice = webHelp.getRollDice(numberOfRolls);
            if (outputDice.remove(0)==-1){
                out.println("||error from woundRoll getRollDice: "+webHelp.getErrorMessage()+"|true|");
            }
            else{
                ArrayList<Boolean> outputResult = webHelp.getWoundRollResult(attackName,weapon, defendingName,outputDice);
                if (!outputResult.remove(0)){
                    out.println("||error from Random getWoundRollResult: "+webHelp.getErrorMessage()+"|true|");
                }
                else{
                    String outputDiceString = "";
                    for (Integer s : outputDice){
                        outputDiceString += Integer.toString(s) + ",";
                    }
                    String outputResultString = "";
                    for (Boolean s : outputResult){
                        outputResultString += String.valueOf(s) + ",";
                    }
                    out.println("|"+outputDiceString+"|"+outputResultString+"|false|");
                }
            }
		}
		else if(typeOfCalc.equals("User")){
			String[] rollsStringList = rollsString.split(",");
            int[] intArray = new int[rollsStringList.length];
            for(int i = 0; i < rollsStringList.length; i++) {
                intArray[i] = Integer.parseInt(rollsStringList[i]);
            }
            //need to convert int[] to ArrayList<Integer>
            ArrayList<Integer> rollsList = new ArrayList<Integer>(intArray.length);
            for (int i : intArray){
                rollsList.add(i);
            }
            ArrayList<Boolean> outputResult = webHelp.getWoundRollResult(attackName,weapon, defendingName,rollsList);
            if (!outputResult.remove(0)){
                    out.println("||error from User getWoundRollResult: "+webHelp.getErrorMessage()+"|true|");
                }
            else{
                String outputResultString = "";
                for (Boolean s : outputResult){
                    outputResultString += String.valueOf(s) + ",";
                }
                out.println("||"+outputResultString+"|false|");
                }
		}
	}

	else if(miniPhase.equals("damageRoll")){
		if(typeOfCalc.equals("Random")){ //if random, call to get dice rolls and call to get the results of the rolls
            int numberOfRolls = Integer.parseInt(rollsString); //string to int
            ArrayList<Integer> outputDice = webHelp.getRollDice(numberOfRolls); //calls backend to randomize dice via webhelper
            if (outputDice.remove(0)==-1){
                out.println("||error from damage getRollDice: "+webHelp.getErrorMessage()+"|true|");
            }
            else{
                ArrayList<Boolean> outputResult = webHelp.getSavingRollResult(attackName,weapon, defendingName,outputDice); //calls backend to get results based on the randomized dice
                if (!outputResult.remove(0)){
                        out.println("||error from Random getSavingRollResult: "+webHelp.getErrorMessage()+"|true|");
                    }
                else{
                    //below is the conversion from ArrayList<Integer> and ArrayList<Boolean> to string as only jsp can return string
                    String outputDiceString = "";
                    for (Integer s : outputDice){
                        outputDiceString += Integer.toString(s) + ",";
                    }
                    String outputResultString = "";
                    for (Boolean s : outputResult){
                        outputResultString += String.valueOf(s) + ",";
                    }
                    out.println("|"+outputDiceString+"|"+outputResultString+"|false|");
                    }
                }
		}
		if(typeOfCalc.equals("User")){ //if user, then only call results of the rolls based on the dice rolls
            //below is a big conversion phase to convert string into ArrayList<Integer>
            String[] rollsStringList = rollsString.split(",");
            int[] intArray = new int[rollsStringList.length];
            for(int i = 0; i < rollsStringList.length; i++) {
                intArray[i] = Integer.parseInt(rollsStringList[i]);
            }
            //need to convert int[] to ArrayList<Integer>
            ArrayList<Integer> rollsList = new ArrayList<Integer>(intArray.length);
            for (int i : intArray){
                rollsList.add(i);
            }

            ArrayList<Boolean> outputResult = webHelp.getSavingRollResult(attackName,weapon, defendingName,rollsList); //calls backend to get results based on the randomized dice
            if (!outputResult.remove(0)){
                    out.println("||error from User getSavingRollResult: "+webHelp.getErrorMessage()+"|true|");
                }
            else{
                String outputResultString = "";
                for (Boolean s : outputResult){
                    outputResultString += String.valueOf(s) + ",";
                }
                out.println("||"+outputResultString+"|false|");
                }
        }
	}
	else if(miniPhase.equals("finalDamage")){ //handles final damage and overkill
        int numberOfFails = Integer.parseInt(rollsString);
        ArrayList<Integer> finalDamage = webHelp.getDamage(attackName, weapon, defendingName, numberOfFails);
        if (finalDamage.remove(0)==-1){
            out.println("||error from getDamage: "+webHelp.getErrorMessage()+"|true|");
        }
        else{
            String finalDamageString = Integer.toString(finalDamage.remove(0));
            ArrayList<Boolean> overkill = webHelp.getIfOverKilled(attackName, weapon, defendingName, numberOfFails);
            if (!overkill.remove(0)){
                out.println("||error from getIfOverKilled: "+webHelp.getErrorMessage()+"|true|");
            }
            else{
                String overkillString = String.valueOf(overkill.remove(0));
                out.println("|"+finalDamageString+"|"+overkillString+"|false|");
            }
        }
	}
}

%>
