package me.combimagnetron.sunscreen.renderer.map;

import me.combimagnetron.passport.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.util.Vec2i;

import java.awt.image.BufferedImage;

public final class MapProtocol {
    private static final byte Magic = 0x24;

    public static ByteBuffer write(Vec2i pos, Vec2i size, byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.empty();
        byteBuffer.write(ByteBuffer.Adapter.BYTE, Magic);
        byteBuffer.write(ByteBuffer.Adapter.INT, pos.x());
        byteBuffer.write(ByteBuffer.Adapter.INT, pos.y());
        byteBuffer.write(ByteBuffer.Adapter.INT, size.x());
        byteBuffer.write(ByteBuffer.Adapter.INT, size.y());
        byteBuffer.write(data);
        return byteBuffer;
    }

    public static byte[] encode(BufferedImage image) {
        byte[] buffer = new byte[16384];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int d = image.getRGB(x, y);
                int b1 = d & 0xFF; int msb1 = b1 >> 7;
                int b2 = (d >> 8) & 0xFF; int msb2 = b2 >> 7;
                int b3 = (d >> 16) & 0xFF; int msb3 = b3 >> 7;
                int b4 = (msb3 << 2) | (msb2 << 1) | msb1;
                b1 &= 0x7F; b2 &= 0x7F; b3 &= 0x7F;
                buffer[(y * 2) * image.getWidth() * 2 + x * 2] = (byte) (b1 + 4);
                buffer[(y * 2) * image.getWidth() * 2 + x * 2 + 1] = (byte) (b2 + 4);
                buffer[(y * 2 + 1) * image.getWidth() * 2 + x * 2] = (byte) (b3 + 4);
                buffer[(y * 2 + 1) * image.getWidth() * 2 + x * 2 + 1] = (byte) (b4 + 4);
            }
        }

        return buffer;
    }

}
