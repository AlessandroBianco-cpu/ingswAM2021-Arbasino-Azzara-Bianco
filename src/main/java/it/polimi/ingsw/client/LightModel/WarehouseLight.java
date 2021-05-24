package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.networking.message.updateMessage.WarehouseUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

import static it.polimi.ingsw.model.ResourceType.NOTHING;

public class WarehouseLight {

    private ResourceLight firstDepot;
    private ResourceLight[] secondDepot;
    private ResourceLight[] thirdDepot;
    private ResourceLight firstExtraDepot;
    private ResourceLight secondExtraDepot;
    private int firstExtraDepotQuantity;
    private int secondExtraDepotQuantity;
    private ParserForModel parser;

    public WarehouseLight(){
        secondDepot = new ResourceLight[2];
        thirdDepot = new ResourceLight[3];

        firstDepot = new NoResourceLight();
        for(int i = 0; i < 2; i++){
            secondDepot[i] = new NoResourceLight();
        }

        for(int i = 0; i < 3; i++){
            thirdDepot[i] = new NoResourceLight();
        }
        parser = new ParserForModel();
        firstExtraDepot = null;
        secondExtraDepot = null;
    }

    public void print(){
        System.out.print(ConsoleColors.RED + "WAREHOUSE" + ConsoleColors.RESET);
        if(firstExtraDepot != null )
            System.out.print( "     " + firstExtraDepot.toString() + ConsoleColors.RED + "DEPOT" + ConsoleColors.RESET);
        if(secondExtraDepot != null )
            System.out.print( "      " + secondExtraDepot.toString() + ConsoleColors.RED + "DEPOT" + ConsoleColors.RESET);
        System.out.println();
        System.out.print("1->|" + firstDepot.toString() + "|");
        if(firstExtraDepot != null ){
            System.out.print("        ");
            for (int i = 0; i<2; i++){
                System.out.print("|");
                if(i< firstExtraDepotQuantity)
                    System.out.print(firstExtraDepot.toString());
                else
                    System.out.print(" ");
                System.out.print("|");
            }
        }
        if(secondExtraDepot != null ){
            System.out.print("      ");
            for (int i = 0; i<2; i++){
                System.out.print("|");
                if(i< secondExtraDepotQuantity)
                    System.out.print(secondExtraDepot.toString());
                else
                    System.out.print(" ");
                System.out.print("|");
            }
        }
        System.out.println();
        System.out.println("2->|" + secondDepot[0].toString() + "||" + secondDepot[1].toString() + "|");
        System.out.println("3->|" + thirdDepot[0].toString() + "||" + thirdDepot[1].toString() + "||" + thirdDepot[2].toString() + "|");
    }



    public void updateWarehouseLight(WarehouseUpdateMessage message){

        firstDepot = parser.parseStorableResource(message.getResourceTypeByShelf(0));

        for(int i = 0; i < 2; i++){
            if(i < message.getQuantityByShelf(1))
                secondDepot[i] = parser.parseStorableResource(message.getResourceTypeByShelf(1));
            else
                secondDepot[i] = new NoResourceLight();
        }

        for(int i = 0; i < 3; i++){
            if(i< message.getQuantityByShelf(2))
                thirdDepot[i] = parser.parseStorableResource(message.getResourceTypeByShelf(2));
            else
                thirdDepot[i] = new NoResourceLight();
        }

        if(message.getResourceTypeByShelf(3) != NOTHING) {
            firstExtraDepot = parser.parseStorableResource(message.getResourceTypeByShelf(3));
            firstExtraDepotQuantity = message.getQuantityByShelf(3);
        }

        if(message.getResourceTypeByShelf(4) != NOTHING) {
            secondExtraDepot = parser.parseStorableResource(message.getResourceTypeByShelf(4));
            secondExtraDepotQuantity = message.getQuantityByShelf(4);
        }
    }
}
