package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.model.LorenzoIlMagnifico.ActionToken;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Popup that shows  Lorenzo's information
 */
public class LorenzoPopup implements Popup {

    private Pane root;
    private Pane activatedToken;
    private List<Pane> positions;

    public LorenzoPopup(int pos, ActionToken lastToken) {

        try {
            root= FXMLLoader.load(getClass().getResource("/lorenzoPopup.fxml"));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        activatedToken = (Pane) root.lookup("#activatedToken");
        positions = new ArrayList<>();

        for(int i = 0; i<25; i++)
            positions.add((Pane) root.lookup("#slot"+i));

        addImage(positions.get(pos),"/punchBoard/blackCross.png");

        if (lastToken == null)
            addImage(activatedToken, "/punchBoard/retroToken.png");
        else
            addImage(activatedToken,"/punchBoard/"+lastToken.toImage());

    }

    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
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

    @Override
    public void displayStringMessages(String s) {

    }

}
