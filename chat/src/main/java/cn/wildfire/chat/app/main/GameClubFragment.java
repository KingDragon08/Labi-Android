package cn.wildfire.chat.app.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.app.model.Ads;
import cn.wildfire.chat.app.model.Game;
import cn.wildfire.chat.kit.net.SimpleCallback;
import com.imchat.ezn.R;

public class GameClubFragment extends Fragment {
    @BindView(R.id.cycle_view)
    CycleViewPager mCycleViewPager;
    @BindView(R.id.game_list)
    ListView gameListView;
    List<Ads> adsList = new ArrayList<>();
    GameListAdapter gameListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_club, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    /**
     * 轮播图点击监听
     */
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener =
        new CycleViewPager.ImageCycleViewListener() {

            @Override
            public void onImageClick(Ads info, int position, View imageView) {
                if (mCycleViewPager.isCycle()) {
                    position = position - 1;
                }
//                Toast.makeText(getContext(), info.getTitle() +
//                        "选择了--" + position, Toast.LENGTH_LONG).show();
            }
        };

    /**
     * 初始化
     */
    public void init() {
        initAds();
        initGames();
    }

    /**
     * 初始化广告
     */
    public void initAds() {
        GameService.getInstance().getAds(new SimpleCallback<ArrayList<Ads>>() {
            @Override
            public void onUiSuccess(ArrayList<Ads> ads) {
                for (Ads ad: ads) {
                    adsList.add(new Ads("", ad.getImg()));
                }

                mCycleViewPager.setIndicators(R.mipmap.ad_select, R.mipmap.ad_unselect);
                //设置轮播间隔时间
                mCycleViewPager.setDelay(5000);
                mCycleViewPager.setData(adsList, mAdCycleViewListener);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 初始化游戏列表
     */
    public void initGames() {
        GameService.getInstance().getGames(new SimpleCallback<ArrayList<Game>>() {
            @Override
            public void onUiSuccess(ArrayList<Game> games) {
                gameListAdapter = new GameListAdapter(games, getContext());
                gameListView.setAdapter(gameListAdapter);
            }

            @Override
            public void onUiFailure(int code, String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static View getImageView(Context context, String url) {
        RelativeLayout rl = new RelativeLayout(context);
        //添加一个ImageView，并加载图片
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(layoutParams);
        //使用Picasso来加载图片
        Picasso.with(context).load(url).placeholder(R.mipmap.placeholder).into(imageView);
        rl.addView(imageView);
        return rl;
    }
}
