package cn.wildfire.chat.app.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.imchat.ezn.R;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;

public class TransActivity extends WfcBaseActivity {

    @BindView(R.id.amount)
    EditText amountEditText;

    private SharedPreferences sp;
    private String userId;

    @Override
    protected int contentLayout() {
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "-");
        return R.layout.activity_withdraw;
    }

    @OnClick(R.id.submit)
    void submit() {
        String amount = amountEditText.getText().toString().trim();
        if (amount.length() < 1 || Integer.valueOf(amount) <= 0) {
            Toast.makeText(getApplicationContext(), "金额错误", Toast.LENGTH_SHORT).show();
            return;
        }
        GameService.getInstance().trans(userId, amount, new SimpleCallback<Void>() {
            @Override
            public void onUiSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
