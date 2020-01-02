import java.util.HashMap;
import java.util.Map;

public enum Rank {
    TWO ('2'),
    THREE ('3'),
    FOUR ('4'),
    FIVE ('5'),
    SIX ('6'),
    SEVEN ('7'),
    EIGHT ('8'),
    NINE ('9'),
    TEN ('T'),
    JACK ('J'),
    QUEEN ('Q'),
    KING ('K'),
    ACE ('A');

    // == Fields ==
    private final char numVal;
    public static final Map<Character, Rank> cardValueMap = new HashMap<>();

    // == Static Block ==
    static {
        for(Rank val: Rank.values()){
            cardValueMap.put(val.getNumVal(), val);
        }
    }

    // == Constructors ==
    Rank(char cardNumVal){
        this.numVal = cardNumVal;
    }

    // == Getters ==
    public char getNumVal(){
        return this.numVal;
    }

    // == Static Methods ==
    public static Rank createCardValue(char cardNumVal){
        Rank temp = cardValueMap.get(cardNumVal);
        return temp;
    }

    // == Overridden Methods ==
    @Override
    public String toString(){
        String name = name();
        String newName = "";
        switch (name){
            case "TWO":
                newName = "2";
                break;
            case "THREE":
                newName = "3";
                break;
            case "FOUR":
                newName = "4";
                break;
            case "FIVE":
                newName = "5";
                break;
            case "SIX":
                newName = "6";
                break;
            case "SEVEN":
                newName = "7";
                break;
            case "EIGHT":
                newName = "8";
                break;
            case "NINE":
                newName = "9";
                break;
            case "TEN":
                newName = "10";
                break;
            case "JACK":
                newName = "J";
                break;
            case "QUEEN":
                newName = "Q";
                break;
            case "KING":
                newName = "K";
                break;
            case "ACE":
                newName = "A";
                break;
        }

        return newName;
    }

}