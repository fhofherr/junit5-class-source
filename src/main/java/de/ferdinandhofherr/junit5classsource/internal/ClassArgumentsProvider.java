package de.ferdinandhofherr.junit5classsource.internal;

import de.ferdinandhofherr.junit5classsource.ClassSource;
import de.ferdinandhofherr.junit5classsource.TestDataProvider;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

public class ClassArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<ClassSource> {

    private Class<? extends TestDataProvider<?>> testDataProviderClass;

    @Override
    public void accept(ClassSource classSource) {
        this.testDataProviderClass = classSource.value();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        TestDataProvider<?> testDataProvider = testDataProviderClass.getDeclaredConstructor().newInstance();
        return testDataProvider.provideTestData().map(Arguments::arguments);
    }
}
