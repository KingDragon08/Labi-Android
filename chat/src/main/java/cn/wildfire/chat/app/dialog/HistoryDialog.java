package cn.wildfire.chat.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.imchat.ezn.R;

import cn.wildfire.chat.kit.net.SimpleCallback;

public class HistoryDialog {
    private Context context;
    private Activity activity;
    private Dialog dialog;

    public HistoryDialog(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void show(SimpleCallback<String> callback) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.history_game_dialog);

        EditText idEditText = (EditText) dialog.findViewById(R.id.game_id);
        TextView searchTextView = (TextView) dialog.findViewById(R.id.find);
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString().trim();
                dialog.dismiss();
                if (id != null && id.length() > 0 && Integer.valueOf(id) > 0) {
                    callback.onUiSuccess(id);
                }
            }
        });
        dialog.show();
    }
}
