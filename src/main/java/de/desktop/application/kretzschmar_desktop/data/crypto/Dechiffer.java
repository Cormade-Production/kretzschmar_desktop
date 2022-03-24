package de.desktop.application.kretzschmar_desktop.data.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

/**
 * Encrypt files in the application to the default encryption ending: ".kretz"
 *
 */
public class Dechiffer {

    private static SecretKeySpec secretKeySpec;
    private static byte[] key;
    private static String stringKey = "AODAD21Aksl";

    /**
     * Encrypt a given String with the given key.
     * @param strToEncrypt The string that has to be encrypted.
     * @return The String as an encrypted one.
     */
    public static String encryptContent(final String strToEncrypt) {
        try {
            setKey(stringKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKC5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt a given String with the given key.
     * @param strToDecrypt The encrypted String, that should be decrypted now.
     * @return The String as a decrypted one.
     */
    public static String decryptContent(final String strToDecrypt) {
        try {
            setKey(stringKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKC5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setKey(final String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
