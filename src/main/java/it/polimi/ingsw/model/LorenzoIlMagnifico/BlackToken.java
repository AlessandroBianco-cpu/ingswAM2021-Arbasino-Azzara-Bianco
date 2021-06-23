package it.polimi.ingsw.model.LorenzoIlMagnifico;

import static it.polimi.ingsw.utils.StaticUtils.BLACK_TOKEN_SPACES;

public class BlackToken extends ActionToken{

    private final LorenzoIlMagnifico lorenzoIlMagnifico;

    public BlackToken(LorenzoIlMagnifico lorenzoIlMagnifico) {
        this.lorenzoIlMagnifico = lorenzoIlMagnifico;
    }

    /**
     * Increases the position of LorenzoIlMagnifico by 2
     */
    @Override
    public void doAction() {
        for (int i = 0; i < BLACK_TOKEN_SPACES; i++)
            lorenzoIlMagnifico.advance();
    }

    @Override
    public boolean isBlack() { return true; }

    @Override
    public String toImage() { return "blackToken.png"; }
}
