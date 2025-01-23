import java.util.Random;

public class Player {

    // PLAYER PROPERTIES
    private String playerName;
    private int health = 100;
    private Weapon weapon;

    private int tempDamage; // placeholder in case I need it

    // constructor for the player
    public Player(String playerName, int health, Weapon weapon) {
        this.playerName = playerName;
        this.health = health;
        this.weapon = weapon;
    }

    // getter for the players private weapon property
    public Weapon getWeapon() {
        return weapon;
    }

    // getter for the players private name property
    public String getPlayerName() {
        return playerName;
    }

    // getter for the players private health property
    public int getHealth() {
        return health;
    }

    // setter for the players private health property
    public void setHealth(int health) {
        this.health = health;
    }

    // a method to show that the player has been attacked
    public String attacked(int damage) {

        // make sure the player never goes under 0 health
        if (this.health <= 0) {
            this.health = 0; // reduce the players health if greater than 0
        } else
            this.health -= damage;

        // return the current health of said player
        return this.playerName + "'s is now at " + this.health + " health";
    }

    // choose a random weapon for the player
    public static Weapon randomWeapon() {
        Random rand = new Random();
        switch (rand.nextInt(1,10)) {
            case 1, 2, 3, 4, 5 -> {
                return new Weapon("sword", "slash");
            }
            case 6, 7, 8, 9, 10 -> {
                return new Weapon("bat", "bonk");
            }
            default -> {
                return null;
            }
        }
    }

}