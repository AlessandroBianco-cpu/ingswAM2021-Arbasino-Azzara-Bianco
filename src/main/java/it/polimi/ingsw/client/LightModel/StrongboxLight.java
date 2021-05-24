package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.networking.message.updateMessage.StrongboxUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

public class StrongboxLight {

    /**
     * 0 -> COIN, 1->SERVANT, 2->SHIELD, 3->STONE
     */
    private Integer[] resources;
    private CoinLight coins;
    private ServantLight servants;
    private ShieldLight shields ;
    private StoneLight stones ;

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

    public void print(){
        System.out.println(ConsoleColors.RED + "STRONGBOX" + ConsoleColors.RESET);
        System.out.println( "| " + coins.toString() + resources[0] + " " +
                            servants.toString() + resources[1] + " " +
                            shields.toString() + resources[2] + " " +
                            stones.toString() + resources[3] + " |");
    }

    public void update(StrongboxUpdateMessage message){
        resources[0] = message.getCoins();
        resources[1] = message.getServants();
        resources[2] = message.getShields();
        resources[3] = message.getStones();
    }
}
