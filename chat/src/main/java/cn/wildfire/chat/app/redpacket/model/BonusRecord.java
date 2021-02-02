package cn.wildfire.chat.app.redpacket.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BonusRecord implements Parcelable {
    public static final Creator<BonusRecord> CREATOR = new Creator<BonusRecord>() {
        @Override
        public BonusRecord createFromParcel(Parcel source) {
            return new BonusRecord(source);
        }

        @Override
        public BonusRecord[] newArray(int size) {
            return new BonusRecord[size];
        }
    };
    public int money; // 钱数,分为单位
    public String displayName; // 抢红包人的名字
    public String createdAt; // 抢红包时间
    public int luckyKing; // 是否运气王 1 运气王 0 非运气王
    public String portrait;
    public String userId; // 抢红包人用户id

    public BonusRecord() {
    }

    protected BonusRecord(Parcel in) {
        this.money = in.readInt();
        this.displayName = in.readString();
        this.createdAt = in.readString();
        this.luckyKing = in.readInt();
        this.portrait = in.readString();
        this.userId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.money);
        dest.writeString(this.displayName);
        dest.writeString(this.createdAt);
        dest.writeInt(this.luckyKing);
        dest.writeString(this.portrait);
        dest.writeString(this.userId);
    }
}
