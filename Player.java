import java.util.*;

public class Player extends GamePlay {

    private int score;

    private ArrayList<String> hand = new ArrayList<>();

    private String name;

    private boolean endGame;


    public void setScore(int points) {
        score += points;
    }

    public void setHand(ArrayList<String> letters) {
        hand = letters;
    }

    public void setName(String userName) {
        name = userName;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<String> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public void setEndGame(boolean exitGame) {
        endGame = exitGame;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public Player(int num) {

            System.out.println();
            System.out.println("Player " + num + " PLEASE ENTER YOUR NAME:  ");
            System.out.println();
            name = getUserInput();
            hand = newHand();

    }

}