package it.polimi.ingsw.model.GameMode;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.Cards.LeaderDeck;
import it.polimi.ingsw.model.DevCardMarket;
import it.polimi.ingsw.model.MarbleMarket.Market;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.VaticanReporter;

import java.util.LinkedList;
import java.util.List;

public abstract class Game implements VaticanReporter {

    List<Player> players = new LinkedList<>();
    protected Market market = new Market();
    protected LeaderDeck leaderDeck;
    protected boolean lastRound = false;
    protected DevCardMarket devCardMarket = new DevCardMarket();
    protected int vaticanReportCounter = 0;

    public void addPlayers(List<Player> playersToAdd) {
        for(Player p : playersToAdd ){
            if(!players.contains(p)){
                players.add(p);
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public LeaderDeck getLeaderDeck() {
        return leaderDeck;
    }

    public Market getMarket() {
        return market;
    }

    public DevCardMarket getDevCardMarket(){ return devCardMarket; }

    public Game(){
        this.leaderDeck = new LeaderDeck();
    }

    public abstract void advanceAfterDiscard(Player discardingPlayer);

    public DevCard popDevCardFromIndex(int index) {
        return devCardMarket.popDevCardFromIndex(index);
    }

    public List<QuantityResource> getDevCardRequirementsFromIndex(int index) {
        return devCardMarket.getDevCardFromIndex(index).getCost();
    }

    public DevCard getDevCardFromIndex(int index) {
        return devCardMarket.getDevCardFromIndex(index);
    }

    @Override
    public void vaticanReport() {
        vaticanReportCounter++;
        for(Player player : players)
            player.vaticanReport(vaticanReportCounter);
        if (vaticanReportCounter == 3) {
            activateLastRound();
        }
    }

    public void activateLastRound() {
        lastRound = true;
    }

    public boolean isLastRound(){
        return lastRound;
    }

    public Player computeWinnerPlayer() {
        int max = 0, maxResources = 0;
        int currentScore;
        Player currentPlayer;
        Player winner = players.get(0);
        for (int i = 1; i < players.size(); i++){
            currentPlayer = players.get(i);
            currentScore = currentPlayer.getTotalVictoryPoints();
            if (currentScore > max){
                winner = currentPlayer;
                max = currentScore;
                maxResources = currentPlayer.getTotalOfResources();
            } else if (currentScore == max){
                if (currentPlayer.getTotalOfResources() > maxResources){
                    winner = currentPlayer;
                    max = currentScore;
                    maxResources = currentPlayer.getTotalOfResources();
                }
            }
        }
        return winner;
    }

}
