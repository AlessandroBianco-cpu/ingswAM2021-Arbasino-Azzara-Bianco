package it.polimi.ingsw.model.LorenzoIlMagnifico;

import it.polimi.ingsw.model.LorenzoGameMethods;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TokenStack implements Serializable {

    private LorenzoIlMagnifico lorenzoIlMagnifico;
    private List<ActionToken> tokenStack;
    private LorenzoGameMethods lorenzoGameMethods;

    public TokenStack(LorenzoIlMagnifico caller,LorenzoGameMethods lorenzoGameMethods) {
        this.lorenzoIlMagnifico = caller;
        this.lorenzoGameMethods = lorenzoGameMethods;
        tokenStack = new LinkedList<>();

        tokenStack.add(new BlueToken(lorenzoGameMethods));
        tokenStack.add(new YellowToken(lorenzoGameMethods));
        tokenStack.add(new PurpleToken(lorenzoGameMethods));
        tokenStack.add(new GreenToken(lorenzoGameMethods));
        tokenStack.add(new BlackToken(caller));
        tokenStack.add(new BlackToken(caller));
        tokenStack.add(new ShuffleToken(caller, this));

        shuffleStack();
    }

    /**
     * Shuffles the TokenStack
     */
    public void shuffleStack(){
        Collections.shuffle(tokenStack);
    }

    /**
     * Executes the action of the Token on top of the TokenStack
     */
    public ActionToken executeFirstToken(){
        ActionToken currentToken = ((LinkedList<ActionToken>)tokenStack).pop();
        ((LinkedList<ActionToken>)tokenStack).addLast(currentToken);
        currentToken.doAction();
        return currentToken;
    }


}
