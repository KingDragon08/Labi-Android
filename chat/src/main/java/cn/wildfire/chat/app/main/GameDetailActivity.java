package cn.wildfire.chat.app.main;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.imchat.ezn.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.app.model.Game;
import cn.wildfire.chat.app.model.GameInfo;
import cn.wildfire.chat.app.model.GameInfoGame;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;

public class GameDetailActivity extends WfcBaseActivity {

    private String id;
    private BetListAdapter betListAdapter;

    @BindView(R.id.game_banker)
    TextView game_banker;
    @BindView(R.id.game_start_time)
    TextView game_start_time;
    @BindView(R.id.game_room_id)
    TextView game_room_id;
    @BindView(R.id.game_bonus_id)
    TextView game_bonus_id;
    @BindView(R.id.game_id)
    TextView game_id;
    @BindView(R.id.game_banker_bonus)
    TextView game_banker_bonus;
    @BindView(R.id.game_banker_win)
    TextView game_banker_win;
    @BindView(R.id.bet_list)
    ListView listView;


    @Override
    protected int contentLayout() {
        init();
        return R.layout.activity_game_detail;
    }

    public String formatTime(String timestamp) {
        Long t = Long.valueOf(timestamp) * 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(t));
    }

    protected void init() {
        id = getIntent().getStringExtra("id");
        GameService.getInstance().gameInfo(Integer.valueOf(id), new SimpleCallback<GameInfo>() {
            @Override
            public void onUiSuccess(GameInfo gameInfo) {
                GameInfoGame gameInfoGame = gameInfo.getGame();
                game_banker.setText(gameInfoGame.getBankerName());
                game_start_time.setText(formatTime(gameInfoGame.getCreated_time()));
                game_room_id.setText(gameInfoGame.getGroupId());
                game_bonus_id.setText(gameInfoGame.getBonusId());
                game_id.setText(String.valueOf(gameInfoGame.getId()));
                game_banker_bonus.setText(gameInfoGame.getBankerBonus());
                game_banker_win.setText(gameInfo.getGame().getBankerWin());
                betListAdapter = new BetListAdapter(gameInfo.getBets(), getApplicationContext());
                listView.setAdapter(betListAdapter);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
