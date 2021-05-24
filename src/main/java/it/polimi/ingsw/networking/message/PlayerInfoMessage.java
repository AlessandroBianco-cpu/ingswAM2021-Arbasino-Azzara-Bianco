package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.Board.ResourcesStock;
import it.polimi.ingsw.model.Board.Warehouse;

public class PlayerInfoMessage extends Client2Server{

    private String playerNicknameInfo;
    private Warehouse playerWarehouse;
    private ResourcesStock resourcesStock;
    private int playerPosition;


    public PlayerInfoMessage(String nickname) {
        this.playerNicknameInfo = nickname;
    }

    public String getPlayerNicknameInfo() {
        return playerNicknameInfo;
    }

    public void setPlayerWarehouse(Warehouse playerWarehouse) {
        this.playerWarehouse = playerWarehouse;
    }

    public void setResourcesStock(ResourcesStock resourcesStock) {
        this.resourcesStock = resourcesStock;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }
}
