import java.math.BigInteger;

public class RSASession {
    public BigInteger p;
    public BigInteger q;
    public BigInteger module;
    public BigInteger eiler;
    public BigInteger e = new BigInteger("65537");
    public BigInteger d;

    public RSASession() {
        // default
    }
}
