package cn.wildfire.chat.app.redpacket;

import android.text.TextUtils;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public class r {
    public static boolean a(String paramString) {
        if (paramString != null && paramString.trim().length() > 0)
            return false;
        return true;
    }

    public static boolean b(String paramString) {
        if (paramString != null && paramString.trim().length() > 0)
            return true;
        return false;
    }

    public static boolean c(String paramString) {
        if (paramString != null && paramString.trim().length() > 0 && !"null".equals(paramString))
            return true;
        return false;
    }

    public static boolean d(String paramString) {
        if (a(paramString) || "null".equals(paramString))
            return true;
        return false;
    }

    public static boolean e(String paramString) {
        if (TextUtils.isEmpty(paramString))
            return false;
        char c = f(paramString.substring(0, paramString.length() - 1));
        if (c == 'N')
            return false;
        return (paramString.charAt(paramString.length() - 1) == c);
    }

    public static char f(String paramString) {
        if (paramString == null || paramString.trim().length() == 0 || !paramString.matches("\\d+"))
            return 'N';
        char[] arrayOfChar = paramString.trim().toCharArray();
        char c = Character.MIN_VALUE;
        int i;
        byte b;
        for (i = arrayOfChar.length - 1, b = 0; i >= 0; i--, b++) {
            char c1 = (char)(arrayOfChar[i] - '0');
            if (b % 2 == 0) {
                c1 *= '\002';
                c1 = (char)(c1 / '\n' + c1 % '\n');
            }
            c += c1;
        }
        return (c % '\n' == '\000') ? '0' : (char)('\n' - c % '\n' + '0');
    }

    public static boolean g(String paramString) {
        if (a(paramString))
            return false;
        return (paramString.length() == 15 || paramString.length() == 18);
    }

    public static String a(double paramDouble) {
        BigDecimal bigDecimal = new BigDecimal(paramDouble);
        return bigDecimal.setScale(2, 4).toString();
    }

    public static String h(String paramString) {
        BigDecimal bigDecimal = new BigDecimal(paramString.trim());
        return bigDecimal.setScale(2, 4).toPlainString();
    }

    public static double i(String paramString) {
        if (d(paramString))
            return 0.0D;
        BigDecimal bigDecimal = new BigDecimal(paramString);
        return bigDecimal.setScale(2, 4).doubleValue();
    }

    public static Double j(String paramString) {
        Double ret = null;
        if (d(paramString))
            return Double.valueOf(0.0D);
        try {
            ret = Double.valueOf(paramString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return ret;
    }

    public static boolean k(String paramString) {
        if (a(paramString))
            return false;
        String str = "^1[0-9]{10}$";
        return Pattern.matches(str, paramString);
    }

    public static String l(String paramString) {
        if (paramString.length() >= 8) {
            int k = 4;
            int m = paramString.length() - 4;
            StringBuilder stringBuilder3 = new StringBuilder(m - k);
            for (byte b1 = 0; b1 < m - k; b1++)
                stringBuilder3 = stringBuilder3.append("*");
            StringBuilder stringBuilder4 = new StringBuilder(paramString);
            return stringBuilder4.replace(k, m, stringBuilder3.toString()).toString();
        }
        if (paramString.length() == 0)
            return paramString;
        int i = 2;
        int j = paramString.length() - 2;
        StringBuilder stringBuilder1 = new StringBuilder(j - i);
        for (byte b = 0; b < j - i; b++)
            stringBuilder1 = stringBuilder1.append("*");
        StringBuilder stringBuilder2 = new StringBuilder(paramString);
        return stringBuilder2.replace(i, j, stringBuilder1.toString()).toString();
    }

    public static String m(String paramString) {
        if (a(paramString))
            paramString = "";
        int i = paramString.length();
        StringBuilder stringBuilder = new StringBuilder();
        if (i <= 1) {
            stringBuilder.append("*");
        } else if (i == 2) {
            stringBuilder.append("*").append(paramString.charAt(i - 1));
        } else if (i == 3) {
            stringBuilder.append("**").append(paramString.charAt(i - 1));
        } else {
            stringBuilder.append("***").append(paramString.charAt(i - 1));
        }
        return stringBuilder.toString();
    }

    public static String n(String paramString) {
        if (a(paramString))
            return "";
        StringBuffer stringBuffer = new StringBuffer();
        if (paramString.length() <= 15) {
            stringBuffer.append(paramString.charAt(0)).append("*************").append(paramString.charAt(paramString.length() - 1));
        } else {
            stringBuffer.append(paramString.charAt(0)).append("****************").append(paramString.charAt(paramString.length() - 1));
        }
        return stringBuffer.toString();
    }
}