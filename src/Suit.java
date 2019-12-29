import java.util.HashMap;
import java.util.Map;

public enum Suit {
    HEARTS('H'),
    DIAMONDS('D'),
    SPADES('S'),
    CLUBS('C');

    private final char suitVal;

    public static final Map<Character, Suit> suitValueMap = new HashMap<>();

    static {
        for (Suit val : Suit.values()) {
            suitValueMap.put(val.getSuitVal(), val);
        }
    }

    Suit(char suitLetterVal) {
        this.suitVal = suitLetterVal;
    }

    public char getSuitVal() {
        return this.suitVal;
    }

    public static Suit createSuitValue(char suitLetterVal) {
        Suit temp = suitValueMap.get(suitLetterVal);
        return temp;
    }

    @Override
    public String toString(){
        String name = name();
        String newName = "";
        switch (name){
            case "HEARTS":
                newName = "\u2764";
                break;
            case "DIAMONDS":
                newName = "\u2666";
                break;
            case "SPADES":
                newName = "\u2660";
                break;
            case "CLUBS":
                newName = "\u2663";
                break;
        }
        return newName;
    }
}