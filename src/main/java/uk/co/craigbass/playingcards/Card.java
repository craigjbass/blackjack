package uk.co.craigbass.playingcards;

public class Card {
    private final Suit suit;
    private final Value value;

    public enum Suit {Heart, Spade, Diamond, Club}

    public enum Value {
        Ace(1), Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), Jack(11), Queen(12), King(13);
        private final int faceValue;

        Value(int faceValue) {
            this.faceValue = faceValue;
        }

        public int getFaceValue() {
            return faceValue;
        }
    }

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isAn(Value value) {
        return getValue().equals(value);
    }

    public Value getValue() {
        return value;
    }

    public int getFaceValue() {
        return value.getFaceValue();
    }
}
