package uk.co.craigbass.blackjack;

import java.util.List;

public class Blackjack {

    private GameState gameState;
    private Presenter presenter;
    private Pack pack;

    public Blackjack(GameState gameState, Presenter presenter, Pack pack) {
        this.gameState = gameState;
        this.presenter = presenter;
        this.pack = pack;
    }

    public void deal() {
        gameState.putPlayerCard(pack.next());
        gameState.putPlayerCard(pack.next());

        gameState.putDealerCard(pack.next());
        gameState.putDealerCard(pack.next());

        List<Card> playerCards = gameState.getPlayerCards();
        presenter.presentHand(playerCards.toArray(new Card[playerCards.size()]));
    }

    interface Presenter {
        void presentHand(Card[] cards);
    }
}
