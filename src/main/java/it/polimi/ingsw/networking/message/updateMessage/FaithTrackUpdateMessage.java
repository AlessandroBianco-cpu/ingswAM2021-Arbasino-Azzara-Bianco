package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.networking.message.Broadcast;

/**
 * Packet used to update the status of the Faith Track
 */
public class FaithTrackUpdateMessage extends Broadcast {
    private static final long serialVersionUID = 1557675867488135049L;

    private final String nickname;
    private final int position;
    private final boolean firstVaticanReportVPAchieved;
    private final boolean secondVaticanReportVPAchieved;
    private final boolean thirdVaticanReportVPAchieved;

    public FaithTrackUpdateMessage(String nickname, int position, boolean firstVaticanReportVPAchieved, boolean secondVaticanReportVPAchieved, boolean thirdVaticanReportVPAchieved) {
        this.nickname = nickname;
        this.position = position;
        this.firstVaticanReportVPAchieved = firstVaticanReportVPAchieved;
        this.secondVaticanReportVPAchieved = secondVaticanReportVPAchieved;
        this.thirdVaticanReportVPAchieved = thirdVaticanReportVPAchieved;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPosition() {
        return position;
    }

    public boolean isFirstVaticanReportVPAchieved() {
        return firstVaticanReportVPAchieved;
    }

    public boolean isSecondVaticanReportVPAchieved() {
        return secondVaticanReportVPAchieved;
    }

    public boolean isThirdVaticanReportVPAchieved() {
        return thirdVaticanReportVPAchieved;
    }
}
