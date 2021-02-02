package cn.wildfire.chat.app.redpacket.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "RedPacketDetail", primaryKeys = {"redPId", "targetId"})
public class RedPacketDetail implements Parcelable {
//    {"money":"300","note":"恭喜发财，大吉大利！","redPId":"440","status":"0","targetId":"v7wAvAvv","senderId":"JXKLJLQQ","senderName":"user_19351"}
//    public long messageUid;
//    public int type;
    public static final int STATUS_EXPIRED = 12;
    public static final int STATUS_OUT_OF_AMOUNT = 11;

    public static final Creator<RedPacketDetail> CREATOR = new Creator<RedPacketDetail>() {
        @Override
        public RedPacketDetail createFromParcel(Parcel source) {
            return new RedPacketDetail(source);
        }

        @Override
        public RedPacketDetail[] newArray(int size) {
            return new RedPacketDetail[size];
        }
    };

    //    @PrimaryKey(autoGenerate = true)
//    public int id;
    public int money;
    public String note;
    public long redPId;
    public int status;
    @NonNull
    public String targetId = "";
    public String senderId;
    public String senderName;
    public long messageUid;

    public RedPacketDetail() {
    }

    protected RedPacketDetail(Parcel in) {
        this.money = in.readInt();
        this.note = in.readString();
        this.redPId = in.readLong();
        this.status = in.readInt();
        String target = in.readString();
        if (target == null) {
            this.targetId = "";
        } else {
            this.targetId = target;
        }
        this.senderId = in.readString();
        this.senderName = in.readString();
        this.messageUid = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.money);
        dest.writeString(this.note);
        dest.writeLong(this.redPId);
        dest.writeInt(this.status);
        dest.writeString(this.targetId);
        dest.writeString(this.senderId);
        dest.writeString(this.senderName);
        dest.writeLong(this.messageUid);
    }
}