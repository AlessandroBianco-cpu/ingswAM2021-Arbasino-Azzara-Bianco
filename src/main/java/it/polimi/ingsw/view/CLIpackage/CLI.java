package it.polimi.ingsw.view.CLIpackage;

import it.polimi.ingsw.client.LightModel.ModelLight;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.*;
import it.polimi.ingsw.networking.message.updateMessage.*;
import it.polimi.ingsw.observer.UiObservable;
import it.polimi.ingsw.utils.ConsoleColors;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static it.polimi.ingsw.utils.StringToPrint.*;

public class CLI extends UiObservable implements Runnable, View {

    private final Scanner in;
    private String owner;
    private boolean answerReady = false;
    private boolean gameCreated = false;
    private ModelLight model = new ModelLight();

    //variables sent from Server
    private int playersNumber = 0;
    private int resToAdd = 0;
    private final List<String> players = new ArrayList<>();


    public CLI() {
        this.in = new Scanner(System.in);
    }

    /**
     * Starts CLI
     */
    @Override
    public void run() {
        askConnection();
        startGame();
        readInput();
    }

    /**
     * Displays the game table
     */
    public void displayTable(){
        if(gameCreated){
            displayMarket();
            System.out.println();
            displayDevCardsAvailable();
        }
    }

    public void readInput() {
        while (true) {

            if (in.hasNext()) {

                String s = in.nextLine().toUpperCase(Locale.ROOT);
                if (s.isEmpty()) continue;

                switch (s) {
                    case "?":
                        giveLegenda();
                        break;
                    case "SHOW":
                        displayPersonalBoard(owner);
                        break;
                    case "SWAP":
                        askSwapType();
                        break;
                    case "SETTING":
                        askPlayersNumber();
                        break;
                    case "NICK":
                        askNickname();
                        break;
                    case "INITIALDIS":
                        askInitialDiscard();
                        break;
                    case "INITIALRES":
                        askInitialResource();
                        break;
                        //action case
                    case "MARBLESROW":
                        chooseMarketPosition("ROW");
                        break;
                    case "MARBLESCOL":
                        chooseMarketPosition("COLUMN");
                        break;
                    case "PRODUCTION":
                        askComboOfSlots();
                        break;
                    case "STOREMARBLE":
                        handleMarbleBuffer();
                        break;
                    case "PAYCARD":
                        askHowToPayDevCard();
                        break;
                    case "PLACECARD":
                        askDevCardSlotPosition();
                        break;
                    case "PAYPROD":
                        askHowToPayProduction();
                        break;
                    case "PLAYERINFO":
                        askPlayerInfo();
                        break;
                    case "BUYCARD":
                        askCardIndex();
                        break;
                    case "SHOWLEADERS":
                        showLeaderCards();
                        break;
                    case "LEADER":
                        askLeaderAction();
                        break;
                    case "ENDTURN":
                        endTurn();
                        break;

                    default:
                        System.out.println(typingErrorMessage);
                        break;
                }
            }
        }
    }

