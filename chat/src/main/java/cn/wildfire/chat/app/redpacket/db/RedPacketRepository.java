package cn.wildfire.chat.app.redpacket.db;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.wildfire.chat.app.MyApp;
import cn.wildfire.chat.app.redpacket.AppExecutors;
import cn.wildfire.chat.app.redpacket.model.RedPacketDetail;

public class RedPacketRepository {
    private static RedPacketRepository sInstance;

    private final RedPacketDatabase mDatabase;

    private RedPacketRepository() {
        mDatabase = RedPacketDatabase.getInstance(MyApp.getContext(), new AppExecutors());
//        mObservableProducts = new MediatorLiveData<>();

//        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(),
//                productEntities -> {
//                    if (mDatabase.getDatabaseCreated().getValue() != null) {
//                        mObservableProducts.postValue(productEntities);
//                    }
//                });
    }

    public static RedPacketRepository getInstance() {
        if (sInstance == null) {
            synchronized (RedPacketRepository.class) {
                if (sInstance == null) {
                    sInstance = new RedPacketRepository();
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<RedPacketDetail>> getRedPackets(String redPacketId) {
        return mDatabase.redPacketDao().getRedPackets(redPacketId);
    }

    public List<RedPacketDetail> getRedPacketsRaw(String redPacketId) {
        return mDatabase.redPacketDao().getRedPacketsRaw();
    }

    public LiveData<RedPacketDetail> getRedPacketDetail(String redPacketId, String userId) {
        return mDatabase.redPacketDao().getRedPacketDetail(redPacketId, userId);
    }

    public RedPacketDetail getRedPacketDetailRaw(String redPacketId, String userId) {
        return mDatabase.redPacketDao().getRedPacketDetailRaw(redPacketId, userId);
    }

    public RedPacketDetail getRedPacketDetailRaw(String redPacketId, String userId, long messageUid) {
        return mDatabase.redPacketDao().getRedPacketDetailRaw(redPacketId, userId, messageUid);
    }

    public long insertRedPacket(RedPacketDetail redPacketDetail) {
        return mDatabase.redPacketDao().insertRedPacket(redPacketDetail);
    }

    public void insertRedPackets(List<RedPacketDetail> redPacketDetails) {
        mDatabase.redPacketDao().insertRedPackets(redPacketDetails);
    }

    public RedPacketDetail getRedPacketDetailWithStatus(String redPacketId, int status) {
        return mDatabase.redPacketDao().getRedPacketDetailWithStatus(redPacketId, status);
    }

    public LiveData<List<RedPacketDetail>> getRedPacketDetailWithinStatus(String redPacketId, int status) {
        return mDatabase.redPacketDao().getRedPacketDetailWithinStatus(redPacketId, status);
    }

    public List<RedPacketDetail> getRedPacketDetailWithinStatusRaw(String redPacketId, int status) {
        return mDatabase.redPacketDao().getRedPacketDetailWithinStatusRaw(redPacketId, status);
    }
}
