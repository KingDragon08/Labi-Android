package cn.wildfire.chat.app.redpacket;

import android.os.CountDownTimer;
import android.widget.TextView;

public class c extends CountDownTimer {
    private TextView a;

    private int b;

    public c(long paramLong1, long paramLong2, TextView paramTextView, int paramInt) {
        super(paramLong1, paramLong2);
        this.b = paramInt;
        this.a = paramTextView;
    }

    public void onTick(long paramLong) {
        /*
        if (this.b == 1) {
            this.a.setText(String.format(RongContext.getInstance().getString(R.string.jrmf_wait_resend_code), new Object[] { Long.valueOf(paramLong / 1000L) }));
        } else {
            this.a.setText(String.format(RongContext.getInstance().getString(R.string.jrmf_some_second), new Object[] { Long.valueOf(paramLong / 1000L) }));
        }
        */
        this.a.setClickable(false);
    }

    public void onFinish() {
        /*
        if (this.b == 1) {
            this.a.setText(RongContext.getInstance().getString(R.string.get_code));
        } else {
            this.a.setText(RongContext.getInstance().getString(R.string.re_send));
        }
         */
        this.a.setClickable(true);
    }
}