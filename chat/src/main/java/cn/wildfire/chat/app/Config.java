package cn.wildfire.chat.app;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by heavyrain lee on 2017/11/24.
 */

public interface Config {

    // 仅仅是host，没有http开头，不可配置为127.0.0.1 或者 192.168.0.1
    // host可以是IP，可以是域名，如果是域名的话只支持主域名或www域名，二级域名不支持！
    // 例如：example.com或www.example.com是支持的；xx.example.com或xx.yy.example.com是不支持的。
    String IM_SERVER_HOST = "8.210.167.86";
    int IM_SERVER_PORT = 80;

    //正式商用时，建议用https，确保token安全
    // String APP_SERVER_ADDRESS = "http://39.106.177.98:18090";
    String APP_SERVER_ADDRESS = "http://8.210.167.86:18090";
    String SECRET_KEY = "123456";

    String GAME_SERVER = "http://47.242.43.179";
    String GAME_SECRET_KEY = "rr9OOYSciZh+LiA";

    // String ICE_ADDRESS = "turn:39.106.177.98:3478";
    String ICE_ADDRESS = "turn:8.210.167.86:3478";
    String ICE_USERNAME = "im809";
    String ICE_PASSWORD = "im809809";
    int DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND = 120;
    String VIDEO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/wfc/video";
    String AUDIO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/wfc/audio";
    String PHOTO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/wfc/photo";
    String FILE_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/wfc/file";

    static void validateConfig() {
        if (TextUtils.isEmpty(IM_SERVER_HOST)
                || IM_SERVER_HOST.startsWith("http")
                || TextUtils.isEmpty(APP_SERVER_ADDRESS)
                || (!APP_SERVER_ADDRESS.startsWith("http") && !APP_SERVER_ADDRESS.startsWith("https"))
                || IM_SERVER_HOST.equals("127.0.0.1")
                || APP_SERVER_ADDRESS.contains("127.0.0.1")
        ) {
            throw new IllegalStateException("im server host config error");
        }

        if (IM_SERVER_PORT != 80) {
            Log.w("wfc config", "如果IM_SERVER_PORT配置为非80端口，无法使用第三方文件存储");
        }
    }

    static Map<String, String> APIS() {
        Map<String, String> APIS = new HashMap<>();
        // 发送红包
        APIS.put("SendBonus", APP_SERVER_ADDRESS + "/redPackage/create");
        //抢红包
        APIS.put("GetBonus", APP_SERVER_ADDRESS + "/redPackage/compete");
        //查看红包详情
        APIS.put("GetRedPacket", APP_SERVER_ADDRESS + "/redPackage/detail");
        //更新红包状态
        APIS.put("GetRedPacketStatus", APP_SERVER_ADDRESS + "/redPackage/status");
        //获取钱包详情
        APIS.put("GetWallet", APP_SERVER_ADDRESS + "/user/wallet/info");
        //创建新钱包
        APIS.put("NewWallet", APP_SERVER_ADDRESS + "/user/wallet/create");
        // 验证码登录
        APIS.put("loginByMobile", APP_SERVER_ADDRESS + "/im/user/loginByMobile");
        // 用户名密码登录
        APIS.put("loginByName", APP_SERVER_ADDRESS + "/im/user/loginByName");
        // 注册
        APIS.put("register", APP_SERVER_ADDRESS + "/im/user/register");
        return APIS;
    }

}
