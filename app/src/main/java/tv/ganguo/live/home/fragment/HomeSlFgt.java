package tv.ganguo.live.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.zaaach.citypicker.CityPickerActivity;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.utils.LCIMConstants;
import tv.ganguo.live.AddFriendsActivity;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiFragment;
import tv.ganguo.live.home.adapter.MyFriendsAdapter;
import tv.ganguo.live.home.event.FirstEvent;
import tv.ganguo.live.home.model.FriendModle;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.lean.Chat;
import tv.ganguo.live.utils.Api;


/**
 * 速聊  通讯录
 * Created by hxj on 2017/12/7.
 */

public class HomeSlFgt extends BaseSiSiFragment {
    private static final String TAG = "HomeSlFgt";
    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.tv_top)
    RelativeLayout mTvTop;
    @Bind(R.id.top_addfragment)
    ImageView mAdd;
    @Bind(R.id.suliao_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.suliao_address)
    LinearLayout mAddress;
    @Bind(R.id.suliao_address_name)
    TextView cityName;
    @Bind(R.id.suliao_gender)
    LinearLayout mGender;
    @Bind(R.id.suliao_shuaxin)
    LinearLayout mShuaXin;
    @Bind(R.id.suliao_xingbie)
    TextView mXinBie;
    int flag = 0;
    private PopupWindow mPopupWindow;
    private List<FriendModle> mList;
    private MyFriendsAdapter adapter;
    private int postin;

    @OnClick(R.id.suliao_address)
    public void setCity(View view) {
        openActivityForResult(CityPickerActivity.class, 10010);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_sl;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        getData();
    }

    private void initListener() {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(AddFriendsActivity.class);
            }
        });
        mGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopuwinond();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        adapter.setItemClick(new MyFriendsAdapter.MyFriendItemClick() {
            @Override
            public void itemClick(final int position) {
                LCChatKit.getInstance().open(mList.get(position).getUid(), new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                        if (null == e) {
                            postin = position;
                            Bundle bundle = new Bundle();
                            bundle.putString(LCIMConstants.PEER_ID, mList.get(position).getFriends_id());
                            openActivity(Chat.class, bundle);
                        } else {
                            toast(e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        if (mList != null) {
            mList.clear();
        } else {
            mList = new ArrayList<>();
        }
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyFriendsAdapter(getContext(), mList);
        mRv.setAdapter(adapter);
        if (flag == 1) {
            mTvTop.setVisibility(View.GONE);
        }
        EventBus.getDefault().register(this);
    }

    private void initPopuwinond() {
        mPopupWindow = new PopupWindow(getContext());


        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_gender, null);
        TextView mBuXian = (TextView) inflate.findViewById(R.id.gender_buxian);
        TextView mNan = (TextView) inflate.findViewById(R.id.gender_nan);
        TextView mNv = (TextView) inflate.findViewById(R.id.gender_nv);
        mBuXian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXinBie.setText("不限");
                mPopupWindow.dismiss();
            }
        });
        mNan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXinBie.setText("男");
                mPopupWindow.dismiss();
            }
        });
        mNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXinBie.setText("女");
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setContentView(inflate);
        mPopupWindow.setWidth((int) (100 * getResources().getDisplayMetrics().density));
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAsDropDown(mGender);
    }

    public void setFlag(int i) {
        flag = i;
    }

    public void getData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", SharePrefsUtils.get(getContext(), "user", "token", ""));
        Api.getFriendsList(getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: " + data);
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                mList.clear();
                JSONArray data1 = data.getJSONArray("data");
                if (data1 != null) {
                    mList.addAll(JSON.parseArray(data1.toString(), FriendModle.class));
                } else {
                    toast(data.getString("descrp"));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void requestFailure(int code, String msg) {
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                toast(msg);
            }
        });
    }

/*
    public int getResourceId(String name) {
        try {
            //根据资源的ID的变量名获得Field的对象，使用反射机制实现的
            Field field = R.drawable.class.getField(name);
            //取得并返回资源的id的字段（静态变量）的值，使用反射机制
            return Integer.parseInt(field.get(null).toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 10010) {
                cityName.setText(data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY));
                getData();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FirstEvent event) {
        if (event.getA() != 0) {
            getData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
