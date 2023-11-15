import java.math.BigInteger;
import java.util.Random;

public class RSAEncryption {
    public static RSASession generateSession(Integer bytesAmount) {
        RSASession rsaSession = new RSASession();

        rsaSession.p = Application.generatePrime(bytesAmount/2);
        rsaSession.q = Application.generatePrime(bytesAmount/2);

        rsaSession.module = rsaSession.p.multiply(rsaSession.q);

        rsaSession.eiler = (rsaSession.p.subtract(BigInteger.ONE))
                .multiply(rsaSession.q.subtract(BigInteger.ONE));

        rsaSession.d = Application.generatePrime(bytesAmount/4);

        while(rsaSession.d.multiply(rsaSession.e)
                .mod(rsaSession.eiler)!=BigInteger.ONE) {
            rsaSession.d = Application.generatePrime(bytesAmount/4);
        }
        return rsaSession;
    }

    public static String encodeMessage(String message, Integer bytesAmount) {
        RSASession rsaSession = generateSession(bytesAmount);
        BigInteger integerMessage = BigInteger.ZERO;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c <= 127) {
                integerMessage = (integerMessage.multiply(new BigInteger("128"))).add(new BigInteger(Character.toString(c))); // Use 128 instead of 256
            }
        }
        return integerMessage.toString();
    }
}
