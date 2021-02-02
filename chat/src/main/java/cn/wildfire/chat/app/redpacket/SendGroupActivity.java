package cn.wildfire.chat.app.redpacket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.wildfire.chat.app.Config;
import cn.wildfire.chat.app.redpacket.model.SendBonusResult;
import cn.wildfire.chat.app.redpacket.passwordview.PasswordEditText;
import cn.wildfire.chat.app.redpacket.passwordview.PayPasswordView;
import cn.wildfire.chat.kit.group.GroupViewModel;
import cn.wildfire.chat.kit.net.OKHttpHelper;
import cn.wildfire.chat.kit.net.SimpleCallback;
import cn.wildfire.chat.kit.user.UserViewModel;
import com.imchat.ezn.R;
import cn.wildfirechat.model.GroupInfo;

public class SendGroupActivity extends BaseSendActivity implements PasswordEditText.PasswordFullListener {
    private int ENVELOPES_TYPE = 1;

    private TextView pop_message;
    private LinearLayout ll_peak_num_layout;
    private EditText et_peak_num;
    private TextView tv_group_member_num;
    private LinearLayout ll_peak_amount_layout;
    private TextView tv_peak_amount_icon;
    private TextView tv_peak_type;
    private TextView tv_amount_for_show;
    private Button btn_putin;
    private TextView tv_ge;
    private ImageView iv_back;

    private BottomSheetDialog bottomSheetDialog;
    private PayPasswordView payPasswordView;

    private GroupViewModel groupViewModel;
    private UserViewModel userViewModel;

    private GroupInfo groupInfo;

