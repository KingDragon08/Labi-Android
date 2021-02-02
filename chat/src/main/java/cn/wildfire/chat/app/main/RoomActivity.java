package cn.wildfire.chat.app.main;

import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.app.model.Room;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;
import com.imchat.ezn.R;

public class RoomActivity extends WfcBaseActivity {
    @BindView(R.id.room_list)
    ListView roomListView;

    private String type;
    private RoomListAdapter roomListAdapter;

    @Override
    protected int contentLayout() {
        return R.layout.activity_room;
    }

    @Override
    protected void afterViews() {
        super.afterViews();
        type = getIntent().getStringExtra("type");
        GameService.getInstance().getRooms(type, new SimpleCallback<ArrayList<Room>>() {
            @Override
            public void onUiSuccess(ArrayList<Room> rooms) {
                roomListAdapter = new RoomListAdapter(rooms, getApplicationContext(), RoomActivity.this);
                roomListView.setAdapter(roomListAdapter);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
