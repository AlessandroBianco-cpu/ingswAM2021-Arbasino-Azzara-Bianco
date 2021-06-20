package it.polimi.ingsw.model.MarbleMarket;

import it.polimi.ingsw.observer.MarketObservable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static it.polimi.ingsw.utils.StaticUtils.COL_SIZE;
import static it.polimi.ingsw.utils.StaticUtils.ROW_SIZE;

public class Market extends MarketObservable {

    private Marble[][] marbleMatrix;
    private Marble marbleLeft;

    /**
     * Initialization of the Market creating 3x4 matrix of Marbles
     */
    public Market() {
        // the size of the row determines the number of columns and vice versa
        marbleMatrix = new Marble[COL_SIZE][ROW_SIZE];

        LinkedList<Marble> marbleLinkedList = new LinkedList<>();

        marbleLinkedList.add(new WhiteMarble());
        marbleLinkedList.add(new YellowMarble());
        marbleLinkedList.add(new GreyMarble());
        marbleLinkedList.add(new BlueMarble());
        marbleLinkedList.add(new BlueMarble());
        marbleLinkedList.add(new PurpleMarble());
        marbleLinkedList.add(new PurpleMarble());
        marbleLinkedList.add(new WhiteMarble());
        marbleLinkedList.add(new GreyMarble());
        marbleLinkedList.add(new YellowMarble());
        marbleLinkedList.add(new WhiteMarble());
        marbleLinkedList.add(new RedMarble());
        marbleLinkedList.add(new WhiteMarble());

        Collections.shuffle(marbleLinkedList);

        for (int i = 0; i < COL_SIZE; i++)
            for (int j = 0; j < ROW_SIZE; j++) {
                marbleMatrix[i][j] = marbleLinkedList.pop();
            }
        marbleLeft = marbleLinkedList.pop();

    }

    /**
     * This method performs the action of inserting the MarbleLeft in the MarbleMatrix in order to give the
     * player the list of Marbles which he will later insert into his depots
     * @param rowIndex index of the row of the MarbleMatrix in which player wants to insert the MarbleLeft
     * @return a list of Marbles that will be converted and added into the depots of the Player who performed this action
     */
    public List<Marble> insertMarbleInRow(int rowIndex) {

        List<Marble> marbleLinkedList = new LinkedList<>();

        //add Marbles to be converted in a List which will be returned to Player
        for (int i = 0; i < ROW_SIZE; i++) {
            marbleLinkedList.add(0, marbleMatrix[rowIndex][i]);
        }

        Marble tempMarble;

        tempMarble = marbleMatrix[rowIndex][0];

        for (int i = 0; i < ROW_SIZE - 1; i++)
            marbleMatrix[rowIndex][i] = marbleMatrix[rowIndex][i + 1];

        marbleMatrix[rowIndex][ROW_SIZE - 1] = marbleLeft;
        marbleLeft = tempMarble;

        notifyMarketState(this);
        return marbleLinkedList;

    }

    /**
     * This method performs the action of inserting the MarbleLeft in the MarbleMatrix in order to give the
     * player the list of Marbles which he will later insert into his depots
     * @param colIndex index of the column of the MarbleMatrix in which player wants to insert the MarbleLeft
     * @return a list of Marbles that will be converted and added into the depots of the Player who performed this action
     */
    public List<Marble> insertMarbleInCol(int colIndex) {

        List<Marble> marbleLinkedList = new LinkedList<>();

        //add Marbles to be converted in a List which will be returned to Player
        for (int i = 0; i < COL_SIZE; i++) {
            marbleLinkedList.add(0, marbleMatrix[i][colIndex]);
        }

        Marble tempMarble;
        tempMarble = marbleMatrix[0][colIndex];

        for (int i = 0; i < COL_SIZE - 1; i++)
            marbleMatrix[i][colIndex] = marbleMatrix[i + 1][colIndex];

        marbleMatrix[COL_SIZE - 1][colIndex] = marbleLeft;
        marbleLeft = tempMarble;

        notifyMarketState(this);
        return marbleLinkedList;

    }


    public Marble getMarbleByIndexes(int i, int j){
        return marbleMatrix[i][j];
    }

    public Marble getMarbleLeft() {
        return marbleLeft;
    }
}


