package cn.wildfire.chat.app.redpacket;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.imchat.ezn.R;

public class BonusRecordViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_item_avatar_icon)
    public ImageView ivAvatar;
    @BindView(R.id.tv_money_to_user)
    public TextView tvName;
    @BindView(R.id.tv_item_money_amount)
    public TextView tvMoney;
    @BindView(R.id.tv_time)
    public TextView tvTime;
    @BindView(R.id.tv_best_icon)
    public TextView tvBest;


    public BonusRecordViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
