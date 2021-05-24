package it.polimi.ingsw.model.LorenzoIlMagnifico;

import it.polimi.ingsw.model.DevCardMarket;
import it.polimi.ingsw.model.LorenzoGameMethods;
import it.polimi.ingsw.observer.LorenzoObservable;

import java.io.Serializable;


public class LorenzoIlMagnifico extends LorenzoObservable implements Serializable {

    private int position;
    private TokenStack tokenStack;
    private ActionToken lastTokenExecuted;
    private DevCardMarket devCardMarket;
    private LorenzoGameMethods lorenzoGameMethods;
    private boolean firstVaticanReportHasOccurred;
    private boolean secondVaticanReportHasOccurred;
    private boolean thirdVaticanReportHasOccurred;

    public LorenzoIlMagnifico(LorenzoGameMethods lorenzoGameMethods) {
        this.position = 0;
        this.tokenStack = new TokenStack(this, lorenzoGameMethods);
        this.lorenzoGameMethods=lorenzoGameMethods;
        firstVaticanReportHasOccurred = false;
        secondVaticanReportHasOccurred = false;
        thirdVaticanReportHasOccurred = false;
    }

    /**
     * Increases LorenzoIlMagnifico position by 1
     */
    public void advance() {
        position++;

        if(position == 8 && !firstVaticanReportHasOccurred){
            lorenzoGameMethods.vaticanReport();
            firstVaticanReportHasOccurred = true;
        }else if(position == 16 && !secondVaticanReportHasOccurred){
            lorenzoGameMethods.vaticanReport();
            secondVaticanReportHasOccurred = true;
        }else if(position == 24 && !thirdVaticanReportHasOccurred){
            lorenzoGameMethods.setLorenzoWin();
        }

        notifyLorenzoPosition(this);
    }

    /**
     * Executes LorenzoIlMagnifico action by executing the
     * action performed by the Token on top of the Stack
     */
    public void doAction() {
        lastTokenExecuted = tokenStack.executeFirstToken();
        notifyLorenzoState(this);
    }

    /**
     * @return the position of LorenzoIlMagnifico
     */
    public int getPosition(){
        return position;
    }

    public TokenStack getTokenStack() {
        return tokenStack;
    }

    public ActionToken getLastTokenExecuted() {
        return lastTokenExecuted;
    }
}
