package cn.wildfire.chat.app.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imchat.ezn.R;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;

public class WithdrawActivity extends WfcBaseActivity {

    @BindView(R.id.amount)
    EditText amountEditText;

    @BindView(R.id.c_50)
    Button button_c_50;
    @BindView(R.id.c_100)
    Button button_c_100;
    @BindView(R.id.c_200)
    Button button_c_200;
    @BindView(R.id.c_500)
    Button button_c_500;
    @BindView(R.id.c_1000)
    Button button_c_1000;
    @BindView(R.id.c_2000)
    Button button_c_2000;

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
        GameService.getInstance().withdraw(userId, amount, new SimpleCallback<Void>() {
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

    @OnClick(R.id.c_50)
    void c_50() {
        amountEditText.setText("50");
    }

    @OnClick(R.id.c_100)
    void c_100() {
        amountEditText.setText("100");
    }

    @OnClick(R.id.c_200)
    void c_200() {
        amountEditText.setText("200");
    }

    @OnClick(R.id.c_500)
    void c_500() {
        amountEditText.setText("500");
    }

    @OnClick(R.id.c_1000)
    void c_1000() {
        amountEditText.setText("1000");
    }

    @OnClick(R.id.c_2000)
    void c_2000() {
        amountEditText.setText("2000");
    }

}
