package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.client.LightModel.market.MarbleLight;
import it.polimi.ingsw.client.LightModel.market.MarketLight;
import it.polimi.ingsw.networking.message.InsertMarbleMessage;
import it.polimi.ingsw.view.GUIpackage.SceneObservable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Popup that handles the interaction with marbles market
 */
public class MarketPopup extends SceneObservable implements Popup{
    private Pane root;
    private final List<Button> selections;


    public MarketPopup(MarketLight market) {

        try {
            root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/marblesMarketPopup.fxml")));
        }  catch (IOException e) {
        e.printStackTrace();
        }

        GridPane grid = (GridPane) root.lookup("#grid");
        Pane marbleLeftPane = (Pane) root.lookup("#marbleLeft");

        selections = new ArrayList<>();
        selections.add((Button) root.lookup("#row1"));
        selections.add((Button) root.lookup("#row2"));
        selections.add((Button) root.lookup("#row3"));
        selections.add((Button) root.lookup("#col1"));
        selections.add((Button) root.lookup("#col2"));
        selections.add((Button) root.lookup("#col3"));
        selections.add((Button) root.lookup("#col4"));

        MarbleLight[][] marbles = market.getMarbleMatrix();
        MarbleLight marbleLeft = market.getMarbleLeft();
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                addImage((Pane) getNodeByRowColumnIndex(i,j,grid), marbles[i][j].toImage());
            }
        }
        addImage(marbleLeftPane, marbleLeft.toImage());


    }

    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);

        for(Button b : selections) {
            b.setOnMouseClicked(event -> {
                boolean row = isRowButton(b);
                int index;
                if (row)
                    index = selections.indexOf(b) +1;
                else
                    index = selections.indexOf(b)-2;

                    notifyNewMessageFromClient(new InsertMarbleMessage(row,index));
                    stage.close();
            });
        }

        stage.setScene(scene);
        stage.show();
    }


    private Boolean isRowButton(Button b) {
        return selections.indexOf(b) < 3;
    }

    /**
     * @param row of the node
     * @param column of the node
     * @param gridPane where the node is contained
     * @return node of the GridPane that has row and column as indexes
     */
    private Node getNodeByRowColumnIndex (final int row,final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);

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
