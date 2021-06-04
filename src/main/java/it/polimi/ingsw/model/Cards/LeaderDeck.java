package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static it.polimi.ingsw.model.Cards.DevCardColor.*;
import static it.polimi.ingsw.model.ResourceType.*;

public class LeaderDeck extends Deck {

    private List<LeaderCard> cards;

    public LeaderDeck() {

        final int DISCOUNT_POINTS = 2;
        final int DEPOT_POINTS = 3;
        final int PRODUCTION_POINTS = 4;
        final int CONVERT_POINTS = 5;
        final int DISCOUNT_AMOUNT = -1;

        cards= new LinkedList<LeaderCard>();
        LinkedList<Requirement> requirementExtraDepotStone = new LinkedList<>();
        LinkedList<Requirement> requirementExtraDepotServant = new LinkedList<>();
        LinkedList<Requirement> requirementExtraDepotShield = new LinkedList<>();
        LinkedList<Requirement> requirementExtraDepotCoin = new LinkedList<>();
        LinkedList<Requirement> requirementDiscountServant = new LinkedList<>();
        LinkedList<Requirement> requirementDiscountShield = new LinkedList<>();
        LinkedList<Requirement> requirementDiscountStone = new LinkedList<>();
        LinkedList<Requirement> requirementDiscountCoin = new LinkedList<>();
        LinkedList<Requirement> requirementConvertToServant = new LinkedList<>();
        LinkedList<Requirement> requirementConvertToShield = new LinkedList<>();
        LinkedList<Requirement> requirementConvertToStone = new LinkedList<>();
        LinkedList<Requirement> requirementConvertToCoin = new LinkedList<>();
        LinkedList<Requirement> requirementProductionWithShield = new LinkedList<>();
        LinkedList<Requirement> requirementProductionWithServant = new LinkedList<>();
        LinkedList<Requirement> requirementProductionWithStone = new LinkedList<>();
        LinkedList<Requirement> requirementProductionWithCoin = new LinkedList<>();

        //list of requirements per card//
        requirementExtraDepotStone.add(new ResourceRequirement(new QuantityResource(COIN, 5)));
        requirementExtraDepotServant.add(new ResourceRequirement(new QuantityResource(ResourceType.STONE, 5)));
        requirementExtraDepotShield.add(new ResourceRequirement(new QuantityResource(ResourceType.SERVANT, 5)));
        requirementExtraDepotCoin.add(new ResourceRequirement(new QuantityResource(ResourceType.SHIELD, 5)));
        requirementDiscountServant.add(new CardRequirement(1,1,YELLOW));
        requirementDiscountServant.add(new CardRequirement(1,1,GREEN));
        requirementDiscountShield.add(new CardRequirement(1,1,BLUE));
        requirementDiscountShield.add(new CardRequirement(1,1,PURPLE));
        requirementDiscountStone.add(new CardRequirement(1,1,GREEN));
        requirementDiscountStone.add(new CardRequirement(1,1,BLUE));
        requirementDiscountCoin.add(new CardRequirement(1,1, YELLOW));
        requirementDiscountCoin.add(new CardRequirement(1,1,PURPLE));
        requirementConvertToServant.add(new CardRequirement(1,2, YELLOW));
        requirementConvertToServant.add(new CardRequirement(1,1,BLUE));
        requirementConvertToShield.add(new CardRequirement(1,2,GREEN));
        requirementConvertToShield.add(new CardRequirement(1,1,PURPLE));
        requirementConvertToStone.add(new CardRequirement(1,2,BLUE));
        requirementConvertToStone.add(new CardRequirement(1,1, YELLOW));
        requirementConvertToCoin.add(new CardRequirement(1,2,PURPLE));
        requirementConvertToCoin.add(new CardRequirement(1,1,GREEN));
        requirementProductionWithShield.add(new CardRequirement(2,1, YELLOW));
        requirementProductionWithServant.add(new CardRequirement(2,1,BLUE));
        requirementProductionWithStone.add(new CardRequirement(2,1,PURPLE));
        requirementProductionWithCoin.add(new CardRequirement(2,1,GREEN));

        LeaderCard convertToCoin = new ConvertWhiteCard(requirementConvertToCoin, COIN, 1, CONVERT_POINTS);
        LeaderCard convertToServant = new ConvertWhiteCard(requirementConvertToServant, SERVANT, 2, CONVERT_POINTS);
        LeaderCard convertToShield = new ConvertWhiteCard(requirementConvertToShield, SHIELD, 3, CONVERT_POINTS);
        LeaderCard convertToStone = new ConvertWhiteCard(requirementConvertToStone, STONE, 4, CONVERT_POINTS);
        LeaderCard discountCoin = new DiscountCard(requirementDiscountCoin, COIN,5, DISCOUNT_POINTS, DISCOUNT_AMOUNT);
        LeaderCard discountServant = new DiscountCard(requirementDiscountServant, SERVANT, 6, DISCOUNT_POINTS, DISCOUNT_AMOUNT);
        LeaderCard discountShield = new DiscountCard(requirementDiscountShield, SHIELD, 7, DISCOUNT_POINTS, DISCOUNT_AMOUNT);
        LeaderCard discountStone = new DiscountCard(requirementDiscountStone, STONE, 8, DISCOUNT_POINTS, DISCOUNT_AMOUNT);
        LeaderCard extraDepotCoin = new ExtraDepotCard(requirementExtraDepotCoin, COIN, 9, DEPOT_POINTS);
        LeaderCard extraDepotServant = new ExtraDepotCard(requirementExtraDepotServant, SERVANT, 10, DEPOT_POINTS);
        LeaderCard extraDepotShield = new ExtraDepotCard(requirementExtraDepotShield, SHIELD, 11, DEPOT_POINTS);
        LeaderCard extraDepotStone = new ExtraDepotCard(requirementExtraDepotStone, STONE, 12, DEPOT_POINTS);
        LeaderCard productionWithCoin = new ExtraDevCard(requirementProductionWithCoin, COIN, 13, PRODUCTION_POINTS);
        LeaderCard productionWithServant = new ExtraDevCard(requirementProductionWithServant, SERVANT, 14, PRODUCTION_POINTS);
        LeaderCard productionWithShield = new ExtraDevCard(requirementProductionWithShield, SHIELD, 15, PRODUCTION_POINTS);
        LeaderCard productionWithStone = new ExtraDevCard(requirementProductionWithStone, STONE, 16, PRODUCTION_POINTS);


        cards.add(discountServant);
        cards.add(discountShield);
        cards.add(discountStone);
        cards.add(discountCoin);
        cards.add(extraDepotStone);
        cards.add(extraDepotServant);
        cards.add(extraDepotShield);
        cards.add(extraDepotCoin);
        cards.add(convertToServant);
        cards.add(convertToShield);
        cards.add(convertToStone);
        cards.add(convertToCoin);
        cards.add(productionWithShield);
        cards.add(productionWithServant);
        cards.add(productionWithStone);
        cards.add(productionWithCoin);

        shuffleDeck();
    }

    private void shuffleDeck(){
        Collections.shuffle(cards);
    }

    @Override
    public LeaderCard popFirstCard() {
        return ((LinkedList<LeaderCard>) cards).pop();
    }

}
