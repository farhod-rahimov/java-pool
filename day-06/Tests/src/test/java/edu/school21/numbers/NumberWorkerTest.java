package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    private final NumberWorker NUMBER_WORKER = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23})
    void isPrimeForPrimes(int number) {
        assertTrue(this.NUMBER_WORKER.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14, 15, 18})
    void isPrimeForNotPrimes(int number) {
        assertFalse(this.NUMBER_WORKER.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -1, -10, -3, -9, -100, -1011})
    void isPrimeForIncorrectNumbers(int number) {

        assertThrows(IllegalNumberException.class, () -> {
            this.NUMBER_WORKER.isPrime(number);
        });
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int number, int sum) {
        assertEquals(sum, NUMBER_WORKER.digitsSum(number));
    }
}