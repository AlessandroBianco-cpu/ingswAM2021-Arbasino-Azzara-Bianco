package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.client.LightModel.ModelLight;
import it.polimi.ingsw.client.LightModel.ProductionZoneLight;
import it.polimi.ingsw.client.LightModel.ResourceLight;
import it.polimi.ingsw.client.LightModel.StrongboxLight;
import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.EndTurnMessage;
import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.observer.UiObservable;
import it.polimi.ingsw.view.GUIpackage.popup.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main scene of the game: displays the playerBoard after the creation and setting of the game
 */
public class PlayerBoardScene extends UiObservable implements SceneObserver {
    private ModelLight gameModel;
    private String owner;

    private Pane root;
    private ImageView showButton;
    private ImageView endTurnButton;
    private ImageView productionButton;
    private ImageView marketButton;
    private ImageView swapButton;
    private ImageView buyButton;
    private List<Pane> slot1;
    private List<Pane> slot2;
    private List<Pane> slot3;
    private List<Pane> depots;
    private List<Pane> leaders;
    private List<Pane> extraDepots;
    private List<Pane> popeSpaces;
    private ChoiceBox showChoice;
    private Text consoleText;
    private List<Label> strongboxLabel;
    private List<Pane> faithTrack;


    public PlayerBoardScene(ModelLight model,String owner) {
        this.owner = owner;
        gameModel = model;

        try {
            root = FXMLLoader.load(getClass().getResource("/playerBoardScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ImageView> buttonsList = new ArrayList<>();
        buyButton = (ImageView) root.lookup("#buyButton");
        swapButton = (ImageView) root.lookup("#swapButton");
        marketButton = (ImageView) root.lookup("#marketButton");
        showButton = (ImageView) root.lookup("#showButton");
        productionButton = (ImageView) root.lookup("#productionButton");

        showChoice = (ChoiceBox) root.lookup("#showChoice");
        consoleText = (Text) root.lookup("#consoleText");
        endTurnButton = (ImageView) root.lookup("#endButton");

        buttonsList.add(buyButton);
        buttonsList.add(swapButton);
        buttonsList.add(showButton);
        buttonsList.add(marketButton);
        buttonsList.add(endTurnButton);
        buttonsList.add(productionButton);


        strongboxLabel = new ArrayList<>();
        strongboxLabel.add((Label) root.lookup("#qtaCoin"));
        strongboxLabel.add((Label) root.lookup("#qtaServant"));
        strongboxLabel.add((Label) root.lookup("#qtaShield"));
        strongboxLabel.add((Label) root.lookup("#qtaStone"));

        for(Label l : strongboxLabel)
            setLabelIntText(l,0);

        popeSpaces = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            int j = i+1;
            popeSpaces.add((Pane) root.lookup("#popeSpace"+j));
        }

        depots = new ArrayList<>();
        depots.add((Pane) root.lookup("#firstDepot"));
        depots.add((Pane) root.lookup("#secondDepot1"));
        depots.add((Pane) root.lookup("#secondDepot2"));
        depots.add((Pane) root.lookup("#thirdDepot1"));
        depots.add((Pane) root.lookup("#thirdDepot2"));
        depots.add((Pane) root.lookup("#thirdDepot3"));

        //set effects on all buttons
        DropShadow shadow = new DropShadow();
        for(ImageView im : buttonsList) {
            im.setOnMouseEntered(event -> im.setEffect(shadow));
            im.setOnMouseExited(event -> im.setEffect(null));
        }

        if (model.getNumberOfPlayers() == 1) {
            showChoice.setValue("LorenzoIlMagnifico");
            showChoice.getItems().add("LorenzoIlMagnifico");
        } else {
            for (String s : model.getOpponentsNicknames())
                showChoice.getItems().add(s);
        }

        slot1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int j = i + 1;
            slot1.add((Pane) root.lookup("#slot1card" + j));
        }

        slot2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int j = i + 1;
            slot2.add((Pane) root.lookup("#slot2card" + j));
        }

        slot3 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int j = i + 1;
            slot3.add((Pane) root.lookup("#slot3card" + j));
        }

        leaders = new ArrayList<>();
        leaders.add((Pane) root.lookup("#leaderCard1"));
        leaders.add((Pane) root.lookup("#leaderCard2"));

        extraDepots = new ArrayList<>();
        extraDepots.add((Pane) root.lookup("#firstExtra1"));
        extraDepots.add((Pane) root.lookup("#firstExtra2"));
        extraDepots.add((Pane) root.lookup("#secondExtra1"));
        extraDepots.add((Pane) root.lookup("#secondExtra2"));

