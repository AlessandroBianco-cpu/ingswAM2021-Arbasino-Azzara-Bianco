package it.polimi.ingsw.utils;

/**
 * Strings used to display messages to the client on the CLI
 */
public class StringToPrint {

    //printing facilities
    public final static String printResourceLegenda = printGreen(" 1")+ "-> " + printYellow("coin") + ", " + printGreen("2") +"-> " + printGrey("stone") + ", " + printGreen("3") + "-> " + printBlue("shield") + ", " + printGreen("4") +"-> " + printPurple("servant");
    public final static String listOfLeaderAction = printGreen(" act1") + " -> activate first leader card," + printGreen(" act2") + " -> activate second leader card," + printGreen(" dis1") + " -> discard first leader card," + printGreen(" dis2") + " -> discard second leader card";
    public final static String listOfMarbleAction = printGreen(" dis ") + "-> discard marble," + printGreen(" war ") + "-> store in warehouse," + printGreen(" extra ") + "-> store in extra depot," + printGreen(" convert ") + "-> convert a white marble";
    public final static String listOfSwapTypeMessage = printGreen(" 1 ") + "-> swap between the depots of the warehouse," + printGreen(" 2 ") + "-> swap from warehouse to extra depot," + printGreen(" 3 ") + "-> swap from extra depot to warehouse";
    public final static String indexOfMarble = "Insert the index of the marble you want to manage";

    //error and wait messages
    public final static String typingErrorMessage = "typing error message! Retype the action!";
    public final static String notInBoundMessage ="Invalid integer: not in-bound, re-type it!";


    private static String printGreen(String string){
        return  (ConsoleColors.GREEN_BOLD + string + ConsoleColors.RESET);
    }

    private static String printBlue(String string){
        return  (ConsoleColors.BLUE + string + ConsoleColors.RESET);
    }

    private static String printYellow(String string){
        return  (ConsoleColors.YELLOW + string + ConsoleColors.RESET);
    }

    private static String printGrey(String string){
        return  (ConsoleColors.BLACK_BRIGHT + string + ConsoleColors.RESET);
    }

    private static String printPurple(String string){
        return  (ConsoleColors.PURPLE + string + ConsoleColors.RESET);
    }


}
