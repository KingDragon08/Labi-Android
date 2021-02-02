package cn.wildfire.chat.app.redpacket;
import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.util.Timer;

public class i {
    public static void a(Activity paramActivity) {
        if (null == paramActivity)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager)paramActivity.getSystemService("input_method");
        inputMethodManager.toggleSoftInput(0, 2);
    }

    public static void b(Activity paramActivity) {
        if (null == paramActivity)
            return;
        try {
            View view = paramActivity.getWindow().peekDecorView();
            if (view != null && view.getWindowToken() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager)paramActivity.getSystemService("input_method");
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception exception) {}
    }

    public static final void a(EditText paramEditText) {
        paramEditText.setFocusableInTouchMode(true);
        paramEditText.requestFocus();
        // Timer timer = new Timer();
        // timer.schedule(new KeyboardUtil$1(paramEditText), 500L);
    }

    public static void a(Context paramContext, IBinder paramIBinder) {
        if (paramContext != null && paramIBinder != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)paramContext.getSystemService("input_method");
            inputMethodManager.hideSoftInputFromWindow(paramIBinder, 2);
        }
    }
}