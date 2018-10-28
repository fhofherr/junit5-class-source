package de.ferdinandhofherr.junit5classsource.internal;

import de.ferdinandhofherr.junit5classsource.ClassSource;
import de.ferdinandhofherr.junit5classsource.TestDataProvider;
import de.ferdinandhofherr.junit5classsource.testsupport.AbstractTestDataProvider;
import de.ferdinandhofherr.junit5classsource.testsupport.PublicTestDataProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import static de.ferdinandhofherr.junit5classsource.testsupport.AbstractTestDataProvider.TEST_DATA;
import static org.junit.jupiter.api.Assertions.*;

class ClassArgumentsProviderTest {

    @ParameterizedTest
    @ValueSource(classes = {
            NestedPrivateTestDataProvider.class,
            NestedPackagePrivateTestDataProvider.class,
            NestedProtectedTestDataProvider.class,
            NestedPublicTestDataProvider.class,
            PublicTestDataProvider.class
    })
    @DisplayName("Obtain values from classes implementing TestDataProvider")
    void obtainValuesFromClasses(Class<? extends TestDataProvider<Object>> testDataProvider) {
        ExtensionContext mockExtensionContext = Mockito.mock(ExtensionContext.class);

        ClassArgumentsProvider provider = new ClassArgumentsProvider();
        provider.accept(fakeClassSource(testDataProvider));
        Stream<? extends Arguments> arguments = provider.provideArguments(mockExtensionContext);
        Object testData = arguments
                .map(Arguments::get)
                .map(args -> args[0])
                .findFirst()
                .orElseGet(() -> fail("Stream was empty"));
        assertSame(TEST_DATA, testData);
    }

    @ParameterizedTest
    @ValueSource(classes = {
            MissingNoArgsConstructor.class,
            AbstractTestDataProvider.class,
            InstantiationFails.class
    })
    @DisplayName("Fails if TestDataProvider can't be instantiated")
    void failsIfTestDataProviderCantBeInstantiated(Class<? extends TestDataProvider<Object>> testDataProvider) {
        ExtensionContext mockExtensionContext = Mockito.mock(ExtensionContext.class);

        ClassArgumentsProvider provider = new ClassArgumentsProvider();
        provider.accept(fakeClassSource(testDataProvider));

        assertThrows(RuntimeException.class, () -> provider.provideArguments(mockExtensionContext));
    }

    private ClassSource fakeClassSource(Class<? extends TestDataProvider<Object>> testDataProvider) {
        return new ClassSource() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return ClassSource.class;
            }

            @Override
            public Class<? extends TestDataProvider<?>> value() {
                return testDataProvider;
            }
        };
    }

    private static class NestedPrivateTestDataProvider extends AbstractTestDataProvider {}

    static class NestedPackagePrivateTestDataProvider  extends AbstractTestDataProvider {}

    protected static class NestedProtectedTestDataProvider  extends AbstractTestDataProvider {}

    public static class NestedPublicTestDataProvider extends AbstractTestDataProvider {}

    private static class MissingNoArgsConstructor extends AbstractTestDataProvider {
        MissingNoArgsConstructor(Object arg) {
            // just a constructor with too many arguments
        }
    }

    private static class InstantiationFails extends AbstractTestDataProvider {
        InstantiationFails() {
            throw new RuntimeException("You will never instantiate me ^^");
        }
    }
}
