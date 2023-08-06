import java.util.*;

public class Game {
    private final int NUMBER_OF_PLAYERS;
    private boolean isGameOver;

    private Stack<Card> cardDeck;
    private Queue<ArrayList<Card>> playerArrayLinkedList;

    public Game() {
        this.NUMBER_OF_PLAYERS = 3;
        this.isGameOver = false;
        this.cardDeck = getCardDeck();
        playerArrayLinkedList = new LinkedList<>();
        initializePlayerQueue();
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
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++){
            playerArrayLinkedList.add(new ArrayList<>());
        }
        for (ArrayList<Card> hand : playerArrayLinkedList) {
            hand.add(cardDeck.pop());
            hand.add(cardDeck.pop());
        }
    }


}
