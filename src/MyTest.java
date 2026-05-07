import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.io.*;

public class MyTest {
    public static void main() throws IOException {
        byte[] my_bytes = new byte[1];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(my_bytes);
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("liczba.bin"));
        dos.write(ByteBuffer.wrap(my_bytes).array());
        dos.write(1017);


    }
}
