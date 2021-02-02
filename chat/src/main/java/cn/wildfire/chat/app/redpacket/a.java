package cn.wildfire.chat.app.redpacket;

public class a {
    private static final char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static String a(byte[] paramArrayOfByte) {
        int i = 0;
        int j = paramArrayOfByte.length;
        StringBuffer stringBuffer = new StringBuffer(paramArrayOfByte.length * 3 / 2);
        int k = j - 3;
        int bool = i;
        byte b = 0;
        while (bool <= k) {
            byte b1 = (byte)((paramArrayOfByte[bool] & 0xFF) << 16 | (paramArrayOfByte[bool + 1] & 0xFF) << 8 | paramArrayOfByte[bool + 2] & 0xFF);
            stringBuffer.append(a[b1 >> 18 & 0x3F]);
            stringBuffer.append(a[b1 >> 12 & 0x3F]);
            stringBuffer.append(a[b1 >> 6 & 0x3F]);
            stringBuffer.append(a[b1 & 0x3F]);
            bool += 1;
            if (b++ >= 14) {
                b = 0;
                stringBuffer.append(" ");
            }
        }
        if (bool == i + j - 2) {
            byte b1 = (byte)((paramArrayOfByte[bool] & 0xFF) << 16 | (paramArrayOfByte[bool + 1] & 0xFF) << 8);
            stringBuffer.append(a[b1 >> 18 & 0x3F]);
            stringBuffer.append(a[b1 >> 12 & 0x3F]);
            stringBuffer.append(a[b1 >> 6 & 0x3F]);
            stringBuffer.append("=");
        } else if (bool == i + j - 1) {
            byte b1 = (byte)((paramArrayOfByte[bool] & 0xFF) << 16);
            stringBuffer.append(a[b1 >> 18 & 0x3F]);
            stringBuffer.append(a[b1 >> 12 & 0x3F]);
            stringBuffer.append("==");
        }
        return stringBuffer.toString();
    }
}