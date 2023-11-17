import java.math.BigInteger;

public class RSASession {
    public BigInteger p;
    public BigInteger q;
    public BigInteger module;
    public BigInteger eiler;
    public BigInteger e = new BigInteger("17");
    public BigInteger d;

    public RSASession() {
        // default
    }

    @Override
    public String toString() {
        return "RSASession{" +
                "p=" + p +
                ", q=" + q +
                ", module=" + module +
                ", eiler=" + eiler +
                ", e=" + e +
                ", d=" + d +
                '}';
    }
}
