package tv.ganguo.live.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.androidutils.fragment.BaseFragment;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.smart.androidutils.utils.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import tv.ganguo.live.R;
import tv.ganguo.live.home.intf.OnRecyclerViewItemClickListener;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.login.LoginActivity;
import tv.ganguo.live.own.UserMainActivity;
import tv.ganguo.live.own.follow.FollowItem;
import tv.ganguo.live.own.follow.FollowListAdapter;
import tv.ganguo.live.utils.Api;

;


public class HomeAttentionFragment extends BaseFragment implements OnRecyclerViewItemClickListener {
    @Override
    public int getLayoutResource() {
        return R.layout.activity_follow;
    }

    @Bind(R.id.swipeRefreshLayout_follow)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recyclerView_follow)
    RecyclerView mRecyclerView;
    @Bind(R.id.noDataLayout_follow)
    RelativeLayout mNodataView;
    private FollowListAdapter mFollowListAdapter;
    private ArrayList<FollowItem> mFollowItems;

    @Bind(R.id.text_top_title)
    TextView mTextTopTitle;
    @Bind(R.id.image_top_back)
    ImageView image_top_back;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//            if (mFollowItems != null) {
//                mFollowItems.clear();
//            }
            getData(0, 20, mSwipeRefreshLayout);
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);*/

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        image_top_back.setVisibility(View.GONE);
        if (mFollowItems == null) {
            mFollowItems = new ArrayList<>();
        }
        mFollowItems.clear();
       /* for (int i = 0; i < 20; i++) {
            FollowItem followItem = new FollowItem();
            mFollowItems.add(followItem);
        }*/
        mSwipeRefreshLayout.setRefreshing(true);
        getData(0, 20, mSwipeRefreshLayout);
        mTextTopTitle.setText("关注列表");
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mFollowListAdapter = new FollowListAdapter(getActivity(), mFollowItems);
        mFollowListAdapter.setOnRecyclerViewItemClickListener(this);
        mRecyclerView.setAdapter(mFollowListAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("MainViewPagerFragment", "--------onScrollStateChanged");
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastVisibleItemPosition == (totalItemCount - 1) && isSlidingToLast) {
                        //toast("没有更多数据了~~");
                        getData(totalItemCount, 20, null);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("MainViewPagerFragment", "--------onScrolled=dx=" + dx + "---dy=" + dy);
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }
        });
    }

    private void getData(int limit_begin, int limit_num, final SwipeRefreshLayout mSwipeRefreshLayout) {
        String token = (String) SharePrefsUtils.get(getActivity(), "user", "token", "");
        String userId = (String) SharePrefsUtils.get(getActivity(), "user", "userId", "");
        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(userId)) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("token", token);
            requestParams.put("id", userId);
            requestParams.put("limit_begin", limit_begin);
            requestParams.put("limit_num", limit_num);
            Api.getUserAttentionList(getActivity(), requestParams, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data) {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (mFollowItems != null) {
                            mFollowItems.clear();
                        }
                    }
                    JSONArray list = data.getJSONArray("data");
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        FollowItem followItem = new FollowItem();
                        followItem.setId(item.getString("id"));
                        followItem.setAttention(item.getString("attention_status"));
                        followItem.setAvatar(item.getString("avatar"));
                        followItem.setIs_truename(item.getString("is_truename"));
                        followItem.setSex(item.getString("sex"));
                        followItem.setChannel_status(item.getString("channel_status"));
                        followItem.setSignature(item.getString("signature"));
                        followItem.setUser_level(item.getString("user_level"));
                        followItem.setUser_nicename(item.getString("user_nicename"));
                        mFollowItems.add(followItem);
                    }
                    mFollowListAdapter.notifyDataSetChanged();

                }

                @Override
                public void requestFailure(int code, String msg) {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    //toast(msg);
                    //加载空数据图
                    if (mFollowItems.size() == 0) {
                        mNodataView.setVisibility(View.VISIBLE);
                    } else {
                        mNodataView.setVisibility(View.GONE);
                    }
                }
            });

        } else {
            openActivity(LoginActivity.class);
            getActivity().finish();
        }
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        Bundle data = new Bundle();
        String id = (String) view.getTag();
        data.putString("id", id);
        openActivity(UserMainActivity.class, data);
    }
}
