import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Stack;

public class Game {
    private final int NUMBER_OF_PLAYERS;
    private boolean isGameOver;
    private ArrayList<Card> playerHand;
    private Stack<Card> cardDeck;
    private Queue<ArrayList<Card>> playerQueueArray;

    public Game() {
        this.NUMBER_OF_PLAYERS = 3;
        this.isGameOver = false;
        this.cardDeck = getCardDeck();
        //getInitialHand();
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


}
