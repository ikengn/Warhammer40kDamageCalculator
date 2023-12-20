import java.util.Random;
import java.util.ArrayList;

/**
 * Dice class for simulating a dice and methods to return rolls.
 */
public class Dice {

    Random roller;

    /**
     * Empty dice instance constructor
     */
    public Dice() {
        roller = new Random();
    }

    /**
     * Dice instance constructor with given seed
     *
     * @param seed random seed
     */
    public Dice(int seed) {
        roller = new Random(seed);
    }

    /**
     * Roll the dice.
     *
     * @return a number between 1-6 inclusively
     */
    public int rollDice() {
        return roller.nextInt(6) + 1;
    }

    /**
     * Roll the dice for a number of times.
     *
     * @param attempt number of time to roll the dice
     * @return a list of dice rolling result
     */
    public ArrayList<Integer> rollDiceMul(int attempt) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < attempt; i++) {
            int temp = this.rollDice();
            result.add(temp);
        }
        return result;
    }

}
