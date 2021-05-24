package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

public class SettingBasePowerMessage extends Client2Server {
    private static final long serialVersionUID = -4306474890678288604L;

    private ResourceType input1;
    private ResourceType input2;
    private ResourceType out;

    public SettingBasePowerMessage(ResourceType in1, ResourceType in2, ResourceType out) {
        this.input1 = in1;
        this.input2 = in2;
        this.out = out;
    }

    public ResourceType getInput1() {
        return input1;
    }

    public ResourceType getInput2() {
        return input2;
    }

    public ResourceType getOut() {
        return out;
    }
}
