package cn.wildfire.chat.app.redpacket;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wildfire.chat.app.Config;
import cn.wildfire.chat.app.redpacket.model.BonusRecord;
import cn.wildfire.chat.app.redpacket.model.RedPacketDetail;
import cn.wildfire.chat.app.redpacket.model.SendBonusResult;
import cn.wildfire.chat.app.redpacket.viewmodel.RedPacketDetailViewModel;
import cn.wildfire.chat.kit.GlideApp;
import cn.wildfire.chat.kit.net.OKHttpHelper;
import cn.wildfire.chat.kit.net.SimpleCallback;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfire.chat.kit.widget.ProgressFragment;
import com.imchat.ezn.R;
import cn.wildfirechat.model.UserInfo;

public class RedPacketDetailFragment extends ProgressFragment {
    @BindView(R.id.rv_red_packet_detail)
    RecyclerView rv;
    @BindView(R.id.tv_money_status)
    TextView tvMoneyStatus;
    @BindView(R.id.tv_money_amount)
    TextView tvMoney;
    @BindView(R.id.tv_money_use)
    TextView tvMoneyUse;
    @BindView(R.id.tv_greeting)
    TextView tvGreeting;
    @BindView(R.id.tv_money_sender)
    TextView tvSender;
    @BindView(R.id.iv_rp_red_packet_detail_avatar)
    ImageView ivSenderAvatar;
    @BindView(R.id.iv_rp_red_packet_detail_lucky)
    ImageView ivRedPacketLucky;
    @BindView(R.id.status_layout)
    View statusLayout;

    private BonusRecordAdapter adapter;

    private RedPacketDetailViewModel redPacketDetailViewModel;
    private UserViewModel userViewModel;
    private SendBonusResult sendBonusResult;
    private String redPacketId;
    private boolean started = false;

    public static RedPacketDetailFragment newInstance(String redPacketId) {
        RedPacketDetailFragment fragment = new RedPacketDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("redPacketId", redPacketId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int contentLayout() {
        return R.layout.rp_fragment_red_packet_detail;
    }

    @Override
    protected void afterViews(View view) {
        ButterKnife.bind(this, view);
        redPacketId = getArguments().getString("redPacketId");

        redPacketDetailViewModel = ViewModelProviders.of(this).get(RedPacketDetailViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        adapter = new BonusRecordAdapter(this);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        LiveData<List<RedPacketDetail>> redPackets = redPacketDetailViewModel.getRedPackets(redPacketId);
        redPackets.observe(this, redPacketDetails -> getNewDetail());
        getNewDetail();

        showContent();
    }

    private void initHeader() {
        String selfUserId = userViewModel.getUserId();
        //普通红包
        if (sendBonusResult.getType() == 1) {
            if (selfUserId.equals(sendBonusResult.getUserId())) {
                //自己发的，只能看收的情况
                tvMoney.setVisibility(View.GONE);
                tvMoneyUse.setVisibility(View.GONE);
            }
//            else {
//                //别人发的，只能看收的钱，不能看每个人的领取情况
//                rv.setVisibility(View.GONE);
//                statusLayout.setVisibility(View.GONE);
//            }
        }
        // 拼手气红包，全部都显示
        // tvMoney.setText(RedPacketUtils.formatPercent(sendBonusResult.getMoney()) + "元");
        int showMoney = 0;
        List<BonusRecord> records = sendBonusResult.getRecords();
        if (records != null) {
            for (BonusRecord item : records) {
                if (item.userId.equals(selfUserId)) {
                    tvMoney.setText(RedPacketUtils.formatPercent(item.money) + "元");
                    showMoney = 1;
                }
            }
        }
        // 没抢到红包
        if (showMoney == 0) {
            tvMoney.setVisibility(View.GONE);
            tvMoneyUse.setVisibility(View.GONE);
        }
        tvGreeting.setText(sendBonusResult.getName());
        UserInfo userInfo = userViewModel.getUserInfo(sendBonusResult.getUserId(), false);
        tvSender.setText(userInfo.displayName + "的红包");
        GlideApp.with(this).load(userInfo.portrait).error(R.mipmap.default_header)
                .into(ivSenderAvatar);
        ivRedPacketLucky.setVisibility(sendBonusResult.getType() == 0 ? View.VISIBLE : View.GONE);
    }

    private void getNewDetail() {
        if (started) return;
        started = true;
        HashMap<String, String> param = new HashMap<>();
        param.put("redPId", redPacketId);
        OKHttpHelper.get(Config.APIS().get("GetRedPacket"), param, new SimpleCallback<SendBonusResult>() {
            @Override
            public void onUiSuccess(SendBonusResult sendBonusResult) {
                RedPacketDetailFragment.this.sendBonusResult = sendBonusResult;
                initHeader();
                setBonusRecords(sendBonusResult.getRecords());
                started = false;
            }

            @Override
            public void onUiFailure(int code, String msg) {
                started = false;
                //finish
            }
        });
    }

    private void setBonusRecords(List<BonusRecord> records) {
        int sum = records == null ? 0 : records.size();
        int total = sendBonusResult.getNumbers() == 0 ? 1 : sendBonusResult.getNumbers();
        tvMoneyStatus.setText(total + "个红包共" + RedPacketUtils.formatPercent(sendBonusResult.getMoney(), false) + "元" + (sum == total ? "已被人领光" : "已有" + sum + "人领取"));
        adapter.setRecords(records);
    }
}
