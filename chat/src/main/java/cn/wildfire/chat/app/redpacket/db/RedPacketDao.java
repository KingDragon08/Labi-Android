package cn.wildfire.chat.app.redpacket.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cn.wildfire.chat.app.redpacket.model.RedPacketDetail;

@Dao
public interface RedPacketDao {
    @Query("select * from RedPacketDetail where redPId = :redPacketId")
    LiveData<List<RedPacketDetail>> getRedPackets(String redPacketId);

    @Query("select * from RedPacketDetail")
    List<RedPacketDetail> getRedPacketsRaw();

    @Query("select * from RedPacketDetail where redPId = :redPacketId and targetId = :userId")
    LiveData<RedPacketDetail> getRedPacketDetail(String redPacketId, String userId);

    @Query("select * from RedPacketDetail where redPId = :redPacketId and senderId = :userId")
    RedPacketDetail getRedPacketDetailRaw(String redPacketId, String userId);

    @Query("select * from RedPacketDetail where redPId = :redPacketId and targetId = :userId and messageUid = :messageUid")
    RedPacketDetail getRedPacketDetailRaw(String redPacketId, String userId, long messageUid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRedPacket(RedPacketDetail redPacketDetail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRedPackets(List<RedPacketDetail> redPacketDetails);

    @Query("select * from RedPacketDetail where redPId = :redPacketId and status = :status")
    RedPacketDetail getRedPacketDetailWithStatus(String redPacketId, int status);

    @Query("select * from RedPacketDetail where redPId = :redPacketId and status >= :status")
    LiveData<List<RedPacketDetail>> getRedPacketDetailWithinStatus(String redPacketId, int status);

    @Query("select * from RedPacketDetail where redPId = :redPacketId and status >= :status")
    List<RedPacketDetail> getRedPacketDetailWithinStatusRaw(String redPacketId, int status);
}
