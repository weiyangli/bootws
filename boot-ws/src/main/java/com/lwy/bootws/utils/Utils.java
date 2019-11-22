package com.lwy.bootws.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Utils {

    private final static String KEY = "bootWsPW";
    /**
     * spa512 加密
     * @param password: MD5后的密码
     * @return
     */
    public static String spa512Encode(String password) throws Exception {
        String newPassword = disorder(password);

        String ciphertext =  encryptDES(newPassword.getBytes("UTF-8"), KEY.getBytes("UTF-8"), KEY.getBytes("UTF-8"));

        return ciphertext;
    }
    /**
     * 加密函数
     *
     * @param data
     *            加密数据
     * @param key
     *            密钥
     * @return 返回加密后的数据
     */
    private static String encryptDES(byte[] data, byte[] key, byte[] iv) {
        try {
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 若采用NoPadding模式，data长度必须是8的倍数
            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

            // 用密匙初始化Cipher对象
            IvParameterSpec param = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);

            // 执行加密操作
            byte encryptedData[] = cipher.doFinal(data);

            return Base64.encodeBase64String(encryptedData);
        } catch (Exception e) {
            System.err.println("DES算法，加密数据出错!");
            e.printStackTrace();
        }
        return null;
    }
    private static String disorder(String password) {
        int length = password.length();

        String partA = password.substring(0, length / 2);
        String partB = password.substring(length / 2);

        String newPassword = partB + "%" + partA;
        return newPassword;
    }
}
