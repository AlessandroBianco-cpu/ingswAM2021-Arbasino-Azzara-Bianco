package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.GameMode.Game;
import it.polimi.ingsw.model.GameMode.MultiPlayerGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FaithTrackTest {

    FaithTrack faithTrack;

    @BeforeEach
    void setup(){
        Game multiplayerGame = new MultiPlayerGame();
        faithTrack = new FaithTrack(multiplayerGame);
    }

    @Test
    void getPosition(){
        assertEquals(0, faithTrack.getPosition());
        faithTrack.moveForwardNPositions(5);
        assertEquals(5, faithTrack.getPosition());
    }

    @Test
    void updatePosition() {
        faithTrack.updatePosition();
        assertEquals(1, faithTrack.getPosition());

    }

    @Test
    void moveForwardNPositions() {
        faithTrack.moveForwardNPositions(5);
        assertEquals(5, faithTrack.getPosition());
        faithTrack.moveForwardNPositions(20); //after this invocation should not go over 24
        assertEquals(24, faithTrack.getPosition());
        faithTrack.moveForwardNPositions(10);
        assertEquals(24, faithTrack.getPosition()); //after this invocation should not go over 24
    }

    @Test
    void vaticanReport(){

    }

    @Test
    void getConsistentPositionScore() {
        assertEquals(0, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(0, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(0, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(1, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(1, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(1, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(2, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(2, faithTrack.getPositionScore()); //7
        faithTrack.moveForwardNPositions(1);
        assertEquals(2, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(4, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(4, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(4, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(6, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(6, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(6, faithTrack.getPositionScore()); //14
        faithTrack.moveForwardNPositions(1);
        assertEquals(9, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(9, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(9, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(12, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(12, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(12, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(16, faithTrack.getPositionScore()); //21
        faithTrack.moveForwardNPositions(1);
        assertEquals(16, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(16, faithTrack.getPositionScore());
        faithTrack.moveForwardNPositions(1);
        assertEquals(20, faithTrack.getPositionScore()); //24
    }

    @Test
    void getVaticanReportFaithPoints(){
        faithTrack.moveForwardNPositions(14);
        faithTrack.vaticanReport(1);
        faithTrack.vaticanReport(2);
        faithTrack.vaticanReport(3);
        assertEquals(5, faithTrack.getVaticanReportFaithPoints());
    }

    @Test
    void getAllVaticanReportFaithPoints(){
        faithTrack.moveForwardNPositions(22);
        faithTrack.vaticanReport(1);
        faithTrack.vaticanReport(2);
        faithTrack.vaticanReport(3);
        assertEquals(9, faithTrack.getVaticanReportFaithPoints());
    }

}