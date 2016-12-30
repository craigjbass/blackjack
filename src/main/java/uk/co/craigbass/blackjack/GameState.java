package uk.co.craigbass.blackjack;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final ArrayList<Card> dealerCards = new ArrayList<>();
    private final ArrayList<Card> playerCards = new ArrayList<>();

    public void putPlayerCard(Card card) {
        playerCards.add(card);
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public void putDealerCard(Card card) {
        dealerCards.add(card);
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }
}