        faithTrack = new ArrayList<>();
        for (int i = 0; i < 25; i++)
            faithTrack.add((Pane) root.lookup("#slot" + i));

        //initial playerBoard setting
        addImage(faithTrack.get(gameModel.getPlayerByNickname(owner).getFaithTrack().getPosition()), "/punchBoard/blackCross.png");
        addImage(leaders.get(0), "/leaderCards/" + model.getPlayerByNickname(owner).getLeaderCardsInHand().getCards().get(0).getId() + ".png");
        addImage(leaders.get(1), "/leaderCards/" + model.getPlayerByNickname(owner).getLeaderCardsInHand().getCards().get(1).getId() + ".png");

        showChoice.setStyle("-fx-font: normal 14 'Avenir Book'");

        for (int i = 0; i < 6; i++) {
            if (! (gameModel.getPlayerByNickname(owner).getWarehouse().getDepots()[i].getResource().equals(ResourceType.NOTHING)) ) {
                addImage(depots.get(i), gameModel.getPlayerByNickname(owner).getWarehouse().getDepots()[i].toImage());
            }
        }

        for(Pane pane : leaders)
            pane.setOnMouseClicked(event -> {
                if ( !(model.getPlayerByNickname(owner).getLeaderCardsInHand().getCards().get(leaders.indexOf(pane)).isActive()) ) {
                    LeaderPopup popup = new LeaderPopup(model.getPlayerByNickname(owner).getLeaderCardsInHand().getCards().get(leaders.indexOf(pane)), leaders.indexOf(pane));
                    popup.addObserver(this);
                    popup.display();
                }
            });

        marketButton.setOnMouseClicked(event -> {
            MarketPopup popup = new MarketPopup(model.getMarbleMarket());
            popup.addObserver(this);
            popup.display();
        });

        swapButton.setOnMouseClicked(event -> {
            SwapPopup popup = new SwapPopup(model.getPlayerByNickname(owner).getWarehouse(),model.getPlayerByNickname(owner).getLeaderCardsInHand(),true);
            popup.addObserver(this);
            popup.display();
        });

        buyButton.setOnMouseClicked(event -> {
            DevMarketPopup popup = new DevMarketPopup(model.getDevCardMarket().getGrid());
            popup.addObserver(this);
            popup.display();
        });

        endTurnButton.setOnMouseClicked(event -> notifyMessage(new EndTurnMessage()));

        showButton.setOnMouseClicked(event -> {
            if (showChoice.getValue().toString().equals("LorenzoIlMagnifico"))
                new LorenzoPopup(model.getLorenzoPosition(),model.getLorenzoLatestUsedToken()).display();
            else
                new ShowOpponentPopup(model.getPlayerByNickname(showChoice.getValue().toString())).display();
        });

