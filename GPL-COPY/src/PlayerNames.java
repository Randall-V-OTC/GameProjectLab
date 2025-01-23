import java.util.Random;

public class PlayerNames {

    // I USED CHATGPT TO GIVE ME AN ARRAY OF 50 RANDOM NAMES TO CHOOSE FROM
    private final String[] RANDOM_NAMES = {
            "ALPHA123", "BETA456", "GAMMA789", "DELTA101", "PLAYER99",
            "SPEEDY42", "NINJA007", "XENON34", "CYBER88", "ROCKET21",
            "ZENITH50", "STARFALL5", "ECLIPSE12", "SOLAR27", "GALAXY3",
            "HYPER99", "TURBO64", "VORTEX11", "ORION67", "NEBULA23",
            "SHADOW6", "FLARE19", "PHANTOM31", "SPECTRE90", "NOVA77",
            "RAPTOR4", "MAVERICK8", "TITAN15", "JAVELIN44", "PREDATOR53",
            "FALCON89", "HAWK32", "WOLF21", "GRYPHON65", "SPARROW16",
            "THUNDER2", "STORM8", "CYCLONE17", "TYPHOON49", "HURRICANE7",
            "LIGHTNING1", "VOLTAGE3", "FLASH99", "SPARK88", "POWER23",
            "ELECTRO66", "MAGNETRON9", "SHOCKWAVE5", "QUANTUM33", "RADON72"
    };

    // CHOOSE A RANDOM NAME FROM THE RANDOM_NAMES ARRAY
    public String randomName() {
        Random rand = new Random();
        return RANDOM_NAMES[rand.nextInt(RANDOM_NAMES.length)];
    }

}
