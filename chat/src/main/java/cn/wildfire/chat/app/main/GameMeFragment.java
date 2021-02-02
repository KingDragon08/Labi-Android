package cn.wildfire.chat.app.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.app.model.GameUserInfo;
import cn.wildfire.chat.app.model.Wallet;
import cn.wildfire.chat.kit.common.OperateResult;
import cn.wildfire.chat.kit.net.OKHttpHelper;
import cn.wildfire.chat.kit.net.SimpleCallback;
import cn.wildfire.chat.kit.third.utils.ImageUtils;
import cn.wildfire.chat.kit.third.utils.UIUtils;
import cn.wildfire.chat.kit.user.ChangeMyNameActivity;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfirechat.model.UserInfo;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.imchat.ezn.R;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GameMeFragment extends Fragment {
    @BindView(R.id.avatar)
    ImageView avatarImageView;
    @BindView(R.id.name)
    TextView nameTextView;
    @BindView(R.id.edit)
    ImageView editImageView;
    @BindView(R.id.game_jifen)
    TextView jifenTextView;
    @BindView(R.id.game_bonus)
    TextView bonusTextView;
    @BindView(R.id.game_ID)
    TextView idTextView;
    @BindView(R.id.game_agent_ID)
    TextView agentTextView;
    @BindView(R.id.game_phone)
    TextView phoneTextView;
    @BindView(R.id.game_email)
    TextView emailTextView;
    @BindView(R.id.game_share_code)
    TextView shareCodeTextView;
    @BindView(R.id.mobile)
    LinearLayout mobileLinearLayout;
    @BindView(R.id.email)
    LinearLayout emailLinearLayout;

    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    private UserInfo userInfo;
    private UserViewModel userViewModel;
    private Observer<List<UserInfo>> userInfoLiveDataObserver;
    private SharedPreferences sp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_me, container, false);
        ButterKnife.bind(this, view);
        sp = getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        init();
        return view;
    }

    @OnClick(R.id.avatar)
    void changeAvatar() {
        ImagePicker.picker().pick(this, REQUEST_CODE_PICK_IMAGE);
    }

    @OnClick(R.id.name)
    void changeName() {
        Intent intent = new Intent(getActivity(), ChangeMyNameActivity.class);
        intent.putExtra("type", "name");
        startActivity(intent);
    }

    @OnClick(R.id.edit)
    void changeNameFromEdit() {
        Intent intent = new Intent(getActivity(), ChangeMyNameActivity.class);
        intent.putExtra("type", "name");
        startActivity(intent);
    }

    @OnClick(R.id.mobile)
    void changeMobile() {
        Intent intent = new Intent(getActivity(), ChangeMyNameActivity.class);
        intent.putExtra("type", "mobile");
        startActivity(intent);
    }

    @OnClick(R.id.email)
    void changeEmail() {
        Intent intent = new Intent(getActivity(), ChangeMyNameActivity.class);
        intent.putExtra("type", "email");
        startActivity(intent);
    }

    @OnClick(R.id.charge)
    void charge() {
         Intent intent = new Intent(getActivity(), ChargeActivity.class);
         startActivity(intent);
    }

    @OnClick(R.id.excharge)
    void excharge() {
        Intent intent = new Intent(getActivity(), WithdrawActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.trans)
    void trans() {
        Intent intent = new Intent(getActivity(), TransActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.refresh)
    void refresh() {
        this.getJifen(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            String imagePath = ImageUtils.genThumbImgFile(images.get(0).path).getAbsolutePath();
            MutableLiveData<OperateResult<Boolean>> result = userViewModel.updateUserPortrait(imagePath);
            result.observe(this, booleanOperateResult -> {
                if (booleanOperateResult.isSuccess()) {
                    Toast.makeText(getActivity(), "更新头像成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "更新头像失败: " + booleanOperateResult.getErrorCode(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 获取积分数据
     */
    protected void getJifen(boolean force) {
        String userId = sp.getString("userId", "-1");
        if (userId.equals("-1")) {
            return;
        }
        jifenTextView.setText(Float.valueOf(sp.getString("jifen", "0")) / 100 + "");
        GameService.getInstance().getUserInfo(userId, new SimpleCallback<GameUserInfo>() {
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
                jifenTextView.setText(Float.valueOf(gameUserInfo.getJifen()) / 100 + "");
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
        bonusTextView.setText(Float.valueOf(sp.getString("money", "0")) / 100 + "");
        GameService.getInstance().getWalletInfo(userId, new SimpleCallback<Wallet>() {
            @Override
            public void onUiSuccess(Wallet wallet) {
                sp.edit()
                    .putString("money", String.valueOf(wallet.getMoney()))
                    .commit();
                bonusTextView.setText(Float.valueOf(wallet.getMoney()) / 100 + "");
                if (force) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.gameme_refresh_success), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 初始化数据
     */
    public void init() {
        userInfoLiveDataObserver = new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(@Nullable List<UserInfo> userInfos) {
                if (userInfos == null) {
                    return;
                }
                for (UserInfo info : userInfos) {
                    if (info.uid.equals(userViewModel.getUserId())) {
                        userInfo = info;
                        updateUserInfo(userInfo);
                        break;
                    }
                }
            }
        };
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUserInfoAsync(userViewModel.getUserId(), true)
            .observe(getViewLifecycleOwner(), info -> {
                userInfo = info;
                if (userInfo != null) {
                    updateUserInfo(userInfo);
                }
            });
        userViewModel.userInfoLiveData().observeForever(userInfoLiveDataObserver);
        idTextView.setText(sp.getString("name", "-"));
        agentTextView.setText(sp.getString("regCode", "-"));
        shareCodeTextView.setText(sp.getString("shareCode", "-"));

        this.getJifen(false);
    }

    private void updateUserInfo(UserInfo userInfo) {
        RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.avatar_def)
            .transforms(new CenterCrop(), new RoundedCorners(30));
        Glide.with(this)
            .load(userInfo.portrait)
            .apply(options)
            .into(avatarImageView);
        nameTextView.setText(userInfo.displayName);
        phoneTextView.setText(userInfo.mobile);
        emailTextView.setText(userInfo.email);
    }



}
