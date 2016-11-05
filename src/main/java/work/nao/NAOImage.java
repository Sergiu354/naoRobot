package work.nao;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALVideoDevice;
import work.nao.NAOSetings;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by sergiu on 12/2/15.
 */

public class NAOImage {
    //private Application application;
    private String name;
    private ALVideoDevice videoDevice;

    public NAOImage(Application application) throws Exception{
        videoDevice = new ALVideoDevice(application.session());
    }

    static BufferedImage createRGBImage(byte[] bytes, int width, int height) {
        DataBufferByte buffer = new DataBufferByte(bytes, bytes.length);
        ColorModel cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        return new BufferedImage(cm, Raster.createInterleavedRaster(buffer, width, height, width * 3, 3, new int[]{0, 1, 2}, null), false, null);
    }

    public BufferedImage getNAOImage(String nameGet) throws Exception {
        name = videoDevice.subscribeCamera(nameGet, 0, 2, 11, 15);
//        name = videoDevice.subscribeCamera(nameGet, 1, 2, 11, 15);
        ArrayList<Object> image = (ArrayList<Object>) videoDevice.getImageRemote(name);
        ByteBuffer buffer = (ByteBuffer) image.get(6);
        byte[] rawData = buffer.array();
        videoDevice.unsubscribe(name);
        BufferedImage imageOUT = createRGBImage(rawData, 640, 480);

        return imageOUT;
    }
}
