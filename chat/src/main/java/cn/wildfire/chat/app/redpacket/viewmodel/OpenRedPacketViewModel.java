package cn.wildfire.chat.app.redpacket.viewmodel;

import java.util.HashMap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cn.wildfire.chat.app.Config;
import cn.wildfire.chat.app.redpacket.db.RedPacketRepository;
import cn.wildfire.chat.app.redpacket.model.RedPacketDetail;
import cn.wildfire.chat.app.redpacket.model.SendBonusResult;
import cn.wildfire.chat.kit.net.Callback;
import cn.wildfire.chat.kit.net.OKHttpHelper;
import cn.wildfire.chat.kit.net.base.LiveDataResultWrapper;

/**
 * 开红包界面ViewModel
 */
public class OpenRedPacketViewModel extends ViewModel {
    /**
     * 获取红包详情
     *
     * @param redPacketId 红包Id
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
     * 打开红包
     *
     * @param userId      打开人id
     * @param redPacketId 红包id
     * @return 红包结果LiveData
     */
    public MutableLiveData<LiveDataResultWrapper<SendBonusResult>> openRedPacket(String userId, String redPacketId) {
        MutableLiveData<LiveDataResultWrapper<SendBonusResult>> sendBonus = new MutableLiveData<>();

        HashMap<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("redPId", String.valueOf(redPacketId));
        OKHttpHelper.post(Config.APIS().get("GetBonus"), param, new Callback<SendBonusResult>() {
            @Override
            public void onSuccess(SendBonusResult sendBonusResult) {
                sendBonus.postValue(new LiveDataResultWrapper<>(sendBonusResult));

                if (0 != sendBonusResult.getStatus()) {
                    int fakeStatus = 10 + sendBonusResult.getStatus();
                    RedPacketDetail redPacketDetail = RedPacketRepository.getInstance().getRedPacketDetailWithStatus(redPacketId, fakeStatus);
                    if (redPacketDetail == null) {
                        //没查到对应状态，插入一条
                        redPacketDetail = new RedPacketDetail();
                        redPacketDetail.status = fakeStatus;
                        redPacketDetail.redPId = Integer.valueOf(redPacketId);
                        redPacketDetail.messageUid = fakeStatus;
                        RedPacketRepository.getInstance().insertRedPacket(redPacketDetail);
                    }
                }
            }

            @Override
            public void onFailure(int code, String message) {
                sendBonus.postValue(new LiveDataResultWrapper<>(code,message));
                int fakeStatus = code == 1004 ? RedPacketDetail.STATUS_OUT_OF_AMOUNT : RedPacketDetail.STATUS_EXPIRED;
                RedPacketDetail redPacketDetail = RedPacketRepository.getInstance().getRedPacketDetailWithStatus(redPacketId, fakeStatus);
                if (redPacketDetail == null) {
                    //没查到对应状态，插入一条
                    redPacketDetail = new RedPacketDetail();
                    redPacketDetail.status = fakeStatus;
                    redPacketDetail.redPId = Integer.valueOf(redPacketId);
                    redPacketDetail.messageUid = fakeStatus;
                    RedPacketRepository.getInstance().insertRedPacket(redPacketDetail);
                }
            }
        });

        return sendBonus;
    }
}
