public class Main {

    public static void main(String[] args) {

        //need to fix high cards so that high cards are put into appropriate order for non paired cards
        PokerGame.playPoker("Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH " +
                "Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S " +
                "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C KH " +
                "Black: 2H 3D 5S 9C KD  White: 2D 3H 5C 9S KH");
        Card card = new Card(Rank.FIVE, Suit.DIAMONDS);
    }

//    Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S
}
