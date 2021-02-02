package cn.wildfire.chat.kit.conversation.message.viewholder;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Message;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lqr.emoji.MoonUtils;

import java.util.List;
import android.os.Handler;
import java.util.logging.LogRecord;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.app.redpacket.OpenRedPacketActivity;
import cn.wildfire.chat.app.redpacket.RedPacketDetailActivity;
import cn.wildfire.chat.app.redpacket.RedPacketUtils;
import cn.wildfire.chat.app.redpacket.model.RedPacketDetail;
import cn.wildfire.chat.app.redpacket.model.SendBonusResult;
import cn.wildfire.chat.app.redpacket.viewmodel.OpenRedPacketViewModel;
import cn.wildfire.chat.app.redpacket.viewmodel.RedPacketDetailViewModel;
import cn.wildfire.chat.kit.WfcWebViewActivity;
import cn.wildfire.chat.kit.annotation.EnableContextMenu;
import cn.wildfire.chat.kit.annotation.MessageContentType;
import cn.wildfire.chat.kit.annotation.ReceiveLayoutRes;
import cn.wildfire.chat.kit.annotation.SendLayoutRes;
import cn.wildfire.chat.kit.conversation.ConversationFragment;
import cn.wildfire.chat.kit.conversation.message.model.UiMessage;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfire.chat.kit.widget.LinkTextViewMovementMethod;
import com.imchat.ezn.R;
import cn.wildfirechat.message.redpacket.LuckyMoneyGroupMessageContent;
import cn.wildfirechat.message.redpacket.LuckyMoneyGroupReceiveMessageContent;
import cn.wildfirechat.message.redpacket.LuckyMoneyMessageContent;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 红包ViewHolder
 */
@MessageContentType({LuckyMoneyMessageContent.class, LuckyMoneyGroupMessageContent.class})
@SendLayoutRes(resId = R.layout.conversation_item_bonus_send)
@ReceiveLayoutRes(resId = R.layout.conversation_item_bonus_receive)
@EnableContextMenu
public class BonusMessageContentViewHolder extends NormalMessageContentViewHolder {
    @BindView(R.id.tv_bri_mess)
    TextView tv_bri_mess;
    @BindView(R.id.tv_bri_target)
    TextView tv_bri_target;
    @BindView(R.id.tv_bri_name)
    TextView tv_bri_name;
    @BindView(R.id.bri_bg)
    View layout;
    @BindView(R.id.bonus_item)
    ImageView bonusImageview;

    private UserViewModel userViewModel;
    private RedPacketDetailViewModel redPacketDetailViewModel;

    private boolean clicked;

    private Gson gson = new Gson();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clicked = false;
        }
    };

    public BonusMessageContentViewHolder(ConversationFragment fragment, RecyclerView.Adapter adapter, View itemView) {
        super(fragment, adapter, itemView);
        userViewModel = ViewModelProviders.of(fragment).get(UserViewModel.class);
        redPacketDetailViewModel = ViewModelProviders.of(fragment).get(RedPacketDetailViewModel.class);
        clicked = false;
    }

//    @OnClick({R.id.tv_bri_mess, R.id.tv_bri_target, R.id.tv_bri_name, R.id.bri_bg})
    @OnClick(R.id.bonus_item)
    public void openSingle() {
//        String myUserId = userViewModel.getUserId();
//        //红包内容
//        RedPacketDetail detail = gson.fromJson(message.message.content.extra, RedPacketDetail.class);
//        //我的领取记录
//        RedPacketDetail myDetail = redPacketDetailViewModel.getRedPacketDetailRaw(String.valueOf(detail.redPId), myUserId);
//        //领取过了
//
//        detail.senderId = message.message.sender;
//        if (detail.senderId.equals(myUserId) || (myDetail != null && (myUserId.equals(myDetail.targetId)))  ) {
//            //自己发的或者已经领取过
//            Intent intent = new Intent(fragment.getActivity(), RedPacketDetailActivity.class);
//            intent.putExtra("redPacketId", String.valueOf(detail.redPId));
//            fragment.startActivity(intent);
//        } else {
        if (clicked) {
            return;
        }
        clicked = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = new Object();
                handler.handleMessage(msg);
            }
        }, 1000);
        ActivityManager am = (ActivityManager) fragment.getActivity().getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        if (!cn.getClassName().contains("ConversationActivity")) {
            return;
        }
        Intent intent = new Intent(fragment.getActivity(), OpenRedPacketActivity.class);
        intent.putExtra("data", message.message);
        intent.putExtra("conversation", message.message.conversation);
        fragment.startActivity(intent);
        fragment.getActivity().overridePendingTransition(R.anim.rp_top_in, R.anim.rp_top_out);
