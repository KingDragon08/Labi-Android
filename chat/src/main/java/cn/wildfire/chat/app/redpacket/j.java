package cn.wildfire.chat.app.redpacket;
import java.util.Locale;

public class j {
    public static String a() {
        String str = Locale.getDefault().getLanguage();
        if ("zh".equals(str))
            return "zh_CN";
        if ("en".equals(str))
            return "en_US";
        return "zh_CN";
    }

    public static boolean b() { return "en".equals(Locale.getDefault().getLanguage()); }
}
