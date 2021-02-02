package cn.wildfire.chat.app.redpacket;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfire.chat.app.redpacket.db.RedPacketRepository;
import cn.wildfire.chat.app.redpacket.model.RedPacketDetail;
import cn.wildfire.chat.app.redpacket.model.SendBonusResult;
import cn.wildfire.chat.app.redpacket.viewmodel.OpenRedPacketViewModel;
import cn.wildfire.chat.kit.conversation.ConversationViewModel;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfire.chat.kit.viewmodel.MessageViewModel;
import com.imchat.ezn.R;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.MessageContent;
import cn.wildfirechat.message.redpacket.LuckyMoneyGroupMessageContent;
import cn.wildfirechat.message.redpacket.LuckyMoneyGroupReceiveMessageContent;
import cn.wildfirechat.message.redpacket.LuckyMoneyReceiveMessageContent;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.UserInfo;

/**
 * Created by hp on 2019/12/25.
 */
public class OpenRedPacketActivity extends FragmentActivity {
    @BindView(R.id.iv_rp_open_red_packet_open)
    ImageView ivOpen;
    @BindView(R.id.iv_rp_open_red_packet_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_rp_open_red_packet_title)
    TextView tvName;
    @BindView(R.id.tv_rp_open_red_packet_message)
    TextView tvMessage;
    @BindView(R.id.tv_rp_open_red_packet_detail)
    TextView tvDetail;
    @BindView(R.id.tickerView)
    TickerView tickerView;

    YRotateAnimation yRotateAnimation;

    UserViewModel userViewModel;
    MessageViewModel messageViewModel;
    ConversationViewModel conversationViewModel;
    OpenRedPacketViewModel openRedPacketViewModel;

