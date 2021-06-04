package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

/**
 * Lightweight representation of the Buffer of marbles to store. It is stored client-side
 */
public class BufferLight {

    private List<MarbleLight> buffer;

    public BufferLight() {
        this.buffer = new ArrayList<>();
    }

    /**
     * Updates the player's buffer state
     * @param buffer latest buffer
     */
    public void updateBuffer(List<MarbleLight> buffer){
        this.buffer = buffer;
    }

    public void print(){
        if (buffer.size() == 0)
            return;

        System.out.println(ConsoleColors.RED + "BUFFER" + ConsoleColors.RESET);
        for(MarbleLight m : buffer){
            System.out.print(m.toString() + " ");
        }
        System.out.println();
    }

    public List<MarbleLight> getBuffer() { return buffer; }
}