    /**
     * This method printf the game starting
     */
    private void startGame() {
        System.out.print("\n" + ConsoleColors.YELLOW_BOLD +
                " _____ ______   ________  ________  _________  _______   ________  ________           ________  ________     \n" +
                "|\\   _ \\  _   \\|\\   __  \\|\\   ____\\|\\___   ___|\\  ___ \\ |\\   __  \\|\\   ____\\         |\\   __  \\|\\  _____\\    \n" +
                "\\ \\  \\\\\\__\\ \\  \\ \\  \\|\\  \\ \\  \\___|\\|___ \\  \\_\\ \\   __/|\\ \\  \\|\\  \\ \\  \\___|_        \\ \\  \\|\\  \\ \\  \\__/     \n" +
                " \\ \\  \\\\|__| \\  \\ \\   __  \\ \\_____  \\   \\ \\  \\ \\ \\  \\_|/_\\ \\   _  _\\ \\_____  \\        \\ \\  \\\\\\  \\ \\   __\\    \n" +
                "  \\ \\  \\    \\ \\  \\ \\  \\ \\  \\|____|\\  \\   \\ \\  \\ \\ \\  \\_|\\ \\ \\  \\\\  \\\\|____|\\  \\        \\ \\  \\\\\\  \\ \\  \\_|    \n" +
                "   \\ \\__\\    \\ \\__\\ \\__\\ \\__\\____\\_\\  \\   \\ \\__\\ \\ \\_______\\ \\__\\\\ _\\ ____\\_\\  \\        \\ \\_______\\ \\__\\     \n" +
                "    \\|__|     \\|__|\\|__|\\|__|\\_________\\   \\|__|  \\|_______|\\|__|\\|__|\\_________\\        \\|_______|\\|__|     \n" +
                "                            \\|_________|                             \\|_________|                            \n" +
                "                                                                                                             \n" +
                "                                                                                                             \n");
        System.out.print(
                " ________  _______   ________   ________  ___  ________   ________  ________  ________   ________  _______      \n" +
                "|\\   __  \\|\\  ___ \\ |\\   ___  \\|\\   __  \\|\\  \\|\\   ____\\ |\\   ____\\|\\   __  \\|\\   ___  \\|\\   ____\\|\\  ___ \\     \n" +
                "\\ \\  \\|\\  \\ \\   __/|\\ \\  \\\\ \\  \\ \\  \\|\\  \\ \\  \\ \\  \\___|_\\ \\  \\___|\\ \\  \\|\\  \\ \\  \\\\ \\  \\ \\  \\___|\\ \\   __/|    \n" +
                " \\ \\   _  _\\ \\  \\_|/_\\ \\  \\\\ \\  \\ \\   __  \\ \\  \\ \\_____  \\\\ \\_____  \\ \\   __  \\ \\  \\\\ \\  \\ \\  \\    \\ \\  \\_|/__  \n" +
                "  \\ \\  \\\\  \\\\ \\  \\_|\\ \\ \\  \\\\ \\  \\ \\  \\ \\  \\ \\  \\|____|\\  \\\\|____|\\  \\ \\  \\ \\  \\ \\  \\\\ \\  \\ \\  \\____\\ \\  \\_|\\ \\ \n" +
                "   \\ \\__\\\\ _\\\\ \\_______\\ \\__\\\\ \\__\\ \\__\\ \\__\\ \\__\\____\\_\\  \\ ____\\_\\  \\ \\__\\ \\__\\ \\__\\\\ \\__\\ \\_______\\ \\_______\\\n" +
                "    \\|__|\\|__|\\|_______|\\|__| \\|__|\\|__|\\|__|\\|__|\\_________|\\_________\\|__|\\|__|\\|__| \\|__|\\|_______|\\|_______|\n" +
                "                                                 \\|_________\\|_________|                                        \n" +
                "                                                                                                                \n" +
                "                                                                                                                \n" +
                "\n" + ConsoleColors.RESET);
    }

    /**
     * Asks the first user connected the number of Players that are going to play (1 to 4 players) and gets the input
     */
    @Override
    public void askPlayersNumber() {
        System.out.println("Choose the number of players, insert 1 for singlePlayer");
        int num = getIntegerInput();

        while (num < 1 || num > 4) {
            System.out.println("Insert a number between 1 and 4 to correctly set-up the players in the game!");
            num = getIntegerInput();
        }
        notifyMessage(new NumOfPlayerMessage(num));
    }

    /**
     * Asks the number and the indexes that player want to activate for the production phase
     */
    @Override
    public void askComboOfSlots() {
        displayProductionZone();
        System.out.println("How many development cards do you want to activate?");

        int n = getIntegerInput();
        while (n < 0 || n > 5) {
            System.out.println(notInBoundMessage);
            n = getIntegerInput();
        }

        ResourceType resIn1 = ResourceType.NOTHING;
        ResourceType resIn2 = ResourceType.NOTHING;
        ResourceType resOut = ResourceType.NOTHING;
        List<Integer> slotsIndexes = new ArrayList<>();
        List<ResourceType> leadersOutput = new ArrayList<>();
        ResourceType tmpOutput;
        for (int i=0; i<n; i++) {
            System.out.println("Insert index of the production slot you want to activate");
            int answerInt = getIntegerInput();
            while (answerInt < 0 || answerInt > 5 || slotsIndexes.contains(answerInt)) {
                System.out.println(notInBoundMessage);
                answerInt = getIntegerInput();
            }
            slotsIndexes.add(answerInt);
            if(answerInt == 0){ //baseProdSlot
                resIn1 = askAResource("Insert the #1 input resource for the base power");
                resIn2 = askAResource("Insert the #2 input resource for the base power");
                resOut = askAResource("Insert the resource you want to have in output");
            } else if (answerInt == 4 || answerInt == 5){ //leaderCard included
                tmpOutput = askAResource("Insert the leader output");
                leadersOutput.add(tmpOutput);
            }
        }

        notifyMessage(new ActivateProductionMessage(slotsIndexes, resIn1, resIn2, resOut, leadersOutput));
    }


