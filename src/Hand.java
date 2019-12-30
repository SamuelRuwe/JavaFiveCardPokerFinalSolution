import java.util.*;

public class Hand implements Comparable<Hand> {

    private final Card[] pokerHand;
    private final String playerName;
    private HandRank handRank;
    private int pairOneValue, pairTwoValue, threeOfKindValue, fourOfKindValue;
    //figure out how to set up highcard appropriately
    private Card[] highCard = new Card[5];

    public Hand(String playerName, Card[] pokerHand) {
        this.playerName = playerName;
        this.pokerHand = pokerHand;
        this.pairOneValue = this.pairTwoValue = this.threeOfKindValue = this.fourOfKindValue = -1;
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

        //change this code so it all runs off the one function
        setMultiCards();
        setHighCards();
                return fourOfKindValue >= 0 ? HandRank.FOUR_OF_A_KIND :
                        threeOfKindValue >= 0 && pairOneValue >= 0 ? HandRank.FULL_HOUSE :
                        threeOfKindValue >= 0 ? HandRank.THREE_OF_A_KIND :
                        pairTwoValue >= 0 ? HandRank.TWO_PAIRS :
                                pairOneValue >= 0 ? HandRank.PAIR : HandRank.HIGH_CARD;
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
    private void setMultiCards(){
        for(int i = 0; i < pokerHand.length-1; i++){
//            System.out.println("i = " + i);
            if(pokerHand[i].getRank() == pokerHand[i+1].getRank()){
                //this should evaluate to false before checking the next value so it won't cause an out of bounds exception
                if((i < pokerHand.length-2 && pokerHand[i].getRank().ordinal() != pokerHand[i+2].getRank().ordinal()) || i == 3){
                    if(pairOneValue == -1){
//                        System.out.println("in pair one " + playerName);
                        this.pairOneValue = pokerHand[i].getRank().ordinal();
                    } else {
//                        System.out.println("in pair two " + playerName);
                        this.pairTwoValue = pokerHand[i].getRank().ordinal();
                    }
//                    System.out.println("pair one value = " + pairOneValue);
                    i++;
                } else if(i < pokerHand.length-2 && pokerHand[i].getRank().ordinal() == pokerHand[i+2].getRank().ordinal()){
                    if(i < pokerHand.length-3 && pokerHand[i].getRank().ordinal() == pokerHand[i+3].getRank().ordinal()){
//                        System.out.println("in four of kind");
                        this.fourOfKindValue = pokerHand[i].getRank().ordinal();
                        i+=3;
                    } else if(fourOfKindValue == -1) {
//                        System.out.println("in three of kind");
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
                    this.highCard[i] = card;
                    i++;
                } else{
                    this.highCard[4] = card;
                }
            }
            return;
        }
        if(threeOfKindValue >= 0){
            for(Card card: pokerHand){
                if(card.getRank().ordinal() == threeOfKindValue){
                    this.highCard[i] = card;
                    i++;
                }
            }
        }
        //check for full house
        if(pairTwoValue >= 0 && i != 0){
            for(Card card: pokerHand){
                if(card.getRank().ordinal() == pairTwoValue){
                    this.highCard[i] = card;
                    i++;
                }
            }
            return;
        }
        if(pairTwoValue >= 0){
//            System.out.println("in two pair value high card setter i = " + i);
            int j = 2;
            for(Card card: pokerHand){
                if(card.getRank().ordinal() == pairTwoValue){
                    this.highCard[i] = card;
                    i++;
                } else if(card.getRank().ordinal() == pairOneValue){
                    this.highCard[j] = card;
                    j++;
                } else {
                    this.highCard[4] = card;
                }
            }
            return;
        }
        if(pairOneValue >= 0){
            int j = 4;
            for(Card card: pokerHand){
                if(card.getRank().ordinal() == pairOneValue){
                    this.highCard[i] = card;
                    i++;
                } else{
                    this.highCard[j] = card;
                    j--;
                }
            }
            return;
        }
        for(int j = 4; j >= 0; j--){
            this.highCard[j] = pokerHand[i];
            i++;
            j--;
        }

//
//        System.out.println("four of kind = " + fourOfKindValue);
//        System.out.println("three of kind = " + threeOfKindValue);
//        System.out.println("pairOne value = " + pairOneValue);
//        System.out.println("pair two value = " + pairTwoValue);
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public HandRank getHandRank() {
        return this.handRank;
    }

    public Card[] getHighCard() {
        return this.highCard;
    }

    public Card[] getPokerHand() {
        return this.pokerHand;
    }

    @Override
    public int compareTo(Hand opponentHand) {
//        System.out.println("Hand1 = " + this.handRank);
//        System.out.println("Hand2 = " + opponentHand.handRank);
        if(this.handRank.ordinal() != opponentHand.handRank.ordinal()){
            return this.handRank.ordinal() > opponentHand.handRank.ordinal() ? 1 : -1;
        }
        for(int i = 0; i < pokerHand.length; i++){
            if(this.pokerHand[i].getRank().ordinal() != opponentHand.pokerHand[i].getRank().ordinal()){
                return this.pokerHand[i].getRank().ordinal() > opponentHand.pokerHand[i].getRank().ordinal() ? 1 : -1;
            }
        }
        return 0;
    }

}