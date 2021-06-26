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
/**
 * Class representing a game
 */
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

    public DevCard popDevCardFromIndex(int index) {
        return devCardMarket.popDevCardFromIndex(index);
    }

    public List<QuantityResource> getDevCardRequirementsFromIndex(int index) { return devCardMarket.getDevCardFromIndex(index).getCost(); }

    public DevCard getDevCardFromIndex(int index) {
        return devCardMarket.getDevCardFromIndex(index);
    }

    /**
     * Performs a vatican report for each player of the game.
     * If the last vatican report is activated, the last round of the game wil start
     */
    @Override
    public void vaticanReport() {
        vaticanReportCounter++;
        for(Player player : players)
            player.vaticanReport(vaticanReportCounter);
        if (vaticanReportCounter == 3) {
            activateLastRound();
        }
    }

    /**
     * Method called after a player discards a non-white marble during a market action.
     * Each opponent of the player who discarded the marble will advance one space in the faith track
     * @param discardingPlayer player who discarded the marble
     */
    public abstract void advanceAfterDiscard(Player discardingPlayer);

    public void activateLastRound() {
        lastRound = true;
    }

    public boolean isLastRound(){
        return lastRound;
    }

    /**
     * Computes the score of each player at the end of the game and returns the winner
     * The player scoring the most VPs is the winner. In case of a tie between two players, the player with the
     * most Resources left in the supply between them is the winner.
     * @return the player who won the game.
     */
    public Player computeWinnerPlayer() {
        int currentScore;
        Player currentPlayer;
        Player winner = players.get(0);
        int max = winner.getTotalVictoryPoints();
        int maxResources = winner.getTotalOfResources();
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