    /**
     * Sets the owner of this CLI after a Server check
     * @param m is the message send from the Server
     */
    @Override
    public void registerClient(ClientAcceptedMessage m) {
        this.owner = m.getNickname();
        System.out.println("You are correctly registered with nickname: "+ owner);
    }

    /**
     * Prints all the command that player can insert to interact with server
     */
    public void giveLegenda(){
        System.out.println("To activate production from development slots, type: " + printGreen("production"));
        System.out.println("To activate or discard a leader, type: " + printGreen("leader"));
        System.out.println("To buy a new development card, type: " + printGreen("buyCard"));
        System.out.println("To take resources from marbles market inserting a marble in a row, type: " + printGreen("marblesRow"));
        System.out.println("To take resources from marbles market inserting a marble in a column, type: " + printGreen("marblesCol"));
        System.out.println("To do a swap action type: " +  printGreen("swap"));
        System.out.println("To show your personal board, type: " +  printGreen("show"));
        System.out.println("To show your leader cards, type: " + printGreen("showLeaders"));
        System.out.println("To store a marble from buffer, type: " + printGreen("storeMarble"));
        System.out.println("To pay a Development Card, type: " + printGreen("payCard"));
        System.out.println("To place a DevCard bought, type: " + printGreen("placeCard"));
        //PLAYER INFO System.out.println("": " + printGreen("playerInfo"));
        System.out.println("To pay the production, type: " + printGreen("payProd"));

        //anche dei comandi per avere le informazioni degli altri player!!
    }

    public String printGreen(String string){
        return (ConsoleColors.GREEN_BOLD + string + ConsoleColors.RESET);
    }

    public void endTurn(){
        notifyMessage(new EndTurnMessage());
    }

    /**
     * This method handle all the leader action that player can do
     */
    public void askLeaderAction() {
        System.out.println(listOfLeaderAction);
        String s = in.nextLine();
        switch(s) {
            case "dis1":
                notifyMessage(new DiscardLeaderMessage(1));
                break;
            case "dis2":
                notifyMessage(new DiscardLeaderMessage(2));
                break;
            case "act1":
                notifyMessage(new ActivateLeaderMessage(1));
                break;
            case "act2":
                notifyMessage(new ActivateLeaderMessage(2));
                break;
            default:
                System.out.println(typingErrorMessage);
        }
    }

    /**
     * Asks the index of the development card which player want to buy
     */
    public void askCardIndex() {
        displayDevCardsAvailable();
        System.out.println("Insert an index between 1 and 12");
        int indexCard = getIntegerInput();
        while(indexCard < 1 || indexCard > 12) {
            System.out.print(notInBoundMessage);
            System.out.println("  Insert an index between 1 and 12");
            indexCard = getIntegerInput();
        }
        notifyMessage(new BuyCardMessage(indexCard));
    }

    @Override
    public void askHowToPayDevCard() {
        ResourceType resourceType = askAResource("Choose the type of resource do you want to pay");
        int fromWarehouse = askAQuantity("warehouse");
        int fromStrongBox = askAQuantity("strongbox");
        int fromExtra = askAQuantity("extra depot");

        boolean useLeaderDiscount = booleanRequest("Do you want to use a leader discount?" + printGreen("0") +" -> no" + printGreen("1") + " -> yes");

        notifyMessage(new DevCardPayment(fromWarehouse, fromExtra, fromStrongBox, resourceType, useLeaderDiscount));
    }

    /**
     * Method used to ask a quantity to withdraw from a Resource spot
     * @param string string of the resource spot name
     * @return the quantity asked
     */
    public int askAQuantity(String string){
        System.out.println("How many from " + string + " ?");
        return getIntegerInput();
    }

    /**
     * Is a boolean request
     * @param request is a string to print
     * @return true/false
     */
    public boolean booleanRequest(String request){
        System.out.println(request);
        int response = getIntegerInput();
        while (response != 1 && response != 0){
            System.out.println("Wrong index, retype");
            response = getIntegerInput();
        }
        return response == 1;
    }