        productionButton.setOnMouseClicked(event -> {
            ProductionPopup popup = new ProductionPopup(model.getPlayerByNickname(owner).getProductionZone(),model.getPlayerByNickname(owner).getLeaderCardsInHand());
            popup.addObserver(this);
            popup.display();
        });
    }

    public void displayWarehouse() {

        for (Pane pane : this.depots)
            pane.getChildren().clear();

        for(Pane pane : this.extraDepots)
            pane.getChildren().clear();

        ResourceLight[] resources = gameModel.getPlayerByNickname(owner).getWarehouse().getDepots();
        for (int i = 0; i < depots.size(); i++) {
            if (!resources[i].toString().equals(" "))
                addImage(depots.get(i), resources[i].toImage());
        }

        int extraDepotIndex = 0;
        List<LeaderCard> inHand = gameModel.getPlayerByNickname(owner).getLeaderCardsInHand().getCards();
        for(int leaderCardIndex = 0; leaderCardIndex < inHand.size(); leaderCardIndex++) {
            if(inHand.get(leaderCardIndex).isExtraDepotCard() && inHand.get(leaderCardIndex).isActive() ) {
                for(int numOfResources=0; numOfResources<gameModel.getPlayerByNickname(owner).getWarehouse().getExtraDepotsQuantity()[extraDepotIndex]; numOfResources++) {
                    addImage(extraDepots.get(2*leaderCardIndex+numOfResources), gameModel.getPlayerByNickname(owner).getWarehouse().getExtraDepotsTypes()[extraDepotIndex].toImage());
                }
                extraDepotIndex++;
            }
        }

    }

    public void displayProductionZone() {

        for(int i = 0; i<3; i++) {
            slot1.get(i).getChildren().clear();
            slot2.get(i).getChildren().clear();
            slot3.get(i).getChildren().clear();
        }

        ProductionZoneLight zone = gameModel.getPlayerByNickname(owner).getProductionZone();
        for(int i = 0; i<zone.getFirstSlot().size(); i++) {
            addImage(slot1.get(i),"/devCards/"+zone.getFirstSlot().get(i).getId()+".png");
        }

        for(int i = 0; i<zone.getSecondSlot().size(); i++) {
            addImage(slot2.get(i),"/devCards/"+zone.getSecondSlot().get(i).getId()+".png");
        }

        for(int i = 0; i<zone.getThirdSlot().size(); i++) {
            addImage(slot3.get(i),"/devCards/"+zone.getThirdSlot().get(i).getId()+".png");
        }
    }

    public void displayBuffer(BufferPopup popup) {
        popup.addObserver(this);
        popup.referenceInternalPopup(this);
        popup.display();
    }

    public void displayResourcesToPay(CardPaymentPopup popup) {
        popup.addObserver(this);
        popup.display();
    }

    public void displayProductionPayment(ProductionPaymentPopup popup){
        popup.addObserver(this);
        popup.display();
    }

    public void displayPlaceNewCard(PlacementCardPopup popup) {
        popup.addObserver(this);
        popup.display();
    }

    public void displayStrongbox(StrongboxLight strongbox) {
        for(int i=0; i<strongboxLabel.size();i++)
            setLabelIntText(strongboxLabel.get(i),strongbox.getResources()[i]);
    }

    public void displayFaithTrack(int pos) {
        for(Pane pane: faithTrack)
            pane.getChildren().clear();
        for(Pane pane : popeSpaces)
            pane.getChildren().clear();

        addImage(faithTrack.get(pos), "/punchBoard/blackCross.png");

        if(gameModel.getPlayerByNickname(owner).getFaithTrack().isFirstPopeFavorAchieved())
            addImage(popeSpaces.get(0),"/punchBoard/yellow_front_tile.png");
        else
            addImage(popeSpaces.get(0),"/punchBoard/yellow_back_tile.png");

        if(gameModel.getPlayerByNickname(owner).getFaithTrack().isSecondPopeFavorAchieved())
            addImage(popeSpaces.get(1),"/punchBoard/orange_front_tile.png");
        else
            addImage(popeSpaces.get(1),"/punchBoard/orange_back_tile.png");

        if(gameModel.getPlayerByNickname(owner).getFaithTrack().isThirdPopeFavorAchieved())
            addImage(popeSpaces.get(2),"/punchBoard/red_front_tile.png");
        else
            addImage(popeSpaces.get(2),"/punchBoard/red_back_tile.png");

    }

    public void displayLeaders() {
        DropShadow borderEffect = new DropShadow();
        borderEffect.setColor(Color.GOLD);

        for(Pane pane : leaders)
            pane.getChildren().clear();
        for(Pane pane : extraDepots)
            pane.getChildren().clear();

        List<LeaderCard> cards = gameModel.getPlayerByNickname(owner).getLeaderCardsInHand().getCards();
        for(int i=0; i < cards.size(); i++) {
            addImage(leaders.get(i), "/leaderCards/" + cards.get(i).getId() + ".png");
            if (cards.get(i).isActive())
                leaders.get(i).setEffect(borderEffect);
        }

        int extraDepotIndex = 0;
        List<LeaderCard> inHand = gameModel.getPlayerByNickname(owner).getLeaderCardsInHand().getCards();
        for(int leaderCardIndex = 0; leaderCardIndex < inHand.size(); leaderCardIndex++) {
            if(inHand.get(leaderCardIndex).isExtraDepotCard()) {
                for(int numOfResources=0; numOfResources<gameModel.getPlayerByNickname(owner).getWarehouse().getExtraDepotsQuantity()[extraDepotIndex]; numOfResources++) {
                    addImage(extraDepots.get(2*leaderCardIndex+numOfResources), gameModel.getPlayerByNickname(owner).getWarehouse().getExtraDepotsTypes()[extraDepotIndex].toImage());
                }
                extraDepotIndex++;
            }
        }
    }

    public void displayInConsole(String message) {
        consoleText.setText(message);
    }

    /**
     * Adds an ImageView to a Pane
     * @param pane where to add the image
     * @param image path
     * @return the new image
     */
    private ImageView addImage(Pane pane, String image) {
        ImageView view = new ImageView();
        view.setImage(new Image(image));

        pane.getChildren().add(view);
        view.fitWidthProperty().bind(pane.widthProperty());
        view.fitHeightProperty().bind(pane.heightProperty());

        return view;
    }

    private void setLabelIntText(Label label, int toSet) {
        if(toSet > 0)
            label.setText(""+toSet);
        else
            label.setText(""+0);
    }

    @Override
    public void updateNewMessageToSend(Message message) {
        notifyMessage(message);
    }

    public Pane getRoot() {
        return root;
    }
}
