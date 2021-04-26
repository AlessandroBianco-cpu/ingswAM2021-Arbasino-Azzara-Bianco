package it.polimi.ingsw.messages;

import java.io.Serializable;

/**
 * Message class used to send a nickname
 */
public class NameMessage implements Serializable {

    //set-up done
    private static final long serialVersionUID = 1720561504621287405L;

    private final String type;
    private final String name;

    public NameMessage(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
