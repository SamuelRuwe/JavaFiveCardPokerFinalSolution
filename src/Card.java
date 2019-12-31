public class Card implements Comparable<Card>{

    // == Fields ==
    private final Rank rank;
    private final Suit suit;

    // == Constructors ==
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(String cardInput){
        char[] cardValues = cardInput.toCharArray();
        this.rank = Rank.createCardValue(cardValues[0]);
        this.suit = Suit.createSuitValue(cardValues[1]);
    }

    // == Getters ==
    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    // == Overridden Methods ==
    @Override
    public String toString() {
        return  "" + this.getRank()+ this.getSuit();
    }

    @Override
    public int compareTo(Card card) {
        return this.getRank().ordinal() > card.getRank().ordinal() ? 1 :
                this.getRank().ordinal() < card.getRank().ordinal() ? -1 : 0;
    }
}