package it.polimi.ingsw.utils;

/**
 * Strings used to display messages to the client on the CLI and default constants declaration
 */
public class StaticUtils {

    //default values
    public final static int DEFAULT_SIZE = 10;
    public final static int DEFAULT_LOCAL_LOBBY_ID = 7069;
    public final static String DEFAULT_IP_ADDRESS = "127.0.0.1";
    public final static String DEFAULT_SERVER_PORT = "12345";
    public final static String LOCAL = "local";
    public final static String LOCAL_GAME_USERNAME = "you";

    //these are the int constants used
    public final static int EXTRA_DEPOT_MAX_SIZE = 2;
    public final static int FIRST_VATICAN_SECTION = 5;
    public final static int SECOND_VATICAN_SECTION = 12;
    public final static int THIRD_VATICAN_SECTION = 19;
    public final static int CARDS_TO_END_GAME = 7;
    public final static int NUMBER_OF_WAR_DEPOTS = 3;
    public final static int NUMBER_OF_DEV_DECKS = 12;
    public final static int DEFAULT_ERROR_NUM = -1;
    public final static int ROW_SIZE = 4;
    public final static int COL_SIZE = 3;
    public final static int BLACK_TOKEN_SPACES = 2;
    public final static int COLORED_TOKEN_CARDS_TO_DESTROY = 2;

    //faith track constants
    public final static int FAITH_TRACK_SIZE = 25;
    public final static int START_FIRST_VATICAN_SECTION = 5;
    public final static int END_FIRST_VATICAN_SECTION = 8;
    public final static int START_SECOND_VATICAN_SECTION = 12;
    public final static int END_SECOND_VATICAN_SECTION = 16;
    public final static int START_THIRD_VATICAN_SECTION = 19;
    public final static int END_THIRD_VATICAN_SECTION = 24;
    public final static int FIRST_POPE_FAVOR_TILE_SCORE = 2;
    public final static int SECOND_POPE_FAVOR_TILE_SCORE = 3;
    public final static int THIRD_POPE_FAVOR_TILE_SCORE = 4;

    //printing facilities
    public final static String listOfResources = printGreen(" 1")+ "-> " + printYellow("coin") + ", " + printGreen("2") +"-> " + printGrey("stone") + ", " + printGreen("3") + "-> " + printBlue("shield") + ", " + printGreen("4") +"-> " + printPurple("servant");
    public final static String listOfLeaderAction = printGreen(" act1") + " -> activate first leader card," + printGreen(" act2") + " -> activate second leader card," + printGreen(" dis1") + " -> discard first leader card," + printGreen(" dis2") + " -> discard second leader card";
    public final static String listOfMarbleAction = printGreen(" dis ") + "-> discard marble," + printGreen(" war ") + "-> store in warehouse," + printGreen(" extra ") + "-> store in extra depot," + printGreen(" convert ") + "-> convert a white marble";
    public final static String listOfSwapTypeMessage = printGreen(" 1 ") + "-> swap between the depots of the warehouse," + printGreen(" 2 ") + "-> swap from warehouse to extra depot," + printGreen(" 3 ") + "-> swap from extra depot to warehouse";
    public final static String indexOfMarble = "Insert the index of the marble you want to manage";

    //error and wait messages
    public final static String typingErrorMessage = "typing error message! Retype the action!";
    public final static String notInBoundMessage = "Invalid integer: not in-bound, re-type it!";

    private static String printGreen(String string){ return  (ConsoleColors.GREEN_BOLD + string + ConsoleColors.RESET); }

    private static String printBlue(String string){
        return  (ConsoleColors.BLUE + string + ConsoleColors.RESET);
    }

    private static String printYellow(String string){
        return  (ConsoleColors.YELLOW + string + ConsoleColors.RESET);
    }

    private static String printGrey(String string){ return  (ConsoleColors.BLACK_BRIGHT + string + ConsoleColors.RESET); }

    private static String printPurple(String string){
        return  (ConsoleColors.PURPLE + string + ConsoleColors.RESET);
    }

}
