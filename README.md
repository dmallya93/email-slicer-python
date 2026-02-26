# Email Slicer (Java)

A simple CLI tool that slices an email address into its username and domain parts. This is a Java 21 port of the original [email-slicer-python](https://github.com/dmallya93/email-slicer-python) project, preserving exact behavioral parity with the Python script.

## Prerequisites

- **Java 21** (JDK) — verify with `java -version`
- **Maven 3.9.x** — verify with `mvn -version`

## Build

```bash
mvn clean package
```

This compiles the source, runs all tests, and produces a runnable JAR in the `target/` directory.

## Run

```bash
java -jar target/email-slicer-1.0.0.jar
```

## Usage

The application prompts for an email address, then displays the username and domain parts.

### Valid Email

```
Please enter your Email Id:
avimax37@gmail.com
Your username is:  avimax37
Your domain is:  gmail.com
```

### Invalid Email

```
Please enter your Email Id:
invalidemail
Please enter a valid Email Id.
```

## Run Tests

```bash
mvn test
```

The test suite includes three cases:
1. **Valid email** — verifies correct username and domain extraction
2. **Invalid email** — verifies the error message for input without `@`
3. **Whitespace handling** — verifies that leading/trailing whitespace is trimmed

## Project Structure

```
pom.xml
src/
  main/
    java/
      emailslicer/
        EmailSlicer.java        # Main application class
  test/
    java/
      emailslicer/
        EmailSlicerTest.java    # JUnit 5 tests
```

## Behavioral Notes

- Output spacing exactly matches the Python original: Python's `print("label: ", value)` produces two spaces before the value (one from the string literal, one from the comma-separator), which is replicated in the Java version.
- Whitespace trimming uses Java's `String.trim()`, which is equivalent to Python's `str.strip()` for standard ASCII email input.
- Email splitting uses `indexOf('@')` to find the first `@` character, matching Python's `index("@")` behavior.
