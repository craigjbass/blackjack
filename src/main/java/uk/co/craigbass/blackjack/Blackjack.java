package uk.co.craigbass.blackjack;

import uk.co.craigbass.blackjack.Blackjack.Presenter.PresentableHand;
import uk.co.craigbass.playingcards.Card;
import uk.co.craigbass.blackjack.domain.BlackjackHand;

import static uk.co.craigbass.blackjack.Blackjack.Presenter.Ending.DEALER_WINS;
import static uk.co.craigbass.blackjack.Blackjack.Presenter.Ending.DRAW;
import static uk.co.craigbass.blackjack.Blackjack.Presenter.Ending.PLAYER_WINS;

public class Blackjack {
    private Table table;
    private Presenter presenter;
    private Pack pack;

    public Blackjack(Table table, Presenter presenter, Pack pack) {
        this.table = table;
        this.presenter = presenter;
        this.pack = pack;
    }

    public void deal() {
        dealTwoCardsToPlayer();
        dealTwoCardsToDealer();

        presentPlayersHand(table.getPlayersHand());
        presentDealersFirstCard(table.getDealersHand());
    }

    public void hit() {
        dealCardToPlayer();
        if (table.getPlayersHand().isBust()) presenter.gameOver(DEALER_WINS);
        presentPlayersHand(table.getPlayersHand());
    }

    public void stick() {
        BlackjackHand dealersHand = getDealersHandAfterDealersTurn();
        BlackjackHand playersHand = table.getPlayersHand();

        if (dealersHand.isBust()) {
            presenter.gameOver(PLAYER_WINS);
        } else if (playersHand.getValue() > dealersHand.getValue()) {
            presenter.gameOver(PLAYER_WINS);
        } else if (playersHand.getValue() == dealersHand.getValue()) {
            presenter.gameOver(DRAW);
        } else {
            presenter.gameOver(DEALER_WINS);
        }
    }

    private BlackjackHand getDealersHandAfterDealersTurn() {
        BlackjackHand dealersHand = table.getDealersHand();
        while (dealersHand.getValue() < 17) {
            dealCardToDealer();
            dealersHand = table.getDealersHand();
        }
        return dealersHand;
    }

    private void dealTwoCardsToDealer() {
        doTwice(this::dealCardToDealer);
    }

    private void dealTwoCardsToPlayer() {
        doTwice(this::dealCardToPlayer);
    }

    private void doTwice(Runnable fun) {
       for(int i = 0; i < 2; i++) fun.run();
    }

    private void dealCardToPlayer() {
        table.givePlayerCard(pack.next());
    }

    private void dealCardToDealer() {
        table.giveDealerCard(pack.next());
    }

    private void presentDealersFirstCard(BlackjackHand dealersHand) {
        presenter.presentDealersFirstCard(dealersHand.firstCard());
    }

    private void presentPlayersHand(BlackjackHand playersHand) {
        PresentableHand presentableHand = new PresentableHand();
        presentableHand.cards = playersHand.getCards();
        presentableHand.value = playersHand.getValue();
        presenter.presentPlayersHand(presentableHand);
    }

    public interface Table {
        void givePlayerCard(Card card);

        void giveDealerCard(Card card);

        BlackjackHand getPlayersHand();

        BlackjackHand getDealersHand();
    }

    public interface Presenter {
        void presentPlayersHand(PresentableHand cards);

        void presentDealersFirstCard(Card card);

        void gameOver(Ending ending);

        enum Ending {DEALER_WINS, DRAW, PLAYER_WINS}

        class PresentableHand {
            public int value;
            public Card[] cards;
        }
    }
}
