package it.polimi.ingsw.view.GUIpackage;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TransitionHandler {

    private static Stage primaryStage;

    private static Scene loadingScene;

    private static Scene nicknameScene;
    private static Scene discardLeadersScene;
    private static Scene waitingScene;
    private static Scene playerBoardScene;
    private static Scene initialResourcesScene;
    private static Scene numPlayersScene;
    private static Scene connectionScene;
    private static Scene winnerScene;

    public static void setPrimaryStage(Stage primaryStage) {
        TransitionHandler.primaryStage = primaryStage;
    }

    //scene setters
    public static void setLoadingScene(Scene loadingScene) {
        TransitionHandler.loadingScene = loadingScene;
    }

    public static void setPlayerBoardScene(PlayerBoardScene playerBoardScene) { TransitionHandler.playerBoardScene = new Scene(playerBoardScene.getRoot()); }

    public static void setInitialResourcesScene(InitialResourcesScene initialResourcesScene) { TransitionHandler.initialResourcesScene = new Scene(initialResourcesScene.getRoot()); }

    public static void setNicknameScene(NicknameScene nicknameScene){ TransitionHandler.nicknameScene = new Scene(nicknameScene.getRoot()); }

    public static void setConnectionScene(ConnectionScene connectionScene){ TransitionHandler.connectionScene = new Scene(connectionScene.getRoot()); }

    public static void setNumPlayersScene(NumPlayersScene numPlayersScene){ TransitionHandler.numPlayersScene = new Scene(numPlayersScene.getRoot()); }

    public static void setDiscardLeadersScene(DiscardLeadersScene discardLeadersScene) { TransitionHandler.discardLeadersScene = new Scene(discardLeadersScene.getRoot()); }

    public static void setWaitingScene(WaitingScene waitingScene) { TransitionHandler.waitingScene = new Scene(waitingScene.getRoot()); }

    public static void setWinnerScene(WinnerScene winnerScene) { TransitionHandler.winnerScene = new Scene(winnerScene.getRoot()); }


    //methods to change scene...
    private static void swapSceneTo(Scene scene){
        Platform.runLater(() -> primaryStage.setScene(scene));
    }

    public static void toLoadingScene(){ swapSceneTo(loadingScene); }

    public static void toNicknameScene(){
        swapSceneTo(nicknameScene);
    }

    public static void toPlayerBoardScene(){
        swapSceneTo(playerBoardScene);
    }

    public static void toDiscardLeadersScene(){
        swapSceneTo(discardLeadersScene);
    }

    public static void toWaitingScene(){
        swapSceneTo(waitingScene);
    }

    public static void toInitialResourcesScene(){
        swapSceneTo(initialResourcesScene);
    }

    public static void toNumPlayersScene(){
        swapSceneTo(numPlayersScene);
    }

    public static void toConnectionScene(){
        swapSceneTo(connectionScene);
    }

    public static void toWinnerScene() { swapSceneTo(winnerScene); }

}
