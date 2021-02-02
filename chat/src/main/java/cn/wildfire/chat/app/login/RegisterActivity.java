package cn.wildfire.chat.app.login;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.imchat.ezn.R;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.kit.WfcBaseNoToolbarActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;
import com.imchat.ezn.R;

public class RegisterActivity extends WfcBaseNoToolbarActivity {
    @BindView(R.id.phone)
    EditText phoneEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.repeat_password)
    EditText repeatPasswordEditText;
    @BindView(R.id.invite_code)
    EditText inviteCodeEditText;
    @BindView(R.id.registerButton)
    ImageView registerButton;
    @BindView(R.id.register_back)
    TextView backTextView;

    boolean clicked;

    @Override
    protected int contentLayout() {
        clicked = false;
        return R.layout.activity_register;
    }

    @OnClick(R.id.registerButton)
    void register() {
        if (clicked) {
            return;
        }
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repeatPassword = repeatPasswordEditText.getText().toString().trim();
        String inviteCode = inviteCodeEditText.getText().toString().trim();
        clicked = true;
        if (phone.length() < 6) {
            toast(getResources().getString(R.string.register_phone_error));
            clicked = false;
            return;
        }
        if (password.length() < 4) {
            toast(getResources().getString(R.string.register_password_error));
            clicked = false;
            return;
        }
        if (!password.equals(repeatPassword)) {
            toast(getResources().getString(R.string.register_repeat_password_error));
            clicked = false;
            return;
        }
        if (inviteCode.length() < 2) {
            toast("邀请码必填");
            clicked = false;
            return;
        }
        GameService.getInstance().register(phone, password, inviteCode, new SimpleCallback<Void>() {
            @Override
            public void onUiSuccess(Void aVoid) {
                toast(getResources().getString(R.string.register_success));
                clicked = false;
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                toast(msg);
                clicked = false;
            }
        });
    }

    @OnClick(R.id.register_back)
    void back() {
        finish();
    }

    void toast(String txt) {
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();
    }


}
