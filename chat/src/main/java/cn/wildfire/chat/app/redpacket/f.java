package cn.wildfire.chat.app.redpacket;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

public class f {
    public static void a(Drawable paramDrawable, TextView paramTextView) {
        if (paramDrawable != null) {
            paramDrawable.setBounds(0, 0, paramDrawable.getMinimumWidth(), paramDrawable.getMinimumHeight());
            paramTextView.setCompoundDrawables(null, null, paramDrawable, null);
        } else {
            paramTextView.setCompoundDrawables(null, null, null, null);
        }
    }

    public static void a(View paramView, boolean paramBoolean) {
        if (paramBoolean) {
            paramView.getBackground().mutate().setAlpha(255);
            paramView.setEnabled(true);
        } else {
            paramView.getBackground().mutate().setAlpha(100);
            paramView.setEnabled(false);
        }
    }

    public static void a(Context paramContext, TextView paramTextView, int paramInt, boolean paramBoolean) {
        Drawable drawable = paramContext.getResources().getDrawable(paramInt);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (paramBoolean) {
            paramTextView.setCompoundDrawables(null, null, drawable, null);
        } else {
            paramTextView.setCompoundDrawables(null, null, null, null);
        }
    }

    public static Drawable a(Context paramContext, String paramString, int paramInt) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (r.a(paramString)) {
            gradientDrawable.setColor(-1);
        } else if (paramString.startsWith("#")) {
            gradientDrawable.setColor(Color.parseColor(paramString));
        } else {
            gradientDrawable.setColor(Color.parseColor("#" + paramString));
        }
        gradientDrawable.setCornerRadius(q.a(paramContext, paramInt));
        return gradientDrawable;
    }
}