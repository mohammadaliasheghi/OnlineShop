package com.google.OnlineShop.config;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crypto {
    private static final String UNICODE_FORMAT = "utf-8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private Cipher cipher;
    byte[] arrayBytes;
    SecretKey key;

    public void SetPassword(String Pass) throws Exception {
        String myEncryptionKey = "ThisIsSpartaThisIsSparta" + Pass;
        String myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        KeySpec ks = new DESedeKeySpec(arrayBytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }

    public String encrypt(String unencryptedString) throws IOException {
        String encryptedString = null;
        if (isEncrypt(unencryptedString)) {
            return unencryptedString;
        } else {

            try {
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
                byte[] encryptedText = cipher.doFinal(plainText);
                encryptedString = new String(Base64.encodeBase64(encryptedText));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return encryptedString;
        }
    }

    public Boolean isEncrypt(String string) {
        String pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(string);
        return m.find();
    }

    public String decrypt(String encryptedString) {
        String decryptedText = null;
        if (!isEncrypt(encryptedString)) {
            return encryptedString;
        } else {
            try {
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] encryptedText = Base64.decodeBase64(encryptedString.getBytes(UNICODE_FORMAT));
                byte[] plainText = cipher.doFinal(encryptedText);
                decryptedText = new String(plainText, UNICODE_FORMAT);

            } catch (Exception e) {

                decryptedText = encryptedString;
            }

            return decryptedText;
        }
    }

}

