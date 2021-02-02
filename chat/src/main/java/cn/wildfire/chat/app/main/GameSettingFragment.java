package cn.wildfire.chat.app.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.wildfire.chat.app.login.GameLoginActivity;
import cn.wildfire.chat.kit.conversation.ConversationActivity;
import cn.wildfirechat.model.Conversation;

import com.imchat.ezn.R;

import java.util.Locale;

public class GameSettingFragment extends Fragment {
    @BindView(R.id.setting_silent)
    Switch silentSwitch;
    @BindView(R.id.setting_lang)
    TextView langTextView;

    private int width;

    SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_setting, container, false);
        ButterKnife.bind(this, view);
        init();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        sp = getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        return view;
    }

    void init() {

    }

    @OnClick(R.id.logout_button)
    void logout() {
        SharedPreferences sp = getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().clear().apply();

        Intent intent = new Intent(getContext(), SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.setting_lang)
    void changeLang() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.setting_action_sheet);
        TextView cn = (TextView) dialog.findViewById(R.id.lang_cn);
        cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                changeLocal(Locale.SIMPLIFIED_CHINESE, "CN");
            }
        });

        TextView en = (TextView) dialog.findViewById(R.id.lang_en);
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                changeLocal(Locale.US, "EN");
            }
        });

        TextView vn = (TextView) dialog.findViewById(R.id.lang_vn);
        vn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                changeLocal(new Locale("ms"), "VN");
            }
        });

        TextView cancel = (TextView) dialog.findViewById(R.id.lang_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().width = width;
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
    }

    void changeLocal(Locale locale, String lang) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        getResources().updateConfiguration(configuration, metrics);
        SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit()
            .putString("lang", lang)
            .commit();
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnCheckedChanged(R.id.setting_silent)
    void silentChecked() {
        if (silentSwitch.isChecked()) {
            sp.edit().putString("mute", "yes").commit();
            Toast.makeText(getContext(), "静音", Toast.LENGTH_LONG).show();
        } else {
            sp.edit().putString("mute", "no").commit();
            Toast.makeText(getContext(), "开启声音", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 修改密码
     */
    @OnClick(R.id.setting_password)
    void editPsw() {
        Intent intent = new Intent(getContext(), ChangePswActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.setting_feedback)
    void feedback() {
        Conversation conversation = new Conversation(Conversation.ConversationType.Single, "NjQPNP55", 0);
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("conversation", conversation);
        startActivity(intent);
    }

    /**
     * 积分记录
     */
    @OnClick(R.id.setting_jifen)
    void jifen() {
        Intent intent = new Intent(getActivity(), JifenHistoryActivity.class);
        startActivity(intent);
    }

    /**
     * 绑定银行卡
     */
    @OnClick(R.id.setting_bank)
    void bank() {
        Intent intent = new Intent(getActivity(), BindBankActivity.class);
        startActivity(intent);
    }

}
