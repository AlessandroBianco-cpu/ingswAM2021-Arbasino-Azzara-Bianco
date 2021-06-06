package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.client.LightModel.ResourceBufferLight;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.ResourcePlayerWantsToSpendMessage;
import it.polimi.ingsw.view.GUIpackage.SceneObservable;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.ResourceType.*;

/**
 * Popup that handles the payment after a production action
 */
public class ProductionPaymentPopup extends SceneObservable implements Popup {

    private Pane root;
    private List<CheckBox> checkList;
    private List<Button> positiveButtons;
    private List<Button> negativeButtons;
    private List<Label> quantityLabel;
    private ImageView doneButton;
    private List<Pane> toPay;
    private List<Text> quantityResources;


    public ProductionPaymentPopup(ResourceBufferLight resourcesToPay) {
        try {
            root= FXMLLoader.load(getClass().getResource("/productionPaymentPopup.fxml"));
        }  catch (IOException e) {
            e.printStackTrace();
        }
        toPay = new ArrayList<>();
        quantityResources = new ArrayList<>();
        for(int i = 0; i<resourcesToPay.getResources().size(); i++) {
            toPay.add((Pane) root.lookup("#resource"+i));
            quantityResources.add((Text) root.lookup("#qtaRes"+i));
            addImage(toPay.get(i),resourcesToPay.getResources().get(i).toImage());
            quantityResources.get(i).setText(resourcesToPay.getAssociatedQuantity().get(i).toString());
        }

        doneButton = (ImageView) root.lookup("#doneButton");

        checkList = new ArrayList<>();
        checkList.add((CheckBox) root.lookup("#coin"));
        checkList.add((CheckBox) root.lookup("#shield"));
        checkList.add((CheckBox) root.lookup("#stone"));
        checkList.add((CheckBox) root.lookup("#servant"));

        quantityLabel = new ArrayList<>();
        quantityLabel.add((Label) root.lookup("#warehouseQuantity"));
        quantityLabel.add((Label) root.lookup("#strongboxQuantity"));
        quantityLabel.add((Label) root.lookup("#extraDepotQuantity"));

        for(Label label : quantityLabel)
            label.setText("0");

        positiveButtons = new ArrayList<>();
        negativeButtons = new ArrayList<>();
        positiveButtons.add ((Button) root.lookup("#+warehouse"));
        positiveButtons.add ((Button) root.lookup("#+strongbox"));
        positiveButtons.add ((Button) root.lookup("#+extraDepot"));
        negativeButtons.add ((Button) root.lookup("#-warehouse"));
        negativeButtons.add ((Button) root.lookup("#-strongbox"));
        negativeButtons.add ((Button) root.lookup("#-extraDepot"));
    }

    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);

        DropShadow shadow = new DropShadow();
        doneButton.setOnMouseEntered(event -> doneButton.setEffect(shadow));
        doneButton.setOnMouseExited(event -> doneButton.setEffect(null));

        for (Button b : positiveButtons)
            b.setOnMouseClicked(event -> {
                searchAndAddOnLabel(positiveButtons.indexOf(b));
            });

        for (Button b : negativeButtons)
            b.setOnMouseClicked(event -> {
                searchAndDecreaseOnLabel(negativeButtons.indexOf(b));
            });

        doneButton.setOnMouseClicked(event -> {
            ResourceType toPay = parseCheckingList();
            if ( !(toPay.equals(NOTHING)) ) {
                notifyNewMessageFromClient(new ResourcePlayerWantsToSpendMessage(getQuantity(quantityLabel.get(0)), getQuantity(quantityLabel.get(1)),
                        getQuantity(quantityLabel.get(2)), toPay));
                stage.close();
            }
            else
                new AlertPopup().displayStringMessages("You have to select only one resource to pay");
        });

        stage.setOnCloseRequest(Event::consume);
        stage.setScene(scene);
        stage.show();
    }

    private int getQuantity(Label label) {
        int value = Integer.parseInt(label.getText());
        return value;
    }


    private void searchAndAddOnLabel(int indexOfButton) {
        int toSet=0;
        switch (indexOfButton) {
            case 0 :
                toSet = Integer.parseInt(quantityLabel.get(0).getText()) + 1;
                setLabelIntText(quantityLabel.get(0),toSet);
                break;
            case 1:
                toSet = Integer.parseInt(quantityLabel.get(1).getText()) +1;
                setLabelIntText(quantityLabel.get(1),toSet);
                break;
            case 2 :
                toSet = Integer.parseInt(quantityLabel.get(2).getText()) +1;
                setLabelIntText(quantityLabel.get(2),toSet);
                break;
        }
        return;
    }

    private void searchAndDecreaseOnLabel(int indexOfButton) {
        int toSet=0;
        switch (indexOfButton) {
            case 0:
                toSet = Integer.parseInt(quantityLabel.get(0).getText()) -1;
                setLabelIntText(quantityLabel.get(0),toSet);
                break;
            case 1 :
                toSet = Integer.parseInt(quantityLabel.get(1).getText()) -1;
                setLabelIntText(quantityLabel.get(1),toSet);
                break;
            case 2 :
                toSet = Integer.parseInt(quantityLabel.get(2).getText()) -1;
                setLabelIntText(quantityLabel.get(2),toSet);
                break;
        }
        return;
    }

    private void setLabelIntText(Label label, int toSet) {
        if( toSet > 0)
            label.setText(""+toSet);
        else
            label.setText(""+0);
    }

    private ResourceType parseCheckingList() {
        int num = 0;
        String checkChosen = "";
        for(int j=0; j< checkList.size(); j++) {
            if (checkList.get(j).isSelected()) {
                num++;
                checkChosen = checkList.get(j).getId();
            }
        }

        if (num == 1)
            return parseResourceType(checkChosen);
        else
            return NOTHING;
    }

    /**
     * This method is a parser for the resource type which user choose
     * @param s is the imageView id code
     * @return resource type
     */
    private ResourceType parseResourceType (String s) {
        switch (s) {
            case "coin" :
                return COIN;
            case "shield" :
                return SHIELD;
            case "servant" :
                return SERVANT;
            case "stone" :
                return STONE;
            default:
                return null;
        }
    }

    @Override
    public void displayStringMessages(String s) {

    }

}
