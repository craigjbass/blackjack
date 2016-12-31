package uk.co.craigbass.blackjack;

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

        presentHand();
    }

    private void presentHand() {
        BlackjackHand blackjackHand = table.getPlayersHand();

        Card[] cards = blackjackHand.getCards();

        Presenter.PresentableHand presentableHand = new Presenter.PresentableHand();
        presentableHand.cards = cards;
        presentableHand.value = blackjackHand.getValue();

        presenter.presentHand(presentableHand);
    }

    public void stick() {
        presenter.gameOver(PLAYER_WINS);
    }

    public interface Table {
        void givePlayerCard(Card card);
        void giveDealerCard(Card card);
        BlackjackHand getPlayersHand();
    }

    public interface Presenter {
        void presentHand(PresentableHand cards);
        void gameOver(Ending playerWins);
        enum Ending { PLAYER_WINS }
        class PresentableHand {
            public int value;
            public Card[] cards;
        }
    }
}
