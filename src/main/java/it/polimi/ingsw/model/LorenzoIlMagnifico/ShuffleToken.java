package it.polimi.ingsw.model.LorenzoIlMagnifico;

public class ShuffleToken extends ActionToken{

    private LorenzoIlMagnifico lorenzoIlMagnifico;
    private TokenStack tokenStack;

    public ShuffleToken(LorenzoIlMagnifico lorenzoIlMagnifico, TokenStack tokenStack) {
        this.lorenzoIlMagnifico = lorenzoIlMagnifico;
        this.tokenStack = tokenStack;
    }

    /**
     * Increases the position of LorenzoIlMagnifico by 1 and shuffles the TokenStack
     */
    @Override
    public void doAction(){
        //System.out.println("Executing ShuffleToken");
        lorenzoIlMagnifico.advance();
        tokenStack.shuffleStack();
    }

    @Override
    public boolean isShuffle() {
        return true;
    }
}
