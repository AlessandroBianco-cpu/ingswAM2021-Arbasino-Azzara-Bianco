package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.networking.message.Message;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class SceneObservable {
    private final List<SceneObserver> observers = new ArrayList<>();

    public void addObserver(SceneObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void notifyNewMessageFromClient(Message message){
        synchronized (observers) {
            for(SceneObserver observer : observers){
                observer.updateNewMessageToSend(message);
            }
        }
    }

    /**
     * Adds an ImageView to a Pane
     * @param pane where to add the image
     * @param image path
     * @return the new image
     */
    public ImageView addImage(Pane pane, String image) {
        ImageView view = new ImageView();
        view.setImage(new Image(image));

        pane.getChildren().add(view);
        view.fitWidthProperty().bind(pane.widthProperty());
        view.fitHeightProperty().bind(pane.heightProperty());

        return view;
    }

}
