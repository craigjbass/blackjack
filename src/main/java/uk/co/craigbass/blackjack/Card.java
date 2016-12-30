package uk.co.craigbass.blackjack;

public class Card {
    private final Suit suit;
    private final Value value;

    enum Suit {Heart, Spade, Diamond, Club}
    enum Value {Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King}

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
    }
}
