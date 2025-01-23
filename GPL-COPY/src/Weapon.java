import java.util.Random;
import java.util.regex.Pattern;

// Represents a weapon used by a player while attacking
public class Weapon {

    String weaponType;
    String weaponAction;

    public Weapon(String type, String action) {
        this.weaponType = type;
        this.weaponAction = action;
    }

    public String Strike(int damage) {
        return switch (this.weaponType) {
            case "sword" -> "The " + this.weaponType + " " + this.weaponAction + "es and causes " + damage + " damage";
            case "bat" -> "The " + this.weaponType + " " + this.weaponAction + "'s and causes " + damage + " damage";
            default -> "Invalid weapon type - Weapon: Strike()";
        };
    }

}
