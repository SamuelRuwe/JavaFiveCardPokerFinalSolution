public class Main {

    public static void main(String[] args) {

        //need to fix high cards so that high cards are put into appropriate order for non paired cards
        PokerGame.playPoker("Black: 2H 2D 2S AC AD  White: 2C 4H 7S 9C TH Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S");
        Card card = new Card(Rank.FIVE, Suit.DIAMONDS);
    }
}
