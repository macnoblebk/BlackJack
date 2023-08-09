import java.util.*;

public class Game {
    private final Integer NUMBER_OF_PLAYERS;
    private final Integer INITIAL_HAND;
    private ArrayList<String> playerNameList;
    private Boolean isGameOver;
    private ArrayList<Boolean> playerStickStatus;
    private Stack<Card> cardDeck;
    private ArrayList<ArrayList<Card>> playerHandList;
    public Game(Scanner keyboard) {
        this.NUMBER_OF_PLAYERS = 3;
        this.INITIAL_HAND = 2;
        playerNameList = new ArrayList<>(NUMBER_OF_PLAYERS);
        playerStickStatus = new ArrayList<>(Arrays.asList(false, false, false));
        populatePlayerNameArray(keyboard);
        this.isGameOver = false;
        this.cardDeck = getCardDeck();
        playerHandList = new ArrayList<>();
        initializePlayerQueue();
    }

    private void populatePlayerNameArray(Scanner keyboard){
        for(int i = 0; i < NUMBER_OF_PLAYERS; i++){
            playerNameList.add(getPlayerName(keyboard));
        }
    }

    private String getPlayerName(Scanner keyboard) {
        String input;
        do {
            System.out.print("Enter player's name : ");
            input = keyboard.nextLine();
            if (input.isEmpty())
            {
                System.out.println("Player name cannot be empty!");
            }
            else if (input.isBlank())
            {
                System.out.println("Player name cannot be only whitespaces!");
            }
        }
        while (input.isBlank());
        return input;
    }

    private Stack<Card> getCardDeck() {
        ArrayList<Card> list = new ArrayList<>();
        Stack<Card> cardStack = new Stack<>();
        for (Card.Suit cs : Card.Suit.values()){
            for (Card.Value cv : Card.Value.values()){
                list.add(new Card(cs, cv.getValue()));
            }
        }
        Collections.shuffle(list);
        cardStack.addAll(list);
        return cardStack;
    }

    private void initializePlayerQueue(){
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            playerHandList.add(new ArrayList<>());
        }

    }

    private void initialDraw() {
        for (ArrayList<Card> hand : playerHandList) {
            for (int i = 0; i < INITIAL_HAND; i++) {
                hand.add(cardDeck.pop());
            }
        }
    }

    public void play() {
        gameEngine();
    }

    private void gameEngine(){

        // TODO: At the end of game, if no player hits 21, player with total closest to 21 wins
        int playerSelector = 0;
        initialDraw();
        displayInitialHand();

        do {
            displayCurrentHand();
            for (ArrayList<Card> hand : playerHandList) {
                int total = 0;
                for (Card c : hand) total += c.getValue();

                if (total > 21) {
                    defaultBustStrategy(playerNameList.get(playerSelector % NUMBER_OF_PLAYERS));
                    ArrayList<ArrayList<Card>> newList = new ArrayList<>(playerHandList);
                    newList.remove(hand);
                    playerHandList = newList;
                    playerNameList.remove(playerSelector % NUMBER_OF_PLAYERS);

                    // TODO: Fix issue with skipping over next player (NUMBER_OF_PLAYERS), use arraylist.size() as nop

                    if(checkWinnerByListSize()){
                        isGameOver = true;
                        displayWinner(playerNameList.get(0));
                    }
                    playerSelector = getNextPlayer(playerSelector);;
                }
                else if (total == 21) {
                    isGameOver = true;
                    displayWinner(playerNameList.get(playerSelector % NUMBER_OF_PLAYERS));
                    playerSelector++;
                }
                else if (total >= 17) {
                    defaultStickStrategy(playerNameList.get(playerSelector % NUMBER_OF_PLAYERS));
                    playerStickStatus.set(playerSelector % NUMBER_OF_PLAYERS, true);
                    if (checkPlayerStickStatus()){
                        isGameOver = true;
                        tieGame();
                    }
                    playerSelector++;
                }
                else {
//                   defaultHitStrategy(playerNameList.get(playerSelector % NUMBER_OF_PLAYERS));
//                   ArrayList<ArrayList<Card>> newList = new ArrayList<>(playerHandList);
//                   newList.get(playerSelector % NUMBER_OF_PLAYERS).add(cardDeck.pop());
//                   playerHandList = newList;
                    System.out.println("This is the else branch!");
//                    //TODO: Create new hand, modify new, assign to old
//                    playerSelector++;
                }
            }
        } while (!isGameOver);
    }

    private int getNextPlayer(int playerSelector){
        return (playerSelector % NUMBER_OF_PLAYERS == 1) ? 1 : 0;
    }
    private boolean checkWinnerByListSize(){
        return (playerHandList.size() == 1);
    }
    private void tieGame(){
        System.out.println("Game over due to all players sticking.");
    }

    private boolean checkPlayerStickStatus(){
        boolean allStick = true;
        for (boolean value: playerStickStatus){
            if (!value) {
                allStick = false;
            }
        }
        return allStick;
    }

    private void displayInitialHand(){
        int playerSelector = 0;
        for (ArrayList<Card> hand : playerHandList) {
            System.out.printf("%n%s is dealt :%n",  playerNameList.get(playerSelector));
            hand.forEach(System.out::println);
            playerSelector++;
        }
    }

    private void displayCurrentHand(){
        int playerSelector = 0;
        for (ArrayList<Card> hand : playerHandList) {
            System.out.printf("%n%s hand :%n",  playerNameList.get(playerSelector));
            hand.forEach(System.out::println);
            playerSelector++;
        }
    }



    private void defaultHitStrategy(String playerName) {
        System.out.printf("%n%s hits.%n", playerName);
    }

    private void defaultStickStrategy(String playerName){
        System.out.printf("%n%s sticks.%n", playerName);
    }

    private void defaultBustStrategy(String playerName){
        System.out.printf("%n%s busts.%n", playerName);
        System.out.printf("%n%s is ejected fromm the game.%n", playerName);
    }

    private void displayWinner(String playerName){
        System.out.printf("%n%s has won!%n", playerName);
    }

}
