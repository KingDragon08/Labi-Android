package cn.wildfire.chat.app.redpacket;

import android.content.Context;
import android.util.TypedValue;

public class q {
    public static float a(int paramInt, Context paramContext) { return TypedValue.applyDimension(0, paramInt, paramContext.getResources().getDisplayMetrics()); }

    public static int a(Context paramContext, float paramFloat) {
        float f = (paramContext.getResources().getDisplayMetrics()).density;
        return (int)(paramFloat * f + 0.5F);
    }

    public static int a(Context paramContext, int paramInt) { return (int)TypedValue.applyDimension(1, paramInt, paramContext.getResources().getDisplayMetrics()); }

    public static int a(Context paramContext) { return (paramContext.getResources().getDisplayMetrics()).heightPixels; }

    public static int b(Context paramContext) { return (paramContext.getResources().getDisplayMetrics()).widthPixels; }
}