    /**
     * Asks how player want to buy the resources needed to activate production slots
     */
    public void askHowToPayProduction() {
        ResourceType resourceType = askAResource("Choose the type of resource do you want to pay");
        int fromWarehouse = askAQuantity("warehouse");
        int fromStrongBox = askAQuantity("strongbox");
        int fromExtra = askAQuantity("extra depot");

        notifyMessage(new ResourcePlayerWantsToSpendMessage(resourceType, fromWarehouse, fromStrongBox, fromExtra));
    }

    /**
     * Asks the position of slots where player want to insert the new development card
     */
    @Override
    public void askDevCardSlotPosition(){
        System.out.println("Where do you want to place the DevCard?");
        int position = getIntegerInput();
        while (position < 1 || position > 3){
            System.out.println("Wrong slot index, retype");
            position = getIntegerInput();
        }
        notifyMessage(new InsertDevCardInDevSlot(position));
    }

    /**
     * Asks the user his nickname and gets the input
     */
    @Override
    public void askNickname() {
        System.out.print("Enter your nickname: ");
        String nickname = in.nextLine();
        notifyMessage(new SettingNicknameMessage(nickname));
    }

    /**
     * Prints the number of players
     * @param number number of players
     */
    public void displayNumberOfPlayers(int number) {
        System.out.println("The game will have " + number + " players");
    }


    @Override
    public void updateStrongboxLight(StrongboxUpdateMessage m) {
        model.updatePlayerStrongbox(m);
    }

    @Override
    public void updateFaithTrack(FaithTrackUpdateMessage m) {
        model.updatePlayerFaithTrack(m);
    }

    @Override
    public void updateDevCardMarket(DevCardMarketUpdateMessage m) {
        model.updateDevCardMarket(m);
    }

    @Override
    public void updateLeaderCardsInHand(LeaderInHandUpdateMessage m) {
        model.updatePlayerLeaderInHands(m);
    }

    @Override
    public void updateProductionZone(ProductionZoneUpdateMessage m) {
        model.updateProductionZone(m);
        if (m.getNickname().equals(owner))
            model.printProductionZone(owner);
    }

    @Override
    public void updateWarehouseLight(WarehouseUpdateMessage m) {
        model.updateWarehouse(m);
    }

    @Override
    public void updateMarketLight(MarketUpdateMessage m) {
        model.getMarbleMarket().updateMarketLight(m);
    }

    @Override
    public void updateBuffer(MarbleBufferUpdateMessage m) {
        model.getPlayerByNickname(owner).updateBuffer(m.getBuffer());
        displayBuffer();
    }

    @Override
    public void displayBuffer(){
        model.printBuffer(owner);
    }
    
    @Override
    public void displayDevCardsAvailable() {
        model.printDevCardMarket();
    }

    @Override
    public void displayLeaderCards() {
        model.printLeaderCardInHand(owner);
    }

    @Override
    public void displayWarehouse() {
        model.printWarehouse(owner);
    }

    @Override
    public void displayPersonalBoard(String nickname) {
        model.printPersonalBoard(nickname);
    }

    /**
     * This method displays the owner leader cards
     */
    public void showLeaderCards(){
        displayLeaderCards();
    }

    /**
     * This method sets up the LightModel players' nickname
     * @param m is the message from Server
     */
    @Override
    public void updateNicknames(NicknamesUpdateMessage m) {
        model.updatePlayersNickname(m);
        gameCreated = true;
    }

    /**
     * Warns that the chosen nickname is already taken and asks a different one
     */
    @Override
    public void displayTakenNickname() {
        System.out.println("This nickname is already taken. Please try again.");
    }


    /**
     * Ask the index of row/column where player wants to insert marble in the market
     * @param position is a string ROW/COLUMN
     */
    @Override
    public void chooseMarketPosition(String position) {
        displayMarket();
        switch (position) {
            case "ROW":
                int row = askRow();
                notifyMessage(new InsertMarbleMessage(true ,row));
                break;
            case "COLUMN":
                int column = askColumn();
                notifyMessage(new InsertMarbleMessage(false ,column));
                break;
        }
    }


