# Email Slicer (Java)

A simple CLI tool that reads an email address from standard input, validates it, and splits it into username and domain components.

This is a Java port of the original [email-slicer-python](https://github.com/dmallya93/email-slicer-python.git) project.

## Prerequisites

- **Java 21** (JDK)
- **Maven 3.9.x**

## Build

```bash
mvn clean package
```

## Run

### Piped input

```bash
echo "avimax37@gmail.com" | java -jar target/email-slicer-java-1.0-SNAPSHOT.jar
```

Output:

```
Please enter your Email Id:
Your username is:  avimax37
Your domain is:  gmail.com
```

### Interactive input

```bash
java -jar target/email-slicer-java-1.0-SNAPSHOT.jar
```

Then type an email address and press Enter.

### Invalid input

```bash
echo "invalidemail" | java -jar target/email-slicer-java-1.0-SNAPSHOT.jar
```

Output:

```
Please enter your Email Id:
Please enter a valid Email Id.
```

## Test

```bash
mvn test
```

## Project Structure

```
├── pom.xml
└── src
    ├── main
    │   └── java
    │       └── emailslicer
    │           └── EmailSlicer.java
    └── test
        └── java
            └── emailslicer
                └── EmailSlicerTest.java
```
