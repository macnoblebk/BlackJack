public class Card {

    private Suit suit;
    private int value;

    public enum Suit {
        HEART, DIAMOND, SPADE, CLUB
    }
    public enum Value {
        ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5),
        SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10);
        private int value;

        Value(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", value=" + value +
                '}';
    }
}
