package cn.wildfire.chat.app.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imchat.ezn.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.wildfire.chat.app.model.Jifen;

public class JifenHistoryAdpater extends BaseAdapter {
    private ArrayList<Jifen> mList;
    private Context context;

    public JifenHistoryAdpater(ArrayList<Jifen> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public void setData(ArrayList<Jifen> jifens) {
        this.mList = jifens;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public String formatTime(String timestamp) {
        Long t = Long.valueOf(timestamp) * 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(t));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.jifen_list_item, null);
        TextView type = (TextView) view.findViewById(R.id.jifen_type);
        type.setText(mList.get(i).getType());
        TextView time = (TextView) view.findViewById(R.id.jifen_time);
        time.setText(formatTime(mList.get(i).getCreated_time()));
        TextView amount = (TextView) view.findViewById(R.id.jifen_amount);
        amount.setText(String.valueOf(Float.valueOf(mList.get(i).getAmount()) / 100));
        return view;
    }
}
