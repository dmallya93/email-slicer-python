# Email Slicer

Email Slicer is a simple CLI tool that takes an email address as input and extracts the username and domain as output. This is a Java port of the original [Python email-slicer](https://github.com/dmallya93/email-slicer-python) project.

## Prerequisites

- **Java Development Kit (JDK) 21** or later
- **Apache Maven 3.9+**

## Build

Compile and run tests:

```bash
mvn clean test
```

Package as an executable JAR:

```bash
mvn clean package
```

## Run

After packaging, run the CLI:

```bash
java -jar target/email-slicer-1.0.0.jar
```

Then enter an email address when prompted.

## Example

Input:

```
Please enter your Email Id:
avimax37@gmail.com
```

Output:

```
Your username is: avimax37
Your domain is: gmail.com
```

If the input does not contain `@`, an error message is displayed:

```
Please enter a valid Email Id.
```

## Project Structure

```
├── pom.xml
└── src/
    ├── main/java/com/emailslicer/
    │   └── EmailSlicer.java
    └── test/java/com/emailslicer/
        └── EmailSlicerTest.java
```

## License

Distributed under the MIT License.
