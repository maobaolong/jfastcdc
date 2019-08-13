package net.mbl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Util {
    public static long logarithm2(long value) {
        return Math.round(Math.log(value)/Math.log(2));
    }

    /**
     * Division that rounds up where modulus would be zero.
     *
     * @param x left op num
     * @param y right op num
     * @return result
     */
    public static long ceil_div(long x, long y) {
        if (x % y == 0) {
            return ((x / y) + 1);
        } else {
            return (x / y);
        }
    }

    /**
     * Find the middle of the desired chunk size, or what the FastCDC paper refers, to as the "normal size".
     * @param average
     * @param minimum
     * @param source_size
     * @return
     */
    public static long center_size(long average, long minimum, long source_size) {
        long offset = minimum + ceil_div(minimum, 2);
        if (offset > average) {
            offset = average;
        }
        long size = average - offset;
        if (size > source_size) {
            return source_size;
        } else {
            return size;
        }
    }

    public static int mask(int bits) {
        assert(bits >= 1);
        assert(bits <= 31);
        return (int) (Math.pow(2, bits) - 1);
    }

    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static long nextPowerOfTwo(long x) {
        long out = 1;
        long exp = 0;

        while (out < x) {
            exp++;
            out = (long) Math.pow(2, exp);
        }

        return out;
    }

    public static void main(String[] args) {
        System.out.println(logarithm2(100));
        System.out.println(logarithm2(10));
        System.out.println(logarithm2(2));
        System.out.println(nextPowerOfTwo(9));
        System.out.println(Long.bitCount(0b1111));
    }
}
