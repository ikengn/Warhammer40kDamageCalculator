import java.util.ArrayList;

/**
 * Parser for the DamageCalculator
 */
public class Parser {

    /**
     *  Parse a list of string attributes into an unit object
     *
     * @param queryResult list of string attributes from db
     * @return an unit object with specified attributes
     * @throws NumberFormatException if the attribute can't be parsed as a number
     */
    public static Unit unitParser(ArrayList<String> queryResult) throws NumberFormatException {
        Unit temp = null;
        try {
            temp = new Unit(
                queryResult.get(0),                                // String name
                parseMovement(queryResult.get(1)),                 // int movement
                parsePlus(queryResult.get(2)),                     // int weaponSkill
                parsePlus(queryResult.get(3)),                     // int ballisticSkill
                Integer.parseInt(queryResult.get(4)),              // int strength
                Integer.parseInt(queryResult.get(5)),              // int toughness
                Integer.parseInt(queryResult.get(6)),              // int wounds
                Integer.parseInt(queryResult.get(7)),              // int attacks
                Integer.parseInt(queryResult.get(8)),              // int leaderShip
                parsePlus(queryResult.get(9))                      // int save
                );
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * Private helper, parse Movement statistics from a string
     *
     * @return parsed -- the parsed integer
     */
    private static int parseMovement(String rawM){
        assert rawM != null;
        rawM = rawM.replaceAll("\"", "");
        return Integer.parseInt(rawM);
    }

    /**
     * Private helper, parse statistics with plus (+) sign from a string
     *
     * @return parsed -- the parsed integer
     */
    private static Integer parsePlus(String raw){
        assert raw != null;
        raw = raw.replaceAll("\\+", "");
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse a list of string attributes into a weapon object
     *
     * @param queryResult list of string attributes
     * @return a weapon object with specified attributes
     * @throws NumberFormatException if the attribute can't be parsed as a number
     */
    public static Weapon weaponParser(ArrayList<String> queryResult) throws NumberFormatException{
        Weapon temp = null;
        try {
            temp = new Weapon(
                    queryResult.get(0),                                 // Name
                    parseRange(queryResult.get(1)),                     // Range
                    parseType(queryResult.get(2)),                      // Type
                    parseHit(queryResult.get(2)),                       // Number of hits for non-melee weapons
                    parseModifier(queryResult.get(3)),                  // Strength Modifier
                    parseModifiedValue(queryResult.get(3)),             // Strength Value
                    Integer.parseInt(queryResult.get(4)),               // Armor Penetration Value
                    Integer.parseInt(queryResult.get(5)),               // Damage
                    queryResult.get(6)                                  // Ability
            );
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * Private helper, parse range stats from a string
     *
     * @param rawR raw string of range
     * @return parsed range as integer
     */
    private static int parseRange(String rawR) {
        if (rawR.equals("Melee")) {
            return 0;
        } else {
            rawR = rawR.replaceAll("\"", "");
            return Integer.parseInt(rawR);
        }
    }

    /**
     * Private helper, parse Type of the weapon from a string
     *
     * @param rawT raw string of type
     * @return parsed type as Type
     */
    private static Type parseType(String rawT) {
        rawT = rawT.replaceAll(" ", "_");
        return Type.valueOf(rawT);
    }

    /**
     * Private helper, parse number of hit for a weapon
     * @param rawT raw string of type
     * @return parsed number of hit as integer
     */
    private static int parseHit(String rawT) {
        if (rawT.equals("Melee")) {
            return 1;
        }
        return Integer.parseInt(String.valueOf(rawT.charAt(rawT.length() - 1)));
    }

    /**
     * Private helper, parse the modifier stats
     *
     * @param toParse raw strength/armorPenetration String from database
     * @return parsed modifier from strength/armorPenetration stats
     */
    private static Modifier parseModifier(String toParse) {
        if (toParse.equals("User")) {
            return Modifier.valueOf("user");
        } else if (toParse.charAt(0) == '-') {
            return Modifier.valueOf("minus");
        } else if (toParse.charAt(0) == 'x' || toParse.charAt(0) == '*') {
            return Modifier.valueOf("mul");
        } else if (toParse.charAt(0) == '/') {
            return Modifier.valueOf("div");
        } else if (toParse.charAt(0) == '+') {
            return Modifier.valueOf("plus");
        } else {
            return Modifier.valueOf("noModifier");
        }
    }

    /**
     * Private helper, parse the AP stats
     *
     * @param rawStat raw armor penetration of weapon in String
     * @return parsed AP as integer
     */
    private static int parseModifiedValue(String rawStat) {
        if (rawStat.length() == 1) {
            try {
                return Integer.parseInt(rawStat);
            } catch (Exception e) {
                return 0;
            }
        }
        return Integer.parseInt(rawStat.substring(1));
    }

    /**
     * Testing -- unit-type tests parsing for parseModifier, parseType, parseRange
     *
     * @param args
     */
    public static void main(String[] args) {
        // Tests for parsing modifiers
        //User
        Modifier expectedMod = Modifier.valueOf("user");
        Modifier resultMod = parseModifier("User");
        if(!expectedMod.equals(resultMod)){
            System.out.println("expected: " + expectedMod + "\nresult: " + resultMod);
        }
        //minus
        expectedMod = Modifier.valueOf("minus");
        resultMod = parseModifier("-7");
        if(!expectedMod.equals(resultMod)){
            System.out.println("expected: " + expectedMod + "\nresult: " + resultMod);
        }
        
        //plus
        expectedMod = Modifier.valueOf("plus");
        resultMod = parseModifier("+7");
        if(!expectedMod.equals(resultMod)){
            System.out.println("expected: " + expectedMod + "\nresult: " + resultMod);
        }

        //mul
        expectedMod = Modifier.valueOf("mul");
        resultMod = parseModifier("*7");
        if(!expectedMod.equals(resultMod)){
            System.out.println("expected: " + expectedMod + "\nresult: " + resultMod);
        }

        expectedMod = Modifier.valueOf("mul");
        resultMod = parseModifier("x7");
        if(!expectedMod.equals(resultMod)){
            System.out.println("expected: " + expectedMod + "\nresult: " + resultMod);
        }

        //div
        expectedMod = Modifier.valueOf("div");
        resultMod = parseModifier("/7");
        if(!expectedMod.equals(resultMod)){
            System.out.println("expected: " + expectedMod + "\nresult: " + resultMod);
        }

        //plus
        expectedMod = Modifier.valueOf("plus");
        resultMod = parseModifier("+7");
        if(!expectedMod.equals(resultMod)){
            System.out.println("expected: " + expectedMod + "\nresult: " + resultMod);
        }

        //no modifier
        expectedMod = Modifier.valueOf("noModifier");
        resultMod = parseModifier("7");
        if(!expectedMod.equals(resultMod)){
            System.out.println("expected: " + expectedMod + "\nresult: " + resultMod);
        }

        // parseRange - quotes at end
        int expectedInt = 5;
        int resultInt = parseRange("5\"");
        if(expectedInt!=resultInt){
            System.out.println("expected: " + expectedInt + "\nresult: " + resultInt);
        }

        // parseRange - quotes at start
        expectedInt = 5;
        resultInt = parseRange("\"5");
        if(expectedInt!=resultInt){
            System.out.println("expected: " + expectedInt + "\nresult: " + resultInt);
        }

        // parseRange - quotes in middle
        expectedInt = 55;
        resultInt = parseRange("5\"5");
        if(expectedInt!=resultInt){
            System.out.println("expected: " + expectedInt + "\nresult: " + resultInt);
        }

        // parseRange - two digit, quotes at end
        expectedInt = 75;
        resultInt = parseRange("75\"");
        if(expectedInt!=resultInt){
            System.out.println("expected: " + expectedInt + "\nresult: " + resultInt);
        }

        // parseRange - two digit, quotes at start
        expectedInt = 75;
        resultInt = parseRange("\"75");
        if(expectedInt!=resultInt){
            System.out.println("expected: " + expectedInt + "\nresult: " + resultInt);
        }

        // parseType
        Type expectedType = Type.valueOf("Rapid_Fire_1");
        Type actualType = parseType("Rapid Fire 1");
        if(!expectedType.equals(actualType)){
            System.out.println("expected: " + expectedType + "\nresult: " + actualType);
        }

        System.out.println(("All tests complete"));
    }
}
