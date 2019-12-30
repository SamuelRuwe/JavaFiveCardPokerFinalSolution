public class Main {

    public static void main(String[] args) {

        //need to fix high cards so that high cards are put into appropriate order for non paired cards
        PokerGame.playPoker("Black: AH 2D 2S 2C 2D  White: 9C 9H 7S TC TH");
        Card card = new Card(Rank.FIVE, Suit.DIAMONDS);
    }

//    Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S
}
