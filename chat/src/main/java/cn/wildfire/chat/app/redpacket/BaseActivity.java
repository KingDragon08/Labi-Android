package cn.wildfire.chat.app.redpacket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.imchat.ezn.R;
import cn.wildfire.chat.app.redpacket.ud.a;

public abstract class BaseActivity extends FragmentActivity implements a {
    public Activity context;
    public TextView tv_title;
    public String target;

    protected void onCreate(Bundle paramBundle) {
        requestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(getLayoutId());
        this.context = this;
        this.tv_title = (TextView)findViewById(R.id.tv_title);
        this.tv_title.setText("发红包");
        this.target = getIntent().getStringExtra("target");
        initView();
        initListener();
    }

    public abstract int getLayoutId();

    public void initView() {}

    public void initListener() {}

    public void onClick(int paramInt) {}

    public void onClick(View paramView) { onClick(paramView.getId()); }

    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == 0) {
            View view = getCurrentFocus();
            if (isShouldHideKeyboard(view, paramMotionEvent))
                i.a(this, view.getWindowToken());
        }
        return super.dispatchTouchEvent(paramMotionEvent);
    }

    private boolean isShouldHideKeyboard(View paramView, MotionEvent paramMotionEvent) {
        if (paramView != null && paramView instanceof android.widget.EditText) {
            int[] arrayOfInt = { 0, 0 };
            paramView.getLocationInWindow(arrayOfInt);
            int i = arrayOfInt[0], j = arrayOfInt[1], k = j + paramView.getHeight(), m = i + paramView.getWidth();
            if (paramMotionEvent.getX() > i && paramMotionEvent.getX() < m && paramMotionEvent.getY() > j && paramMotionEvent.getY() < k)
                return false;
            return true;
        }
        return false;
    }
}
