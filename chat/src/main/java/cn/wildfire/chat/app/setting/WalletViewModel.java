package cn.wildfire.chat.app.setting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import cn.wildfire.chat.app.Config;
import cn.wildfire.chat.app.setting.model.Wallet;
import cn.wildfire.chat.kit.net.Callback;
import cn.wildfire.chat.kit.net.OKHttpHelper;

/**
 * 钱包ViewModel
 */
public class WalletViewModel extends ViewModel {
    /**
     * 获取钱包详情
     *
     * @param userId 用户id
     * @return 钱包详情
     */
    public MutableLiveData<Wallet> getWallet(String userId) {
        MutableLiveData<Wallet> liveData = new MutableLiveData<>();
        HashMap<String, String> param = new HashMap<>();
        param.put("userId", userId);
        OKHttpHelper.get(Config.APIS().get("GetWallet"), param, new Callback<Wallet>() {
            @Override
            public void onSuccess(Wallet wallet) {
                liveData.postValue(wallet);
            }

            @Override
            public void onFailure(int code, String message) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    /**
     * 创建新钱包
     *
     * @param userId 用户id
     * @return 钱包详情
     */
    public MutableLiveData<Wallet> newWallet(String userId, String payNumber) {
        MutableLiveData<Wallet> liveData = new MutableLiveData<>();
        HashMap<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("payNumber", payNumber);
        OKHttpHelper.post(Config.APIS().get("NewWallet"), param, new Callback<Wallet>() {
            @Override
            public void onSuccess(Wallet wallet) {
                liveData.postValue(wallet);
            }

            @Override
            public void onFailure(int code, String message) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }


}
