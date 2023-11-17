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

    @Override
    public String toString() {
        return "RSASession{" +
                "\np=" + p +
                ", \nq=" + q +
                ", \nmodule=" + module +
                ", \neiler=" + eiler +
                ", \ne=" + e +
                ", \nd=" + d +
                "\n}";
    }
}
