package cn.wildfire.chat.app.redpacket.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;

import cn.wildfire.chat.app.Config;
import cn.wildfire.chat.app.redpacket.db.RedPacketRepository;
import cn.wildfire.chat.app.redpacket.model.RedPacketDetail;
import cn.wildfire.chat.app.redpacket.model.SendBonusResult;
import cn.wildfire.chat.kit.net.Callback;
import cn.wildfire.chat.kit.net.OKHttpHelper;

public class RedPacketDetailViewModel extends ViewModel {

    /**
     * 获取红包详情
     *
     * @param redPacketId 红包id
     * @return 红包详情
     */
    public MutableLiveData<SendBonusResult> getRedPacketDetail(String redPacketId) {
        MutableLiveData<SendBonusResult> liveData = new MutableLiveData<>();

        HashMap<String, String> param = new HashMap<>();
        param.put("redPId", redPacketId);
        OKHttpHelper.get(Config.APIS().get("GetRedPacket"), param, new Callback<SendBonusResult>() {
            @Override
            public void onSuccess(SendBonusResult sendBonusResult) {
                liveData.postValue(sendBonusResult);
            }

            @Override
            public void onFailure(int code, String message) {
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    /**
     * 领取红包的记录
     *
     * @param redPacketId 红包id
     * @return 领取过这个红包的记录
     */
    public LiveData<List<RedPacketDetail>> getRedPackets(String redPacketId) {
        return RedPacketRepository.getInstance().getRedPackets(redPacketId);
    }

    public List<RedPacketDetail> getRedPacketsRaw(String redPacketId) {
        return RedPacketRepository.getInstance().getRedPacketsRaw(redPacketId);
    }

    /**
     * 某个人领取红包的记录
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 领取过这个红包的记录
     */
    public RedPacketDetail getRedPacketDetailRaw(String redPacketId, String userId) {
        return RedPacketRepository.getInstance().getRedPacketDetailRaw(redPacketId, userId);
    }

    /**
     * 某个人某个红包消息的领取记录
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @param messageUid  红包对应的消息id
     * @return 领取记录
     */
    public RedPacketDetail getRedPacketDetailRaw(String redPacketId, String userId, long messageUid) {
        return RedPacketRepository.getInstance().getRedPacketDetailRaw(redPacketId, userId, messageUid);
    }

    /**
     * 某个人某个红包消息的不能领取的记录
     *
     * @param redPacketId 红包id
     * @param status      status
     * @return 领取记录
     */
    public List<RedPacketDetail> getRedPacketDetailWithinStatusRaw(String redPacketId, int status) {
        return RedPacketRepository.getInstance().getRedPacketDetailWithinStatusRaw(redPacketId, status);
    }

    /**
     * 某个人某个红包消息的不能领取的记录
     *
     * @param redPacketId 红包id
     * @param status      status
     * @return 领取记录
     */
    public LiveData<List<RedPacketDetail>> getRedPacketDetailWithinStatus(String redPacketId, int status) {
        return RedPacketRepository.getInstance().getRedPacketDetailWithinStatus(redPacketId, status);
    }
}
