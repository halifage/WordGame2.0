import java.io.File;
import java.util.*;

public class GamePlay {
    private ArrayList<String> alphabet = alphabet(new ArrayList<>());
    private HashMap<String, Integer> letterValues = letterValues(new HashMap<String, Integer>());
    private ArrayList<String> tiles = letterBank(new ArrayList<>());
    private int wordScore;
    private ArrayList<String> wordPlayed = new ArrayList<>();
    private static ArrayList<String> dictionary = dictionary(new ArrayList<>());
    private static Random random = new Random();
    private static String userInput;
    ArrayList<Player> players = new ArrayList<>();
    private static int counter;
    ArrayList<String> lettersToSwap = new ArrayList<>();

//    public HashMap<String, Integer> getLetterValues() {
//        return letterValues;
//    }

//    public static ArrayList<String> getTiles() {
//        return tiles;
//    }

//    public ArrayList<String> getLetterBank() {
//        return letterBank();
//    }

    private ArrayList<String> alphabet(ArrayList<String> alphabet) {
        for (char x = 65; x <= 90; x++) {
            alphabet.add(Character.toString(x).trim().toUpperCase());
        }
        return alphabet;
    }

    private ArrayList<String> letterBank(ArrayList<String> tiles) {
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

    private HashMap<String, Integer> letterValues(HashMap<String, Integer> letterValues) {
        // create a letter bank with letters and their corresponding values

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
            } else if ("#".equals(letter)) {
                letterValues.put(letter, 0);
            } else {
                letterValues.put(letter, 1);
            }
        }
        return letterValues;
    }

    protected String getUserInput() {
        Scanner scan = new Scanner(System.in);
        userInput = scan.next().trim().toUpperCase();
        return userInput;
    }

    protected void playersList() {
        while (true) {
            try {
                System.out.println("HOW MANY PLAYERS? 1, 2, 3, or 4?");
                System.out.println();
                int i = Integer.parseInt(getUserInput());
                if (i > 4) {
                    System.out.println("ONLY A MAXIMUM OF 4 PLAYERS IS ALLOWED!");
                    System.out.println();
                } else {
                    for (int x = 1; x <= i; x++) {
                        players.add(new Player(x));
                    }
                    break;
                }
            } catch (Exception e) {
                System.out.println("INVALID ENTRY!");
                continue;
            }
        }
    }

    protected ArrayList<String> newHand() {
        //give player an initial set of 7 random letters
        ArrayList<String> newHand = new ArrayList<>();
        int x = 0;

        while (x < 7) {
            String randomLetter = tiles.get(random.nextInt(tiles.size())).trim().toUpperCase();
            newHand.add(randomLetter);
            tiles.remove(randomLetter);
            x++;
        }
        return newHand;
    }

    protected ArrayList<String> updateHand(List<String> tiles, ArrayList<String> hand, List<String> word) {
        for (String letter : word) {
            hand.remove(letter);
        }
        if (tiles.size() >= word.size()) {
            for (String letter : word) {
                String newLetter = tiles.get(random.nextInt(tiles.size()));
                hand.add(newLetter);
                tiles.remove(newLetter);
            }
        } else {
            for (String letter : tiles) {
                hand.add(letter);
            }
            tiles.clear();
        }
        System.out.println("YOUR LETTERS ARE >>>>> " + hand + " <<<<<");
        System.out.println();
        return hand;
    }

    private static ArrayList<String> dictionary(ArrayList<String> dictionary) {
        //build a dictionary List of English words
        Scanner s;
        File f = new File("dictionary.txt");                            //file containing the words
        try {
            s = new Scanner(f);
            while (s.hasNextLine()) {                            //read file and add each word to the list
                dictionary.add(s.nextLine().trim().toUpperCase());
            }
        } catch (Exception e) {
            System.out.println("FILE NOT FOUND!");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println();
        }
        return dictionary;
    }

    private void printScores(List<Player> players) {
        List<Integer> totalScores = new ArrayList<>();
        for (Player p : players) {
            totalScores.add(p.getScore());
        }

        for (Player player : players) {
            if (Collections.max(totalScores) == player.getScore()) {
                System.out.println("THE WINNER IS >>>>> " + player.getName() + " <<<<<  WITH " + player.getScore() + " POINTS!!!!!");
                System.out.println();
            } else {
                System.out.println(">>>>> " + player.getName() + " <<<<< " + " SCORED " + player.getScore() + " POINTS.");
                System.out.println();
            }
        }
    }

    protected void wordScore(HashMap<String, Integer> letterValues, ArrayList<String> word) {    // calculates the points for word played, returns it and updates player's score
        //calculate the point value of word played and update total score

        if (!word.get(0).equals("")) {
            for (String letter : word) {
                int tileValue = letterValues.get(letter);
                wordScore += tileValue;
            }
        }

        if (word.size() == 7) {                                                    //bonus of 50 points if word uses all 7 letters in hand!
            wordScore += 50;
        }
        System.out.println("YOUR WORD SCORED: ------------ " + wordScore);            //print word value
        System.out.println();
    }

    private void playWord(ArrayList<String> hand) {
        ArrayList<String> handAfterWord;
        
        while (true) {
            handAfterWord = (ArrayList<String>) hand.clone();
            try {
                wordPlayed.clear();
                System.out.println("PLAY A WORD... (or Press 'X' to pass your turn)");
                getUserInput();
                if (userInput.equals("X")) {
                    break;
                }
                for (String str : userInput.split("")) {
                    if (alphabet.contains(str) || str.equals("#")) {
                        wordPlayed.add(str);
                    } else {
                        System.out.println(str + " IS NOT A VALID LETTER!");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            for (String letter : wordPlayed) {
                handAfterWord.remove(letter);
            }

            if (hand.size() - handAfterWord.size() == wordPlayed.size()) {
                if (wordPlayed.contains("#")) {
                    wordPlayed.clear();
                    userInput = convertBlank(userInput);
                }
                if (dictionary.contains(userInput)) {
                    System.out.println("WORD PLAYED ==========> " + userInput);
                    wordScore(letterValues, wordPlayed);
                    hand = handAfterWord;
                    break;
                }
                System.out.println("Sorry '" + userInput + "' is NOT in the Dictionary!");
            } else {
                for (String str : hand) {
                    wordPlayed.remove(str);
                }
                System.out.println(wordPlayed + " NOT IN HAND!");
                continue;
            }
        }
    }

    private String convertBlank(String word) {
        int x = 0;
        int i = 1;
        String userWord = "";
        while (x < 1) {
            try {
                for (String letter : word.split("")) {
                    if (letter.equals("#")) {
                        System.out.println();
                        System.out.println("WHICH LETTER DOES 'Blank " + i++ + "' REPRESENT? ");
                        System.out.println();
                        String replaceBlank = getUserInput();
                        if (alphabet.contains(replaceBlank)) {
                            userWord += replaceBlank;

                        } else {
                            System.out.println("PLEASE CHOOSE A VALID LETTER (A to Z)");
                            System.out.println();
                            continue;
                        }
                    } else {
                        userWord += letter;
                    }
                }
            } catch (Exception e) {
                System.out.println("INVALID INPUT!");
                System.out.println();
                continue;
            }
            x++;
        }
        for (String letter : userWord.split("")) {
            wordPlayed.add(letter);
        }
        return userWord;
    }

    protected void swapLetters(List<String> hand) {
        WHILE:
        while (true) {
            lettersToSwap.clear();
            try {
                System.out.println("ENTER LETTERS TO SWAP (or enter 'X' to pass your turn)");
                getUserInput();
                if (userInput.equals("X")) {
                    break;
                }
                for (String str : userInput.split("")) {
                    if (hand.contains(str)) {
                        lettersToSwap.add(str);
                    } else {
                        System.out.println(str + " NOT IN HAND!");
                        System.out.println("PLEASE SELECT A VALID LETTER");
                        continue WHILE;
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    protected String choice() {
        String choice;
        while (true) {
            if (tiles.size() >= 7) {
                System.out.println("PLEASE SELECT 'P' (play), 'S' (swap), 'X' (pass)");
                choice = getUserInput();
                if ((choice.equals("P")) || (choice.equals("S")) || (choice.equals("X"))) {
                    return choice;
                } else {
                    System.out.println("INVALID SELECTION!");
                    System.out.println();
                    continue;
                }
            } else {
                System.out.println("PLEASE SELECT 'P' (play) or 'X' (pass)");
                choice = getUserInput();
                if ((choice == "P") || (choice == "X")) {
                    return choice;
                } else {
                    System.out.println("INVALID SELECTION!");
                    System.out.println();
                    continue;
                }
            }
        }
    }

    public void play() {
        playersList();
        OUTER_WHILE:
        while (counter < players.size()) {
            FOR:
            for (Player player : players) {
                INNER_WHILE:
                while (!player.isEndGame() && player.getHand().size() > 0) {
                    System.out.println();
                    System.out.println();
                    System.out.println(">>>>> " + player.getName() + " <<<<<  PLAYING.....");
                    System.out.println();
                    System.out.println(player.getName()+"'S LETTERS ARE >>>>> " + player.getHand() + " <<<<<");
                    System.out.println();
                    switch (choice()) {
                        case "P":
                            System.out.println(player.getName()+"'S LETTERS ARE >>>>> " + player.getHand() + " <<<<<");
                            playWord(player.getHand());
                            player.setHand(updateHand(tiles, player.getHand(), wordPlayed));
                            player.setScore(wordScore);
                            System.out.println(player.getName()+ "'S SCORE IS >>>> " + player.getScore());
                            break;
                        case "S":
                            System.out.println(player.getName()+"'S LETTERS ARE >>>>> " + player.getHand() + " <<<<<");
                            swapLetters(player.getHand());
                            player.setHand(updateHand(tiles, player.getHand(), lettersToSwap));
                            player.setScore(wordScore);
                            System.out.println(player.getName()+ "'S SCORE IS >>>> " + player.getScore());
                            break;
                        case "X":
                            System.out.println(player.getName() + " HAS LEFT THE GAME.");
                            System.out.println(player.getName()+ "'S SCORE IS >>>> " + player.getScore());
                            player.setEndGame(true);
                            counter++;
                            continue OUTER_WHILE;
                    }
                    continue FOR;
                }
            }
        }
        printScores(players);
    }
}


