package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.VaticanReporter;
import it.polimi.ingsw.observer.FaithTrackObservable;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.utils.StaticUtils.*;

public class FaithTrack extends FaithTrackObservable {

    private Map<Integer, Integer> faithTrack = new HashMap<>();

    private int position;
    private int vaticanReportFaithPoints;

    private boolean firstVaticanReportHasOccurred;
    private boolean secondVaticanReportHasOccurred;
    private boolean thirdVaticanReportHasOccurred;

    private boolean firstVaticanSectionPointsAchieved;
    private boolean secondVaticanSectionPointsAchieved;
    private boolean thirdVaticanSectionPointsAchieved;

    private String owner;
    private VaticanReporter vaticanReporter;

    public FaithTrack(VaticanReporter vaticanReporter, String owner) {
        this.vaticanReporter = vaticanReporter;
        this.owner = owner;
        position = 0;
        vaticanReportFaithPoints = 0;
        firstVaticanReportHasOccurred = false;
        secondVaticanReportHasOccurred = false;
        thirdVaticanReportHasOccurred = false;

        faithTrack.put(0,0);
        faithTrack.put(1,0);
        faithTrack.put(2,0);
        faithTrack.put(3,1);
        faithTrack.put(4,1);
        faithTrack.put(5,1);
        faithTrack.put(6,2);
        faithTrack.put(7,2);
        faithTrack.put(8,2);
        faithTrack.put(9,4);
        faithTrack.put(10,4);
        faithTrack.put(11,4);
        faithTrack.put(12,6);
        faithTrack.put(13,6);
        faithTrack.put(14,6);
        faithTrack.put(15,9);
        faithTrack.put(16,9);
        faithTrack.put(17,9);
        faithTrack.put(18,12);
        faithTrack.put(19,12);
        faithTrack.put(20,12);
        faithTrack.put(21,16);
        faithTrack.put(22,16);
        faithTrack.put(23,16);
        faithTrack.put(24,20);
    }


    public int getPosition() {
        return position;
    }

    public void updatePosition(){
        position++;

        if ((position == 8 && !firstVaticanReportHasOccurred) ||
            (position == 16 && !secondVaticanReportHasOccurred) ||
            (position == 24 && !thirdVaticanReportHasOccurred)
        ) {
            vaticanReporter.vaticanReport();
        }
    }

    /**
     * This method updates player's position in the faithTrack by N cells
     * @param n number of positions to move forward
     */
    public void moveForwardNPositions(int n){
        if ((position + n > 24))
            n = 24 - position;

        for (int i = 0; i < n; i++)
            updatePosition();

        notifyFaithTrackState(this);
    }

    private void firstVaticanReport(){
        if (!firstVaticanReportHasOccurred){
            if (position >= FIRST_VATICAN_SECTION){
                vaticanReportFaithPoints += FIRST_POPE_FAVOR_TILE_SCORE;
                firstVaticanSectionPointsAchieved = true;
            }
            firstVaticanReportHasOccurred = true;
        }
    }

    private void secondVaticanReport(){
        if (!secondVaticanReportHasOccurred){
            if (position >= SECOND_VATICAN_SECTION){
                vaticanReportFaithPoints += SECOND_POPE_FAVOR_TILE_SCORE;
                secondVaticanSectionPointsAchieved = true;
            }
            secondVaticanReportHasOccurred = true;
        }

    }

    private void thirdVaticanReport(){
        if (!thirdVaticanReportHasOccurred){
            if (position >= THIRD_VATICAN_SECTION){
                vaticanReportFaithPoints += THIRD_POPE_FAVOR_TILE_SCORE;
                thirdVaticanSectionPointsAchieved = true;
            }
            thirdVaticanReportHasOccurred = true;
        }
    }

    /**
     * This method is used to start the vaticanReport corresponding to the one has not occurred yet
     * @param vaticanReportNumber number of vatican Report that has to occur
     */
    public void vaticanReport(int vaticanReportNumber){
        switch (vaticanReportNumber){
            case 1: firstVaticanReport(); break;
            case 2: secondVaticanReport(); break;
            case 3: thirdVaticanReport(); break;
        }
        notifyFaithTrackState(this);
    }

    /**
     * @return the score corresponding to the one on the Pope's Favor
     * tile where the Player's faith marker is
     */
    public int getPositionScore(){
        return faithTrack.get(position);
    }

    public int getVaticanReportFaithPoints(){
        return vaticanReportFaithPoints;
    }

    public boolean isFirstVaticanSectionPointsAchieved() {
        return firstVaticanSectionPointsAchieved;
    }

    public boolean isSecondVaticanSectionPointsAchieved() {
        return secondVaticanSectionPointsAchieved;
    }

    public boolean isThirdVaticanSectionPointsAchieved() {
        return thirdVaticanSectionPointsAchieved;
    }

    public String getOwner(){return owner;}
}