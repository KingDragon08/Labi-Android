package cn.wildfire.chat.app.setting;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.app.redpacket.RedPacketUtils;
import cn.wildfire.chat.app.redpacket.i;
import cn.wildfire.chat.app.redpacket.passwordview.PasswordEditText;
import cn.wildfire.chat.app.redpacket.passwordview.PayPasswordView;
import cn.wildfire.chat.app.setting.model.Wallet;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfire.chat.kit.widget.OptionItemView;
import com.imchat.ezn.R;

public class WalletActivity extends WfcBaseActivity implements PasswordEditText.PasswordFullListener {
    @BindView(R.id.oiv_wallet_modify_password)
    OptionItemView oivPassword;
    @BindView(R.id.oiv_wallet_rest)
    OptionItemView oivRest;
    @BindView(R.id.oiv_wallet_recharge)
    OptionItemView oivRecharge;
    UserViewModel userViewModel;
    WalletViewModel walletViewModel;
    private BottomSheetDialog bottomSheetDialog;
    private Wallet wallet;

    @Override
    protected int contentLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void afterViews() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);

        MutableLiveData<Wallet> walletLiveData = walletViewModel.getWallet(userViewModel.getUserId());
        walletLiveData.observe(this, this::setWallet);
    }

    /**
     * 设置钱包显示
     *
     * @param wallet 钱包信息
     */
    private void setWallet(Wallet wallet) {
        this.wallet = wallet;
        if (wallet == null) {
            oivPassword.setTitle("创建钱包");
            oivRest.setVisibility(View.GONE);
            oivRecharge.setVisibility(View.GONE);
        } else {
            oivRest.setVisibility(View.VISIBLE);
            oivRest.setDesc(RedPacketUtils.formatPercent(wallet.money) + "元");
            oivRecharge.setVisibility(View.VISIBLE);
            oivPassword.setTitle("修改密码");
        }
    }

    @OnClick(R.id.oiv_wallet_modify_password)
    void createNewWallet() {
        if (wallet == null) {
            checkPassword();
        }
    }


    //验证支付密码
    private void checkPassword() {
        PayPasswordView payPasswordView = new PayPasswordView(this);
        if (bottomSheetDialog == null) {
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(payPasswordView);
            PasswordEditText mPasswordEditText = payPasswordView.getmPasswordEditText();
            mPasswordEditText.setPasswordFullListener(this);
        }
        bottomSheetDialog.show();
    }

    @Override
    protected void onDestroy() {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
        super.onDestroy();
    }

    public void onBackPressed() {
        super.onBackPressed();
        i.b(this);
    }

    @Override
    public void passwordFull(String password) {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
        LiveData<Wallet> liveData = walletViewModel.newWallet(userViewModel.getUserId(), password);
        liveData.observe(this, this::setWallet);
    }
}
