package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.networking.message.updateMessage.CardPaymentResourceBufferUpdateMessage;
import it.polimi.ingsw.networking.message.updateMessage.ProductionResourceBufferUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public class ResourceBufferLight {
    private List<ResourceLight> resources;
    private List<Integer> associatedQuantity;
    private final ParserForModel parser;

    public ResourceBufferLight() {
        parser = new ParserForModel();
    }

    // ------------------------ UPDATES ------------------------

    public void updateResourceBufferLight(CardPaymentResourceBufferUpdateMessage message){
        updateResourceBufferLight(message.getResourcesToPay());
    }

    public void updateResourceBufferLight(ProductionResourceBufferUpdateMessage message){
        updateResourceBufferLight(message.getResourcesToPay());
    }

    private void updateResourceBufferLight(List<QuantityResource> resourcesToUpdate){
        resources = new ArrayList<>();
        associatedQuantity = new ArrayList<>();

        for (QuantityResource q : resourcesToUpdate){
            resources.add(parser.parseStorableResource(q.getResourceType()));
            associatedQuantity.add(q.getQuantity());
        }
    }

    // ------------------------ GETTERS ------------------------
    public List<ResourceLight> getResources() {
        return resources;
    }

    public List<Integer> getAssociatedQuantity() {
        return associatedQuantity;
    }

    // ------------------------ PRINTERS ------------------------
    public void print(){

        if (resources.size() == 0)
            return;

        System.out.println(ConsoleColors.RED + "RESOURCES TO PAY:" + ConsoleColors.RESET);

        for(int i = 0; i < resources.size(); i++){
            System.out.print(associatedQuantity.get(i) + resources.get(i).toString() + " " );
        }
        System.out.println();
    }

}
