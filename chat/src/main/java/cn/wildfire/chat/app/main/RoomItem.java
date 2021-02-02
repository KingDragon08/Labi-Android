package cn.wildfire.chat.app.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imchat.ezn.R;
import com.squareup.picasso.Picasso;

import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.app.model.Room;
import cn.wildfire.chat.kit.conversation.ConversationActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;
import cn.wildfirechat.model.Conversation;

public class RoomItem extends LinearLayout {
    private LinearLayout linearLayout;
    private ImageView imageView;
    private TextView textView;
    private Room mRoom;
    private Context mContext;
    private RoomActivity activity;

    public RoomItem(Context context) {
        this(context, (AttributeSet) null);
        this.mContext = context;
    }

    public RoomItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.game_item, this);
        linearLayout = (LinearLayout) findViewById(R.id.game_item_linear_layout);
        imageView = (ImageView) findViewById(R.id.game_item_image);
        textView = (TextView) findViewById(R.id.game_item_text);
        linearLayout.setOnClickListener(new RoomItem.MyOnClick());
    }

    public void setData(Room room) {
        this.mRoom = room;
        textView.setText(room.getName());
    }

    public void setContext(Context context) {
        this.mContext = context;
        Picasso.with(context).load((mRoom.getAvatar())).placeholder(R.mipmap.logo).into(imageView);
    }

    public void setActivity(RoomActivity activity) {
        this.activity = activity;
    }

    private class MyOnClick implements OnClickListener {

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("请阅读群规")
                .setMessage(mRoom.getRules())
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 加入群组
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
                        String id = sharedPreferences.getString("id", null);
                        GameService.getInstance().joinRoom(mRoom.getSn(), id, new SimpleCallback<Void>() {
                            @Override
                            public void onUiSuccess(Void aVoid) {
                                Conversation conversation = new Conversation(Conversation.ConversationType.Group, mRoom.getSn());
                                Intent intent = new Intent(mContext, ConversationActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("conversation", conversation);
                                mContext.startActivity(intent);
                            }

                            @Override
                            public void onUiFailure(int code, String msg) {
                                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "不同意", Toast.LENGTH_LONG).show();
                    }
                });
            builder.show();
        }
    }
}
