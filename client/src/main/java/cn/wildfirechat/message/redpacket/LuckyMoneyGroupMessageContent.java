package cn.wildfirechat.message.redpacket;

import android.os.Bundle;
import android.os.Parcel;

import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.PersistFlag;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_Lucky_Money_AGroup;

/**
 * 群组红包
 */
@ContentTag(type = ContentType_Lucky_Money_AGroup, flag = PersistFlag.Persist_And_Count)
public class LuckyMoneyGroupMessageContent extends LuckyMoneyMessageContent{

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

    public LuckyMoneyGroupMessageContent() {
    }

    public LuckyMoneyGroupMessageContent(Bundle bundle) {
        super(bundle);
    }

    public LuckyMoneyGroupMessageContent(Parcel in) {
        super(in);
    }

    public static final Creator<LuckyMoneyGroupMessageContent> CREATOR = new Creator<LuckyMoneyGroupMessageContent>() {
        @Override
        public LuckyMoneyGroupMessageContent createFromParcel(Parcel source) {
            return new LuckyMoneyGroupMessageContent(source);
        }

        @Override
        public LuckyMoneyGroupMessageContent[] newArray(int size) {
            return new LuckyMoneyGroupMessageContent[size];
        }
    };
}