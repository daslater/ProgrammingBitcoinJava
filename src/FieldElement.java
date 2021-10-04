import java.math.BigInteger;
import java.util.Formatter;

public class FieldElement {
    private final BigInteger num;
    private final BigInteger prime;

    public FieldElement(BigInteger num, BigInteger prime) throws IllegalArgumentException {
        if (num.compareTo(prime) >= 0 || num.compareTo(BigInteger.ZERO) < 0) {
            Formatter fmt = new Formatter();
            fmt.format("Num %d not in field range 0 to %d", num, prime.subtract(BigInteger.ONE));
            throw new IllegalArgumentException(fmt.toString());
        }
        this.num = num;
        this.prime = prime;
    }

    @Override
    public String toString() {
        Formatter fmt = new Formatter();
        fmt.format("FieldElement_%d(%d)", prime, num);
        return fmt.toString();
    }

    public boolean equals(FieldElement other) {
        return num.equals(other.num) && prime.equals(other.prime);
    }

    public FieldElement add(FieldElement other) throws IllegalArgumentException {
        if (!prime.equals(other.prime)) {
            throw new IllegalArgumentException("Cannot add two numbers in different Fields");
        }
        BigInteger sum = num.add(other.num).mod(prime);
        return new FieldElement(sum, prime);
    }

    public FieldElement subtract(FieldElement other) throws IllegalArgumentException {
        if (!prime.equals(other.prime)) {
            throw new IllegalArgumentException("Cannot add two numbers in different Fields");
        }
        BigInteger difference = num.subtract(other.num).mod(prime);
        return new FieldElement(difference, prime);
    }

    public FieldElement multiply(FieldElement other) throws IllegalArgumentException {
        if (!prime.equals(other.prime)) {
            throw new IllegalArgumentException("Cannot add two numbers in different Fields");
        }
        BigInteger product = num.multiply(other.num).mod(prime);
        return new FieldElement(product, prime);
    }

    public FieldElement pow(BigInteger exponent) {
        BigInteger n = exponent.mod(prime.subtract(BigInteger.ONE));
        BigInteger result = num.modPow(n, prime);
        return new FieldElement(result, prime);
    }

    public FieldElement divide(FieldElement other) throws IllegalArgumentException {
        if (!prime.equals(other.prime)) {
            throw new IllegalArgumentException("Cannot add two numbers in different Fields");
        }
        // use Fermat's little theorem:
        // num**(prime-1) % prime == 1
        // this means: 1/num == modPow(num, prime-2, prime)
        // quotient = (num * (other.num**(prime - 2) % prime)) % prime
        BigInteger primeMinusTwo = prime.subtract(BigInteger.TWO);
        BigInteger quotient = other.num.modPow(primeMinusTwo, prime);
        quotient = num.multiply(quotient).mod(prime);
        return new FieldElement(quotient, prime);
    }

    public FieldElement multiply (BigInteger coefficient) {
        BigInteger product = num.multiply(coefficient).mod(prime);
        return new FieldElement(product, prime);
    }

}