import java.net.*;
import java.io.*;

public class NyietInSengProtocol {
    private static final int WAITING = 0;
    private static final int ACKNOWLEDGE = 1;
    private static final int HINT = 2;
    private static final int ANOTHER_JOKE = 3;

    private static final int NUMJOKES = 3;

    private int state = WAITING;
    private int currentJoke = 0;

    private String[] clues = 
	{ "Ali", "Di Sini", "Hari"};
    private String[] answers = 
	{ "Ali Baba, Bujang Lapok la..  ",
        "Di Sini lah, Oi Di Sana!  ",
        "Harimau Kuat! Grrrrrr   "};

    public String processInput(String input) {
        String output = null;
        
        if (state == WAITING) {
            output = "NyietInSeng MatekAji Semar Ngiseng!";
            state = ACKNOWLEDGE;
        } else if (state == ACKNOWLEDGE) {
            if (input.equalsIgnoreCase("Siapa tu?")) {
                output = clues[currentJoke];
                state = HINT;
            } else {
                output = "Sepatutnya awak cakap \"Siapa tu?\"! " +
                "Cuba lagi. NyietInSeng MatekAji Semar Ngiseng!";
            }
        } else if (state == HINT) {
            if (input.equalsIgnoreCase(clues[currentJoke] + " mana?")) {
                output = answers[currentJoke] + " Main lagi? (y/n)";
                state = ANOTHER_JOKE;
            } else {
                output = "Sepatutnya awak cakap \"" + 
                clues[currentJoke] + 
                " mana?\"" + 
                "! Cuba lagi. NyietInSeng MatekAji Semar Ngiseng!";
                state = ACKNOWLEDGE;
            }
        } else if (state == ANOTHER_JOKE) {
            if (input.equalsIgnoreCase("y")) {
                output = "NyietInSeng MatekAji Semar Ngiseng!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                state = ACKNOWLEDGE;
            } else {
                output = "tata titi tutu";
                state = WAITING;
            }
        }
        return output;
    }
}