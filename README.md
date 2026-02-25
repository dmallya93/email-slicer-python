# Email Slicer (Java)

A simple CLI tool that parses an email address into its username and domain parts. This is a Java 21 port of the original [Python email-slicer](https://github.com/dmallya93/email-slicer-python.git) script.

## Prerequisites

- **Java 21** (JDK) — [Eclipse Temurin](https://adoptium.net/) or any compatible distribution
- **Maven 3.9.x** — [Download](https://maven.apache.org/download.cgi)

## Build

```bash
mvn clean package
```

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

The test suite verifies behavioral parity with the original Python script, including:

- Valid email parsing (username and domain extraction)
- Invalid email rejection (missing `@`)
- Whitespace trimming (leading/trailing spaces stripped before parsing)

## Behavioral Notes

- Output formatting exactly matches the original Python script, including the two-space gap before values (e.g., `Your username is:  avimax37`), which replicates Python's `print()` comma-separator behavior.
- Whitespace trimming uses Java's `String.trim()`, which is functionally equivalent to Python's `str.strip()` for standard ASCII email inputs.
- The email is split on the first `@` character, matching the Python original's `index("@")` behavior.
