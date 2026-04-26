# Blizzity Common

Shared utility library for Blizzity projects — argument parsing, password hashing, key generation, and math helpers.

## Installation

Add the dependency using [github-gradle](https://github.com/intisy/github-gradle):

```groovy
plugins {
    id "io.github.intisy.github-gradle" version "1.8.2.1"
}

dependencies {
    githubImplementation "intisy:blizzity-common:1.0"
}
```

## Usage

```java
// CLI argument parsing with .env directory support
Arguments args = new Arguments(new String[]{"--debug", "--port=8080"});
String port = args.get("port", "3000");
boolean debug = args.getAsBoolean("debug");

// Password hashing (PBKDF2)
String hashed = PasswordUtils.hashPassword("secret");
boolean valid = PasswordUtils.checkPassword("secret", hashed);

// API key generation
String apiKey = KeyUtils.generateApiKey();

// Secure password generation
PasswordGenerator gen = new PasswordGenerator();
String password = gen.generateSecurePassword();
```

## Modules

| Class | Package | Description |
|---|---|---|
| `Arguments` | `cli` | CLI argument parser with `.env/` directory and environment variable fallback |
| `PasswordUtils` | `utils` | PBKDF2 password hashing and verification |
| `KeyUtils` | `utils` | Cryptographic API key generation |
| `PasswordGenerator` | `utils` | Secure random password generation |
| `MathUtils` | `utils` | BigDecimal arithmetic helpers |

## Requirements

- Java 8+

## License

Copyright (c) Finn Birich. All rights reserved.
