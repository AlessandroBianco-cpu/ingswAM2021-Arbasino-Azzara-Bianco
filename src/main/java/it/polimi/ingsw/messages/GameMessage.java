package it.polimi.ingsw.messages;

public class GameMessage {

    public final static String NumOfPlayersMessage = "Welcome in the lobby, choose the number of players!";
    public final static String nicknameMessage = "nicknameMessage";
    public final static String takenNameMessage = "takenNameMessage";
    public final static String acceptedMessage = "client registered";

    public final static String commandMessage = "commandMessage";
    public final static String chooseAResToAddMessage = "chooseAResToAddMessage";
    public final static String boardPowerInput = "boardPowerInput";
    public final static String chooseDepotMessage = "chooseDepotMessage";
    public final static String chooseRowMessage ="chooseRowMessage";
    public final static String chooseColumnMessage ="chooseColumnMessage";
    public final static String usePowerMessage = "usePowerMessage";


    //printing facilities
    public final static String printResourceLegenda = "1 -> coins, 2-> shield, 3-> servant, 4->stone";
    public final static String printMainAction = "0-> take resource from market (by column)\n1-> take resource from market (by row)\n 2-> buy a new dev card\n3-> activate production\n";
    public final static String printExtraAction ="4-> activate leader card\n5-> discard leader card\n6-> swap warehouse's depots\n7-> swap resource between warehouse's depot and extra depot\n";
    public final static String printfSlotsLegenda = "0-> basePower\n1-> first DevCard\n 2-> second DevCard\n3-> third DevCard\n 4-> first extraCard\n5-> second extraCard";


    //main action messages
    public final static String startProdMessage ="startProdMessage";

    //extra action messages

    public final static String leaderDiscardingMessage = " leaderDiscardingMessage";
    public final static String activateLeaderMessage = "Choose the index of leader you want to activate ";

    public final static String productionMessage = "How many development card you want to active";
    public final static String insertCardIndexMessage = "What card do you want to activate?";
    public final static String howManyFromStrongbox ="How many resource want to remove from strongbox?";
    public final static String howManyFromWarehouse ="How many resource want to remove from warehouse's depot?";
    public final static String howManyFromExtraDepot ="How many resource want to remove from extra depot?";
    public final static String winMessage = "winMessage";
    public final static String loseMessage = "loseMessage";
    public final static String startTurnMessage = "start";
    public final static String endTurnMessage = "end";




    //error and wait messages
    public final static String waitPlayersNum = "waitPlayersNum";
    public final static String waitMessage = "waitMessage";
    public final static String wrongActionMessage = "wrongActionMessage";
    public final static String notInBoundMessage ="Invalid integer: not in this bound, re-type!";
    public final static String wrongTurnMessage = "wrongTurnMessage";
}
