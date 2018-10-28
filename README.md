# junit5-class-source

A simple `@ClassSource` annotation to use with
[JUnit5](https://junit.org) [parameterized
tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests).

## Example

```java
...

@ParameterizedTest
@ClassSource(SampleDataProvider.class)
void showClassSourceUsage(SampleData data) {
    assertTrue(asList("value1", "value2").contains(data.value));
}

...

private static class SampleDataProvider implements TestDataProvider<SampleData> {

    @Override
    public Stream<SampleData> provideTestData() {
        return Stream.of(
                new SampleData("value1"),
                new SampleData("value2"));
    }
}

private static class SampleData {
    String value;

    SampleData(String value) {
        this.value = value;
    }
}
```

## Rationale

I really love to use the [parameterized
tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)
feature of JUnit5. Mostly I use the `@MethodSource`. However, often I wish
I could specify the source my test data in a more refactoring-save way instead
of a string pointing to a method in a class.

The `@ClassSource` allows me to do just this. Instead of a string I specify
a class. I don't have to bother about updating the string (or count on my IDE
doing it) when I decide to refactor my sources.

## License

Copyright © 2018 Ferdinand Hofherr

Distributed under the MIT License.
