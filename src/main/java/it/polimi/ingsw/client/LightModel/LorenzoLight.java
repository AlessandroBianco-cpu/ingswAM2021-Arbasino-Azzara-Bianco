package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.networking.message.updateMessage.LorenzoUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

public class LorenzoLight {
    private final int FAITH_TRACK_SIZE = 25;
    private int position;
    private String lorenzoAction;
    private ParserForModel parser;

    public LorenzoLight() {
        position = 0;
        lorenzoAction = "";
        parser = new ParserForModel();
    }

   public void updateLorenzo(LorenzoUpdateMessage message){
       position = message.getPosition();
       lorenzoAction = parser.parseActionToken(message.getToken());
   }

   public void print() {
       System.out.println(lorenzoAction);
       for (int i = 0; i < FAITH_TRACK_SIZE + 1; i++) {
           if (i == FAITH_TRACK_SIZE)
               System.out.println("|");
           else {
               System.out.print("| ");
               if (i != position)
                   System.out.print(" ");
               else
                   System.out.print(ConsoleColors.RED + "✞" + ConsoleColors.RESET);
               System.out.print(" ");
           }
           System.out.print(ConsoleColors.RESET);
       }
       System.out.println("Lorenzo's position: " + position);
   }
}
