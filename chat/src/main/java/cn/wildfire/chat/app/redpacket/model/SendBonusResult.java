package cn.wildfire.chat.app.redpacket.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import cn.wildfire.chat.app.redpacket.db.converter.BonusRecordConverter;

public class SendBonusResult implements Parcelable {
    public static final Creator<SendBonusResult> CREATOR = new Creator<SendBonusResult>() {
        @Override
        public SendBonusResult createFromParcel(Parcel source) {
            return new SendBonusResult(source);
        }

        @Override
        public SendBonusResult[] newArray(int size) {
            return new SendBonusResult[size];
        }
    };
    private String createdAt; // 创建时间
    private int money; // 钱,分为单位
    private String targetUsers; // 可领取的用户id,逗号分隔,针对群指定领取人场景
    private int number; // 红包个数,针对群发场景
    private String name; // 红包备注
    private String id = ""; // 红包唯一id
    private int type; // 红包类型, 0:拼手气，1:普通
    private String userId; // 发红包人的id
    private int status; // 发送状态
    private String updatedAt; // 更新时间
    private List<BonusRecord> records; // 红包领取记录

    public SendBonusResult() {
    }

    public SendBonusResult(RedPacketDetail detail) {
        this.money = Integer.valueOf(detail.money);
        this.name = detail.note;
        this.id = String.valueOf(detail.redPId);
        this.type = 0;
        this.userId = detail.senderId;
        this.status = detail.status;
    }

    protected SendBonusResult(Parcel in) {
        this.createdAt = in.readString();
        this.money = in.readInt();
        this.targetUsers = in.readString();
        this.number = in.readInt();
        this.name = in.readString();
        this.id = in.readString();
        this.type = in.readInt();
        this.userId = in.readString();
        this.status = in.readInt();
        this.updatedAt = in.readString();
        this.records = in.createTypedArrayList(BonusRecord.CREATOR);
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getTargetUsers() {
        return targetUsers;
    }

    public void setTargetUsers(String targetUsers) {
        this.targetUsers = targetUsers;
    }

    public int getNumbers() {
        return number;
    }

    public void setNumbers(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<BonusRecord> getRecords() {
        return records;
    }

    public void setRecords(List<BonusRecord> records) {
        this.records = records;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createdAt);
        dest.writeInt(this.money);
        dest.writeString(this.targetUsers);
        dest.writeInt(this.number);
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.userId);
        dest.writeInt(this.status);
        dest.writeString(this.updatedAt);
        dest.writeTypedList(this.records);
    }

    public boolean hasReceived(String userId) {
        if (records != null && records.size() > 0) {
            for (BonusRecord record : records) {
                if (record.userId.equals(userId)) {
                    this.money = record.money;
                    return true;
                }
            }
        }
        return false;
    }
}
