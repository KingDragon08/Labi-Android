package cn.wildfire.chat.app.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imchat.ezn.R;

import java.util.ArrayList;

import cn.wildfire.chat.app.model.GameInfoBet;

public class BetListAdapter extends BaseAdapter {

    private ArrayList<GameInfoBet> mList;
    private Context context;

    public BetListAdapter(ArrayList<GameInfoBet> mList, Context context) {
        this.mList = mList;
        this.context = context;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.bet_list_item, null);
        TextView game_bet = (TextView) view.findViewById(R.id.game_bet);
        game_bet.setText(String.valueOf(Float.valueOf(mList.get(i).getBet()) / 100));
        TextView game_bet_bonus = (TextView) view.findViewById(R.id.game_bet_bonus);
        game_bet_bonus.setText(String.valueOf(Float.valueOf(mList.get(i).getBonus()) / 100));
        TextView game_bet_type = (TextView) view.findViewById(R.id.game_bet_type);
        game_bet_type.setText(mList.get(i).getType());
        TextView game_bet_user_name = (TextView) view.findViewById(R.id.game_bet_user_name);
        game_bet_user_name.setText(mList.get(i).getUsername());
        TextView game_bet_win = (TextView) view.findViewById(R.id.game_bet_win);
        game_bet_win.setText(String.valueOf(Float.valueOf(mList.get(i).getWin()) / 100));
        return view;
    }
}
