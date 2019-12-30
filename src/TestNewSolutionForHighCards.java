//import java.util.*;
//
//public class Hand implements Comparable<Hand> {
//
//    private final Card[] pokerHand;
//    private final String playerName;
//    private HandRank handRank;
//    private ArrayList<Rank> highCards = new ArrayList<>();
//
//    public Hand(String playerName, Card[] pokerHand) {
//        this.playerName = playerName;
//        this.pokerHand = pokerHand;
//        Arrays.sort(this.pokerHand);
//        this.handRank = setHandRank();
//    }
//
//    private HandRank setHandRank(){
//        boolean FLUSH = checkFlush(this.pokerHand);
//        boolean STRAIGHT = checkStraight(this.pokerHand);
//
//        //check for straight here. If there is a straight there can't be a pair in five card poker
//        if(STRAIGHT){
//            this.highCards.add(this.pokerHand[4].getRank());
//            if(FLUSH){
//                System.out.println("straight-flush!");
//                return  HandRank.STRAIGHT_FLUSH;
//            } else {
//                System.out.println("straight!");
//                return HandRank.STRAIGHT;
//            }
//        }
//
//        Map<Rank, Integer> cardCount = new LinkedHashMap<>();
//        for(int i = pokerHand.length; i > 0; i--){
//            if(cardCount.containsKey(pokerHand[i-1].getRank())) {
//                cardCount.put(pokerHand[i-1].getRank(), cardCount.get(pokerHand[i-1].getRank()) + 1);
//            } else {
//                cardCount.put(pokerHand[i-1].getRank(), 1);
//            }
//        }
//
//        if(FLUSH){
//            return HandRank.FLUSH;
//        }
//        System.out.println("card count = " + cardCount);
//
//        boolean PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE;
//        PAIR = TWO_PAIR = THREE_OF_A_KIND = FULL_HOUSE = false;
//        for(Map.Entry<Rank, Integer> entry: cardCount.entrySet()){
//            if(entry.getValue() == 4){
//                this.highCards.add(entry.getKey());
//                return HandRank.FOUR_OF_A_KIND;
//            } else if(entry.getValue() == 3){
//                this.highCards.add(0, entry.getKey());
//                THREE_OF_A_KIND = true;
//                if(PAIR){
//                    FULL_HOUSE = true;
//                }
//            } else if(entry.getValue() == 2){
//                this.highCards.add(entry.getKey());
//                if(PAIR) {
//                    TWO_PAIR = true;
//                    //we know the cards are already organized by ascending rank so we can put any pairs after the first
//                    //before the first pair in the highcard list
//                    this.highCards.add(0, entry.getKey());
//                    //make sure the order is correct for high card here
//                } else {
//                    PAIR = true;
//                    this.highCards.add(entry.getKey());
//                }
//            }
//            this.highCards.add(entry.getKey());
//        }
//        System.out.println("highcards = " + this.highCards);
//
//        return FULL_HOUSE ? HandRank.FULL_HOUSE :
//                THREE_OF_A_KIND ? HandRank.THREE_OF_A_KIND :
//                        TWO_PAIR ? HandRank.TWO_PAIRS :
//                                PAIR ? HandRank.PAIR : HandRank.HIGH_CARD;
//    }
//
//    private boolean checkFlush(Card[] pokerHand){
//        if(pokerHand[0].getSuit() == pokerHand[1].getSuit() &&
//                pokerHand[0].getSuit() == pokerHand[2].getSuit() &&
//                pokerHand[0].getSuit() == pokerHand[3].getSuit() &&
//                pokerHand[0].getSuit() == pokerHand[4].getSuit()) {
//            return true;
//        }
//        return false;
//    }
//
//    private boolean checkStraight(Card[] pokerHand){
//        int checkRank = pokerHand[0].getRank().ordinal();
//        //2 is ordinal 0. Checking for if low ace straight is posible here
//        if(checkRank == 0){
//            for(int i = 1; i < pokerHand.length-1; i++){
//                if(pokerHand[i].getRank().ordinal() != checkRank+i){
//                    return false;
//                }
//            }
//            return pokerHand[4].getRank() == Rank.ACE ? true : false;
//        }
//        //checking for regular straight
//        for(int i = 1; i < pokerHand.length; i++){
//            if(pokerHand[i].getRank().ordinal() != checkRank+i){
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public String getPlayerName() {
//        return playerName;
//    }
//
//    public HandRank getHandRank() {
//        return handRank;
//    }
//
//    public ArrayList<Rank> getHighCards() {
//        return highCards;
//    }
//
//    @Override
//    public int compareTo(Hand opponentHand) {
//        System.out.println("Hand1 = " + this.handRank);
//        System.out.println("Hand2 = " + opponentHand.handRank);
//        if(this.handRank.ordinal() != opponentHand.handRank.ordinal()){
//            return this.handRank.ordinal() > opponentHand.handRank.ordinal() ? 1 : -1;
//        }
//        if(this.highCards.equals(opponentHand.highCards)){
//            return 0;
//        }
//        for(int i = 0; i < this.highCards.size(); i++){
//            if(this.highCards.get(i).ordinal() != opponentHand.highCards.get(i).ordinal()){
//                return this.highCards.get(i).ordinal() > opponentHand.highCards.get(i).ordinal() ? 1 : 0;
//            }
//        }
//        return 0;
//    }
//}








//    private boolean checkTwoPair(){
//        int pairOne = -1;
//        for(int i = 0; i < pokerHand.length-1; i++){
//            if(pokerHand[i].getRank() == pokerHand[i+1].getRank()){
//                if(pairOne != -1){
//                    if(pokerHand[i].getRank().ordinal() != pairOne){
//                        System.out.println("two pair is true");
//                        return true;
//                    }
//                } else {
//                    pairOne = pokerHand[i].getRank().ordinal();
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean checkThreeOfAKind(){
//        for(int i = 0; i < pokerHand.length-2; i++){
//            if(pokerHand[i].getRank() == pokerHand[i+1].getRank() &&
//            pokerHand[i].getRank() == pokerHand[i+2].getRank()){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean checkFullHouse(){
//        for(int i = 0; i < pokerHand.length-2; i++){
//            if(pokerHand[i].getRank() == pokerHand[i+1].getRank() &&
//                    pokerHand[i].getRank() == pokerHand[i+2].getRank()){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean checkFourOfAKind(){
//        for(int i = 0; i < 2; i++){
//            if(pokerHand[i].getRank() == pokerHand[i+1].getRank() &&
//                    pokerHand[i].getRank() == pokerHand[i+2].getRank() &&
//                    pokerHand[i].getRank() == pokerHand[i+3].getRank()){
//                this.highCard = i == 0 ? pokerHand[4] : pokerHand[0];
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }


//check for full house
//        if(pairOneValue >= 0 && i != 0){
//            for(Card card: pokerHand){
//                if(card.getRank().ordinal() == pairOneValue){
//                    this.highCard[i] = card;
//                    i++;
//                }
//            }
//            return;
//        }