import java.util.*;

public class Game {
    private final int NUMBER_OF_PLAYERS;
    private final String[] PLAYER_NAME_ARRAY;
    private boolean isGameOver;
    private Stack<Card> cardDeck;
    private Queue<ArrayList<Card>> playerArrayLinkedList;

    public Game(Scanner keyboard) {
        this.NUMBER_OF_PLAYERS = 3;
        PLAYER_NAME_ARRAY = new String[NUMBER_OF_PLAYERS];
        populatePlayerNameArray(keyboard);
        this.isGameOver = false;
        this.cardDeck = getCardDeck();
        playerArrayLinkedList = new LinkedList<>();
        initializePlayerQueue();
    }

    private void populatePlayerNameArray(Scanner keyboard){
        for(int i = 0; i < NUMBER_OF_PLAYERS; i++){
            PLAYER_NAME_ARRAY[i] = getPlayerName(keyboard);
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

    private void draw() {
        for (ArrayList<Card> hand : playerArrayLinkedList) {
            hand.add(cardDeck.pop());
            hand.add(cardDeck.pop());
        }
    }

    private void gamePlay() {
        draw();

    }

    private void defaultHitStrategy(ArrayList<Card> hand) {
        hand.add(cardDeck.pop());
    }

}
