package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.client.LightModel.ParserForModel;
import it.polimi.ingsw.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public class BufferLight {

    private List<MarbleLight> buffer;

    public BufferLight() {
        this.buffer = new ArrayList<>();
    }

    public void updateBuffer(List<MarbleLight> buffer){
        this.buffer = buffer;
    }

    public void print(){
        if (buffer.size() == 0)
            return;
        ParserForModel resourceParser = new ParserForModel();
        System.out.println(ConsoleColors.RED + "BUFFER" + ConsoleColors.RESET);
        for(MarbleLight m : buffer){
            System.out.print(m.toString() + " ");
        }
        System.out.println();
    }
}
