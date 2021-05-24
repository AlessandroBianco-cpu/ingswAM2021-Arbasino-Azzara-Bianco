package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.client.LightModel.market.*;
import it.polimi.ingsw.model.Cards.DevCardColor;
import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.model.LorenzoIlMagnifico.ActionToken;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class ParserForModel {

    public ResourceLight parseStorableResource(ResourceType resource){
        switch (resource){
            case SERVANT:
                return new ServantLight();
            case COIN:
                return new CoinLight();
            case SHIELD:
                return new ShieldLight();
            case STONE:
                return new StoneLight();
            case NOTHING:
                return new NoResourceLight();
            default:
                return new NoResourceLight();
        }
    }

    public String parseQuantityResource(QuantityResource resource){

        switch (resource.getResourceType()){
            case SERVANT:
                return (ConsoleColors.PURPLE + resource.getQuantity() + ConsoleColors.RESET);
            case COIN:
                return (ConsoleColors.YELLOW + resource.getQuantity() + ConsoleColors.RESET);
            case SHIELD:
                return (ConsoleColors.BLUE + resource.getQuantity() + ConsoleColors.RESET);
            case STONE:
                return (ConsoleColors.BLACK_BRIGHT + resource.getQuantity() + ConsoleColors.RESET);
            case FAITH:
                return (ConsoleColors.RED + resource.getQuantity() + ConsoleColors.RESET);
            default:
                return "error";
        }
    }

    public String parseCardColor(DevCardColor color){
        switch(color){
            case BLUE:
                return ConsoleColors.BLUE;
            case GREEN:
                return ConsoleColors.GREEN;
            case PURPLE:
                return  ConsoleColors.PURPLE;
            case YELLOW:
                return ConsoleColors.YELLOW;
            default:
                return ConsoleColors.RESET;
        }
    }

    public String parseActiveCard(LeaderCard card){
        if(card.isActive())
            return ConsoleColors.YELLOW;
        return ConsoleColors.RESET;
    }

    public String parseActionToken(ActionToken token){
        if(token == null){
            return "";
        }else if(token.isBlack()){
            return "Lorenzo gained 2 Faith Points";
        }else if(token.isBlue()){
            return "Lorenzo destroyed 2 blue cards";
        }else if(token.isGreen()){
            return "Lorenzo destroyed 2 green cards";
        }else if(token.isPurple()){
            return "Lorenzo destroyed 2 purple cards";
        }else if(token.isShuffle()){
            return "Lorenzo gained 1 Faith Point";
        }else if(token.isYellow()){
            return "Lorenzo destroyed 2 yellow cards";
        }else
            return "";
    }

    public MarbleLight parserFromMarbleToLight(Marble marble){
        switch (marble.getResourceType()){
            case SERVANT:
                return new PurpleMarbleLight();
            case COIN:
                return new YellowMarbleLight();
            case SHIELD:
                return new BlueMarbleLight();
            case STONE:
                return new GreyMarbleLight();
            case NOTHING:
                return new WhiteMarbleLight();
            default:
                return new WhiteMarbleLight();
        }
    }
}


