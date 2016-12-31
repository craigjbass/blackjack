package uk.co.craigbass.blackjack.test;

import uk.co.craigbass.blackjack.Pack;
import uk.co.craigbass.playingcards.Card;

import java.util.LinkedList;
import java.util.Queue;

class ConfigurableLoopingPack implements Pack {
    private Queue<Card> cards = new LinkedList<>();

    void add(Card card) {
        cards.add(card);
    }

    @Override
    public Card next() {
        Card card = cards.remove();
        cards.add(card);
        return card;
    }
}
