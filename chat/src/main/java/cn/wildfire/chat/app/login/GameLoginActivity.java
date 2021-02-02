package cn.wildfire.chat.app.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.app.AppService;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.app.login.model.LoginResult;
import cn.wildfire.chat.app.main.MainActivity;
import cn.wildfire.chat.app.model.GameUserInfo;
import cn.wildfire.chat.kit.ChatManagerHolder;
import cn.wildfire.chat.kit.WfcBaseNoToolbarActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;
import com.imchat.ezn.R;

public class GameLoginActivity extends WfcBaseNoToolbarActivity {
    @BindView(R.id.phone)
    EditText phoneEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    ImageView loginButton;
    @BindView(R.id.register)
    ImageView registerTextView;
    @BindView(R.id.lang_cn)
    TextView langCnTextView;
    @BindView(R.id.lang_en)
    TextView langEnTextView;
    @BindView(R.id.lang_vn)
    TextView langVnTextView;

    boolean clicked;

    @Override
    protected int contentLayout() {
        clicked = false;
        return R.layout.login_activity_game;
    }

    @OnClick(R.id.loginButton)
    void login() {
        if (clicked) {
            return;
        }
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (phone.length() < 6 || password.length() < 2) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_tips), Toast.LENGTH_LONG).show();
            return;
        }
        // loginButton.setEnabled(false);
        clicked = true;
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .content(getResources().getString(R.string.login_process))
                .progress(true, 100)
                .cancelable(false)
                .build();
        dialog.show();
        AppService.Instance().namePwdLogin(phone, password, new AppService.LoginCallback() {
            @Override
            public void onUiSuccess(LoginResult loginResult) {
                if (isFinishing()) {
                    return;
                }
                ChatManagerHolder.gChatManager.connect(loginResult.getUserId(), loginResult.getImToken());
                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit()
                        .putString("id", loginResult.getUserId())
                        .putString("token", loginResult.getImToken())
                        .putString("userId", loginResult.getUserId())
                        .putString("name", loginResult.getName())
                        .putString("displayName", loginResult.getDisplayName())
                        .putString("portrait", loginResult.getPortrait())
                        .putString("email", loginResult.getEmail())
                        .putString("address", loginResult.getAddress())
                        .putString("company", loginResult.getCompany())
                        .putString("clientId", loginResult.getClientId())
                        .putString("imToken", loginResult.getImToken())
                        .putString("extra", loginResult.getExtra())
                        .putString("createdAt", loginResult.getCreatedAt())
                        .commit();
                // 从游戏服务器拉取用户信息
                GameService.getInstance().getUserInfo(loginResult.getUserId(), new SimpleCallback<GameUserInfo>() {
                    @Override
                    public void onUiSuccess(GameUserInfo gameUserInfo) {
                        sp.edit()
                                .putString("shareCode", gameUserInfo.getShareCode())
                                .putString("created_time", gameUserInfo.getCreated_time())
                                .putString("agent", gameUserInfo.getAgent())
                                .putString("jifen", gameUserInfo.getJifen())
                                .putString("regCode", gameUserInfo.getRegCode())
                                .putString("game_id", gameUserInfo.getId())
                                .putString("bank_no", gameUserInfo.getBank())
                                .putString("bank_name", gameUserInfo.getBankName())
                                .putString("real_name", gameUserInfo.getRealName())
                                .commit();
                        Intent intent = new Intent(GameLoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onUiFailure(int code, String msg) {
                        dialog.dismiss();
                        Toast.makeText(GameLoginActivity.this, msg, Toast.LENGTH_LONG).show();
                        //loginButton.setEnabled(true);
                        clicked = false;
                    }
                });


            }

            @Override
            public void onUiFailure(int code, String msg) {
                if (isFinishing()) {
                    return;
                }
                dialog.dismiss();
                Toast.makeText(GameLoginActivity.this, msg, Toast.LENGTH_LONG).show();
                //loginButton.setEnabled(true);
                clicked = false;
            }
        });
    }

    @OnClick(R.id.register)
    void register() {
        Intent intent = new Intent(GameLoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.lang_cn)
    void changeLangCn() {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        getResources().updateConfiguration(configuration, metrics);
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit()
                .putString("lang", "CN")
                .commit();
        Intent intent = new Intent(this, GameLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.lang_en)
    void changeLangEn() {
        Locale locale = Locale.US;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        getResources().updateConfiguration(configuration, metrics);
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit()
                .putString("lang", "EN")
                .commit();
        Intent intent = new Intent(this, GameLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.lang_vn)
    void changeLangVn() {
        Locale locale = new Locale("ms");

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        getResources().updateConfiguration(configuration, metrics);
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit()
                .putString("lang", "VN")
                .commit();
        Intent intent = new Intent(this, GameLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
