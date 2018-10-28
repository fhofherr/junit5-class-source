package de.ferdinandhofherr.junit5classsource.internal;

import de.ferdinandhofherr.junit5classsource.ClassSource;
import de.ferdinandhofherr.junit5classsource.TestDataProvider;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class ClassArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<ClassSource> {

    private Class<? extends TestDataProvider<?>> testDataProviderClass;

    @Override
    public void accept(ClassSource classSource) {
        this.testDataProviderClass = classSource.value();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        TestDataProvider<?> testDataProvider = instantiateTestDataProvider();
        return testDataProvider.provideTestData().map(Arguments::arguments);
    }

    private TestDataProvider<?> instantiateTestDataProvider() {
        Constructor<? extends TestDataProvider<?>> ctor;
        try {
             ctor = testDataProviderClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    "The test data provider " +
                            testDataProviderClass.getName() +
                            " did not implement a no-arguments constructor", e);
        }
        if (!ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
        try {
            return ctor.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Could not create instance of " + testDataProviderClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Could not access no-args constructor of " +
                    testDataProviderClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Instantiation of " + testDataProviderClass.getName() + " failed", e);
        }
    }
}
