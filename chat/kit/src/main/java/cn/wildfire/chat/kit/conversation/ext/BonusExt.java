package cn.wildfire.chat.kit.conversation.ext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import cn.wildfire.chat.app.redpacket.SendGroupActivity;
import cn.wildfire.chat.app.redpacket.SendSingleActivity;
import cn.wildfire.chat.kit.annotation.ExtContextMenuItem;
import cn.wildfire.chat.kit.conversation.ext.core.ConversationExt;
import cn.wildfire.chat.kit.viewmodel.MessageViewModel;
import com.imchat.ezn.R;
import cn.wildfirechat.message.MessageContent;
import cn.wildfirechat.message.redpacket.LuckyMoneyGroupMessageContent;
import cn.wildfirechat.message.redpacket.LuckyMoneyMessageContent;
import cn.wildfirechat.message.redpacket.LuckyMoneyReceiveMessageContent;
import cn.wildfirechat.model.Conversation;

/**
 * 聊天菜单中的红包项
 */
public class BonusExt extends ConversationExt {

    private MessageViewModel messageViewModel;
    private Conversation conversation;

    /**
     * @param containerView 扩展view的container
     * @param conversation
     */
    @ExtContextMenuItem(title = "红包")
    public void bonus(View containerView, Conversation conversation) {
        this.conversation = conversation;
        int conversationType = conversation.type.getValue();
        // 单聊
        if (conversationType == 0) {
            Intent intent = new Intent(activity, SendSingleActivity.class);
            intent.putExtra("target", conversation.target);
            startActivityForResult(intent, 200);
        }
        if (conversationType == 1) {
            Intent intent = new Intent(activity, SendGroupActivity.class);
            intent.putExtra("target", conversation.target);
            startActivityForResult(intent, 201);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            // 私发红包
            if (requestCode == 200) {
                Bundle bundle = intent.getBundleExtra("extra");
                messageViewModel = ViewModelProviders.of(this.activity).get(MessageViewModel.class);
                MessageContent content = new LuckyMoneyMessageContent(bundle);
                messageViewModel.sendMessage(this.conversation, content);
            }
            // 群发红包
            if (requestCode == 201) {
                Bundle bundle = intent.getBundleExtra("extra");
                messageViewModel = ViewModelProviders.of(this.activity).get(MessageViewModel.class);
                MessageContent content = new LuckyMoneyGroupMessageContent(bundle);
                messageViewModel.sendMessage(this.conversation, content);
            }
        }
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public int iconResId() {
        return R.mipmap.ic_func_bonus;
    }

    @Override
    public String title(Context context) {
        return "红包";
    }

    @Override
    public boolean filter(Conversation conversation) {
        // 仅私聊和群聊显示红包
        if (conversation.type.getValue() != 0 && conversation.type.getValue() != 1) {
            return true;
        }
        return false;
    }
}
