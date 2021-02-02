package cn.wildfirechat.message.redpacket;

import android.os.Bundle;
import android.os.Parcel;

import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.PersistFlag;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_Lucky_Money_Accept_AGroup;

/**
 * 收群组红包
 */
@ContentTag(type = ContentType_Lucky_Money_Accept_AGroup, flag = PersistFlag.Persist_And_Count)
public class LuckyMoneyGroupReceiveMessageContent extends LuckyMoneyReceiveMessageContent {

    //单发
//    [
//    {
//        "sender": "gGgigi44",
//            "conv": {
//        "type": 1,
//                "target": "dlesdsKK",
//                "line": 0
//    },
//        "payload": {
//        "type": 2002,
//                "searchableContent": "恭喜发财，大吉大利",
//                "pushContent": null,
//                "content": "{\"status\":0,\"money\":\"200\",\"messageUid\":0,\"redPId\":\"417\",\"note\":\"恭喜发财，大吉大利\",\"type\":3}",
//                "mediaType": 0,
//                "remoteMediaUrl": null,
//                "persistFlag": 3,
//                "expireDuration": 0,
//                "mentionedType": 0,
//                "mentionedTarget": []
//    }
//    }
//]

    //群发

//    [
//    {
//        "sender": "gGgigi44",
//            "conv": {
//        "type": 1,
//                "target": "dlesdsKK",
//                "line": 0
//    },
//        "payload": {
//        "type": 2002,
//                "searchableContent": "恭喜发财，大吉大利",
//                "pushContent": null,
//                "content": "{\"status\":0,\"money\":\"1000\",\"messageUid\":0,\"redPId\":\"418\",\"note\":\"恭喜发财，大吉大利\",\"type\":3}",
//                "mediaType": 0,
//                "remoteMediaUrl": null,
//                "persistFlag": 3,
//                "expireDuration": 0,
//                "mentionedType": 0,
//                "mentionedTarget": []
//    }
//    }
//]

    public LuckyMoneyGroupReceiveMessageContent() {
        super();
    }

    public LuckyMoneyGroupReceiveMessageContent(Bundle bundle) {
        super(bundle);
    }

    public LuckyMoneyGroupReceiveMessageContent(Parcel in) {
        super(in);
    }

    public static final Creator<LuckyMoneyGroupReceiveMessageContent> CREATOR = new Creator<LuckyMoneyGroupReceiveMessageContent>() {
        @Override
        public LuckyMoneyGroupReceiveMessageContent createFromParcel(Parcel source) {
            return new LuckyMoneyGroupReceiveMessageContent(source);
        }

        @Override
        public LuckyMoneyGroupReceiveMessageContent[] newArray(int size) {
            return new LuckyMoneyGroupReceiveMessageContent[size];
        }
    };
}
