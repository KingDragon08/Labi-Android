package cn.wildfire.chat.kit.conversation.message.viewholder;


import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import cn.wildfire.chat.app.redpacket.model.RedPacketDetail;
import cn.wildfire.chat.kit.annotation.LayoutRes;
import cn.wildfire.chat.kit.annotation.MessageContentType;
import cn.wildfire.chat.kit.conversation.ConversationFragment;
import cn.wildfire.chat.kit.conversation.message.model.UiMessage;
import cn.wildfire.chat.kit.user.UserViewModel;
import com.imchat.ezn.R;
import cn.wildfirechat.message.redpacket.LuckyMoneyGroupReceiveMessageContent;
import cn.wildfirechat.message.redpacket.LuckyMoneyReceiveMessageContent;
import cn.wildfirechat.model.UserInfo;

/**
 * 收红包消息holder
 */
@MessageContentType({LuckyMoneyReceiveMessageContent.class, LuckyMoneyGroupReceiveMessageContent.class})
@LayoutRes(resId = R.layout.conversation_item_notification)
public class ReceiveBonusMessageContentViewHolder extends SimpleNotificationMessageContentViewHolder {

    private UserViewModel userViewModel;

    public ReceiveBonusMessageContentViewHolder(@NonNull ConversationFragment fragment, RecyclerView.Adapter adapter, View itemView) {
        super(fragment, adapter, itemView);
        userViewModel = ViewModelProviders.of(fragment).get(UserViewModel.class);
    }

    @Override
    public boolean contextMenuItemFilter(UiMessage uiMessage, String tag) {
        return true;
    }

    @Override
    public void onBind(UiMessage message, int position) {
        setMessageTime(message.message, position);
        Gson gson = new Gson();
        RedPacketDetail redPacketDetail = gson.fromJson(message.message.content.extra, RedPacketDetail.class);
        String selfId = userViewModel.getUserId();
        boolean selfSent = selfId.equals(redPacketDetail.targetId);
        boolean selfTaken = !TextUtils.isEmpty(redPacketDetail.senderId) && redPacketDetail.senderId.contains(userViewModel.getUserId());
        UserInfo sender = userViewModel.getUserInfo(redPacketDetail.targetId, false);
        UserInfo receiver = userViewModel.getUserInfo(redPacketDetail.senderId, false);
        SpannableStringBuilder string = new SpannableStringBuilder();
        if (selfSent && selfTaken) {
            string.append("你领取了自己的");
        } else if (selfSent) {
            string.append(receiver.displayName)
                    .append("领取了你的");
        } else if (selfTaken) {
            string.append("你领取了")
                    .append(sender.displayName)
                    .append("的");
        } else {
            string.append(receiver.displayName)
                    .append("领取了")
                    .append(sender.displayName)
                    .append("的");
        }
        int start = string.length();
        string.append("红包");
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#E54141")), start, start + 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        notificationTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable._xiaobao_icon, 0, 0, 0);
        notificationTextView.setText(string);
        notificationTextView.setVisibility(View.GONE);
        notificationLayout.removeView(notificationTextView);
    }
}
