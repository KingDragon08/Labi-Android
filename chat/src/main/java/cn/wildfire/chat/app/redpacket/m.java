package cn.wildfire.chat.app.redpacket;

public class m {
    private static long a;

    public static boolean a() {
        boolean bool;
        long l = System.currentTimeMillis();
        if (l - a > 500L) {
            bool = false;
        } else {
            bool = true;
        }
        a = l;
        return bool;
    }
}