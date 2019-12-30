import java.util.*;

public class Hand implements Comparable<Hand> {

    private final Card[] pokerHand;
    private final String playerName;
    private HandRank handRank;
    //figure out how to set up highcard appropriately
    private Card highCard;

    public Hand(String playerName, Card[] pokerHand) {
        this.playerName = playerName;
        this.pokerHand = pokerHand;
        Arrays.sort(this.pokerHand);
        this.handRank = setHandRank();
    }

    private HandRank setHandRank(){
        boolean FLUSH = checkFlush();
        boolean STRAIGHT = checkStraight();

        //if FLUSH is true then there can't be any pairs due to only one card with each rank being in that suit
        if(FLUSH){
            if(STRAIGHT){
                return HandRank.STRAIGHT_FLUSH;
            }
            return HandRank.FLUSH;
        }

        //check for straight here. If there is a straight there can't be a pair in five card poker
        if(STRAIGHT){
            return HandRank.STRAIGHT;
        }

        int pairOneValue, pairTwoValue, threeKindValue, fourKindValue;
        pairOneValue = pairTwoValue = threeKindValue = fourKindValue = -1;
        boolean PAIR = checkPair();
        boolean TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND;
        TWO_PAIR = THREE_OF_A_KIND = FULL_HOUSE = FOUR_OF_A_KIND = false;
        if(PAIR){
            TWO_PAIR = checkTwoPair();
            THREE_OF_A_KIND = checkThreeOfAKind();
            if(THREE_OF_A_KIND){
                FULL_HOUSE = checkFullHouse();
                FOUR_OF_A_KIND = checkFourOfAKind();
            }
        }
                return FOUR_OF_A_KIND ? HandRank.FOUR_OF_A_KIND :
                        FULL_HOUSE ? HandRank.FULL_HOUSE :
                        THREE_OF_A_KIND ? HandRank.THREE_OF_A_KIND :
                        TWO_PAIR ? HandRank.TWO_PAIRS :
                                PAIR ? HandRank.PAIR : HandRank.HIGH_CARD;
    }

    private boolean checkFlush(){
        if(pokerHand[0].getSuit() == pokerHand[1].getSuit() &&
                pokerHand[0].getSuit() == pokerHand[2].getSuit() &&
                pokerHand[0].getSuit() == pokerHand[3].getSuit() &&
                pokerHand[0].getSuit() == pokerHand[4].getSuit()) {
            return true;
        }
        return false;
    }

    private boolean checkStraight(){
        int checkRank = pokerHand[0].getRank().ordinal();
        //check for possibility of a low ace straight
        if(pokerHand[4].getRank().ordinal() == 12 && checkRank == 0){
            for(int i = 1; i < pokerHand.length-1; i++){
                if(pokerHand[i].getRank().ordinal() != checkRank+i){
                    return false;
                }
            }

            Card temp = pokerHand[4];
            for (int i = 3; i >= 0; i--) {
                pokerHand[i+1] = pokerHand[i];
            }
            pokerHand[0] = temp;
            return true;
        }
        //checking for regular straight
        for(int i = 1; i < pokerHand.length; i++){
            if(pokerHand[i].getRank().ordinal() != checkRank+i){
                return false;
            }
        }
        return true;
    }

    //cards are already sorted so if there is a pair it will be the next card in the set
    private boolean checkPair(){
        for(int i = 0; i < pokerHand.length-1; i++){
            if(pokerHand[i].getRank() == pokerHand[i+1].getRank()){
                return true;
            }
        }
        return false;
    }

    private boolean checkTwoPair(){
        int pairOne = -1;
        for(int i = 0; i < pokerHand.length-1; i++){
            if(pokerHand[i].getRank() == pokerHand[i+1].getRank()){
                if(pairOne != -1){
                    if(pokerHand[i].getRank().ordinal() != pairOne){
                        System.out.println("two pair is true");
                        return true;
                    }
                } else {
                    pairOne = pokerHand[i].getRank().ordinal();
                }
            }
        }
        return false;
    }

    private boolean checkThreeOfAKind(){
        for(int i = 0; i < pokerHand.length-2; i++){
            if(pokerHand[i].getRank() == pokerHand[i+1].getRank() &&
            pokerHand[i].getRank() == pokerHand[i+2].getRank()){
                return true;
            }
        }
        return false;
    }

    private boolean checkFullHouse(){
        for(int i = 0; i < pokerHand.length-2; i++){
            if(pokerHand[i].getRank() == pokerHand[i+1].getRank() &&
                    pokerHand[i].getRank() == pokerHand[i+2].getRank()){
                return true;
            }
        }
        return false;
    }

    private boolean checkFourOfAKind(){
        for(int i = 0; i < 2; i++){
            if(pokerHand[i].getRank() == pokerHand[i+1].getRank() &&
                    pokerHand[i].getRank() == pokerHand[i+2].getRank() &&
                    pokerHand[i].getRank() == pokerHand[i+3].getRank()){
                this.highCard = i == 0 ? pokerHand[4] : pokerHand[0];
                return true;
            }
            return false;
        }
        return false;
    }


    public String getPlayerName() {
        return this.playerName;
    }

    public HandRank getHandRank() {
        return this.handRank;
    }

    public Card getHighCard() {
        return this.highCard;
    }

    public Card[] getPokerHand() {
        return this.pokerHand;
    }

    @Override
    public int compareTo(Hand opponentHand) {
        System.out.println("Hand1 = " + this.handRank);
        System.out.println("Hand2 = " + opponentHand.handRank);
        if(this.handRank.ordinal() != opponentHand.handRank.ordinal()){
            return this.handRank.ordinal() > opponentHand.handRank.ordinal() ? 1 : -1;
        }
        return 0;
    }
}