//        }
    }

    @Override
    public void onBind(UiMessage message) {
        tv_bri_target.setText(R.string.jrmf_rp_receive_rp);
        MoonUtils.identifyFaceExpression(fragment.getContext(), tv_bri_mess, ((LuckyMoneyMessageContent) message.message.content).getNote(), ImageSpan.ALIGN_BOTTOM);
        tv_bri_mess.setMovementMethod(new LinkTextViewMovementMethod(link -> {
            WfcWebViewActivity.loadUrl(fragment.getContext(), "", link);
            return true;
        }));
        int direction = getItemViewType() >> 24;
        // 3秒以内的红包自动弹打开框
        long current = System.currentTimeMillis();
        if (message.message.serverTime + 12000 > current) {
            openSingle();
        }
        //根据消息的内容和状态来显示
        RedPacketDetail infoDetail = gson.fromJson(message.message.content.extra, RedPacketDetail.class);
        // 自己领取过就变灰
        RedPacketDetail readDetail = redPacketDetailViewModel.getRedPacketDetailRaw(String.valueOf(infoDetail.redPId), message.message.sender, message.message.messageUid);
        // tv_bri_name.setText(infoDetail.note);
        if (direction == 0) {
            //send
            if (readDetail != null && readDetail.status == 10) {
//                layout.setBackgroundResource(R.drawable.bg_red_packet_sender_received);
                bonusImageview.setImageAlpha(128);
            } else {
                layout.setBackgroundResource(R.drawable._bg_from_hongbao_send);
            }
        } else {
            //receive
            if (readDetail != null && readDetail.status == 10) {
//                layout.setBackgroundResource(R.drawable.bg_red_packet_receiver_received);
//                layout.setBackgroundResource(R.mipmap.bonus_item_opened);
                bonusImageview.setImageAlpha(128);
            } else {
                layout.setBackgroundResource(R.mipmap.bonus_item);
            }
        }
        //检查是不是不可领取
        List<RedPacketDetail> details = redPacketDetailViewModel.getRedPacketDetailWithinStatusRaw(String.valueOf(infoDetail.redPId), 11);
        if (details != null && details.size() > 0) {
            if (direction == 0) {
                //send
                layout.setBackgroundResource(R.drawable.bg_red_packet_sender_received);
            } else {
                //receive
//                layout.setBackgroundResource(R.mipmap.bonus_item_opened);
                bonusImageview.setImageAlpha(128);
            }
            setTargetName(details);
        } else {
            LiveData<List<RedPacketDetail>> detailLiveData = redPacketDetailViewModel.getRedPacketDetailWithinStatus(String.valueOf(infoDetail.redPId), 11);
            detailLiveData.observe(fragment, redPacketDetails -> {
                if (redPacketDetails != null && redPacketDetails.size() > 0) {
                    if (direction == 0) {
                        //send
                        layout.setBackgroundResource(R.drawable.bg_red_packet_sender_received);
                    } else {
                        //receive
//                        layout.setBackgroundResource(R.mipmap.bonus_item_opened);
                        bonusImageview.setImageAlpha(128);
                    }
                    setTargetName(redPacketDetails);
                }
            });
        }
    }

    private void setTargetName(List<RedPacketDetail> details) {
        int status = RedPacketUtils.getStatusFromRedPacketDetails(details);
        switch (status){
            case RedPacketDetail.STATUS_EXPIRED:
                tv_bri_target.setText("已过期");
                break;
            case RedPacketDetail.STATUS_OUT_OF_AMOUNT:
                tv_bri_target.setText("已领光");
                break;
        }
    }
}
