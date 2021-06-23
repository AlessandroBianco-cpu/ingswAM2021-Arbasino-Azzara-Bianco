package it.polimi.ingsw.model.LorenzoIlMagnifico;

public class ShuffleToken extends ActionToken{

    private final LorenzoIlMagnifico lorenzoIlMagnifico;
    private final TokenStack tokenStack;

    public ShuffleToken(LorenzoIlMagnifico lorenzoIlMagnifico, TokenStack tokenStack) {
        this.lorenzoIlMagnifico = lorenzoIlMagnifico;
        this.tokenStack = tokenStack;
    }

    /**
     * Increases the position of LorenzoIlMagnifico by 1 and shuffles the TokenStack
     */
    @Override
    public void doAction(){
        lorenzoIlMagnifico.advance();
        tokenStack.shuffleStack();
    }

    @Override
    public boolean isShuffle() { return true; }

    @Override
    public String toImage() { return "shuffleToken.png"; }
}
