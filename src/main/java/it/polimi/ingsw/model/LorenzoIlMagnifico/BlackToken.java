package it.polimi.ingsw.model.LorenzoIlMagnifico;

public class BlackToken extends ActionToken{

    private final int NEXT_SPACE = 2;
    private final LorenzoIlMagnifico lorenzoIlMagnifico;

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

    @Override
    public boolean isBlack() { return true; }

    @Override
    public String toImage() { return "blackToken.png"; }
}
