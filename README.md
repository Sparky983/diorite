# Diorite

Diorite is a fast, powerful, reactive library to enable the quick and easy creation of Minecraft 
bots. 

## Quick Links

- Wiki: https://github.com/Sparky983/diorite/wiki
- Maven Repository: https://repo.sparky983.me/
- Github: https://github.com/sparky983/diorite

## Installation

Gradle (kotlin)

```kotlin
repositories {
    maven("https://repo.sparky983.me/snapshots")
}

dependencies {
    implementation("io.github.sparky983:diorite-client:1.0.0-SNAPSHOT")
}
```

Gradle (groovy)

```groovy
repositories {
    maven { url 'https://repo.sparky983.me/snapshots' }
}

dependencies {
    implementation 'io.github.sparky983:diorite-client:1.0.0-SNAPSHOT'
}
```

Maven

```xml
<repositories>
    <repository>
        <id>sparky-snapshots</id>
        <name>Sparky's repository</name>
        <url>https://repo.sparky983.me/snapshots</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>io.github.sparky983</groupId>
        <artifactId>diorite-client</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Hello World Bot

```java
public class Main {
    public static void main(String[] args) {
        // Connect to localhost:25565
        DioriteClient client = DioriteClient.builder().connect();
        
        // Say "Hello, world!" in chat and block until complete
        client.chat("Hello, world!").block();
        
        // Disconnect the client
        client.close();
    }
}
```

## Related Projects

### [EmbeddedPaper](https://github.com/Sparky983/EmbeddedPaper)

Run paper plugins within single contained jar. 

When paired with the diorite client, this can be used for easy acceptance testing (and soon 
integration testing). 
