package cn.wildfire.chat.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.wildfire.chat.app.login.model.VerifyCode;
import cn.wildfire.chat.app.model.Ads;
import cn.wildfire.chat.app.model.Bank;
import cn.wildfire.chat.app.model.Game;
import cn.wildfire.chat.app.model.GameInfo;
import cn.wildfire.chat.app.model.GameUserInfo;
import cn.wildfire.chat.app.model.Jifen;
import cn.wildfire.chat.app.model.RegisterResult;
import cn.wildfire.chat.app.model.Room;
import cn.wildfire.chat.app.model.Wallet;
import cn.wildfire.chat.kit.net.OKHttpHelper;
import cn.wildfire.chat.kit.net.SimpleCallback;

public class GameService {
    private static GameService instance = new GameService();
    public static String APP_SERVER_ADDRESS = Config.APP_SERVER_ADDRESS;
    public static String GAME_SERVER = Config.GAME_SERVER;

    private GameService() {}

    public static GameService getInstance() {
        return instance;
    }

    /**
     * 注册新用户
     * @param name       用户名
     * @param password   密码
     * @param inviteCode 邀请码
     * @param callback   回调
     */
    public static void register(String name, String password, String inviteCode, SimpleCallback<Void> callback) {
        String url = GAME_SERVER + "/app/verifyCode";
        Map<String, String> params = new HashMap<>();
        params.put("code", inviteCode);
        OKHttpHelper.game(url, params, new SimpleCallback<VerifyCode>() {
            @Override
            public void onUiSuccess(VerifyCode code) {
                if (code.isExist()) {
                    String url = APP_SERVER_ADDRESS + "/im/user/register";
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("password", password);

                    OKHttpHelper.post(url, params, new SimpleCallback<RegisterResult>() {

                        @Override
                        public void onUiSuccess(RegisterResult result) {
                            // 同步数据给game server
                            String url = GAME_SERVER + "/app/register";
                            Map<String, String> params = new HashMap<>();
                            params.put("id", result.getUserId());
                            params.put("regCode", inviteCode);
                            OKHttpHelper.game(url, params, null);
                            // 创建钱包
                            createBonus(result.getUserId());
                            callback.onUiSuccess(null);
                        }

                        @Override
                        public void onUiFailure(int code, String msg) {
                            callback.onUiFailure(code, msg);
                        }
                    });
                } else {
                    callback.onUiFailure(-1, "邀请码不存在");
                }
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });


    }

    /**
     * 创建钱包
     * @param userId
     */
    public static void createBonus(String userId) {
        String url = APP_SERVER_ADDRESS + "/user/wallet/create";
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("payNumber", "123456");

        OKHttpHelper.post(url, params, null);
    }

    /**
     * 获取用户游戏信息
     * @param sn
     * @param callback
     */
    public static void getUserInfo(String sn, SimpleCallback<GameUserInfo> callback) {
        String url = GAME_SERVER + "/app/userInfo";
        Map<String, String> params = new HashMap<>();
        params.put("sn", sn);

        OKHttpHelper.game(url, params, new SimpleCallback<GameUserInfo>() {

            @Override
            public void onUiSuccess(GameUserInfo gameUserInfo) {
                callback.onUiSuccess(gameUserInfo);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 获取广告
     * @param callback
     */
    public static void getAds(SimpleCallback<ArrayList<Ads>> callback) {
        String url = GAME_SERVER + "/app/ads";
        Map<String, String> params = new HashMap<>();
        params.put("fom", "android");

        OKHttpHelper.game(url, params, new SimpleCallback<ArrayList<Ads>>() {

            @Override
            public void onUiSuccess(ArrayList<Ads> ads) {
                callback.onUiSuccess(ads);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 获取游戏列表
     * @param callback
     */
    public static void getGames(SimpleCallback<ArrayList<Game>> callback) {
        String url = GAME_SERVER + "/app/games";
        Map<String, String> params = new HashMap<>();
        params.put("fom", "android");

        OKHttpHelper.game(url, params, new SimpleCallback<ArrayList<Game>>() {

            @Override
            public void onUiSuccess(ArrayList<Game> games) {
                callback.onUiSuccess(games);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 获取游戏房间列表
     * @param type
     * @param callback
     */
    public static void getRooms(String type, SimpleCallback<ArrayList<Room>> callback) {
        String url = GAME_SERVER + "/app/rooms";
        Map<String, String> params = new HashMap<>();
        params.put("type", type);

        OKHttpHelper.game(url, params, new SimpleCallback<ArrayList<Room>>() {

            @Override
            public void onUiSuccess(ArrayList<Room> rooms) {
                callback.onUiSuccess(rooms);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 加入房间
     * @param room
     * @param callback
     */
    public static void joinRoom(String room, String user, SimpleCallback<Void> callback) {
        String url = GAME_SERVER + "/app/join";
        Map<String, String> params = new HashMap<>();
        params.put("room", room);
        params.put("user", user);

        OKHttpHelper.game(url, params, new SimpleCallback<Void>() {

            @Override
            public void onUiSuccess(Void aVoid) {
                callback.onUiSuccess(null);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    public static void changePsw(String userId, String op, String np, SimpleCallback<Void> callback) {
        String url = APP_SERVER_ADDRESS + "/im/user/changePassword";
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);
        params.put("userId", userId);
        params.put("oldPw", op);
        params.put("newPw", np);

        OKHttpHelper.post(url, params, new SimpleCallback<Void>() {
            @Override
            public void onUiSuccess(Void aVoid) {
                callback.onUiSuccess(null);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 获取钱包信息
     * @param userId
     */
    public static void getWalletInfo(String userId, SimpleCallback<Wallet> callback) {
        String url = APP_SERVER_ADDRESS + "/user/wallet/info";
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);

        OKHttpHelper.get(url, params, new SimpleCallback<Wallet>() {
            @Override
            public void onUiSuccess(Wallet wallet) {
                callback.onUiSuccess(wallet);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 获取充值转账的卡号列表
     */
    public static void getChargeBanks(SimpleCallback<ArrayList<Bank>> callback) {
        String url = GAME_SERVER + "/app/getChargeBankCards";
        Map<String, String> params = new HashMap<>();

        OKHttpHelper.game(url, params, new SimpleCallback<ArrayList<Bank>> () {

            @Override
            public void onUiSuccess(ArrayList<Bank> banks) {
                callback.onUiSuccess(banks);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 充值
     */
    public static void charge(String userId, String amount, String bank, String proof, SimpleCallback<Void> callback) {
        String url = GAME_SERVER + "/app/charge";
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("bank", bank);
        params.put("amount", amount);
        params.put("shortcut", proof);

        OKHttpHelper.game(url, params, new SimpleCallback<String>() {
            @Override
            public void onUiSuccess(String ret) {
                callback.onUiSuccess(null);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 提现
     * @param userId
     * @param amount
     * @param callback
     */
    public static void withdraw(String userId, String amount, SimpleCallback<Void> callback) {
        String url = GAME_SERVER + "/app/excharge";
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("amount", amount);

        OKHttpHelper.game(url, params, new SimpleCallback<String>() {
            @Override
            public void onUiSuccess(String ret) {
                callback.onUiSuccess(null);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 红包转积分
     * @param userId
     * @param amount
     * @param callback
     */
    public static void trans(String userId, String amount, SimpleCallback<Void> callback) {
        String url = GAME_SERVER + "/app/bonus2Jifen";
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("bonus", amount);

        OKHttpHelper.game(url, params, new SimpleCallback<String>() {
            @Override
            public void onUiSuccess(String ret) {
                callback.onUiSuccess(null);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 绑定银行卡
     * @param userId
     * @param bank_no
     * @param bank_name
     * @param real_name
     * @param callback
     */
    public static void bindBankCard(String userId, String bank_no, String bank_name, String real_name, SimpleCallback<Void> callback) {
        String url = GAME_SERVER + "/app/bindBankCard";
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("card", bank_no);
        params.put("bank", bank_name);
        params.put("rname", real_name);

        OKHttpHelper.game(url, params, new SimpleCallback<String>() {
            @Override
            public void onUiSuccess(String ret) {
                callback.onUiSuccess(null);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 获取积分历史
     * @param userId
     * @param callback
     */
    public static void jifenHistory(String userId, int type, SimpleCallback<ArrayList<Jifen>> callback) {
        String url = GAME_SERVER + "/app/jifenHistory";
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("type", String.valueOf(type));

        OKHttpHelper.game(url, params, new SimpleCallback<ArrayList<Jifen>>() {

            @Override
            public void onUiSuccess(ArrayList<Jifen> jifens) {
                callback.onUiSuccess(jifens);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }

    /**
     * 获取游戏对局详情
     * @param id
     * @param callback
     */
    public static void gameInfo(int id, SimpleCallback<GameInfo> callback) {
        String url = GAME_SERVER + "/app/game";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        OKHttpHelper.game(url, params, new SimpleCallback<GameInfo>() {


            @Override
            public void onUiSuccess(GameInfo gameInfo) {
                callback.onUiSuccess(gameInfo);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                callback.onUiFailure(code, msg);
            }
        });
    }


}
