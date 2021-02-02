package cn.wildfire.chat.app.main;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import cn.wildfire.chat.app.model.Game;

import com.imchat.ezn.R;

public class GameItem extends LinearLayout {
    private LinearLayout linearLayout;
    private ImageView imageView;
    private TextView textView;
    private Game mGame;
    private Context mContext;

    public GameItem(Context context) {
        this(context, (AttributeSet) null);
        this.mContext = context;
    }

    public GameItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.game_item, this);
        linearLayout = (LinearLayout) findViewById(R.id.game_item_linear_layout);
        imageView = (ImageView) findViewById(R.id.game_item_image);
        textView = (TextView) findViewById(R.id.game_item_text);
        linearLayout.setOnClickListener(new MyOnClick());
    }

    public void setData(Game game) {
        this.mGame = game;
        textView.setVisibility(View.GONE);
        //textView.setText(game.getName());
    }

    public void setContext(Context context) {
        this.mContext = context;
        Picasso.with(context).load((mGame.getImg())).placeholder(R.mipmap.logo).into(imageView);
    }

    private class MyOnClick implements OnClickListener {

        @Override
        public void onClick(View v) {
		    Intent intent = new Intent(mContext, RoomActivity.class);
		    intent.putExtra("type", mGame.getType());
            mContext.startActivity(intent);
        }
    }
}
