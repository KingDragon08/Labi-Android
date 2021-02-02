package cn.wildfire.chat.app.redpacket;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.NonNull;
import cn.wildfire.chat.app.redpacket.model.RedPacketDetail;

public class RedPacketUtils {
    /**
     * 把分格式化为元
     *
     * @param percent 分的钱数
     * @return x.xx格式的钱数
     */
    public static String formatPercent(int percent) {
        return formatPercent(percent, true);
    }

    /**
     * 把分格式化为元
     *
     * @param percent    分的钱数
     * @param withSymbol 要不要前面的金钱符号
     * @return x.xx格式的钱数
     */
    public static String formatPercent(int percent, boolean withSymbol) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String yuan = format.format(percent / 100f);
        if (withSymbol) return yuan;
        return yuan.substring(1);
    }

    /**
     * 把原来的时间格式化成符合业务逻辑的时间
     *
     * @param originDate 原来的时间
     * @return 格式化后的时间
     */
    public static String formatDate(String originDate) {
        return originDate;
    }

    /**
     * 从红包记录中取出最优先的状态
     *
     * @param details 红包记录
     * @return 优先状态
     */
    public static int getStatusFromRedPacketDetails(@NonNull List<RedPacketDetail> details) {
        for (RedPacketDetail detail : details) {
            if (detail.status == RedPacketDetail.STATUS_EXPIRED) {
                return RedPacketDetail.STATUS_EXPIRED;
            }
        }
        return RedPacketDetail.STATUS_OUT_OF_AMOUNT;
    }
}