    Message message;
    RedPacketDetail redPacketDetail;
    Conversation conversation;
    boolean groupRedPacket;

    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rp_open_red_packet);
        ButterKnife.bind(this);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        conversationViewModel = ViewModelProviders.of(this).get(ConversationViewModel.class);
        openRedPacketViewModel = ViewModelProviders.of(this).get(OpenRedPacketViewModel.class);

        message = getIntent().getParcelableExtra("data");
        redPacketDetail = new Gson().fromJson(message.content.extra, RedPacketDetail.class);
        redPacketDetail.senderId = message.sender;
        conversation = getIntent().getParcelableExtra("conversation");
        groupRedPacket = message.content instanceof LuckyMoneyGroupMessageContent;

        tickerView.setCharacterList(TickerUtils.getDefaultListForUSCurrency());//可以有小数点，"/"字符
        tickerView.setAnimationInterpolator(new DecelerateInterpolator());
        tickerView.setTypeface(Typeface.MONOSPACE);
        tickerView.setAnimationDuration(800);

        initViewState();

    }

    private void initViewState() {
        //检查本地有没有打开过的记录，有的话就根据群组和单人分开处理，没有的话就直接显示打开就行
        //领取过的红包记录
        RedPacketDetail receivedDetail = RedPacketRepository.getInstance().getRedPacketDetailRaw(String.valueOf(redPacketDetail.redPId), userViewModel.getUserId());
        //不可用红包记录(超时、领光)
        List<RedPacketDetail> unavailableDetail = RedPacketRepository.getInstance().getRedPacketDetailWithinStatusRaw(String.valueOf(redPacketDetail.redPId), 11);

        if (unavailableDetail != null && unavailableDetail.size() > 0) {
            //有不可用记录
            hideOpen(RedPacketUtils.getStatusFromRedPacketDetails(unavailableDetail));
        } else if (receivedDetail != null) {
            //有历史领取记录-显示金额
            if (groupRedPacket) {
                UserInfo userInfo = userViewModel.getUserInfo(message.sender, true);
                Glide.with(this).load(userInfo.portrait).apply(new RequestOptions().placeholder(R.mipmap.avatar_def).centerCrop()).into(ivAvatar);
                tvName.setText(userInfo.displayName + "的红包");
                tvMessage.setTextSize(50);
                tvMessage.setText(RedPacketUtils.formatPercent(receivedDetail.money, false));
                tvDetail.setVisibility(View.VISIBLE);
                tickerView.setVisibility(View.GONE);
                ivOpen.setVisibility(View.GONE);
            } else {
                toDetail();
            }
        } else {
            LiveData<SendBonusResult> liveData = openRedPacketViewModel.getRedPacketDetail(String.valueOf(redPacketDetail.redPId));
            liveData.observe(this, this::setRedPacketDetail);
        }
    }

    private void setRedPacketDetail(SendBonusResult sendBonusResult) {
        UserInfo userInfo = userViewModel.getUserInfo(sendBonusResult.getUserId(), true);
        Glide.with(this).load(userInfo.portrait).apply(new RequestOptions().placeholder(R.mipmap.avatar_def).centerCrop()).into(ivAvatar);
        tvName.setText(userInfo.displayName + "的红包");
        tvMessage.setText(sendBonusResult.getName());
        tvDetail.setVisibility(View.GONE);

//        hideOpen(sendBonusResult.getStatus());
    }

    /**
     * 设置不要显示打开按钮，当打开按钮显示时，则详情按钮不显示
     */
    private void hideOpen(Integer status) {
        if (status != null) {
            if (status != 0) {
                ivOpen.setVisibility(View.GONE);
                tvDetail.setVisibility(View.VISIBLE);
            }
            switch (status) {
                case 1:
                    tvMessage.setText("手慢了，红包派完了");
                    break;
                case 2:
                    tvMessage.setText("手慢了，已过期");
                    break;
                case 3:
                    tvMessage.setText("没有权限");
                    Toast.makeText(getApplicationContext(), "没有权限", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * 关闭界面
     */
    @OnClick(R.id.iv_open_red_packet_close)
    public void exit() {
        finish();
    }

    @OnClick(R.id.tickerView)
    public void gooo() {
        Intent intent = new Intent(this, RedPacketDetailActivity.class);
        intent.putExtra("redPacketId", String.valueOf(redPacketDetail.redPId));
        startActivity(intent);
        finish();
    }

    /**
     * 直接查看红包详情
     */
    @OnClick(R.id.tv_rp_open_red_packet_detail)
    public void toDetail() {
        Intent intent = new Intent(this, RedPacketDetailActivity.class);
        intent.putExtra("redPacketId", String.valueOf(redPacketDetail.redPId));
        startActivity(intent);
        finish();
    }

    /**
     * 打开红包，成功的话更改红包状态
     */
    @OnClick(R.id.iv_rp_open_red_packet_open)
    public void open() {
        if (yRotateAnimation == null) {
            yRotateAnimation = new YRotateAnimation(0, 360, ivOpen.getMeasuredWidth() / 2f, ivOpen.getMeasuredHeight() / 2f, 0, false);
            yRotateAnimation.setRepeatCount(Animation.INFINITE);
            yRotateAnimation.setInterpolator(new LinearInterpolator());
            yRotateAnimation.setDuration(1500);
        }
        if (yRotateAnimation.hasStarted()) {
            return;
        }
        ivOpen.startAnimation(yRotateAnimation);

        final String userId = userViewModel.getUserId();
        handler.postDelayed(() -> openRedPacketViewModel.openRedPacket(userId, String.valueOf(redPacketDetail.redPId)).observe(OpenRedPacketActivity.this,
                data -> {
                    SendBonusResult sendBonusResult = data.data;
                    //stop animation
                    ivOpen.clearAnimation();
                    ivOpen.setVisibility(View.GONE);
                    //发领取成功消息，查一下本地有没有红包领取记录，没有的话就插入一条，有的话根据个人和群组处理
                    if (sendBonusResult != null) {
                        if (sendBonusResult.hasReceived(userViewModel.getUserId())) {
                            tvMessage.setVisibility(View.GONE);
                            tickerView.setText("0.00");
                            Bundle bundle = new Bundle();
                            bundle.putInt("money", sendBonusResult.getMoney());
                            bundle.putString("note", sendBonusResult.getName());
                            bundle.putString("redPId", sendBonusResult.getId());
                            bundle.putInt("type", 1);
                            bundle.putInt("status", 10);
                            bundle.putString("extra", "");
                            bundle.putString("senderId", userViewModel.getUserId());
                            bundle.putString("targetId", sendBonusResult.getUserId());
                            //我的(领取)个人信息
                            UserInfo myUserInfo = userViewModel.getUserInfo(redPacketDetail.senderId, false);
                            bundle.putString("senderName", myUserInfo.displayName);
                            //发送人的个人信息
                            UserInfo senderUserInfo = userViewModel.getUserInfo(redPacketDetail.senderId, false);
                            bundle.putString("targetName", senderUserInfo.displayName);
                            bundle.putLong("messageUid", message.messageUid);
                            //检查本地数据库中的记录，如果有领取记录，则不可再领取
                            MessageContent content;
                            if (conversation.type == Conversation.ConversationType.Single) {
                                content = new LuckyMoneyReceiveMessageContent(bundle);
                            } else {
                                content = new LuckyMoneyGroupReceiveMessageContent(bundle);
                            }
                            messageViewModel.sendMessage(conversation, content);

                            //tvMessage.setText(RedPacketUtils.formatPercent(sendBonusResult.getMoney(), false));
                            tickerView.setText(RedPacketUtils.formatPercent(sendBonusResult.getMoney(), false));
                            // toDetail();
                        } else {
                            hideOpen(sendBonusResult.getStatus());
                        }
                    } else {
                        switch (data.code) {
                            //领光
                            case 1004:
                                hideOpen(1);
                                break;
                            //超时
                            case 1005:
                                hideOpen(2);
                                break;
                            //没有权限
                            case 1003:
                                hideOpen(3);
                                break;
                            default:
                                //没领到
                                hideOpen(1);
                                break;
                        }
                    }
                }), 500);
    }


}
