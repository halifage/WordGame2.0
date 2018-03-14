import java.io.File;
import java.util.*;

public class GamePlay {

    private static HashMap<String, Integer> letterValues;
    private static ArrayList<String> tiles;
    protected int wordScore;
    private static ArrayList<String> dictionary;
    private static ArrayList<String> alphabet;

    public HashMap<String, Integer> getLetterValues() {
        return letterValues;
    }

    public static ArrayList<String> getTiles() {
        return tiles;
    }

    public ArrayList<String> getLetterBank() {
        return letterBank();
    }

    private void alphabet(){
        for (char x = 65; x <= 90; x++) {
            alphabet.add(Character.toString(x).trim());
        }
    }

    private ArrayList<String> letterBank() {
        // create a letter bank with letters and their corresponding number of tiles

        HashMap<String, Integer> letters = new HashMap<>();
        ArrayList<String> alpha = (ArrayList<String>) alphabet.clone();
        alpha.add("#");
        for (String letter : alphabet) {
            if (Arrays.asList("A", "I").contains(letter)) {
                letters.put(letter, 9);
            } else if (Arrays.asList("N", "R", "T").contains(letter)) {
                letters.put(letter, 6);
            } else if (Arrays.asList("L", "S", "U", "D").contains(letter)) {
                letters.put(letter, 4);
            } else if (Arrays.asList("K", "J", "X", "Q", "Z").contains(letter)) {
                letters.put(letter, 1);
            } else if (Arrays.asList("G").contains(letter)) {
                letters.put(letter, 3);
            } else if (Arrays.asList("O").contains(letter)) {
                letters.put(letter, 8);
            } else if (Arrays.asList("E").contains(letter)) {
                letters.put(letter, 12);
            } else if (Arrays.asList("#").contains(letter)) {
                letters.put(letter, 2);
            } else {
                letters.put(letter, 2);
            }
        }
        for (Object key : letters.keySet()) {
            for (int x = 1; x <= letters.get(key); x++) {
                tiles.add((String) key);
            }
        }
        return tiles;
    }

    private static void letterValues() {
        // create a letter bank with letters and their corresponding values
        ArrayList<String> alphabet = new ArrayList<>();

        for (char x = 65; x <= 90; x++) {
            alphabet.add(Character.toString(x));
        }
        alphabet.add("#");
        alphabet.add("#");

        for (String letter : alphabet) {
            if (Arrays.asList("D", "G").contains(letter)) {
                letterValues.put(letter, 2);
            } else if (Arrays.asList("B", "C", "M", "P").contains(letter)) {
                letterValues.put(letter, 3);
            } else if (Arrays.asList("F", "H", "V", "W", "Y").contains(letter)) {
                letterValues.put(letter, 4);
            } else if (Arrays.asList("K").contains(letter)) {
                letterValues.put(letter, 5);
            } else if (Arrays.asList("J", "X").contains(letter)) {
                letterValues.put(letter, 8);
            } else if (Arrays.asList("Q", "Z").contains(letter)) {
                letterValues.put(letter, 10);
            } else if (Arrays.asList("#").contains(letter)) {
                letterValues.put(letter, 0);
            } else {
                letterValues.put(letter, 1);
            }
        }
    }

    protected List<Player> playersList(List<Player> players) {

        while (true) {
            try {
                Scanner s = new Scanner(System.in);
                System.out.println("HOW MANY PLAYERS? 1, 2, 3, or 4?");
                System.out.println();
                int input = s.nextInt();
                if (input > 4) {
                    System.out.println("ONLY A MAXIMUM OF 4 PLAYERS IS ALLOWED!");
                    System.out.println();
                    continue;
                } else {
                    for (int x = 1; x <= input; x++) {
                        players.add(new Player(x));
                    }
                }
                return players;
            } catch (Exception e) {
                System.out.println();
                System.out.println("INVALID ENTRY!");
                System.out.println();
                System.out.println("PLEASE SELECT 1, 2, 3 or 4 ");
                System.out.println();
            }
        }
    }

    public static ArrayList<String> newHand(ArrayList<String> tilesLeft) {
        //give player an initial set of 7 random letters
        ArrayList<String> emptyHand = new ArrayList<>();
        Random r = new Random();
        int x = 0;

        while (x < 7) {
            String randomLetter = tilesLeft.get(r.nextInt(tilesLeft.size())).trim().toUpperCase();
            emptyHand.add(randomLetter);
            tilesLeft.remove(randomLetter);
            x++;
        }
        return emptyHand;
    }

    public void updateHand(List<String> tiles, List<String> hand, List<String> word) {
        Random r = new Random();

        if (!word.get(0).equals("")) {
            if (tiles.size() >= word.size()) {
                for (String letter : word) {
                    String newLetter = tiles.get(r.nextInt(tiles.size()));
                    hand.add(newLetter);
                    tiles.remove(newLetter);
                }
            } else {
                for (String letter : tiles) {
                    hand.add(letter);
                }
                tiles.clear();
            }
        }
    }

    public ArrayList<String> dictionary(){
        //build a dictionary List of English words
        Scanner s;
        File f = new File("dictionary.txt");							//file containing the words
        try {
            s = new Scanner(f);
            while(s.hasNextLine()) {							//read file and add each word to the list
                dictionary.add(s.nextLine().trim().toUpperCase());
            }
        } catch (Exception e) {
            System.out.println("FILE NOT FOUND!");
            System.out.println();
        }
        return dictionary;
    }

    public void printScores(List<Player> players){
        List<Integer> totalScores = new ArrayList<>();
        for(Player p : players) {
            totalScores.add(p.getScore());
        }

        for(Player player: players) {
            if(Collections.max(totalScores)==player.getScore()) {
                System.out.println("THE WINNER IS ======> " + player.getName() +  " <======  WITH " +player.getScore() + " POINTS!!!!!");
                System.out.println();
            }else {
                System.out.println("====> " +player.getName() + " <==== " + " SCORED " + player.getScore() + " POINTS.");
                System.out.println();
            }
        }
    }

    public void wordScore(HashMap<String, Integer> letterValues, ArrayList<String> word) { 	// calculates the points for word played, returns it and updates player's score
        //calculate the point value of word played and update total score

        if(!word.get(0).equals("")){
            for(String letter : word) {
                int tileValue = letterValues.get(letter);
                wordScore += tileValue;
            }
        }

        if(word.size() == 7) {													//bonus of 50 points if word uses all 7 letters in hand!
            wordScore += 50;
        }
        System.out.println("YOUR WORD SCORED: ------------ " + wordScore);			//print word value
        System.out.println();
    }



}
