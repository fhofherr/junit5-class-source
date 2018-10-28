package de.ferdinandhofherr.junit5classsource;

import java.util.stream.Stream;

public interface TestDataProvider<T> {
    Stream<T> provideTestData();
}
