package cn.wildfirechat.message.redpacket;

import android.os.Bundle;
import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.MessageContent;
import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;
import static cn.wildfirechat.message.core.MessageContentType.ContentType_Lucky_Money;

/**
 * 单发红包消息
 */
@ContentTag(type = ContentType_Lucky_Money, flag = PersistFlag.Persist_And_Count)
public class LuckyMoneyMessageContent extends MessageContent {
//    [
//    {
//        "sender": "gGgigi44",
//            "conv": {
//        "type": 0,
//                "target": "6B7N6N00",
//                "line": 0
//    },
//        "payload": {
//        "type": 2001,
//                "searchableContent": "这里是文字",
//                "pushContent": null,
//                "content": "{\"status\":0,\"money\":\"100\",\"messageUid\":0,\"redPId\":\"416\",\"note\":\"这里是文字\",\"type\":0}",
//                "mediaType": 0,
//                "remoteMediaUrl": null,
//                "persistFlag": 3,
//                "expireDuration": 0,
//                "mentionedType": 0,
//                "mentionedTarget": []
//    }
//    }
//]

    public LuckyMoneyMessageContent() {}

    public LuckyMoneyMessageContent(Bundle bundle) {
        try {
            JSONObject objWrite = new JSONObject();
            objWrite.put("money", String.valueOf(bundle.getInt("money", 0)));
            objWrite.put("note", bundle.getString("note", "恭喜发财，大吉大利！"));
            objWrite.put("redPId", bundle.getString("redPId", "-1"));
            objWrite.put("status", String.valueOf(bundle.getInt("status", -1)));
            objWrite.put("targetId", bundle.getString("targetId", "-1"));
            objWrite.put("senderId", bundle.getString("senderId", "-1"));
            objWrite.put("senderName",  bundle.getString("senderName", ""));
            this.extra = objWrite.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 获取红包Id
    public String getBonusId() {
        try {
            JSONObject bonus = new JSONObject(this.extra);
            return  bonus.optString("redPId", "");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 获取备注信息
    public String getNote() {
        try {
            JSONObject bonus = new JSONObject(this.extra);
            return  bonus.optString("note", "恭喜发财,大吉大利!");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public MessagePayload encode() {
        MessagePayload payload = new MessagePayload();
        payload.searchableContent = "[红包]";
        payload.content = this.extra;
        return payload;
    }

    @Override
    public void decode(MessagePayload payload) {
        extra = payload.content;
    }

    @Override
    public String digest(Message message) {
        return "[红包]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.extra);
    }

    protected LuckyMoneyMessageContent(Parcel in) {
        super(in);
        this.extra = in.readString();
    }

    public static final Creator<LuckyMoneyMessageContent> CREATOR = new Creator<LuckyMoneyMessageContent>() {
        @Override
        public LuckyMoneyMessageContent createFromParcel(Parcel source) {
            return new LuckyMoneyMessageContent(source);
        }

        @Override
        public LuckyMoneyMessageContent[] newArray(int size) {
            return new LuckyMoneyMessageContent[size];
        }
    };
}