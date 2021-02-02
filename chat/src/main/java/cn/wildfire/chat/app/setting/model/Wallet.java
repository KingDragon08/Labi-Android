package cn.wildfire.chat.app.setting.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Wallet implements Parcelable {
    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel source) {
            return new Wallet(source);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };

    public long id;
    public long score;
    public int money;
    public String userId;
    public int status;

    public Wallet() {
    }

    protected Wallet(Parcel in) {
        this.id = in.readLong();
        this.score = in.readLong();
        this.money = in.readInt();
        this.userId = in.readString();
        this.status = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.score);
        dest.writeInt(this.money);
        dest.writeString(this.userId);
        dest.writeInt(this.status);
    }
}
