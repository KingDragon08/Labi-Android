package cn.wildfire.chat.app.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cn.wildfire.chat.app.model.Game;
import com.imchat.ezn.R;

public class GameListAdapter extends BaseAdapter {
    private List<Game> mList;
    private Context mContext;
    private int sumCount;

    public GameListAdapter(List<Game> List, Context context) {
        this.mList = List;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        int count = mList.size();
        if (count % 2 == 0) {
            sumCount = count / 2;
        } else {
            sumCount = (int) Math.floor((double) count / 2) + 1;
        }
        return sumCount;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        GameItem gameItem1;
        GameItem gameItem2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.game_list, null);
            holder.gameItem1 = (GameItem) convertView.findViewById(R.id.game_item1);
            if (position * 2 < mList.size()) {
                holder.gameItem1.setData((Game) getItem(position * 2));
                holder.gameItem1.setContext(mContext);
            }
            holder.gameItem2 = (GameItem) convertView.findViewById(R.id.game_item2);
            if (position * 2 + 1 < mList.size()) {
                holder.gameItem2.setData((Game) getItem(position * 2 + 1));
                holder.gameItem2.setContext(mContext);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
