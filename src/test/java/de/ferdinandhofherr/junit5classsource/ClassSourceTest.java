package de.ferdinandhofherr.junit5classsource;

import de.ferdinandhofherr.junit5classsource.testsupport.PublicTestDataProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static de.ferdinandhofherr.junit5classsource.testsupport.AbstractTestDataProvider.TEST_DATA;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ClassSourceTest {

    @ParameterizedTest
    @ClassSource(PublicTestDataProvider.class)
    @DisplayName("Injects values generated by passed class")
    void injectsValuesGeneratedByPassedClass(Object value) {
        assertSame(TEST_DATA, value);
    }
}
