import java.util.Arrays;

public class PokerGame {

    // == Fields ==
    private final Hand[] hands = new Hand[2];
    private static Card winningCard;
    private static Card losingCard;

    // == Constructors ==
    private PokerGame(Hand black, Hand white) {
        this.hands[0] = black;
        this.hands[1] = white;
    }

    // == Setters ==
    static void setWinningCard(Card card) {
        winningCard = card;
    }

    static void setLosingCard(Card card) {
        losingCard = card;
    }

    // == Class Methods ==
    private String returnWinner() {
        int score = this.hands[0].compareTo(this.hands[1]);
        String playerOne, playerTwo;
        boolean kicker = winningCard != null ? true : false;
        if (score > 0) {
            playerOne = handRankResult(this.hands[0].getHandRank(), this.hands[0].getHighCards(), true, kicker);
            playerTwo = handRankResult(this.hands[1].getHandRank(), this.hands[1].getHighCards(), false, kicker);
            return this.hands[0].getPlayerName() + " wins. - with " + playerOne + playerTwo;
        } else if (score < 0) {
            playerTwo = handRankResult(this.hands[1].getHandRank(), this.hands[1].getHighCards(), true, kicker);
            playerOne = handRankResult(this.hands[0].getHandRank(), this.hands[0].getHighCards(), false, kicker);
            return this.hands[1].getPlayerName() + " wins. - with " + playerTwo + playerOne;
        } else {
            return "Tie.";
        }
    }

    private String handRankResult(HandRank handRank, Card[] pokerHand, boolean winner, boolean kicker) {
        StringBuilder result = new StringBuilder();
        switch (handRank) {
            case HIGH_CARD:
                result.append("high card " + pokerHand[0]);
                break;
            case PAIR:
                result.append("a pair of " + pokerHand[0].getRank() + "'s");
                break;
            case TWO_PAIRS:
                result.append("two pairs: " + pokerHand[0].getRank() + "'s and " + pokerHand[2].getRank() + "'s");
                break;
            case THREE_OF_A_KIND:
                result.append("three of a kind: " + pokerHand[0].getRank() + "'s");
                break;
            case STRAIGHT:
                result.append("straight: " + pokerHand[4].getRank() + " to " + pokerHand[0].getRank());
                break;
            case FLUSH:
                result.append("flush: " + pokerHand[0].getSuit() + "'s");
                break;
            case FULL_HOUSE:
                result.append("full house: " + pokerHand[0].getRank().toString() + " over " + pokerHand[3].getRank());
                break;
            case FOUR_OF_A_KIND:
                result.append("four of a kind: " + pokerHand[0].getRank() + "'s");
                break;
            case STRAIGHT_FLUSH:
                result.append("straight flush: " + pokerHand[4].getRank() + " to " + pokerHand[0].getRank() + " in " + pokerHand[0].getSuit() + "'s");
                break;
        }
        if (kicker && handRank != HandRank.HIGH_CARD ||
                winner && kicker && pokerHand[0].getRank() != winningCard.getRank() ||
                !winner && kicker && pokerHand[0].getRank() != losingCard.getRank()) {
            if (winner) {
                result.append(" and a kicker of " + winningCard);
            } else {
                result.append(" and a kicker of " + losingCard);
            }
        }
        if (winner) {
            result.append(" vs. ");
        } else {
            result.append(".");
        }
        return result.toString();
    }

    // == Static Methods ==
    static void playPoker(String input) {
        input = input.trim()
                .replaceAll("\\s{2}", " ")
                .replaceAll(":", "");
        String[] temp = input.split(" ");
        int counter = 0;
        while (counter <= temp.length - 12) {
            try {
                Hand Black = createHand(Arrays.copyOfRange(temp, counter, counter + 6));
                counter += 6;
                Hand White = createHand(Arrays.copyOfRange(temp, counter, counter + 6));
                counter += 6;
                PokerGame pokerGame = new PokerGame(Black, White);
                System.out.println(pokerGame.returnWinner());
                reset();
            } catch (NullPointerException e) {
                System.out.println("Please input a valid hand comparison.");
                reset();
                while (counter <= temp.length - 12) {
                    if (temp[counter].equalsIgnoreCase("black")) {
                        break;
                    }
                    counter++;
                }
            }
        }
    }

    private static Hand createHand(String[] handString) {
        String name = handString[0];
        Card[] pokerHand = new Card[5];
        for (int i = 1; i < 6; i++) {
            Card card = new Card(handString[i]);
            pokerHand[i - 1] = card;
        }
        return new Hand(name, pokerHand);
    }

    static void reset() {
        winningCard = losingCard = null;
    }
}