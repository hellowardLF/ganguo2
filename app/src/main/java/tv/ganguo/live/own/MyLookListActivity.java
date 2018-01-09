package tv.ganguo.live.own;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import tv.ganguo.live.intf.OnCustomClickListener;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.own.adapter.MyLookListAdapter;
import tv.ganguo.live.own.adapter.MyShortListAdapter;
import tv.ganguo.live.own.modle.MyLookListModle;
import tv.ganguo.live.utils.Api;
import tv.ganguo.live.utils.DialogEnsureUtiles;

/**
 * Function：
 * Created by lijiefenf on 2018/1/3.
 */

public class MyLookListActivity extends BaseSiSiActivity {
    private static final String TAG = "MyLookListActivity";
    @Bind(R.id.mylooklist_sw)
    SwipeRefreshLayout mSw;
    @Bind(R.id.mylooklist_rv)
    RecyclerView mRv;
    @Bind(R.id.image_top_back)
    ImageView back;
    @Bind(R.id.top_text)
    TextView topRight;
    @Bind(R.id.text_top_title)
    TextView mTitle;
    private LinearLayoutManager manager;
    private MyLookListAdapter adapter;
    private List<MyLookListModle> list;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_mylooklist;
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
                String id = list.get(postion).getAnchor_id();
                Bundle data=new Bundle();
                data.putString("id", id);
                openActivity(UserMainActivity.class, data);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEnsureUtiles.showConfirm(MyLookListActivity.this, "是否要清除全部记录", new OnCustomClickListener() {
                    @Override
                    public void onClick(String value) {
                        clearAll();
                    }
                });
            }
        });
    }

    private void clearAll() {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("token",SharePrefsUtils.get(this,"user","token",""));
        Api.clearAll(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: "+data.toString() );
                toast(data.getString("descrp"));
                list.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
            }
        });
    }

    private void initView() {
        list=new ArrayList<>();
        mTitle.setText("观看记录");
        topRight.setVisibility(View.VISIBLE);
        topRight.setText("全部清除");
        manager=new LinearLayoutManager(this);
        mRv.setLayoutManager(manager);
        adapter=new MyLookListAdapter(list,this);
        mRv.setAdapter(adapter);

    }

    public void getData() {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("token", SharePrefsUtils.get(this,"user","token",""));
        Api.getLookList(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if (mSw!=null&&mSw.isRefreshing()){
                    mSw.setRefreshing(false);
                }
                Log.e(TAG, "requestSuccess: "+data.toString() );
                JSONArray data1 = data.getJSONArray("data");
                if (data1!=null){
                    list.clear();
                    list.addAll(JSON.parseArray(data1.toString(),MyLookListModle.class));
                    adapter.notifyDataSetChanged();
                }else {
                    toast(data.getString("descrp"));
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                if (mSw!=null&&mSw.isRefreshing()){
                    mSw.setRefreshing(false);
                }
                toast(msg);
            }
        });
    }
}
