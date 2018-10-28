package de.ferdinandhofherr.junit5classsource.testsupport;

import de.ferdinandhofherr.junit5classsource.TestDataProvider;

import java.util.stream.Stream;

public abstract class AbstractTestDataProvider implements TestDataProvider<Object> {
    public static final Object TEST_DATA = new Object();

    @Override
    public Stream<Object> provideTestData() {
        return Stream.of(TEST_DATA);
    }
}
