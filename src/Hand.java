import java.util.*;

public class Hand implements Comparable<Hand> {

    // == Fields ==
    private final Card[] pokerHand;
    private final String playerName;
    private HandRank handRank;
    private int pairOneValue, pairTwoValue, threeOfKindValue, fourOfKindValue;
    private Card[] highCards = new Card[5];

    // == Constructors ==
    public Hand(String playerName, Card[] pokerHand) {
        this.playerName = playerName;
        this.pokerHand = pokerHand;
        this.pairOneValue = this.pairTwoValue = this.threeOfKindValue = this.fourOfKindValue = -1;
        Arrays.sort(this.pokerHand);
        this.handRank = setHandRank();
        setHighCards();
    }

    // == Getters ==
    public String getPlayerName() {
        return this.playerName;
    }

    public HandRank getHandRank() {
        return this.handRank;
    }

    public Card[] getHighCards() {
        return this.highCards;
    }

    // == Setters ==
    private void setMultiCards(){
        for(int i = 0; i < pokerHand.length-1; i++){
            if(pokerHand[i].getRank() == pokerHand[i+1].getRank()){
                //this should evaluate to false before checking the next value so it won't cause an out of bounds exception
                if((i < pokerHand.length-2 && pokerHand[i].getRank().ordinal() != pokerHand[i+2].getRank().ordinal()) || i == 3){
                    if(pairOneValue == -1){
                        this.pairOneValue = pokerHand[i].getRank().ordinal();
                    } else {
                        this.pairTwoValue = pokerHand[i].getRank().ordinal();
                    }
                    i++;
                } else if(i < pokerHand.length-2 && pokerHand[i].getRank().ordinal() == pokerHand[i+2].getRank().ordinal()){
                    if(i < pokerHand.length-3 && pokerHand[i].getRank().ordinal() == pokerHand[i+3].getRank().ordinal()){
                        this.fourOfKindValue = pokerHand[i].getRank().ordinal();
                        i+=3;
                    } else if(fourOfKindValue == -1) {
                        this.threeOfKindValue = pokerHand[i].getRank().ordinal();
                        i+=2;
                    }
                }
            }
        }
    }

    private void setHighCards(){
        int i = 0;
        if(fourOfKindValue >= 0){
            for(Card card: pokerHand){
                if(card.getRank().ordinal() == fourOfKindValue){
                    this.highCards[i] = card;
                    i++;
                } else{
                    this.highCards[4] = card;
                }
            }
            return;
        }

        if(threeOfKindValue >= 0){
            int j = 4;
            for(Card card: pokerHand){
                if(card.getRank().ordinal() == threeOfKindValue){
                    this.highCards[i] = card;
                    i++;
                } else {
                    this.highCards[j] = card;
                    j--;
                }
            }
            return;
        }

        if(pairTwoValue >= 0){
            int j = 2;
            for(Card card: pokerHand){
                if(card.getRank().ordinal() == pairTwoValue){
                    this.highCards[i] = card;
                    i++;
                } else if(card.getRank().ordinal() == pairOneValue){
                    this.highCards[j] = card;
                    j++;
                } else {
                    this.highCards[4] = card;
                }
            }
            return;
        }

        if(pairOneValue >= 0){
            int j = 4;
            for(Card card: pokerHand){
                if(card.getRank().ordinal() == pairOneValue){
                    this.highCards[i] = card;
                    i++;
                } else{
                    this.highCards[j] = card;
                    j--;
                }
            }
            return;
        }

        for(int j = 4; j >= 0; j--){
            this.highCards[j] = pokerHand[i];
            i++;
        }
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

        setMultiCards();
                return fourOfKindValue >= 0 ? HandRank.FOUR_OF_A_KIND :
                        threeOfKindValue >= 0 && pairOneValue >= 0 ? HandRank.FULL_HOUSE :
                        threeOfKindValue >= 0 ? HandRank.THREE_OF_A_KIND :
                        pairTwoValue >= 0 ? HandRank.TWO_PAIRS :
                                pairOneValue >= 0 ? HandRank.PAIR : HandRank.HIGH_CARD;
    }

    // == Class Methods to Check for HandRanks ==
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

    // == Overriden Methods ==
    @Override
    public int compareTo(Hand opponentHand) {
        if(this.handRank.ordinal() != opponentHand.handRank.ordinal()){
            return this.handRank.ordinal() > opponentHand.handRank.ordinal() ? 1 : -1;
        }
        for(int i = 0; i < highCards.length; i++){
            if(this.highCards[i].getRank().ordinal() != opponentHand.highCards[i].getRank().ordinal()){
                if(this.highCards[i].getRank().ordinal() > opponentHand.highCards[i].getRank().ordinal()){
                    PokerGame.setWinningCard(this.highCards[i]);
                    PokerGame.setLosingCard(opponentHand.highCards[i]);
                    return 1;
                } else if (this.highCards[i].getRank().ordinal() < opponentHand.highCards[i].getRank().ordinal()){
                    PokerGame.setWinningCard(opponentHand.highCards[i]);
                    PokerGame.setLosingCard(this.highCards[i]);
                    return -1;
                }
            }
        }
        return 0;
    }
}