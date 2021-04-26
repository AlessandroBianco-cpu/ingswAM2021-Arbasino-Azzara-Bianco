package it.polimi.ingsw.model.LorenzoIlMagnifico;

public class BlackToken extends ActionToken{

    private final int NEXT_SPACE = 2;
    private LorenzoIlMagnifico lorenzoIlMagnifico;

    public BlackToken(LorenzoIlMagnifico lorenzoIlMagnifico) {
        this.lorenzoIlMagnifico = lorenzoIlMagnifico;
    }

    /**
     * Increases the position of LorenzoIlMagnifico by 2
     */
    @Override
    public void doAction() {
        for (int i = 0; i < NEXT_SPACE; i++)
            lorenzoIlMagnifico.advance();
    }

}
