package it.polimi.ingsw.model;

import java.io.Serializable;

public class QuantityResource implements Serializable {
    private ResourceType resourceType;
    private int quantity;

    public QuantityResource(ResourceType resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increase(int value) {
        quantity+=value;
    }

    public void decrease(int value) { quantity-=value; }

    public void setResourceType(ResourceType typeResource){
        this.resourceType =typeResource;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        QuantityResource toCompare= (QuantityResource) obj;
        return ((this.getResourceType()==toCompare.getResourceType()) && (this.getQuantity()==toCompare.getQuantity()));
    }

}


