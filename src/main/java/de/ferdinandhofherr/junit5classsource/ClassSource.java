package de.ferdinandhofherr.junit5classsource;

import de.ferdinandhofherr.junit5classsource.internal.ClassArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(ClassArgumentsProvider.class)
public @interface ClassSource {
    Class<? extends TestDataProvider<?>> value();
}
