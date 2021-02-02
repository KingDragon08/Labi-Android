package cn.wildfire.chat.app.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.imchat.ezn.R;

import java.util.List;

import cn.wildfire.chat.app.model.Room;

public class RoomListAdapter extends BaseAdapter {
    private List<Room> mList;
    private Context mContext;
    private int sumCount;
    private RoomActivity roomActivity;

    public RoomListAdapter(List<Room> List, Context context, RoomActivity roomActivity) {
        this.mList = List;
        this.mContext = context;
        this.roomActivity = roomActivity;
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
        RoomItem roomItem1;
        RoomItem roomItem2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RoomListAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new RoomListAdapter.ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.room_list, null);
            holder.roomItem1 = (RoomItem) convertView.findViewById(R.id.room_item1);
            if (position * 2 < mList.size()) {
                holder.roomItem1.setData((Room) getItem(position * 2));
                holder.roomItem1.setContext(mContext);
                holder.roomItem1.setActivity(roomActivity);
            }
            holder.roomItem2 = (RoomItem) convertView.findViewById(R.id.room_item2);
            if (position * 2 + 1 < mList.size()) {
                holder.roomItem2.setData((Room) getItem(position * 2 + 1));
                holder.roomItem2.setContext(mContext);
                holder.roomItem2.setActivity(roomActivity);
            }
            convertView.setTag(holder);
        } else {
            holder = (RoomListAdapter.ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
