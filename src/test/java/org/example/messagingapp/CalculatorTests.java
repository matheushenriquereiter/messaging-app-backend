package org.example.messagingapp;

import org.example.messagingapp.util.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculatorTests {
    @Test
    void shouldSumTwoNumbers() {
        Calculator calculator = new Calculator();

        int result = calculator.sum(2, 2);

        Assertions.assertEquals(4, result);
    }
}
