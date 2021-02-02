package cn.wildfire.chat.app.main;

import android.content.Context;
import android.content.Intent;
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

public class ChangePswActivity extends WfcBaseActivity {

    @BindView(R.id.old_password)
    EditText oldPasswordEditText;
    @BindView(R.id.new_password)
    EditText newPasswordEditText;
    @BindView(R.id.new_password_confirm)
    EditText newPasswordConfirmEditText;
    @BindView(R.id.submit_button)
    Button submitButton;


    @Override
    protected int contentLayout() {
        return R.layout.change_password;
    }

    @OnClick(R.id.submit_button)
    void submit() {
        submitButton.setEnabled(false);
        String op = oldPasswordEditText.getText().toString().trim();
        String np = newPasswordEditText.getText().toString().trim();
        String npc = newPasswordConfirmEditText.getText().toString().trim();
        if (
            op.length() < 4 ||
            np.length() < 4 ||
            npc.length() < 4 ||
            !np.equals(npc)
        ) {
            Toast.makeText(getApplicationContext(), "数据错误", Toast.LENGTH_LONG).show();
            submitButton.setEnabled(true);
            return;
        }
        SharedPreferences sp = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", "");
        GameService.getInstance().changePsw(userId, op, np, new SimpleCallback<Void>() {
            @Override
            public void onUiSuccess(Void aVoid) {
                submitButton.setEnabled(true);
                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit().clear().apply();

                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                submitButton.setEnabled(true);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }


}
