package de.ferdinandhofherr.junit5classsource;

import org.junit.jupiter.params.ParameterizedTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;

public class ClassSourceTest {

    @ParameterizedTest
    @ClassSource(TestData.class)
    void injectsValuesGeneratedByPassedClass(TestValue value) {
        assertSame(TestData.SINGLE_VALUE, value);
    }

    public static class TestData implements TestDataProvider<TestValue> {
        static final TestValue SINGLE_VALUE = new TestValue();

        @Override
        public Stream<TestValue> provideTestData() {
            return Stream.of(SINGLE_VALUE);
        }
    }

    public static class TestValue {}
}
