import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application {
    public static void main(String args[]) {
        try(BufferedWriter writter = new BufferedWriter(new FileWriter("output.txt"))) {
            Kuznechik kuz = new Kuznechik();
            byte[] encripted = kuz.Encript(new BigInteger("1122334455667700ffeeddccbbaa9988", 16));
            for(int i = 0; i < encripted.length; i++) {
                System.out.print(byteToHex(encripted[i]) + " ");
            }

            byte[] decripted = kuz.Decript(encripted);
            System.out.println();
            for(int i = 0; i < decripted.length; i++) {
                System.out.print(byteToHex(decripted[i]) + " ");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }


    public static byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    public static boolean isPrime(BigInteger number) {
        // Проверка числа на меньше или равно 1
        if (number.compareTo(BigInteger.ONE) <= 0) {
            return false;
        }

        // Начинаем проверку с делителя 2
        BigInteger divisor = new BigInteger("2");

        // Вычисляем корень числа и округляем его до ближайшего целого
        BigInteger sqrt = number.sqrt();
        if (sqrt.multiply(sqrt).equals(number)) {
            return false; // Если число является полным квадратом, то оно не простое
        }

        // Проверяем делители от 2 до корня числа
        while (divisor.compareTo(sqrt) <= 0) {
            if (number.remainder(divisor).equals(BigInteger.ZERO)) {
                return false; // Если число делится нацело, оно не простое
            }
            // Переходим к следующему возможному делителю
            divisor = divisor.add(BigInteger.ONE);
        }

        // Если не найдено делителей, кроме 1 и самого числа, то оно простое
        return true;
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger temp = b;
            b = a.remainder(b);
            a = temp;
        }
        return a.abs();
    }

    public static boolean isCoprime(BigInteger a, BigInteger b) {
        // Находим НОД (наибольший общий делитель) для a и b
        BigInteger gcd = gcd(a, b);

        // Если НОД равен 1, числа являются взаимно простыми
        return gcd.equals(BigInteger.ONE);
    }

    public static BigInteger generatePrime(Integer bitesAmount) {
        BigInteger res;
        do {
            Random random = new Random();
            res = new BigInteger(bitesAmount, random);
        } while(!isPrime(res));
        return res;
    }

    public static List<BigInteger> generateCoprimes() {
        BigInteger a = generatePrime(32);
        List<BigInteger> list = new ArrayList<>();
        list.add(a);
        BigInteger b;
        do {
            Random random = new Random();
            b = new BigInteger(32, random);
        } while(!isCoprime(a,b));
        list.add(b);
        return list;
    }
}
