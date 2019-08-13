package net.mbl;

import static net.mbl.Util.toByteArray;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hello world!
 */
public class App 
{

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("hello");
        int size = 131072;
        int avg_size = size;
        int min_size = avg_size / 2;
        int max_size = avg_size * 2;
        String fileName = "/tmp/xxx.jar";
        byte[] buf = toByteArray(fileName);
        FastCDC fastCDC = new FastCDC(buf, min_size, avg_size, max_size);
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        while(fastCDC.hasNext()) {
            FastCDC.Chunk chunk = fastCDC.next();
            int end = chunk.offset + chunk.length;
            digest.update(buf, chunk.offset, chunk.length);
            String digestStr = fastCDC.sum.byteToHex(digest.digest());
            System.out.println(String.format("hash=%s offset=%d size=%d", digestStr, chunk.offset, chunk.length));
        }
    }
}