    public void handleMarbleBuffer() {
        int index;
        System.out.println(listOfMarbleAction);
        String s = in.nextLine();
        displayWarehouse();
        switch(s) {
            case "dis":
                System.out.println(indexOfMarble);
                index = getIntegerInput();
                notifyMessage(new DiscardMarble(index));
                break;
            case "extra":
                System.out.println(indexOfMarble);
                index = getIntegerInput();
                notifyMessage(new StoreResourceInExtraDepot(index));
                break;
            case "war":
                System.out.println(indexOfMarble);
                index = getIntegerInput();
                int depot = askWarehouseDepot("Where do you want to store the resource?");
                notifyMessage(new StoreResourceInWarehouse(index,depot));
                break;
            case "convert":
                System.out.println(indexOfMarble);
                index = getIntegerInput();
                ResourceType res = askAResource("Choose the resource you want to convert to");
                notifyMessage(new ConvertWhiteMarble(index,res));
                break;
            default:
                System.out.println(typingErrorMessage);
        }
    }

    /**
     * Asks row to the user and gets the input
     * @return chosen row
     */
    private int askRow() {
        int row;
        System.out.println("Choose a row between 1 and 3");
        while (true) {
            row = getIntegerInput();
            if(row < 1 || row > 3)
                System.out.println("Invalid row. Choose a number between 1 and 3: ");
            else break;
        }

        return row;
    }

    /**
     * Asks column to the user and gets the input
     * @return chosen column
     */
    private int askColumn() {
        System.out.println("Choose a column between 1 and 4 ");
        int column;

        while (true) {
            column = getIntegerInput();
            if(column < 1 || column > 4)
                System.out.println("Invalid column. Choose a number between 1 and 4: ");
            else break;
        }

        return column;
    }


    /**
     * Ask the index of the warehouse's depot
     *
     * @return the index of depot
     */
    public int askWarehouseDepot(String s){
        System.out.println("Choose the number of depot " + s);
        int depotIndex = getIntegerInput();
        while  (depotIndex < 1 || depotIndex > 3){
            System.out.println(notInBoundMessage);
            System.out.println("Choose the number of depot " + s);
            depotIndex = getIntegerInput();
        }

        return depotIndex;
    }


    public int askExtraDepot(String s){
        System.out.println("Insert the number of an extra depot "+s);
        int depIndex = getIntegerInput();
        while  (depIndex < 1 || depIndex > 2){
            System.out.println(notInBoundMessage);
            System.out.println("Insert the number of an extra depot "+s);
            depIndex = getIntegerInput();
        }
        return depIndex;
    }


    public ResourceType askAResource(String toPrint) {
        System.out.print(toPrint);
        System.out.println(printResourceLegenda);
        int resource = getIntegerInput();
        while(resource < 1 || resource > 4) {
            System.out.println(notInBoundMessage);
            System.out.println(printResourceLegenda);
            resource = getIntegerInput();
        }

        return parseResourceIndex(resource);
    }


    private ResourceType parseResourceIndex(int res) {
        switch (res) {
            case 1: return ResourceType.COIN;
            case 2: return ResourceType.STONE;
            case 3: return ResourceType.SHIELD;
            case 4: return ResourceType.SERVANT;
        }
        return  null;
    }


    public void askPlayerInfo() {
        System.out.println("Insert the nickname of the player you want to show info:\n");
        for (String s : players)
            System.out.println(" "+s+" ");

        String nicknameInfo = in.nextLine();
        while (! (players.contains(nicknameInfo)) ) {
            System.out.println(typingErrorMessage);
            nicknameInfo = in.nextLine();
        }

        notifyMessage(new PlayerInfoMessage(nicknameInfo));
    }


    @Override
    public void askInitialDiscard() {
        List<Integer> indexes = new ArrayList<>();
        displayLeaderCards();
        for(int i=0; i<2; i++) {
            System.out.println("Insert the index of the card you want to discard:");
            int x = getIntegerInput();
            while (x < 1 || x > 4 || indexes.contains(x)) {
                if (indexes.contains(x)) {
                    System.out.println("You have already insert this index!");
                    System.out.println("Insert the index of the card you want to discard:");
                    x = getIntegerInput();
                } else {
                    System.out.println(notInBoundMessage);
                    System.out.println("Insert the index of the card you want to discard:");
                    x = getIntegerInput();
                }
            }
            indexes.add(x);
        }
        notifyMessage(new ChooseLeaderMessage(indexes));
    }

    public void askInitialResource() {
        ChooseResourcesMessage resMessage = new ChooseResourcesMessage(resToAdd);

        for(int i=0; i<resToAdd; i++) {
            ResourceType res = askAResource("Choose a resource to add");
            int indexToAdd = askWarehouseDepot("where you want to put this resource");
            resMessage.addResource(res,indexToAdd);
        }

        notifyMessage(resMessage);
    }