    private NoUnderClickableSpan peakTypeClick = new NoUnderClickableSpan() {
        public void onClick(View param1View) {
            if (SendGroupActivity.this.ENVELOPES_TYPE == 1) {
                SendGroupActivity.this.ENVELOPES_TYPE = 0;
            } else if (SendGroupActivity.this.ENVELOPES_TYPE == 0) {
                SendGroupActivity.this.ENVELOPES_TYPE = 1;
            }
            SendGroupActivity.this.setPeakTypeStyle();
            SendGroupActivity.this.checkType();
        }
    };
    private int NUMBER = -1;
    private double AMOUNTMONEY = -1.0D;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rp_send_group;
    }

    public void initView() {
        this.pop_message = (TextView) findViewById(R.id.pop_message);
        this.ll_peak_num_layout = (LinearLayout) findViewById(R.id.ll_peak_num_layout);
        this.et_peak_num = (EditText) findViewById(R.id.et_peak_num);
        this.et_peak_num.setFocusable(true);
        this.et_peak_num.setFocusableInTouchMode(true);
        this.et_peak_num.requestFocus();
        this.et_peak_num.setSelection(this.et_peak_num.getText().length());
        this.et_peak_num.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        this.tv_group_member_num = (TextView) findViewById(R.id.tv_group_member_num);
        this.ll_peak_amount_layout = (LinearLayout) findViewById(R.id.ll_peak_amount_layout);
        this.tv_peak_amount_icon = (TextView) findViewById(R.id.tv_peak_amount_icon);
        this.et_amount = (EditText) findViewById(R.id.et_peak_amount);
        this.tv_peak_type = (TextView) findViewById(R.id.tv_peak_type);
        this.tv_ge = (TextView) findViewById(R.id.tv_ge);
        if (j.b())
            this.tv_ge.setVisibility(8);
        this.et_peak_message = (EditText) findViewById(R.id.et_peak_message);
        this.tv_amount_for_show = (TextView) findViewById(R.id.tv_amount_for_show);
        this.btn_putin = (Button) findViewById(R.id.btn_putin);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        i.a(this.et_peak_num);
        setDefaultView();

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public void initListener() {
        this.iv_back.setOnClickListener(this);
        this.et_peak_num.addTextChangedListener(new MyTextWatcher() {
            public void afterTextChanged(Editable param1Editable) {
                SendGroupActivity.this.NUMBER = SendGroupActivity.this.checkNum();
                if (SendGroupActivity.this.NUMBER != -1) {
                    SendGroupActivity.this.AMOUNTMONEY = SendGroupActivity.this.checkAmount();
                }
                SendGroupActivity.this.checkForAllAmount();
                SendGroupActivity.this.checkForButton();
            }
        });
        this.et_amount.addTextChangedListener(new MyTextWatcher() {
            public void afterTextChanged(Editable param1Editable) {
                SendGroupActivity.this.NUMBER = SendGroupActivity.this.checkNum();
                if (SendGroupActivity.this.NUMBER != -1) {
                    SendGroupActivity.this.AMOUNTMONEY = SendGroupActivity.this.checkAmount();
                }
                SendGroupActivity.this.checkForAllAmount();
                SendGroupActivity.this.checkForButton();
            }
        });
        this.et_amount.setFilters(new InputFilter[]{new BaseSendActivity.InputMoney()});
        this.btn_putin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                if (!m.a()) {
                    SendGroupActivity.this.sendBonus();
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
        this.summary = getString(R.string._bribery_message);
        this.setGroupMemberNumber();
        this.et_peak_message.addTextChangedListener(new MyTextWatcher() {
            public void afterTextChanged(Editable param1Editable) {
                super.afterTextChanged(param1Editable);
                if (TextUtils.isEmpty(param1Editable.toString())) {
                    return;
                }
                if (param1Editable.toString().length() == 25) {
                    ToastUtil.showToast(SendGroupActivity.this.getApplicationContext(), SendGroupActivity.this.getString(R.string.red_envelope_blessing));
                }
            }
        });
    }

    private void setGroupMemberNumber() {
        this.groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);
        this.groupInfo = groupViewModel.getGroupInfo(this.target, false);
        this.tv_group_member_num.setVisibility(0);
        String str1 = this.getResources().getString(R.string.group_number);
        String str2 = String.format(str1, new Object[]{Integer.valueOf(groupInfo.memberCount)});
        this.tv_group_member_num.setText(str2);
    }

    private void setDefaultView() {
        this.pop_message.setVisibility(4);
        this.pop_message.getBackground().mutate().setAlpha(80);
        setPeakTypeStyle();
        f.a(this.btn_putin, false);
    }

    private void setPeakTypeStyle() {
        String str = "";
        if (this.ENVELOPES_TYPE == 1) {
            f.a(getResources().getDrawable(R.drawable._ic_pin), this.tv_peak_amount_icon);
            this.tv_peak_amount_icon.setText(getString(R.string.jrmf_rp_all_amount));
            str = getString(R.string.jrmf_rp_luck_to_normal);
        } else if (this.ENVELOPES_TYPE == 0) {
            f.a(null, this.tv_peak_amount_icon);
            this.tv_peak_amount_icon.setText(getString(R.string.jrmf_rp_single_amount));
            str = getString(R.string.jrmf_rp_normal_to_luck);
        }
        int i = str.indexOf("，") + 1;
        if (i < 1) {
            return;
        }
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(this.peakTypeClick, i, str.length(), 33);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.rp_yellow)), i, str.length(), 33);
        this.tv_peak_type.setText(spannableString);
        this.tv_peak_type.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showTips(String paramString) {
        this.pop_message.setText(paramString);
        this.pop_message.setVisibility(0);
    }

    private void closeTips() {
        this.pop_message.setText("");
        this.pop_message.setVisibility(4);
    }

    private void checkForAllAmount() {
        int i = -1;
        String str1 = this.et_peak_num.getText().toString();
        if (!r.a(str1) &&
                isNumber(str1)) {
            BigDecimal bigDecimal = new BigDecimal(str1);
            i = bigDecimal.intValue();
        }
        float f = 0.0F;
        String str2 = this.et_amount.getText().toString();
        if (!r.a(str2) &&
                !str2.startsWith(".")) {
            BigDecimal bigDecimal = new BigDecimal(str2);
            f = bigDecimal.floatValue();
        }
        if (i > 0 && f > 0.0F) {
            if (this.ENVELOPES_TYPE == 1) {
                BigDecimal bigDecimal = new BigDecimal(f);
                bigDecimal = bigDecimal.setScale(2, 5);
                this.tv_amount_for_show.setText(bigDecimal + "");
            } else if (this.ENVELOPES_TYPE == 0) {
                BigDecimal bigDecimal1 = new BigDecimal(f);
                BigDecimal bigDecimal2 = bigDecimal1.multiply(new BigDecimal(i));
                bigDecimal2 = bigDecimal2.setScale(2, 5);
                this.tv_amount_for_show.setText(bigDecimal2 + "");
            }
        } else {
            this.tv_amount_for_show.setText("0.00");
        }
    }

    private void checkForButton() {
        f.a(this.btn_putin, false);
        if (this.NUMBER > 0 && this.AMOUNTMONEY > 0.0D)
            f.a(this.btn_putin, true);
    }

    private void checkType() {
        if (this.ENVELOPES_TYPE == 1) {
            String str = this.tv_amount_for_show.getText().toString();
            this.et_amount.setText(str);
        } else if (this.ENVELOPES_TYPE == 0) {
            String str1 = this.et_amount.getText().toString();
            String str2 = this.et_peak_num.getText().toString();
            if (str1 != null && !str1.isEmpty() && str2 != null && !str2.isEmpty()) {
                BigDecimal bigDecimal1 = new BigDecimal(str1);
                BigDecimal bigDecimal2 = new BigDecimal(str2);
                if (bigDecimal2.floatValue() > 0.0F) {
                    BigDecimal bigDecimal = bigDecimal1.divide(bigDecimal2, 3, 5);
                    this.et_amount.setText(r.a(bigDecimal.doubleValue()));
                }
            }
        }
    }

    private int checkNum() {
        String str = this.et_peak_num.getText().toString();
        if (!r.a(str)) {
            if (isNumber(str)) {
                BigDecimal bigDecimal = new BigDecimal(str);
                int i = bigDecimal.intValue();
                if (i == 0) {
                    showTips(getString(R.string.jrmf_rp_min_num));
                    this.ll_peak_num_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
                    return -1;
                }
                if (i > this.maxCount) {
                    showTips(String.format(getString(R.string.jrmf_rp_max_num), new Object[]{Integer.valueOf(this.maxCount)}));
                    this.ll_peak_num_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
                    return -1;
                }
                this.ll_peak_num_layout.setBackgroundResource(R.drawable._bg_white_round);
                closeTips();
                return i;
            }
            showTips(getString(R.string.jrmf_rp_num_error));
            this.ll_peak_num_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
            return -1;
        }
        closeTips();
        this.ll_peak_num_layout.setBackgroundResource(R.drawable._bg_white_round);
        return -1;
    }

    private double checkAmount() {
        String str = this.et_amount.getText().toString();
        if (!r.a(str)) {
            if (!str.startsWith(".")) {
                BigDecimal bigDecimal = new BigDecimal(str);
                double d = bigDecimal.doubleValue();
                if (d == 0.0D) {
                    this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round);
                    closeTips();
                    return -1.0D;
                }
                if (d < 0.009999999776482582D) {
                    showTips(getString(R.string.jrmf_rp_min_amount));
                    this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
                    return -1.0D;
                }
                if (this.ENVELOPES_TYPE == 1) {
                    if (d > r.j(this.maxLimitMoney).doubleValue()) {
                        showTips(String.format(getString(R.string.jrmf_rp_max_amount), new Object[]{this.maxLimitMoney}));
                        this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
                        return -1.0D;
                    }
                    this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round);
                    closeTips();
                    return d;
                }
                if (this.ENVELOPES_TYPE == 0) {
                    String str1 = this.et_peak_num.getText().toString();
                    if (str1 != null && !str1.isEmpty()) {
                        double d1 = r.i(str1);
                        if (d1 != 0.0D && d1 * d > r.i(this.maxLimitMoney)) {
                            showTips(String.format(getString(R.string.jrmf_rp_max_amount), new Object[]{this.maxLimitMoney}));
                            this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
                            return -1.0D;
                        }
                        this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round);
                        closeTips();
                        return d;
                    }
                    if (d > r.i(this.maxLimitMoney)) {
                        showTips(String.format(getString(R.string.jrmf_rp_max_amount), new Object[]{this.maxLimitMoney}));
                        this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
                        return -1.0D;
                    }
                    closeTips();
                    this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round);
                    return d;
                }
                return -1.0D;
            }
            showTips(getString(R.string.jrmf_rp_amount_error));
            this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round_stroke);
            return -1.0D;
        }
        closeTips();
        this.ll_peak_amount_layout.setBackgroundResource(R.drawable._bg_white_round);
        return -1.0D;
    }

    public void onBackPressed() {
        super.onBackPressed();
        i.b(this);
    }

    // 向服务端请求发送红包
    private void sendBonus() {
        payPasswordView = new PayPasswordView(this);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(payPasswordView);
        bottomSheetDialog.show();
        PasswordEditText mPasswordEditText = payPasswordView.getmPasswordEditText();
        mPasswordEditText.setPasswordFullListener(this);
    }

    @Override
    public void passwordFull(String password) {
        // 金额
        int money = (int) (Double.valueOf(this.et_amount.getText().toString()) * 100);
        // 文字
        String name = this.et_peak_message.getText().toString();
        if (TextUtils.isEmpty(name)) {
            name = getResources().getString(R.string._bribery_message);
        }
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        Map<String, String> params = new HashMap<>();
        params.put("userId", userViewModel.getUserId());
        params.put("payNumber", password);
        params.put("money", String.valueOf(money));
        params.put("number", String.valueOf(et_peak_num.getText()));
        params.put("name", name);
        params.put("type", String.valueOf(ENVELOPES_TYPE == 1 ? 0 : 1));
        params.put("targetType", "1");
        params.put("targetId", "*");
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
                bundle.putString("targetId", target);
                bundle.putString("senderId", sp.getString("id", ""));
                bundle.putString("senderName", sp.getString("displayName", ""));
                intent.putExtra("extra", bundle);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                bottomSheetDialog.hide();
                ToastUtil.showToast(context, "发送红包失败:" + msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
        super.onDestroy();
    }
}