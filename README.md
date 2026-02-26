# Email Slicer (Java)

A simple CLI tool that parses an email address into its username and domain components. This is a Java 21 port of the original [Python email-slicer script](https://github.com/dmallya93/email-slicer-python).

## Prerequisites

- **Java 21** (JDK) — e.g., Eclipse Temurin, Oracle JDK, or any compatible distribution
- **Maven 3.9.x**

## Build

```bash
mvn clean package
```

This compiles the source, runs all tests, and produces a runnable JAR in the `target/` directory.

## Run

```bash
java -jar target/email-slicer-1.0.0.jar
```

### Example Session (Valid Email)

```
Please enter your Email Id:
avimax37@gmail.com
Your username is:  avimax37
Your domain is:  gmail.com
```

### Example Session (Invalid Email)

```
Please enter your Email Id:
invalidemail
Please enter a valid Email Id.
```

## Test

```bash
mvn test
```

The test suite includes three JUnit 5 tests verifying:

- **Valid email input** — correct username and domain extraction with proper spacing
- **Invalid email input** — appropriate error message for input without `@`
- **Whitespace handling** — leading/trailing whitespace is trimmed before parsing

## Project Structure

```
pom.xml
src/
  main/
    java/
      emailslicer/
        EmailSlicer.java
  test/
    java/
      emailslicer/
        EmailSlicerTest.java
```

## Behavioral Notes

This Java application preserves exact behavioral parity with the original Python script (`emailSlicer.py`):

- Prompts with `"Please enter your Email Id:"` before reading input
- Trims leading and trailing whitespace (Java `trim()` matching Python `strip()`)
- Splits on the first `@` character to extract username and domain
- Output lines include two spaces before the value (e.g., `Your username is:  avimax37`), matching Python's `print("label: ", value)` comma-separator behavior
- Exits with code 0 for all standard flows
