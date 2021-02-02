package cn.wildfire.chat.app.redpacket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.wildfire.chat.app.Config;
import cn.wildfire.chat.app.redpacket.model.SendBonusResult;
import cn.wildfire.chat.app.redpacket.passwordview.PasswordEditText;
import cn.wildfire.chat.app.redpacket.passwordview.PayPasswordView;
import cn.wildfire.chat.kit.net.OKHttpHelper;
import cn.wildfire.chat.kit.net.SimpleCallback;
import com.imchat.ezn.R;

public class OpenSingleActivity extends BaseSendActivity implements PasswordEditText.PasswordFullListener {
    private TextView pop_message;
    private LinearLayout ll_amount_layout;
    private TextView tv_amount;
    private Button btn_putin;
    private ImageView iv_back;
    private BottomSheetDialog bottomSheetDialog;
    private PayPasswordView payPasswordView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rp_send_single;
    }

    public void initView() {
        this.pop_message = (TextView)findViewById(R.id.pop_message);
        this.pop_message.getBackground().mutate().setAlpha(80);
        this.pop_message.setVisibility(4);
        this.ll_amount_layout = (LinearLayout)findViewById(R.id.ll_amount_layout);
        this.et_amount = (EditText)findViewById(R.id.et_amount);
        this.et_peak_message = (EditText)findViewById(R.id.et_peak_message);
        this.tv_amount = (TextView)findViewById(R.id.tv_amount);
        this.tv_amount.setText("0.00");
        this.btn_putin = (Button)findViewById(R.id.btn_putin);
        this.iv_back = (ImageView)findViewById(R.id.iv_back);
        f.a(this.btn_putin, false);

    }

    public void initListener() {
        this.iv_back.setOnClickListener(this);
        this.et_amount.addTextChangedListener(new MyTextWatcher() {
            public void afterTextChanged(Editable param1Editable) {
                super.afterTextChanged(param1Editable);
                OpenSingleActivity.this.checkAmount();
            }
        });
        this.et_amount.setFilters(new InputFilter[] { new InputMoney() });
        this.btn_putin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                if (!m.a()) {
                    OpenSingleActivity.this.valiPwd();
                }
            }
        });
    }

    public void onClick(int paramInt) {
        if (paramInt == R.id.iv_back) {
            i.b(this);
            finish();
        }
    }

    protected void onStart() {
        super.onStart();
        this.et_peak_message.addTextChangedListener(new MyTextWatcher() {
            public void afterTextChanged(Editable param1Editable) {
                super.afterTextChanged(param1Editable);
                if (TextUtils.isEmpty(param1Editable.toString())) {
                    return;
                }
                if (param1Editable.toString().length() >= 25) {
                    ToastUtil.showToast(OpenSingleActivity.this, OpenSingleActivity.this.getString(R.string.red_envelope_blessing));
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        if (bottomSheetDialog != null){
            bottomSheetDialog.dismiss();
        }
        super.onDestroy();
    }

    private void checkAmount() {
        String str = this.et_amount.getText().toString();
        if (!r.a(str)) {
            if (!str.startsWith(".")) {
                BigDecimal bigDecimal = new BigDecimal(str);
                double d = bigDecimal.doubleValue();
                if (d == 0.0D) {
                    closeTips();
                    this.ll_amount_layout.setBackgroundResource(R.drawable._bg_white_round);
                } else if (d < 0.009999999776482582D) {
                    showTips(getString(R.string.jrmf_rp_min_amount));
                    this.ll_amount_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
                    f.a(this.btn_putin, false);
                } else if (d > r.i(this.maxLimitMoney)) {
                    showTips(String.format(getString(R.string.jrmf_rp_single_max_amount), new Object[] { this.maxLimitMoney }));
                    this.ll_amount_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
                    f.a(this.btn_putin, false);
                } else {
                    closeTips();
                    this.ll_amount_layout.setBackgroundResource(R.drawable._bg_white_round);
                    f.a(this.btn_putin, true);
                }
                if (d > 0.0D) {
                    bigDecimal = bigDecimal.setScale(2, 4);
                    this.tv_amount.setText("" + bigDecimal);
                } else {
                    this.tv_amount.setText("0.00");
                }
            } else {
                showTips(getString(R.string.jrmf_rp_amount_error));
                this.ll_amount_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
                f.a(this.btn_putin, false);
            }
        } else {
            closeTips();
            f.a(this.btn_putin, false);
            this.ll_amount_layout.setBackgroundResource(R.drawable._bg_white_round);
            this.tv_amount.setText("0.00");
        }
    }

    private void showTips(String paramString) {
        this.pop_message.setText(paramString);
        this.pop_message.setVisibility(0);
        (new Handler()).postDelayed(new Runnable() {
            public void run() {}
        },  2000L);
    }

    private void closeTips() {
        this.pop_message.setText("");
        this.pop_message.setVisibility(4);
    }

    public void onBackPressed() {
        super.onBackPressed();
        i.b(this);
    }

    // 向服务端请求发送红包
    private void valiPwd() {
//        payPasswordView = new PayPasswordView(this);
//        bottomSheetDialog = new BottomSheetDialog(this);
//        bottomSheetDialog.setContentView(payPasswordView);
//        bottomSheetDialog.show();
//        PasswordEditText mPasswordEditText = payPasswordView.getmPasswordEditText();
//        mPasswordEditText.setPasswordFullListener(this);
    }

    @Override
    public void passwordFull(String password) {
        // ToastUtil.showToast(SendSingleActivity.this, password);
        // 金额
        int money = (int)(Double.valueOf(this.et_amount.getText().toString()) * 100);
        // 文字
        String name = this.et_peak_message.getText().toString();
        if (TextUtils.isEmpty(name)) {
            name = getResources().getString(R.string._bribery_message);
        }
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        Map<String, String> params = new HashMap<>();
        params.put("userId", sp.getString("id", ""));
        params.put("payNumber", password);
        params.put("money", String.valueOf(money));
        params.put("number", "1");
        params.put("name", name);
        params.put("type", "1");
        params.put("targetType", "0");
        params.put("targetId", this.target);
        // 请求服务端发送红包
        OKHttpHelper.post(Config.APIS().get("SendBonus"), params, new SimpleCallback<SendBonusResult>() {
            @Override
            public void onUiSuccess(SendBonusResult result) {
                bottomSheetDialog.hide();
                // 返回发送IM消息
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("money", result.getMoney());
                bundle.putString("note", result.getName());
                bundle.putString("redPId", result.getId());
                bundle.putInt("type", 1);
                bundle.putInt("status", 0);
                bundle.putString("extra", "");
                bundle.putString("targetId", OpenSingleActivity.this.target);
                bundle.putString("senderId", sp.getString("id", ""));
                bundle.putString("senderName", sp.getString("displayName", ""));
                intent.putExtra("extra", bundle);
                setResult(RESULT_OK, intent);
                finish();
        }

            @Override
            public void onUiFailure(int code, String msg) {
                bottomSheetDialog.hide();
                ToastUtil.showToast( OpenSingleActivity.this.context, "发送红包失败:" + msg);
            }
        });
    }
}
