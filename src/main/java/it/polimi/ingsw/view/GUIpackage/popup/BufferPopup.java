package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.client.LightModel.LeaderCardsInHandLight;
import it.polimi.ingsw.client.LightModel.WarehouseLight;
import it.polimi.ingsw.client.LightModel.market.BufferLight;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.ConvertWhiteMarbleMessage;
import it.polimi.ingsw.networking.message.DiscardMarbleMessage;
import it.polimi.ingsw.networking.message.StoreResourceInExtraDepotMessage;
import it.polimi.ingsw.networking.message.StoreResourceInWarehouseMessage;
import it.polimi.ingsw.view.GUIpackage.SceneObservable;
import it.polimi.ingsw.view.GUIpackage.SceneObserver;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static it.polimi.ingsw.model.ResourceType.*;

/**
 * Popup used to store the marbles buffer after interactions with the marbles market
 */
@SuppressWarnings("unchecked")
public class BufferPopup extends SceneObservable implements Popup {

    private Pane root;
    private final ImageView discardButton;
    private final ImageView convertButton;
    private final ChoiceBox<String> resourceBox;
    private final List<Pane> depots;
    private final List<Pane> buffer;
    private final List<Pane> extraLeaders;
    private final ImageView swapButton;
    private final SwapPopup internalSwapping;
    private final List<ImageView> buttonsList;
    private int chosenIndex = 0;

    public BufferPopup(WarehouseLight warehouse, BufferLight bufferLight, LeaderCardsInHandLight leaders) {

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/bufferPopup.fxml")));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        internalSwapping = new SwapPopup(warehouse,leaders,false);
        extraLeaders = new ArrayList<>();
        extraLeaders.add((Pane) root.lookup("#leader1"));
        extraLeaders.add((Pane) root.lookup("#leader2"));
        List<Pane> extraDepots = new ArrayList<>();
        extraDepots.add((Pane) root.lookup("#firstExtra1"));
        extraDepots.add((Pane) root.lookup("#firstExtra2"));
        extraDepots.add((Pane) root.lookup("#secondExtra1"));
        extraDepots.add((Pane) root.lookup("#secondExtra2"));

        depots = new ArrayList<>();
        for(int i=1; i<7; i++) {
            depots.add((Pane) root.lookup("#"+i));
        }

        buffer = new ArrayList<>();
        for(int i = 0; i<bufferLight.getBuffer().size(); i++) {
            buffer.add((Pane) root.lookup("#buffer"+i));
            addImage(buffer.get(i),bufferLight.getBuffer().get(i).toImage());
        }

        for(int i =0; i < warehouse.getDepots().length; i++)
            if(!(warehouse.getDepots()[i].getResource().equals(NOTHING)))
                addImage(depots.get(i),warehouse.getDepots()[i].toImage());

        discardButton = (ImageView) root.lookup("#discardButton");
        convertButton = (ImageView) root.lookup("#convertButton");
        swapButton = (ImageView) root.lookup("#swapButton");
        resourceBox = (ChoiceBox<String>)  root.lookup("#resourceBox");

        buttonsList = new ArrayList<>();
        buttonsList.add(discardButton);
        buttonsList.add(convertButton);
        buttonsList.add(swapButton);

        resourceBox.setValue("SHIELD");
        resourceBox.getItems().add("SHIELD");
        resourceBox.getItems().add("COIN");
        resourceBox.getItems().add("SERVANT");
        resourceBox.getItems().add("STONE");

        resourceBox.setStyle("-fx-font: normal 13 'Avenir Book'");

        for(int i = 0; i < leaders.getActiveCards().size();i++){
            if(leaders.getActiveCards().get(i).isExtraDepotCard()) {
                addImage(extraLeaders.get(i), "/graphics/leaderCards/" +leaders.getActiveCards().get(i).getId() +".png"); //leader cards images
                for (int j = 0; j < warehouse.getExtraDepotsQuantity()[i]; j++) //leader cards resources on cards
                    addImage(extraDepots.get((2 * i) + j), warehouse.getExtraDepotsTypes()[i].toImage());
            }
        }

    }

    private void setChosenIndex(int toSet) {
        chosenIndex = toSet;
    }


    public void referenceInternalPopup(SceneObserver observer) {
        internalSwapping.addObserver(observer);
    }

    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.NONE);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);

        DropShadow shadow = new DropShadow();
        for(ImageView im : buttonsList) {
            im.setOnMouseEntered(event -> im.setEffect(shadow));
            im.setOnMouseExited(event -> im.setEffect(null));
        }

       for(Pane pane : buffer)
           pane.setOnMouseClicked(event -> setChosenIndex(buffer.indexOf(pane)+1));

       for(Pane pane : extraLeaders)
           pane.setOnMouseClicked(event -> {
               if(chosenIndex != 0) {
                   notifyNewMessageFromClient(new StoreResourceInExtraDepotMessage(chosenIndex));
                   stage.close();
               }
           });


       for(Pane pane : depots)
           pane.setOnMouseClicked(event -> {
               if ((chosenIndex != 0)) {
                   notifyNewMessageFromClient(new StoreResourceInWarehouseMessage(chosenIndex, calculateDepot(pane.getId())));
                   stage.close();
               }
           });

       discardButton.setOnMouseClicked(event -> {
           if(chosenIndex != 0) {
               notifyNewMessageFromClient(new DiscardMarbleMessage(chosenIndex));
               stage.close();
           }
       });

       convertButton.setOnMouseClicked(event -> {
           if(chosenIndex != 0) {
               notifyNewMessageFromClient(new ConvertWhiteMarbleMessage(chosenIndex, parseTextField(resourceBox.getValue())));
               stage.close();
           }
       });

       swapButton.setOnMouseClicked(mouseEvent -> {
           internalSwapping.display();
           stage.close();
       });

        stage.setOnCloseRequest(Event::consume);

        stage.setScene(scene);
        stage.show();
    }

    private int calculateDepot(String id) {
        int index = Integer.parseInt(id);
        if(index == 1)
            return 1;
        else if(index < 4)
            return 2;
        else
            return 3;
    }

    private ResourceType parseTextField (String s) {
        switch (s) {
            case "SHIELD":
                return SHIELD;
            case "SERVANT":
                return SERVANT;
            case "COIN":
                return COIN;
            case "STONE":
                return STONE;

        }
        return NOTHING;
    }


    @Override
    public void displayStringMessages(String s) {
        new AlertPopup().displayStringMessages(s);
    }
}
