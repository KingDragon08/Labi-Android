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

public class BindBankActivity extends WfcBaseActivity {
    @BindView(R.id.b_bank_no)
    EditText bankNoEditText;
    @BindView(R.id.b_bank_name)
    EditText bankNameEditText;
    @BindView(R.id.b_bank_user_name)
    EditText bankUsernameEditText;

    private String bank_no;
    private String bank_name;
    private String real_name;
    private SharedPreferences sp;


    @Override
    protected int contentLayout() {
        return R.layout.activity_bind_bank;
    }

    @Override
    protected void afterViews() {
        super.afterViews();
        init();
    }

    /**
     * 初始化
     */
    protected void init() {
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        bank_no = sp.getString("bank_no", null);
        if (bank_no != null) {
            bankNoEditText.setText(bank_no);
        }
        bank_name = sp.getString("bank_name", null);
        if (bank_name != null) {
            bankNameEditText.setText(bank_name);
        }
        real_name = sp.getString("real_name", null);
        if (real_name != null) {
            bankUsernameEditText.setText(real_name);
        }
    }

    @OnClick(R.id.submit)
    void submit() {
        String b_bank_no = bankNoEditText.getText().toString().trim();
        String b_bank_name = bankNameEditText.getText().toString().trim();
        String b_real_name = bankUsernameEditText.getText().toString().trim();
        if (
            !b_bank_no.equals(bank_no) ||
            !b_bank_name.equals( bank_name) ||
            !b_real_name.equals(real_name)
        ) {
            GameService.getInstance().bindBankCard(sp.getString("userId", "-"), b_bank_no, b_bank_name, b_real_name, new SimpleCallback<Void>() {
                @Override
                public void onUiSuccess(Void aVoid) {
                    sp.edit()
                        .putString("bank_no", b_bank_no)
                        .putString("bank_name", b_bank_name)
                        .putString("real_name", b_real_name)
                        .commit();
                    Toast.makeText(getApplicationContext(), "绑定成功", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onUiFailure(int code, String msg) {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