    /**
     * This method sets the number of player
     * @param num is the number of game's players
     */
    @Override
    public void updatePlayersNumber(int num) {
        playersNumber = num;
        displayNumberOfPlayers(playersNumber);
    }

    /**
     * This method sets the number of resource which player must to add
     * @param num is the number of resources
     */
    @Override
    public void updateNumOfResourcesToAdd(int num) {
        this.resToAdd = num;
    }

    @Override
    public void updateLorenzoLight(LorenzoUpdateMessage m) {
        model.updateLorenzo(m);
        model.printLorenzo();
    }

    /**
     * Ask to the player what type of swap he wants to do
     */
    @Override
    public void askSwapType() {
        int quantity; //quantity in case of extra swapping!
        displayWarehouse();
        System.out.println(listOfSwapTypeMessage);
        int type = getIntegerInput();
        while (type < 1 || type > 3) {
            System.out.println(notInBoundMessage);
            System.out.println(listOfSwapTypeMessage);
            type = getIntegerInput();
        }

        switch(type) {
            case 1: //swap between the depots of the warehouse
                int from = askWarehouseDepot("#1");
                int to = askWarehouseDepot("#2");
                notifyMessage(new WarehouseSwapMessage(from,to));
                break;
            case 2: //swap from warehouse to extra depot
                int depFrom = askWarehouseDepot(" from which you want to move your resources");
                int extraDepTo = askExtraDepot(" where you want to put your resources");
                quantity = askQuantity();
                while(quantity > 2){
                    System.out.println(notInBoundMessage);
                    quantity = askQuantity();
                }
                notifyMessage(new MoveToExtraDepotMessage(depFrom,extraDepTo,quantity));
                break;
            case 3: //swap from extraDepot to warehouse
                int extraDepFrom = askExtraDepot(" from which you want to move your resources");
                int depTo = askWarehouseDepot(" where you want to put your resources");
                quantity = askQuantity();
                while(quantity > 2){
                    System.out.println(notInBoundMessage);
                    quantity = askQuantity();
                }
                notifyMessage(new MoveFromExtraDepotMessage(extraDepFrom,depTo,quantity));
                break;
        }
    }

    private int askQuantity(){
        System.out.println("How many resources do you want to move?\n");
        return getIntegerInput();
    }

    private void endGame() {
        System.out.println("Thanks for playing!");
        System.exit(0);
    }
    
    @Override
    public void displayStartTurn(StartTurnMessage m) {
        if (m.getCurrentPlayerNickname().equals(owner)) {
            System.out.println("It's" + ConsoleColors.GREEN_BOLD + " your" + ConsoleColors.RESET + " turn");
            displayTable();
        }
        else
            System.out.println(m.getMessage());
    }

    private int getIntegerInput(){
        int x = -1;
        boolean isOk = false;
        while(!isOk) {
            try {
                x = Integer.parseInt(in.nextLine());
                isOk = true;
            } catch (NumberFormatException e) {
                System.out.println("Please insert a number");
            }
        }
        return x;
    }

    /**
     * Prints the winner's nickname
     * @param winnerMessage is a string to print
     */
    @Override
    public void displayWinner(String winnerMessage) {
        System.out.println(winnerMessage);
        endGame();
    }


    private void askConnection() {
        /*
        System.out.println("IP address of server?");
        String ip = in.nextLine();
        System.out.println("Port?");
        String port = in.nextLine();
        */
        notifyConnection("127.0.0.1", "12345");
    }

    /**
     * Warns the user that it's not his/her turn
     */
    @Override
    public void displayWrongTurn() {
        System.out.println("It's not your turn.");
    }

    /**
     * This method is used to display strings
     * @param message
     */
    @Override
    public void displayStringMessages(String message){
        System.out.println(message);
    }

    /**
     * Warns the user that a network error has occurred
     */
    @Override
    public void displayNetworkError() {
        System.out.println("Connection closed from server side");
        System.exit(0);
    }

    @Override
    public void waiting() {
        System.out.println("Wait...");
    }

    @Override
    public void displayMarket(){
        model.getMarbleMarket().print();
    }

    @Override
    public void displayProductionZone(){model.printProductionZone(owner);}


}

