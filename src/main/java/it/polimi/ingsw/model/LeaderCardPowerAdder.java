package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.ConvertWhiteCard;
import it.polimi.ingsw.model.Cards.DiscountCard;
import it.polimi.ingsw.model.Cards.ExtraDepotCard;
import it.polimi.ingsw.model.Cards.ExtraDevCard;

/**
 * This interface is a collection of methods that player implements in order to activate
 * the powers of LeaderCards
 */
public interface LeaderCardPowerAdder {

    void addExtraDepotCardPower(ExtraDepotCard givenCard);
    void addConvertWhiteCardPower(ConvertWhiteCard givenCard);
    void addDiscountCardPower(DiscountCard givenCard);
    void addExtraDevCardPower(ExtraDevCard givenCard);

}
