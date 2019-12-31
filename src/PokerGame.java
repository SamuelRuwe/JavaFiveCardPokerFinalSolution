import java.util.Arrays;

public class PokerGame {

    private final Hand[] hands = new Hand[2];
    private static Card winningCard;

    // == Constructors ==
    private PokerGame(Hand black, Hand white) {
        this.hands[0] = black;
        this.hands[1] = white;
        returnWinner();
    }

    // == Setters ==
    static void setWinningCard(Card card){
        winningCard = card;
    }

    // == Class Methods ==
    private void returnWinner(){
        int score = this.hands[0].compareTo(this.hands[1]);
        String playerOne = this.hands[0].getHandRank().toString().toLowerCase() + " " + Arrays.toString(this.hands[0].getHighCard());
        String playerTwo = this.hands[1].getHandRank().toString().toLowerCase() + " " + Arrays.toString(this.hands[1].getHighCard());
        winningCard = null;
        if(score > 0){
            System.out.println(this.hands[0].getPlayerName() + " wins. - with " + playerOne + " vs. " + playerTwo);
        } else if(score < 0){
            System.out.println(this.hands[1].getPlayerName() + " wins. - with " + playerTwo + " vs. " + playerOne);
        } else {
            System.out.println("Tie.");
        }
    }

    // == Static Methods ==
    static void playPoker(String input){
        input = input.trim()
                .replaceAll("\\s{2}", " ")
                .replaceAll(":", "");
        String[] temp = input.split(" ");
        int counter = 0;
        while(counter < temp.length-6){
            Hand Black = createHand(Arrays.copyOfRange(temp, counter, counter+6));
            counter +=6;
            Hand White = createHand(Arrays.copyOfRange(temp, counter, counter+6));
            counter+=6;
            PokerGame pokerGame = new PokerGame(Black, White);
        }
    }

    private static Hand createHand(String[] handString){
        String name = handString[0];
        Card[] pokerHand = new Card[5];
        for(int i = 1; i < 6; i++){
            Card card = new Card(handString[i]);
            pokerHand[i-1] = card;
        }
        return new Hand(name, pokerHand);
    }
}