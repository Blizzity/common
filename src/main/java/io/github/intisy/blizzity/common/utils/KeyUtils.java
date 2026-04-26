
package io.github.intisy.blizzity.common.utils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility methods for cryptographic key operations.
 * @author Finn Birich
 */

public class KeyUtils {
    private static final int DEFAULT_KEY_LENGTH = 32;
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateApiKey() {
        return generateApiKey(DEFAULT_KEY_LENGTH);
    }

    public static String generateApiKey(int keyLengthBytes) {
        byte[] randomBytes = new byte[keyLengthBytes];
        secureRandom.nextBytes(randomBytes);

        String encodedKey = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        return "sk-" + encodedKey.replaceAll("[^a-zA-Z0-9]", "");
    }
}
