import java.util.*;

public class Game {
    private final int NUMBER_OF_PLAYERS;
    private final int INITIAL_HAND;
    private String[] playerNameArray;
    private boolean isGameOver;
    private boolean[] playerStickStatus;
    private Stack<Card> cardDeck;
    private Queue<ArrayList<Card>> playerArrayLinkedList;

    public Game(Scanner keyboard) {
        this.NUMBER_OF_PLAYERS = 3;
        this.INITIAL_HAND = 2;
        playerNameArray = new String[NUMBER_OF_PLAYERS];
        playerStickStatus = new boolean[]{false, false, false};
        populatePlayerNameArray(keyboard);
        this.isGameOver = false;
        this.cardDeck = getCardDeck();
        playerArrayLinkedList = new LinkedList<>();
        initializePlayerQueue();
    }

    private void populatePlayerNameArray(Scanner keyboard){
        for(int i = 0; i < NUMBER_OF_PLAYERS; i++){
            playerNameArray[i] = getPlayerName(keyboard);
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
            playerArrayLinkedList.add(new ArrayList<>());
        }

    }

    private void initialDraw() {
        for (ArrayList<Card> hand : playerArrayLinkedList) {
            for (int i = 0; i < INITIAL_HAND; i++) {
                hand.add(cardDeck.pop());
            }
        }
    }

    public void gamePlay() {
        initialDraw();
        displayHand();

        do {
            gameEngine();
            displayHand();
        } while(!isGameOver);

        // TODO: At the end of game, if no player hits 21, player with total closest to 21 wins
    }

    private void gameEngine(){
        int playerSelector = 0;
        int total = 0;
        for (ArrayList<Card> hand : playerArrayLinkedList) {
           for (Card c : hand) {
               total += c.getValue();
               if (total > 21) {
                   defaultBustStrategy(playerNameArray[playerSelector]);
                   playerArrayLinkedList.remove(hand);
                   removePlayerName(playerNameArray[playerSelector]);
                   if(checkWinner()){
                       isGameOver = true;
                       displayWinner(playerNameArray[playerSelector]);
                   }
                      isGameOver = true;
               }
               else if (total == 21) {
                   isGameOver = true;
                   displayWinner(playerNameArray[playerSelector]);
               }
               else if (total >= 17) {
                   defaultStickStrategy(playerNameArray[playerSelector]);
                   playerStickStatus[playerSelector] = true;
                   if (checkPlayerStickStatus()){
                       isGameOver = true;
                       tieGame();
                   }
               }
               else {
                   defaultHitStrategy(hand, playerNameArray[playerSelector]);
               }
           }
           playerSelector++;
        }
    }

    private boolean checkWinner(){
        return (playerArrayLinkedList.size() == 1);
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

    private void removePlayerName(String playerName) {
        int size = this.playerNameArray.length-1;
        String[] playerNameArray = new String[size];
        int index = 0;
        for (String str: this.playerNameArray){
            if (!str.equals(playerName)){
                playerNameArray[index] = str;
                index++;
            }
        }
        this.playerNameArray = playerNameArray;
    }

    private void displayHand(){
        int playerSelector = 0;
        for (ArrayList<Card> cards : playerArrayLinkedList) {
            System.out.printf("%n%s is dealt :%n",  playerNameArray[playerSelector]);
            cards.forEach(System.out::println);
            System.out.println();
            playerSelector++;
        }
    }


    private void defaultHitStrategy(ArrayList<Card> hand, String playerName) {
        System.out.printf("%s hits.%n", playerName);
        hand.add(cardDeck.pop());
    }

    private void defaultStickStrategy(String playerName){
        System.out.printf("%s sticks.%n", playerName);
    }

    private void defaultBustStrategy(String playerName){
        System.out.printf("%s busts.%n", playerName);
        System.out.printf("%s is ejected fromm the game.%n", playerName);
    }
    
    private void displayWinner(String playerName){
        System.out.printf("%s has won!%n", playerName);
    }

}
