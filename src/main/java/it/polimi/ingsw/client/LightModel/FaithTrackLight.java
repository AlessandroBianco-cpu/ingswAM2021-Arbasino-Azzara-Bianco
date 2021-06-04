package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.networking.message.updateMessage.FaithTrackUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

/**
 * Lightweight representation of the FaithTrack stored client-side
 */
public class FaithTrackLight{

    private final int FAITH_TRACK_SIZE = 25;
    private int position;
    private final int START_FIRST_VATICAN_SECTION = 5;
    private final int END_FIRST_VATICAN_SECTION = 8;
    private final int START_SECOND_VATICAN_SECTION = 12;
    private final int END_SECOND_VATICAN_SECTION = 16;
    private final int START_THIRD_VATICAN_SECTION = 19;
    private final int END_THIRD_VATICAN_SECTION = 24;
    private final int FIRST_POPE_FAVOR_TILE_SCORE = 2;
    private final int SECOND_POPE_FAVOR_TILE_SCORE = 3;
    private final int THIRD_POPE_FAVOR_TILE_SCORE = 4;
    private boolean firstPopeFavorAchieved = false;
    private boolean secondPopeFavorAchieved = false;
    private boolean thirdPopeFavorAchieved = false;


    /**
     * Updates the status of the faith-tracka
     * @param m content to update
     */
    public void update (FaithTrackUpdateMessage m){
        this.position = m.getPosition();
        firstPopeFavorAchieved = m.isFirstVaticanReportVPAchieved();
        secondPopeFavorAchieved = m.isSecondVaticanReportVPAchieved();
        thirdPopeFavorAchieved = m.isThirdVaticanReportVPAchieved();
    }

    // ------------------------ GETTERS ------------------------

    public int getPosition() {
        return position;
    }

    public boolean isFirstPopeFavorAchieved() {
        return firstPopeFavorAchieved;
    }

    public boolean isSecondPopeFavorAchieved() {
        return secondPopeFavorAchieved;
    }

    public boolean isThirdPopeFavorAchieved() {
        return thirdPopeFavorAchieved;
    }

    // ------------------------ PRINTERS ------------------------

    public void print(){
        System.out.println("|   |   |   | 1 |   |   | 2 |   |   | 4 |   |   | 6 |   |   | 9 |   |   |12 |   |   |16 |   |   |20 |<-Points");
        for(int i=0; i<FAITH_TRACK_SIZE+1; i++){
            if(i >= START_FIRST_VATICAN_SECTION && i < END_FIRST_VATICAN_SECTION
                    || i >= START_SECOND_VATICAN_SECTION  && i < END_SECOND_VATICAN_SECTION
                    || i >= START_THIRD_VATICAN_SECTION  && i < END_THIRD_VATICAN_SECTION ){
                System.out.print(ConsoleColors.YELLOW);
            }else if(i == END_FIRST_VATICAN_SECTION || i == END_FIRST_VATICAN_SECTION + 1
                    || i == END_SECOND_VATICAN_SECTION || i == END_SECOND_VATICAN_SECTION + 1
                    || i == END_THIRD_VATICAN_SECTION || i == END_THIRD_VATICAN_SECTION + 1){
                System.out.print(ConsoleColors.RED);
            }else{
                System.out.print(ConsoleColors.RESET);
            }
            if(i==25)
                System.out.println("|");
            else{
                System.out.print("| ");
                if(i!=position)
                    System.out.print(" ");
                else
                    System.out.print(ConsoleColors.RESET + "✞");
                System.out.print(" ");
            }
            System.out.print(ConsoleColors.RESET);
        }
        for(int i = 0; i<FAITH_TRACK_SIZE+1; i++){
            if(i==25)
                System.out.println("|<-Pope's Favor");
            else{
                System.out.print("|");
                if(i == END_FIRST_VATICAN_SECTION && firstPopeFavorAchieved)
                    System.out.print(ConsoleColors.GREEN_BACKGROUND + " " + FIRST_POPE_FAVOR_TILE_SCORE + " " + ConsoleColors.RESET);
                else if(i == END_FIRST_VATICAN_SECTION && !firstPopeFavorAchieved)
                    System.out.print(ConsoleColors.RED_BACKGROUND + " " + FIRST_POPE_FAVOR_TILE_SCORE + " " + ConsoleColors.RESET);
                else if(i == END_SECOND_VATICAN_SECTION && secondPopeFavorAchieved)
                    System.out.print(ConsoleColors.GREEN_BACKGROUND + " " + SECOND_POPE_FAVOR_TILE_SCORE + " " + ConsoleColors.RESET);
                else if(i == END_SECOND_VATICAN_SECTION && !secondPopeFavorAchieved)
                    System.out.print(ConsoleColors.RED_BACKGROUND + " " + SECOND_POPE_FAVOR_TILE_SCORE + " " + ConsoleColors.RESET);
                else if(i == END_THIRD_VATICAN_SECTION && thirdPopeFavorAchieved)
                    System.out.print(ConsoleColors.GREEN_BACKGROUND + " " + THIRD_POPE_FAVOR_TILE_SCORE + " " + ConsoleColors.RESET);
                else if(i == END_THIRD_VATICAN_SECTION && !thirdPopeFavorAchieved)
                    System.out.print(ConsoleColors.RED_BACKGROUND + " " + THIRD_POPE_FAVOR_TILE_SCORE + " " + ConsoleColors.RESET);
                else
                    System.out.print("   ");
            }
        }
        System.out.println("Position: " + position);
    }
}
