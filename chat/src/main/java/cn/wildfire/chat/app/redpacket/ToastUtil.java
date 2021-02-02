package cn.wildfire.chat.app.redpacket;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;

    public static void showToast(final Context context, final String message) {
        if (context == null)
            return;
        (new Handler(Looper.getMainLooper())).post(new Runnable() {
            public void run() { Toast.makeText(context, message, 0).show(); }
        });
    }

    public static void showNoWaitToast(final Context context, final String message) {
        if (context == null)
            return;
        (new Handler(Looper.getMainLooper())).post(new Runnable() {
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(context, message, 0);
                    toast.show();
                } else {
                    toast.setText(message);
                    toast.show();
                }
            }
        });
    }
}
