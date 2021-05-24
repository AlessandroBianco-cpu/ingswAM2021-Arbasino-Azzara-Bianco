package it.polimi.ingsw.client.LightModel.market;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BufferLightTest {

    @Test
    void graphicBufferTest() {
        BufferLight bufferLight = new BufferLight();
        List<MarbleLight> updateList = new ArrayList<>();

        updateList.add(new GreyMarbleLight());
        updateList.add(new YellowMarbleLight());
        updateList.add(new BlueMarbleLight());
        updateList.add(new BlueMarbleLight());

        bufferLight.updateBuffer(updateList);

        bufferLight.print();


    }

}