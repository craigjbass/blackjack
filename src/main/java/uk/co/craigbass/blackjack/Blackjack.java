package uk.co.craigbass.blackjack;

import uk.co.craigbass.blackjack.Blackjack.Presenter.PresentableHand;
import uk.co.craigbass.playingcards.Card;
import uk.co.craigbass.blackjack.domain.BlackjackHand;

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
        table.givePlayerCard(pack.next());
        table.givePlayerCard(pack.next());

        table.giveDealerCard(pack.next());
        table.giveDealerCard(pack.next());

        presentPlayersHand(table.getPlayersHand());
        presentDealersFirstCard(table.getDealersHand());
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

    public void stick() {
        presenter.gameOver(PLAYER_WINS);
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

        enum Ending { PLAYER_WINS }
        class PresentableHand {
            public int value;
            public Card[] cards;
        }
    }
}
