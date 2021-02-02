package cn.wildfire.chat.app.redpacket;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public final class d {
    private static Cipher a = null;

    static  {
        try {
            a = Cipher.getInstance("DES");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        DESKeySpec dESKeySpec = new DESKeySpec(paramArrayOfByte2);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = secretKeyFactory.generateSecret(dESKeySpec);
        a.init(1, secretKey, secureRandom);
        return a.doFinal(paramArrayOfByte1);
    }

    public static String a(String paramString) {
        try {
            return a(a(paramString.getBytes(), "yilucaifu".getBytes()));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static String a(String paramString1, String paramString2) {
        try {
            return a(a(paramString1.getBytes("UTF-8"), paramString2.getBytes("UTF-8")));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static String a(byte[] paramArrayOfByte) {
        String str1 = "";
        String str2 = "";
        for (byte b = 0; b < paramArrayOfByte.length; b++) {
            str2 = Integer.toHexString(paramArrayOfByte[b] & 0xFF);
            if (str2.length() == 1) {
                str1 = str1 + "0" + str2;
            } else {
                str1 = str1 + str2;
            }
        }
        return str1.toUpperCase();
    }
}