package cn.wildfire.chat.app.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.imchat.ezn.R;

import java.security.PrivateKey;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.app.model.Jifen;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;

public class JifenHistoryActivity extends WfcBaseActivity {

    private SharedPreferences sp;
    private String userId;
    private JifenHistoryAdpater jifenHistoryAdpater;

    @BindView(R.id.jifen_list)
    ListView jifenListView;
    @BindView(R.id.game)
    TextView gameTextView;
    @BindView(R.id.jifen)
    TextView jifenTextView;

    @Override
    protected int contentLayout() {
        init();
        return R.layout.activity_jifen_history;
    }

    /**
     * 初始化
     */
    protected void init() {
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "-");
        GameService.getInstance().jifenHistory(userId, 2, new SimpleCallback<ArrayList<Jifen>>() {
            @Override
            public void onUiSuccess(ArrayList<Jifen> jifens) {
                jifenHistoryAdpater = new JifenHistoryAdpater(jifens, getApplicationContext());
                jifenListView.setAdapter(jifenHistoryAdpater);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.game)
    void game() {
        gameTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
        jifenTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
        GameService.getInstance().jifenHistory(userId, 2, new SimpleCallback<ArrayList<Jifen>>() {
            @Override
            public void onUiSuccess(ArrayList<Jifen> jifens) {
                jifenHistoryAdpater.setData(jifens);
                jifenHistoryAdpater.notifyDataSetChanged();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.jifen)
    void jifen() {
        jifenTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
        gameTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
        GameService.getInstance().jifenHistory(userId, 1, new SimpleCallback<ArrayList<Jifen>>() {
            @Override
            public void onUiSuccess(ArrayList<Jifen> jifens) {
                jifenHistoryAdpater.setData(jifens);
                jifenHistoryAdpater.notifyDataSetChanged();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
