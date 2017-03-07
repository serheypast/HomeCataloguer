package ru.bsuir.lab2.sample.encrypt;

/**
 * Created by Сергей on 07.03.2017.
 */

public class EncryptPassword {

    private static final String key = "key";

    /**
     * encrypt string by xor
     * @param stringForEncrypt String stringForEncrypt
     * @return encryptString
     */
    public static String encryptString(String stringForEncrypt){
        byte[] arr = stringForEncrypt.getBytes();
        byte[] keyarr = key.getBytes();
        byte[] result = new byte[arr.length];
        for(int i = 0; i< arr.length; i++)
        {
            result[i] = (byte) (arr[i] ^ keyarr[i % keyarr.length]);
        }
        String encryptPassword = new String(result);
        return  encryptPassword;
    }
}
