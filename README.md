# 

Archives containing JAR files are available as [releases](https://github.com/Blizzity/common/releases).

## What is common?

Shared utility library for Blizzity projects — argument parsing, password hashing, key generation, and math helpers.

## Usage in private projects

 * Maven (inside the  file)
```xml
  <repository>
      <id>github</id>
      <url>https://maven.pkg.github.com/Blizzity/common</url>
      <snapshots><enabled>true</enabled></snapshots>
  </repository>
  <dependency>
      <groupId>io.github.intisy</groupId>
      <artifactId>common</artifactId>
      <version>1.0</version>
  </dependency>
```

 * Maven (inside the  file)
```xml
  <servers>
      <server>
          <id>github</id>
          <username>your-username</username>
          <password>your-access-token</password>
      </server>
  </servers>
```

 * Gradle (inside the  or  file)
```groovy
  repositories {
      maven {
          url "https://maven.pkg.github.com/Blizzity/common"
          credentials {
              username = "<your-username>"
              password = "<your-access-token>"
          }
      }
  }
  dependencies {
      implementation 'io.github.intisy:common:1.0'
  }
```

## Usage in public projects

 * Gradle (inside the  or  file)
```groovy
  plugins {
      id "io.github.intisy.github-gradle" version "1.3.7"
  }
  dependencies {
      githubImplementation "intisy:common:1.0"
  }
```

Once you have it installed you can use it like so:

```
Arguments args = new Arguments(new String[]{"--debug", "--port=8080"});
String port = args.get("port", "3000");
boolean debug = args.getAsBoolean("debug");

String hashed = PasswordUtils.hashPassword("secret");
boolean valid = PasswordUtils.checkPassword("secret", hashed);

String apiKey = KeyUtils.generateApiKey();
```

## License

[![Apache License 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
