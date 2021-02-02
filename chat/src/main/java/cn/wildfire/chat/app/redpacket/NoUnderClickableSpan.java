package cn.wildfire.chat.app.redpacket;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class NoUnderClickableSpan extends ClickableSpan {
    public void onClick(View paramView) {}

    public void updateDrawState(TextPaint paramTextPaint) {
        super.updateDrawState(paramTextPaint);
        paramTextPaint.setUnderlineText(false);
        paramTextPaint.setColor(-65536);
    }
}
