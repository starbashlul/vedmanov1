import java.math.BigInteger;
import java.util.Random;

public class RSAEncryption {

    public static RSASession currentSession;

    public static RSASession generateSession(Integer bitesAmount) {
        RSASession rsaSession = new RSASession();
        Random rand = new Random();

        rsaSession.p = BigInteger.probablePrime(bitesAmount / 2, rand);

        rsaSession.q = BigInteger.probablePrime(bitesAmount / 2, rand);

        System.out.println("p and q generated successfully " + rsaSession.p + " " + rsaSession.q);

        rsaSession.module = rsaSession.p.multiply(rsaSession.q);

        System.out.println("module generated successfully " + rsaSession.module);

        rsaSession.eiler = (rsaSession.p.subtract(BigInteger.ONE))
                .multiply(rsaSession.q.subtract(BigInteger.ONE));


        System.out.println("eiler generated successfully " + rsaSession.eiler);

        rsaSession.d = rsaSession.e.modInverse(rsaSession.eiler);

        System.out.println("d generated successfully " + rsaSession.d);

        return rsaSession;
    }

    public static BigInteger encodeMessage(String message, Integer bitesAmount) {
        RSASession rsaSession = generateSession(bitesAmount);
        RSAEncryption.currentSession = rsaSession;
        BigInteger integerMessage = BigInteger.ZERO;
        for (int i = 0; i < message.length(); i++) {
            char cha = message.charAt(i);
            int c = cha;
            if (c <= 127) {
                integerMessage = (integerMessage.multiply(new BigInteger("128"))).add(new BigInteger(String.valueOf(c)));
            }
        }
        System.out.println("Integer message " + integerMessage);
        integerMessage = integerMessage.modPow(rsaSession.e, rsaSession.module);
        System.out.println("Integer encoded message " + integerMessage);
        return integerMessage;
    }

    public static String decodeMessage(BigInteger message) {
        StringBuilder stringMessage = new StringBuilder();
        BigInteger decodedMessage;
        decodedMessage = message.modPow(RSAEncryption.currentSession.d, RSAEncryption.currentSession.module);
        System.out.println("Integer decoded message " + decodedMessage);
        while(decodedMessage.compareTo(BigInteger.ZERO)==1) {
            char c = (char) (decodedMessage.mod(new BigInteger("128")).intValue());
            decodedMessage = decodedMessage.divide(new BigInteger("128"));
            stringMessage.insert(0, c);
        }
        return stringMessage.toString();
    }
}
