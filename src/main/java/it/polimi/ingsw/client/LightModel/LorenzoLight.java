package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.LorenzoIlMagnifico.ActionToken;
import it.polimi.ingsw.networking.message.updateMessage.LorenzoUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

/**
 * Lightweight representation of LorenzoIlMagnifico. It is stored client-side
 */
public class LorenzoLight {
    private final int FAITH_TRACK_SIZE = 25;
    private int position;
    private String lorenzoAction;
    private final ParserForModel parser;
    private ActionToken lastToken = null; //it is null when initialized, after his first move it will have each turn the last token used by Lorenzo

    public LorenzoLight() {
        position = 0;
        lorenzoAction = "";
        parser = new ParserForModel();
    }

    // ------------------------ UPDATES ------------------------
    /**
     * Updates the state of lorenzoIlMagnifico
     * @param message lorenzoIlMagnifico updated state
     */
    public void updateLorenzo(LorenzoUpdateMessage message){
        position = message.getPosition();
        lastToken = message.getToken();
        lorenzoAction = parser.parseActionToken(message.getToken());
    }

    // ------------------------ GETTERS ------------------------

    public ActionToken getLastToken() {
        return lastToken;
    }

    public int getPosition() {
        return position;
    }

    // ------------------------ PRINTERS ------------------------
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
