import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // HOLDS THE PLAYER (COMPUTER)
    private static Player computerPlayer = null;

    // HOLDS THE PLAYER (USER)
    private static Player player = null;

    public static void main(String[] args) {

        boolean quitGame = false; // USED TO QUIT THE GAME FROM THE MENU
        boolean gameComplete = false; // USED TO DETERMINE IF THE GAME IS COMPLETE
        boolean resetToMain = false; // USED TO RESET BACK TO THE BEGINNING MENU

        // repeat until the player stops or someone reaches 0 hp
        while (!gameComplete) {

            // CONFIRM THE PLAYER WANTS TO PLAY THE GAME
            System.out.println("Hello, would you like to play the game?");
            System.out.println("1) Yes");
            System.out.println("2) No");

            Scanner scanner = new Scanner(System.in); // SCANNER OBJECT TO RECEIVE USER INPUT
            String userAnswer = scanner.nextLine(); // GRAB THE USER'S ANSWER FROM ABOVE

            // INVALID INPUT
            if (!userAnswer.equals("1") && !userAnswer.equals("2") && !userAnswer.equalsIgnoreCase("yes") && !userAnswer.equalsIgnoreCase("no")) {
                System.out.println("Invalid input, please try again.");
            }
            // DON'T WANT TO PLAY SO EXIT
            else if (userAnswer.equals("2") || userAnswer.equalsIgnoreCase("no")) {
                System.out.println("Thank you for playing!");
                quitGame = true;
                break;
            }
            // OK LET'S PLAY
            else if (userAnswer.equals("1") || userAnswer.equalsIgnoreCase("yes")) {

                // SETUP A NEW PLAYER
                player = setupPlayer();

                // IF THE PLAYER FROM ABOVE EXISTS, CONTINUE
                if (player != null) {

                    // WHILE THE GAME IS NOT COMPLETE, DO EVERYTHING WITHIN
                    while (!gameComplete) {

                        String isPlayerSetupAnswer; // VARIABLE TO HOLD THE USERS ANSWER IF THEY WANT TO CONTINUE TO PLAY

                        // DO WHILE LOOP THAT REPEATS THE INCLUDED INFORMATION UNTIL A VALID ANSWER IS INPUT
                        do {

                            // PRINT THE INFORMATION TO THE PLAYER TO MAKE SURE EVERYTHING IS CORRECT
                            System.out.println("Ok, " + player.getPlayerName() + " is using a " + player.getWeapon().weaponType.toLowerCase() + ", is that correct?");
                            System.out.println("1) Yes, lets play!");
                            System.out.println("2) No, I want to change something.");
                            System.out.println("3) Quit");
                            isPlayerSetupAnswer = scanner.nextLine(); // GRAB THE USER ANSWER

                            // IF NOT THE ANSWER IS NOT 1,2, OR 3, NOTIFY THE USER TO TRY AGAIN WITH A VALID INPUT
                            if (!isPlayerSetupAnswer.equals("1") && !isPlayerSetupAnswer.equals("2") && !isPlayerSetupAnswer.equals("3")) {
                                System.out.println("Not a valid input, please try again.");
                            }

                        } while (!isPlayerSetupAnswer.equals("1") && !isPlayerSetupAnswer.equals("2") && !isPlayerSetupAnswer.equals("3"));

                        // USE THE RECEIVED USER ANSWER TO CALL THE NECESSARY ANSWER
                        switch (isPlayerSetupAnswer) {

                            case "1": // PLAY THE GAME
                                gameComplete = playGame(player);
                                break;
                            case "2": // CHANGE SOMETHING
                                System.out.println("Go back");
                                player = null;
                                resetToMain = true;
                                break;
                            case "3": // QUIT THE GAME
                                quitGame = true;
                                System.out.println("Sorry to see you go :(");
                                break;
                        }
                        if (resetToMain || quitGame) break; // BREAK OUT IF USER CHOOSES THEY WANT TO CHANGE SOMETHING
                    }

                }
            }

            if (gameComplete || quitGame) {
                if (quitGame) break; // break out and quit the game
                displayEndingDetails(player);
            }
        }
    }

    // START THE GAME WITH THIS METHOD
    public static boolean playGame(Player player) {

        boolean gameComplete = false; // INITIALLY SET THE GAME COMPLETE BOOLEAN TO FALSE
        Scanner scanner = new Scanner(System.in); // NEW SCANNER OBJECT TO READ USER INPUT

        // WELCOME MESSAGE WITH BASIC PLAYER INFO
        System.out.println("Welcome " + player.getPlayerName());
        System.out.println("You're current health is " + player.getHealth() + " and you're using a " + player.getWeapon().weaponType);

        // WHILE GAME IS NOT COMPLETE, DO THE FOLLOWING
        while (!gameComplete) {

            // SETTING UP THE COMPUTER PLAYER
            while (true) {
                System.out.println("Setting up an opposing player...");
                computerPlayer = setupComputerPlayer();
                if (computerPlayer != null) {
                    System.out.println("Opposing player successfully setup!");
                    System.out.println("You're battling " + computerPlayer.getPlayerName() + " who's using a " + computerPlayer.getWeapon().weaponType + ".");
                    System.out.println("You attack first! When ready, press enter to continue.");
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("Opposing player failed to setup!");
                }
            }

            // DO-WHILE LOOP TO REPEAT THE ATTACKS BETWEEN USER AND COMPUTER UNTIL SOMEONE WINS
            do {
                Random rand = new Random(); // NEW RANDOM OBJECT TO RANDOMIZE SOME THINGS

                // Player attacks computer
                int randComputerDmg = rand.nextInt(5, 20);
                computerPlayer.attacked(randComputerDmg);
                System.out.println("You swing your " + player.getWeapon().weaponType + " at " + computerPlayer.getPlayerName());
                System.out.println(player.getWeapon().Strike(randComputerDmg));
                System.out.println(computerPlayer.getPlayerName() + "'s health is now at " + (Math.max(computerPlayer.getHealth(), 0)));
                System.out.println("Press enter to continue. Get ready to defend yourself!");
                scanner.nextLine();

                // IF THE COMPUTERS HEALTH IS LESS THAN OR EQUAL TO 0, THE GAME IS OVER SO BREAK OUT AND STOP THE GAME
                if (computerPlayer.getHealth() <= 0) {
                    gameComplete = true;
                    break;
                }

                // Computer attacks player
                int randPlayerDmg = rand.nextInt(5, 20);
                player.attacked(randPlayerDmg);
                System.out.println(computerPlayer.getPlayerName() + " swings their " + computerPlayer.getWeapon().weaponType + " at you.");
                System.out.println(computerPlayer.getWeapon().Strike(randPlayerDmg));
                System.out.println("Your health is now at " + (Math.max(player.getHealth(), 0)));
                System.out.println("Press enter to continue. Get ready to attack!");
                scanner.nextLine();

                // IF THE PLAYERS HEALTH IS LESS THAN OR EQUAL TO 0, THE GAME IS OVER SO BREAK OUT AND STOP THE GAME
                if (player.getHealth() <= 0) {
                    gameComplete = true;
                }

            } while (!gameComplete);

        }
        return true;
    }

    // method to set up a player and return said player
    public static Player setupPlayer() {

        // variables to hold the name, weapon, scanner (to receive user input), and the answer (for repeating if needed)
        String playerName = "";
        String playerWeaponAnswer = "";
        Weapon playerWeapon = null;
        Scanner scanner = new Scanner(System.in);

        // GET THE NAME OF THE PLAYER
        while (playerName.isEmpty()) {
            System.out.println("Please enter a name for the player: ");
            playerName = scanner.nextLine();
        }

        do {

            // choose weapon (SHOULD I ADD MORE WEAPONS?)
            System.out.println("Please choose a weapon.");
            System.out.println("1) Sword");
            System.out.println("2) Bat");
            playerWeaponAnswer = scanner.nextLine();

            if (!playerWeaponAnswer.equals("1") && !playerWeaponAnswer.equals("2")) {
                System.out.println("Invalid input, please try again.");
            }

            switch (playerWeaponAnswer) {
                case "1" -> {
                    playerWeapon = new Weapon("sword", "slash");
                }
                case "2" -> {
                    playerWeapon = new Weapon("bat", "bonk");
                }
            }

        } while (playerWeapon == null); // setup a player's weapon
        return new Player(playerName, 100, playerWeapon); // RETURN THE PLAYER THAT'S BEING SETUP USING THE INFORMATION GATHERED
    }

    // CREATES A COMPUTER PLAYER USING THE PlayerNames CLASS TO RANDOMLY GENERATE A NAME FROM THE INCLUDED ARRAY
    public static Player setupComputerPlayer() {
        PlayerNames names = new PlayerNames(); // instantiate the PlayerNames class since we're not extending from it
        String randomName = names.randomName(); // call the randomName method to generate a random name from the array
        return new Player(randomName, 100, Player.randomWeapon()); // return a randomly generated player
    }

    // DISPLAYS THE OUTCOME OF THE GAME WITH SOME DETAILS
    static void displayEndingDetails(Player player) {

        // ternary operators for player and computerPlayer outcomes (used in the progress report)
        String outcome = (player.getHealth() >= 0) ? "Win!" : "Lost :(";
        String compOutcome = (computerPlayer.getHealth() >= 0) ? "won" : "lost :)";

        // print out the details of the game
        System.out.println("/----------------- Game Complete ~ You " + outcome + " -----------------\\");
        System.out.println("| Player Name: " + player.getPlayerName());
        System.out.println("| Player Weapon: " + player.getWeapon().weaponType);
        System.out.println("| " + player.getPlayerName() + "'s remaining health: " + ((player.getHealth() > 0) ? player.getHealth() : "0")); // PRINTS OUT THE REMAINING HEALTH IF MORE THAN 0, OTHERWISE IT PRINTS 0 (PROTECTS AGAINST NEGATIVE VALUES BEING DISPLAYED)
        System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~ You're Opponent " + compOutcome + " ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("| Opponent Name: " + computerPlayer.getPlayerName());
        System.out.println("| Opponent Weapon: " + computerPlayer.getWeapon().weaponType);
        System.out.println("| " + computerPlayer.getPlayerName() + "'s remaining health: " + (computerPlayer.getHealth() > 0 ? computerPlayer.getHealth() : "0")); // PRINTS OUT THE REMAINING HEALTH IF MORE THAN 0, OTHERWISE IT PRINTS 0 (PROTECTS AGAINST NEGATIVE VALUES BEING DISPLAYED)
        System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~ Thanks for playing ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

}