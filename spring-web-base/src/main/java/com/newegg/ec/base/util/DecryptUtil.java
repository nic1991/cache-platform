package com.newegg.ec.base.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;


/**
 * @author William Zhu
 *
 */
public class DecryptUtil {


    private static final byte[] Keys = { (byte) 0xEF, (byte) 0xAB, (byte) 0x56, (byte) 0x78, (byte) 0x90, (byte) 0x34, (byte) 0xCD, (byte) 0x12 };

    /**
     * 编码 base64
     *
     * @param bstr
     * @return String
     */
    private static String encode(byte[] bstr) {
        return new sun.misc.BASE64Encoder().encode(bstr);
    }

    /**
     * 解码 base64
     *
     * @param str
     * @return string
     */
    private static byte[] decode(String str) {
        byte[] bt = null;
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bt;
    }

    /**
     * 解密
     *
     * @param decryptString
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static String DecryptDES(String decryptString, String decryptKey) throws Exception {
        byte[] rgbKey = decryptKey.substring(0, 8).getBytes();
        byte[] rgbIV = Keys;
        byte[] inputByteArray = decode(decryptString);

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(rgbKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(rgbIV);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        return new String(cipher.doFinal(inputByteArray), "UTF-8");
    }

    /**
     * 加密
     *
     * @param encryptString
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String EncryptDES(String encryptString, String encryptKey) throws Exception {
        byte[] rgbKey = encryptKey.substring(0, 8).getBytes();
        byte[] rgbIV = Keys;
        byte[] inputByteArray = encryptString.getBytes();

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(rgbKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(rgbIV);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return encode(cipher.doFinal(inputByteArray));
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        System.out.println(DecryptDES("8t1Ze8ySnUoRb95t+6HR8g==", "newegg123"));
        System.out.println(DecryptDES("epPzNpO4Un8=", "newegg123"));
        System.out.println(EncryptDES("wz68dfdfdf", "newegg123greg"));
    }
}
