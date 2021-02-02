package cn.wildfire.chat.app.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.imchat.ezn.R;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.app.model.Bank;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;
import cn.wildfire.chat.kit.third.utils.ImageUtils;
import cn.wildfirechat.message.ImageMessageContent;
import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.core.MessageDirection;
import cn.wildfirechat.message.core.MessageStatus;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.remote.ChatManager;
import cn.wildfirechat.remote.SendMessageCallback;

public class ChargeActivity extends WfcBaseActivity {

    private String bank;
    private int amount;
    private String proof;

    private BankListAdapter bankListAdapter;
    private SharedPreferences sp;

    @BindView(R.id.banks)
    ListView banksListView;
    @BindView(R.id.proof)
    ImageView proofImageView;
    @BindView(R.id.amount)
    EditText amountEditText;

    @Override
    protected int contentLayout() {
        init();
        return R.layout.activity_charge;
    }

    /**
     * 初始化数据
     */
    protected void init() {
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        GameService.getInstance().getChargeBanks(new SimpleCallback<ArrayList<Bank>>() {
            @Override
            public void onUiSuccess(ArrayList<Bank> banks) {
                bankListAdapter = new BankListAdapter(banks, getApplicationContext());
                banksListView.setAdapter(bankListAdapter);
                banksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        bankListAdapter.setCurrent(i);
                        bankListAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.proof)
    void proofClick() {
        Intent intent = ImagePicker.picker().showCamera(true).enableMultiMode(1).buildPickIntent(this);
        startActivityForResult(intent, 100);
    }

    @OnClick(R.id.submit)
    void submit() {
        String amount = amountEditText.getText().toString().trim();
        if (amount.length() < 1 || Integer.valueOf(amount) <= 0) {
            Toast.makeText(getApplicationContext(), "金额错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (proof == null || proof.length() < 1) {
            Toast.makeText(getApplicationContext(), "必须上传支付截图", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bankListAdapter.getSelected() == null) {
            Toast.makeText(getApplicationContext(), "必须选择卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        GameService.getInstance().charge(sp.getString("userId", "-"), amount, bankListAdapter.getSelected(), proof, new SimpleCallback<Void>() {
            @Override
            public void onUiSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "充值提交成功", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.online_charge)
    void onlineCharge() {
        String userId = sp.getString("userId", "-1");
        if (userId.equals("-1")) {
            return;
        }
        String url = "http://labi168.com/pay.php?uid=" + userId;
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                File imageFileSource = new File(image.path);
                Uri uri = Uri.fromFile(imageFileSource);
                ImageMessageContent imgContent = new ImageMessageContent(uri.getEncodedPath());
                Message msg = new Message();
                String userId = sp.getString("userId", "-");
                Conversation conversation = new Conversation(Conversation.ConversationType.Single, userId);
                msg.conversation = conversation;
                msg.content = imgContent;
                msg.sender = userId;
                msg.direction = MessageDirection.Send;
                msg.status = MessageStatus.Sending;
                msg.serverTime = System.currentTimeMillis();
                ChatManager.Instance().sendMessage(msg, 30, new SendMessageCallback() {
                    @Override
                    public void onSuccess(long messageUid, long timestamp) {

                    }

                    @Override
                    public void onFail(int errorCode) {

                    }

                    @Override
                    public void onPrepare(long messageId, long savedTime) {

                    }

                    @Override
                    public void onMediaUpload(String remoteUrl) {
                        Picasso.with(getApplicationContext()).load(remoteUrl).placeholder(R.mipmap.placeholder).into(proofImageView);
                        proof = remoteUrl;
                    }
                });

            }
        }
    }

}
