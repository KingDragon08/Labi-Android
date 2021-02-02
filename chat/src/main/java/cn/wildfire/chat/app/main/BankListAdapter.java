package cn.wildfire.chat.app.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imchat.ezn.R;

import java.util.ArrayList;

import cn.wildfire.chat.app.model.Bank;

public class BankListAdapter extends BaseAdapter {
    private ArrayList<Bank> banks;
    private Context context;
    private int current = -1;

    @Override
    public int getCount() {
        return banks.size();
    }

    @Override
    public Object getItem(int i) {
        return banks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setCurrent(int i) {
        // 重复取消选中,toggle
        if (this.current == i) {
            this.current = -1;
        } else {
            this.current = i;
        }
    }

    /**
     * 获取选中的卡号
     * @return
     */
    public String getSelected() {
        if (this.current == -1) {
            return null;
        }
        return banks.get(current).getNumber();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.bank_list_item, null);
        TextView bankNo = (TextView) view.findViewById(R.id.bank_no);
        bankNo.setText(banks.get(i).getNumber());
        TextView bankUserName = (TextView) view.findViewById(R.id.bank_user_name);
        bankUserName.setText(banks.get(i).getName());
        TextView bankName = (TextView) view.findViewById(R.id.bank_name);
        bankName.setText(banks.get(i).getBank());

        if (i == current) {
            view.setBackgroundColor(context.getResources().getColor(R.color.hint));
        }
        return view;
    }

    public BankListAdapter(ArrayList<Bank> banks, Context context) {
        this.banks = banks;
        this.context = context;
    }
}
