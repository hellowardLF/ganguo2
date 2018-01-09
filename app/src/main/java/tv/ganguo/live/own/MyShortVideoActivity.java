package tv.ganguo.live.own;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.androidutils.utils.SharePrefsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiActivity;
import tv.ganguo.live.home.ShortVideoActivity;
import tv.ganguo.live.home.model.ShortVideoItem;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.own.adapter.MyShortListAdapter;
import tv.ganguo.live.shrotVideo.SnapRecorderSetting;
import tv.ganguo.live.utils.Api;

/**
 * Function：
 * Created by lijiefenf on 2018/1/3.
 */

public class MyShortVideoActivity extends BaseSiSiActivity {
    private static final String TAG = "MyShortVideoActivity";
    @Bind(R.id.myshortvideo_sw)
    SwipeRefreshLayout mSw;
    @Bind(R.id.myshortvideo_rv)
    RecyclerView mRv;
    @Bind(R.id.image_top_back)
    ImageView back;
    @Bind(R.id.top_add)
    ImageView topRight;
    @Bind(R.id.text_top_title)
    TextView mTitle;
    @Bind(R.id.myshortvideo_lin)
    LinearLayout mLin;
    @Bind(R.id.myshortvideo_go)
    TextView mGo;
    private StaggeredGridLayoutManager manager;
    private MyShortListAdapter adapter;
    private List<ShortVideoItem> list;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_shortvideo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initLintener();
    }

    private void initData() {
        getData();
    }

    private void initLintener() {
        mSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        adapter.setmItemClick(new MyShortListAdapter.ItemOnClick() {
            @Override
            public void itemClick(int postion) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",list.get(postion));
               openActivity(ShortVideoActivity.class,bundle);
            }
        });
        mGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SnapRecorderSetting.class);
                finish();
            }
        });
        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SnapRecorderSetting.class);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTitle.setText("小视频");
        topRight.setVisibility(View.VISIBLE);
        topRight.setImageResource(R.drawable.icon_pic);
        list = new ArrayList<>();
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        adapter = new MyShortListAdapter(list, this);
        mRv.setAdapter(adapter);

    }

    public void getData() {
        JSONObject jsonObject = new JSONObject();
        final String token = (String) SharePrefsUtils.get(this, "user", "token", "");
        jsonObject.put("token", token);
        Api.getShortVideoList(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if (mSw != null && mSw.isRefreshing()) {
                    mSw.setRefreshing(false);
                }
                Log.e(TAG, "requestSuccess: " + data.toString());
                JSONArray data1 = data.getJSONArray("data");
                if (data1 != null) {
                    mSw.setVisibility(View.VISIBLE);
                    mLin.setVisibility(View.GONE);
                    list.clear();
                    list.addAll(JSON.parseArray(data1.toString(), ShortVideoItem.class));
                    adapter.notifyDataSetChanged();
                } else {
                    mSw.setVisibility(View.GONE);
                    mLin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                if (mSw != null && mSw.isRefreshing()) {
                    mSw.setRefreshing(false);
                }
                toast(msg);
            }
        });
    }
}
