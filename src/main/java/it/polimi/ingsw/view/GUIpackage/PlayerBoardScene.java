package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.client.LightModel.ModelLight;
import it.polimi.ingsw.client.LightModel.ProductionZoneLight;
import it.polimi.ingsw.client.LightModel.ResourceLight;
import it.polimi.ingsw.client.LightModel.StrongboxLight;
import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.networking.message.EndTurnMessage;
import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.observer.NetworkHandlerObservable;
import it.polimi.ingsw.view.GUIpackage.popup.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Main scene of the game, it implements all the actions which player can do
 */
@SuppressWarnings("unchecked")
public class PlayerBoardScene extends NetworkHandlerObservable implements SceneObserver {
    private final ModelLight gameModel;
    private final String owner;

    private Pane root;
    private final List<Pane> slot1;
    private final List<Pane> slot2;
    private final List<Pane> slot3;
    private final List<Pane> depots;
    private final List<Pane> leaders;
    private final List<Pane> extraDepots;
    private final List<Pane> popeSpaces;
    private final ChoiceBox<String> showChoice;
    private final Text consoleText;
    private final List<Label> strongboxLabel;
    private final List<Pane> faithTrack;


    public PlayerBoardScene(ModelLight model, String owner, MediaPlayer mp) {
        this.owner = owner;
        gameModel = model;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/playerBoardScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ImageView> buttonsList = new ArrayList<>();
        ImageView buyButton = (ImageView) root.lookup("#buyButton");
        ImageView swapButton = (ImageView) root.lookup("#swapButton");
        ImageView marketButton = (ImageView) root.lookup("#marketButton");
        ImageView showButton = (ImageView) root.lookup("#showButton");
        ImageView productionButton = (ImageView) root.lookup("#productionButton");
        ImageView endTurnButton = (ImageView) root.lookup("#endButton");
        ImageView volumeButton = (ImageView) root.lookup("#volumeButton");
        Pane inkwell = (Pane) root.lookup("#inkwell");

        showChoice = (ChoiceBox<String>) root.lookup("#showChoice");
        consoleText = (Text) root.lookup("#consoleText");

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
            for (String s : model.getOpponentsNicknames()) {
                showChoice.getItems().add(s);
            }
            showChoice.setValue(model.getOpponentsNicknames().get(0));
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

        if(model.getPlayerByNickname(owner).hasInkwell())
            addImage(inkwell, "/graphics/punchBoard/inkwell.png");

        faithTrack = new ArrayList<>();
        for (int i = 0; i < 25; i++)
            faithTrack.add((Pane) root.lookup("#slot" + i));

        //initial playerBoard setting
        addImage(faithTrack.get(gameModel.getPlayerByNickname(owner).getFaithTrack().getPosition()), "/graphics/punchBoard/blackCross.png");
        displayWarehouse();
        displayStrongbox(gameModel.getPlayerByNickname(owner).getStrongbox());
        displayProductionZone();
        updatePopeTiles();
        displayLeaders();

        showChoice.setStyle("-fx-font: normal 14 'Avenir Book'");

        for(Pane pane : leaders)
            pane.setOnMouseClicked(event -> {
                if ( !(model.getPlayerByNickname(owner).getLeaderCardsInHand().getCards().get(leaders.indexOf(pane)).isActive()) ) {
                    LeaderPopup popup = new LeaderPopup(model.getPlayerByNickname(owner).getLeaderCardsInHand().getCards().get(leaders.indexOf(pane)), leaders.indexOf(pane));
                    popup.addObserver(this);
                    popup.display();
                }
            });

        volumeButton.setOnMouseClicked(event -> mp.setMute(!mp.isMute()));

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
            if (showChoice.getValue().equals("LorenzoIlMagnifico"))
                new LorenzoPopup(model.getLorenzoPosition(),model.getLorenzoLatestUsedToken()).display();
            else
                new ShowOpponentPopup(model.getPlayerByNickname(showChoice.getValue())).display();
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

        leaderGraphicUpdate();

    }


