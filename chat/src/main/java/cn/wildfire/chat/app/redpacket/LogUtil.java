package cn.wildfire.chat.app.redpacket;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogUtil {
    private static boolean IS_SHOW_LOG = true;

    private static final String DEFAULT_MESSAGE = "execute";

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static final int JSON_INDENT = 4;

    private static final int V = 1;

    private static final int D = 2;

    private static final int I = 3;

    private static final int W = 4;

    private static final int E = 5;

    private static final int A = 6;

    private static final int JSON = 7;

    private static final int FILE = 8;

    public static void init(boolean paramBoolean) { IS_SHOW_LOG = paramBoolean; }

    public static void v() { printLog(1, null, "execute"); }

    public static void v(Object paramObject) { printLog(1, null, paramObject); }

    public static void v(String paramString1, String paramString2) { printLog(1, paramString1, paramString2); }

    public static void d() { printLog(2, null, "execute"); }

    public static void d(Object paramObject) { printLog(2, null, paramObject); }

    public static void d(String paramString, Object paramObject) { printLog(2, paramString, paramObject); }

    public static void i() { printLog(3, null, "execute"); }

    public static void i(Object paramObject) { printLog(3, null, paramObject); }

    public static void i(String paramString, Object paramObject) { printLog(3, paramString, paramObject); }

    public static void w() { printLog(4, null, "execute"); }

    public static void w(Object paramObject) { printLog(4, null, paramObject); }

    public static void w(String paramString, Object paramObject) { printLog(4, paramString, paramObject); }

    public static void e() { printLog(5, null, "execute"); }

    public static void e(Object paramObject) { printLog(5, null, paramObject); }

    public static void e(String paramString, Object paramObject) { printLog(5, paramString, paramObject); }

    public static void a() { printLog(6, null, "execute"); }

    public static void a(Object paramObject) { printLog(6, null, paramObject); }

    public static void a(String paramString, Object paramObject) { printLog(6, paramString, paramObject); }

    public static void json(String paramString) { printLog(7, null, paramString); }

    public static void json(String paramString1, String paramString2) { printLog(7, paramString1, paramString2); }

    public static void file(File paramFile, Object paramObject) { printFile(null, paramFile, null, paramObject); }

    public static void file(String paramString, File paramFile, Object paramObject) { printFile(paramString, paramFile, null, paramObject); }

    public static void file(String paramString1, File paramFile, String paramString2, Object paramObject) { printFile(paramString1, paramFile, paramString2, paramObject); }

    private static void printLog(int paramInt, String paramString, Object paramObject) {
        if (!IS_SHOW_LOG)
            return;
        StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
        byte b = 4;
        String str1 = arrayOfStackTraceElement[b].getFileName();
        String str2 = arrayOfStackTraceElement[b].getMethodName();
        int i = arrayOfStackTraceElement[b].getLineNumber();
        String str3 = (paramString == null) ? str1 : paramString;
        String str4 = str2.substring(0, 1).toUpperCase() + str2.substring(1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ (").append(str1).append(":").append(i).append(")#").append(str4).append(" ] ");
        String str5 = (paramObject == null) ? "Log with null Object" : paramObject.toString();
        if (str5 != null && paramInt != 7)
            stringBuilder.append(str5);
        String str6 = stringBuilder.toString();
        switch (paramInt) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                segmentPrintLog(paramInt, str3, str6);
                break;
            case 7:
                if (TextUtils.isEmpty(str5)) {
                    Log.e(str3, "Empty or Null json content");
                    return;
                }
                printJson(str3, str5, str6);
                break;
        }
    }

    private static void printLog(int paramInt, String paramString1, String paramString2) {
        switch (paramInt) {
            case 1:
                Log.v(paramString1, paramString2);
                break;
            case 2:
                Log.d(paramString1, paramString2);
                break;
            case 3:
                Log.i(paramString1, paramString2);
                break;
            case 4:
                Log.w(paramString1, paramString2);
                break;
            case 5:
                Log.e(paramString1, paramString2);
                break;
            case 6:
                Log.wtf(paramString1, paramString2);
                break;
        }
    }

    private static void printJson(String paramString1, String paramString2, String paramString3) {
        String str = null;
        try {
            if (paramString2.startsWith("{")) {
                JSONObject jSONObject = new JSONObject(paramString2);
                str = jSONObject.toString(4);
            } else if (paramString2.startsWith("[")) {
                JSONArray jSONArray = new JSONArray(paramString2);
                str = jSONArray.toString(4);
            }
        } catch (JSONException jSONException) {
            e(paramString1, jSONException.getCause().getMessage() + "\n" + paramString2);
            return;
        }
        printLine(paramString1, true);
        str = paramString3 + LINE_SEPARATOR + str;
        String[] arrayOfString = str.split(LINE_SEPARATOR);
        StringBuilder stringBuilder = new StringBuilder();
        for (String str1 : arrayOfString)
            stringBuilder.append("║ ").append(str1).append(LINE_SEPARATOR);
        Log.d(paramString1, stringBuilder.toString());
        printLine(paramString1, false);
    }

    private static void printFile(String paramString1, File paramFile, String paramString2, Object paramObject) {
        if (!IS_SHOW_LOG)
            return;
        StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
        byte b = 4;
        String str1 = arrayOfStackTraceElement[b].getFileName();
        String str2 = arrayOfStackTraceElement[b].getMethodName();
        int i = arrayOfStackTraceElement[b].getLineNumber();
        paramString1 = (paramString1 == null) ? str1 : paramString1;
        String str3 = str2.substring(0, 1).toUpperCase() + str2.substring(1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ (").append(str1).append(":").append(i).append(")#").append(str3).append(" ] ");
        String str4 = (paramObject == null) ? "Log with null Object" : paramObject.toString();
        String str5 = stringBuilder.toString();
        if (str4 != null)
            str4 = str5 + str4;
        paramString2 = (paramString2 == null) ? g.a() : paramString2;
        if (g.a(paramFile, paramString2, str4)) {
            Log.d(paramString1, str5 + " save log jrmf_b_success ! location is >>>" + paramFile.getAbsolutePath() + "/" + paramString2);
        } else {
            Log.e(paramString1, str5 + "save log fails !");
        }
    }

    private static void printLine(String paramString, boolean paramBoolean) {
        if (paramBoolean) {
            Log.d(paramString, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(paramString, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    private static void segmentPrintLog(int paramInt, String paramString1, String paramString2) {
        if (r.a(paramString1) || r.a(paramString2))
            return;
        char c = 'ఀ';
        long l = paramString2.length();
        if (l <= c) {
            printLog(paramInt, paramString1, paramString2);
        } else {
            while (paramString2.length() > c) {
                String str = paramString2.substring(0, c);
                paramString2 = paramString2.replace(str, "");
                printLog(paramInt, paramString1, str);
            }
            if (r.b(paramString2))
                printLog(paramInt, paramString1, paramString2);
        }
    }
}