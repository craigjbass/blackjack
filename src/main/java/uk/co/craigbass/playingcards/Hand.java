package uk.co.craigbass.playingcards;

import java.util.List;

public class Hand {
    protected final List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public Card[] getCards() {
        return cards.toArray(new Card[cards.size()]);
    }
}
