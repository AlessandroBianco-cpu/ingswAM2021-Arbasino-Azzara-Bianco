package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.networking.message.updateMessage.StrongboxUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

/**
 * Lightweight representation of the Strongbox. It is stored client-side
 */
public class StrongboxLight {

    /**
     * 0 -> COIN, 1->SERVANT, 2->SHIELD, 3->STONE
     */
    private final Integer[] resources;
    private final CoinLight coins;
    private final ServantLight servants;
    private final ShieldLight shields ;
    private final StoneLight stones ;

    public StrongboxLight() {
        resources = new Integer[4];
        coins = new CoinLight();
        servants = new ServantLight();
        shields = new ShieldLight();
        stones = new StoneLight();
        for(int i = 0; i<4; i++){
            resources[i] = 0;
        }
    }

    public Integer[] getResources() {
        return resources;
    }

    /**
     * Updates the state of the strongbox
     * @param message updated state of the strongbox
     */
    public void update(StrongboxUpdateMessage message){
        resources[0] = message.getCoins();
        resources[1] = message.getServants();
        resources[2] = message.getShields();
        resources[3] = message.getStones();
    }

    // ------------------------ PRINTERS ------------------------
    public void print(){
        System.out.println(ConsoleColors.RED + "STRONGBOX" + ConsoleColors.RESET);
        System.out.println( "| " + coins + resources[0] + " " + //implicit call of the ToString method
                            servants + resources[1] + " " +
                            shields + resources[2] + " " +
                            stones + resources[3] + " |");
    }
}
