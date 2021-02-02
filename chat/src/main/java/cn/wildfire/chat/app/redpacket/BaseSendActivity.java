package cn.wildfire.chat.app.redpacket;

import android.widget.EditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.TextView;


public abstract class BaseSendActivity extends BaseActivity {
    protected String summary; // 红包留言
    protected int type; // 红包类型, 0->单发,1->普通群发,2->拼运气群发
    protected int id; // 服务端的红包id
    protected EditText et_peadk_message;
    protected String maxLimitMoney = "1000";
    protected int maxCount = 100;
    protected EditText et_amount;
    protected EditText et_peak_message;

    public abstract int getLayoutId();

    public void initView() {
    }

    public void initListener() {
    }

    public static boolean isNumber(String paramString) {
        if (!r.a(paramString))
            return paramString.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        return false;
    }

    protected class InputMoney implements InputFilter {
        public CharSequence filter(CharSequence param1CharSequence, int param1Int1, int param1Int2, Spanned param1Spanned, int param1Int3, int param1Int4) {
            if (param1CharSequence.toString().equals(".") && param1Int3 == 0 && param1Int4 == 0) {
                BaseSendActivity.this.et_amount.setText("0" + param1CharSequence + param1Spanned);
                BaseSendActivity.this.et_amount.setSelection(2);
            }

            if (param1Int3 >= 8) {
                return "";
            }

            if (param1Spanned.toString().indexOf(".") != -1 && param1Spanned.length() - param1Spanned.toString().indexOf(".") > 2 &&
                    param1Spanned.length() - param1Int3 < 3) {
                return "";
            }
            return null;
        }
    }

}
