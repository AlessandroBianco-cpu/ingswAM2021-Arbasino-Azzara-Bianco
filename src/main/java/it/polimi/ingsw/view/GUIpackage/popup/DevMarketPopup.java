package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.networking.message.BuyCardMessage;
import it.polimi.ingsw.view.GUIpackage.SceneObservable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DevMarketPopup extends SceneObservable implements Popup{

    private Pane root;
    private GridPane grid;

    public DevMarketPopup(DevCard[] cards) {

        try {
            root= FXMLLoader.load(getClass().getResource("/devCardMarketPopup.fxml"));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        grid = (GridPane) root.lookup("#grid");

        int k = 0;
        for(int i = 0; i<3; i++)
            for(int j = 0; j < 4; j++) {
                if (cards[k] != null)
                    addImage((Pane) getNodeByRowColumnIndex(i,j,grid), "/devCards/"+cards[k].getId()+".png");
                k++;
            }
    }

    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        for(Node card : grid.getChildren()) {
            card.setOnMouseClicked(event -> {
                String id = card.getId();
                int indexToBuy = Integer.parseInt(id);
                notifyNewMessageFromClient(new BuyCardMessage(indexToBuy));
                stage.close();
            });
        }

        stage.setScene(scene);
        stage.show();
    }


    /**
     * @param row of the node
     * @param column of the node
     * @param gridPane where the node is contained
     * @return node of the GridPane that has row and column as indexes
     */
    private Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            Integer nodeRow = gridPane.getRowIndex(node);
            Integer nodeCol = gridPane.getColumnIndex(node);

            if(nodeRow == null)
                nodeRow=0;
            if(nodeCol == null)
                nodeCol=0;

            if(nodeRow == row && nodeCol == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    @Override
    public void displayStringMessages(String s) {
        new AlertPopup().displayStringMessages(s);
    }
}
