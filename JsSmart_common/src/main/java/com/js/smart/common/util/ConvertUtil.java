package com.js.smart.common.util;

public class ConvertUtil {

    /**
     * 校验和
     */
    public static String checkSum(byte[] data, int len) {
        byte[] rets = new byte[1];
        byte ret = 0;

        for (byte i = 0; i < len; i++) {
            ret += data[i];
        }
        rets[0] = (byte)~ret;
        return printHexString(rets);
    }

    /**
     * 16进制字符串转byte
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * char转byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * byte转16进制字符串
     */
    public static String printHexString( byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
