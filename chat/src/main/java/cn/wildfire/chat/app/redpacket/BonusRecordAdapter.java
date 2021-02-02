package cn.wildfire.chat.app.redpacket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import cn.wildfire.chat.app.redpacket.model.BonusRecord;
import cn.wildfire.chat.kit.GlideApp;
import cn.wildfire.chat.kit.user.UserViewModel;
import com.imchat.ezn.R;

public class BonusRecordAdapter extends RecyclerView.Adapter<BonusRecordViewHolder> {
    private Fragment fragment;

    private List<BonusRecord> records;

    private UserViewModel userViewModel;

    public BonusRecordAdapter(Fragment fragment) {
        this.fragment = fragment;
        userViewModel = ViewModelProviders.of(fragment).get(UserViewModel.class);
    }

    @NonNull
    @Override
    public BonusRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BonusRecordViewHolder(LayoutInflater.from(fragment.getActivity()).inflate(R.layout.rp_details_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BonusRecordViewHolder holder, int position) {
        BonusRecord record = records.get(position);
        GlideApp.with(fragment).load(record.portrait).error(R.mipmap.default_header)
                .into(holder.ivAvatar);
        holder.tvName.setText(userViewModel.getUserInfo(record.userId, false).displayName);
        holder.tvMoney.setText(RedPacketUtils.formatPercent(record.money));
        holder.tvTime.setText(record.createdAt);
        holder.tvBest.setVisibility(record.luckyKing == 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }

    public void setRecords(List<BonusRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }
}