    private void updatePopeTiles() {
        if(gameModel.getPlayerByNickname(owner).getFaithTrack().isFirstPopeFavorAchieved())
            addImage(popeSpaces.get(0), "/graphics/punchBoard/yellow_front_tile.png");
        else
            addImage(popeSpaces.get(0), "/graphics/punchBoard/yellow_back_tile.png");

        if(gameModel.getPlayerByNickname(owner).getFaithTrack().isSecondPopeFavorAchieved())
            addImage(popeSpaces.get(1), "/graphics/punchBoard/orange_front_tile.png");
        else
            addImage(popeSpaces.get(1), "/graphics/punchBoard/orange_back_tile.png");

        if(gameModel.getPlayerByNickname(owner).getFaithTrack().isThirdPopeFavorAchieved())
            addImage(popeSpaces.get(2), "/graphics/punchBoard/red_front_tile.png");
        else
            addImage(popeSpaces.get(2), "/graphics/punchBoard/red_back_tile.png");

    }

    public void displayProductionZone() {

        for(int i = 0; i<3; i++) {
            slot1.get(i).getChildren().clear();
            slot2.get(i).getChildren().clear();
            slot3.get(i).getChildren().clear();
        }

        ProductionZoneLight zone = gameModel.getPlayerByNickname(owner).getProductionZone();
        for(int i = 0; i<zone.getFirstSlot().size(); i++) {
            addImage(slot1.get(i), "/graphics/devCards/" +zone.getFirstSlot().get(i).getId()+".png");
        }

        for(int i = 0; i<zone.getSecondSlot().size(); i++) {
            addImage(slot2.get(i), "/graphics/devCards/" +zone.getSecondSlot().get(i).getId()+".png");
        }

        for(int i = 0; i<zone.getThirdSlot().size(); i++) {
            addImage(slot3.get(i), "/graphics/devCards/" +zone.getThirdSlot().get(i).getId()+".png");
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

        addImage(faithTrack.get(pos), "/graphics/punchBoard/blackCross.png");

        updatePopeTiles();
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
            addImage(leaders.get(i), "/graphics/leaderCards/" + cards.get(i).getId() + ".png");
            if (cards.get(i).isActive())
                leaders.get(i).setEffect(borderEffect);
        }

        leaderGraphicUpdate();

    }


    private  void leaderGraphicUpdate() {
        List<LeaderCard> inHand = gameModel.getPlayerByNickname(owner).getLeaderCardsInHand().getCards();
        for (int leaderCardIndex = 0; leaderCardIndex < inHand.size(); leaderCardIndex++) {
            LeaderCard current = inHand.get(leaderCardIndex);
            if (current.isExtraDepotCard() && current.isActive()) {
                int extraDepotIndex = gameModel.getPlayerByNickname(owner).getWarehouse().getExtraDepotsIndexByType(current.getAbilityResource());
                if(extraDepotIndex != -1){
                for (int numOfResources = 0; numOfResources < gameModel.getPlayerByNickname(owner).getWarehouse().getExtraDepotsQuantity()[extraDepotIndex]; numOfResources++) {
                    addImage(extraDepots.get(2 * leaderCardIndex + numOfResources), gameModel.getPlayerByNickname(owner).getWarehouse().getExtraDepotsTypes()[extraDepotIndex].toImage());
                    }
                }
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
     */
    private void addImage(Pane pane, String image) {
        ImageView view = new ImageView();
        view.setImage(new Image(image));

        pane.getChildren().add(view);
        view.fitWidthProperty().bind(pane.widthProperty());
        view.fitHeightProperty().bind(pane.heightProperty());

    }

    private void setLabelIntText(Label label, int toSet) {
        if(toSet > 0)
            label.setText(""+toSet);
        else
            label.setText(""+0);
    }

    public void displayInPopup (String message) {
            new AlertPopup().displayStringMessages(message);
    }

    @Override
    public void updateNewMessageToSend(Message message) {
        notifyMessage(message);
    }

    public Pane getRoot() {
        return root;
    }
}
