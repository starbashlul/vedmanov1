import java.math.BigInteger;
import java.util.Random;

public class RSAEncryption {

    public static RSASession currentSession;

    public static RSASession generateSession(Integer bytesAmount) {
        RSASession rsaSession = new RSASession();

        rsaSession.p = Application.generatePrime(bytesAmount);
        rsaSession.q = Application.generatePrime(bytesAmount);

        System.out.println("p and q generated successfully " + rsaSession.p + " " + rsaSession.q);

        rsaSession.module = rsaSession.p.multiply(rsaSession.q);

        System.out.println("module generated successfully " + rsaSession.module);

        rsaSession.eiler = (rsaSession.p.subtract(BigInteger.ONE))
                .multiply(rsaSession.q.subtract(BigInteger.ONE));


        System.out.println("eiler generated successfull " + rsaSession.eiler);

        for(BigInteger d = new BigInteger("2"); d.compareTo(rsaSession.eiler)<1; d=d.add(BigInteger.ONE)) {
            if(d.multiply(rsaSession.e).mod(rsaSession.eiler).equals(BigInteger.ONE)) {
                rsaSession.d=new BigInteger(String.valueOf(d));
                break;
            }
        }

        System.out.println("d generated successfully " + rsaSession.d);

        return rsaSession;
    }

    public static BigInteger encodeMessage(String message, Integer bytesAmount) {
        RSASession rsaSession = generateSession(bytesAmount);
        RSAEncryption.currentSession = rsaSession;
        BigInteger integerMessage = BigInteger.ZERO;
        for (int i = 0; i < message.length(); i++) {
            char cha = message.charAt(i);
            int c = cha;
            if (c <= 127) {
                integerMessage = (integerMessage.multiply(new BigInteger("128"))).add(new BigInteger(String.valueOf(c-96)));
            }
        }
        System.out.println("Integer message" + integerMessage);
        integerMessage = (integerMessage.pow(rsaSession.e.intValue())).mod(rsaSession.module);
        System.out.println("Integer encoded message " + integerMessage);
        return integerMessage;
    }

    public static String decodeMessage(BigInteger message) {
        StringBuilder stringMessage = new StringBuilder();
        BigInteger decodedMessage;
        decodedMessage = message.pow(RSAEncryption.currentSession.d.intValue()).mod(RSAEncryption.currentSession.module);
        System.out.println("Integer decoded message" + decodedMessage);
        while(decodedMessage.intValue()>0) {
            char c = (char) (decodedMessage.mod(new BigInteger("128")).intValue() + 96);
            decodedMessage = decodedMessage.divide(new BigInteger("128"));
            stringMessage.append(c);
        }
        return stringMessage.toString();
    }
}
