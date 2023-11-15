import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application {
    public static void main(String args[]) {
        try(BufferedWriter writter = new BufferedWriter(new FileWriter("output.txt"))){
            //part 1
            writter.write("Part 1" + "\n");
            BigInteger bigint = new BigInteger("2");
            BigInteger res = bigint.pow(82589933);
            res = res.subtract(new BigInteger("1"));
            writter.write( res+ "\n");

            //part 2.1
            BigInteger probPrimeNumber = generatePrime(32);
            //BigInteger probPrimeNumber = new BigInteger(args[0]);
            writter.write("Простое ли число " + probPrimeNumber + " " + Boolean.toString(isPrime(probPrimeNumber)) + "\n");

            //part 2.2
            //BigInteger a = new BigInteger(args[1]);
            BigInteger a = new BigInteger("23562345");
            //BigInteger b = new BigInteger(args[2]);
            BigInteger b = new BigInteger("9568203");
            writter.write("Взаимнопростые ли числа " + a + " " + b + Boolean.toString(isCoprime(a, b)) + "\n");

            //part 3.1
            writter.write("Сгенерированное простое число " + generatePrime(32) + "\n");

            //part 3.2
            writter.write("Сгенерированные взаимнопростые числа " + generateCoprimes().toString());
        }
        catch (Exception ex) {
        }
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

    public static BigInteger generatePrime(Integer bytesAmount) {
        BigInteger res;
        do {
            Random random = new Random();
            res = new BigInteger(bytesAmount, random);
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
