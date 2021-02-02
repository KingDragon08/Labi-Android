package cn.wildfire.chat.app.redpacket.db.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.wildfire.chat.app.redpacket.model.BonusRecord;

public class BonusRecordConverter {
    private Gson gson = new Gson();

    @TypeConverter
    public String bonusToString(List<BonusRecord> records) {
        return gson.toJson(records);
    }

    @TypeConverter
    public List<BonusRecord> stringToBonus(String recordStr) {
        return gson.fromJson(recordStr, new TypeToken<List<BonusRecord>>() {
        }.getType());
    }
}
