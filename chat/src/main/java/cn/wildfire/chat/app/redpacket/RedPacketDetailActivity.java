package cn.wildfire.chat.app.redpacket;

import android.os.Build;

import cn.wildfire.chat.kit.WfcBaseActivity;
import com.imchat.ezn.R;

/**
 * 红包详情页
 * 1.发送人打开，未开启
 * 2.发送人打开，已开启
 * 3.接收人打开，未开启
 * 4.接收人打开，已开启
 */
public class RedPacketDetailActivity extends WfcBaseActivity {
    @Override
    protected void afterViews() {
        int color = getResources().getColor(R.color.red_packet_background);
        toolbar.setBackgroundColor(color);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable._top_back);
        getSupportActionBar().setTitle("");
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(color);
        }
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.containerFrameLayout, RedPacketDetailFragment.newInstance(getIntent().getStringExtra("redPacketId")))
                .commit();
    }

    @Override
    protected int contentLayout() {
        return R.layout.fragment_container_activity;
    }
}
