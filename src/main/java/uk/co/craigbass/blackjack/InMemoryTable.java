package uk.co.craigbass.blackjack;

import uk.co.craigbass.blackjack.Blackjack.Table;
import uk.co.craigbass.playingcards.Card;
import uk.co.craigbass.blackjack.domain.BlackjackHand;

import java.util.ArrayList;
import java.util.List;

import static uk.co.craigbass.blackjack.domain.BlackjackHand.handFromCards;

public class InMemoryTable implements Table {
    private final ArrayList<Card> playersCards = new ArrayList<>();
    private final ArrayList<Card> dealerCards = new ArrayList<>();

    public void givePlayerCard(Card card) {
        playersCards.add(card);
    }

    public void giveDealerCard(Card card) {
        dealerCards.add(card);
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public BlackjackHand getPlayersHand() {
        return handFromCards(playersCards);
    }

    @Override
    public BlackjackHand getDealersHand() {
        return handFromCards(dealerCards);
    }
}
