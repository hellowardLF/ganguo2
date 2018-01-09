package tv.ganguo.live.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.smart.androidutils.utils.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import tv.ganguo.live.MyApplication;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiFragment;
import tv.ganguo.live.home.adapter.LatestRecyclerListAdapter;
import tv.ganguo.live.home.intf.OnRecyclerViewItemClickListener;
import tv.ganguo.live.home.model.TopicItem;
import tv.ganguo.live.home.model.VideoItem;
import tv.ganguo.live.home.view.GridLayoutManagerTopic;
import tv.ganguo.live.intf.OnCustomClickListener;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.living.LivingActivity;
import tv.ganguo.live.login.LoginActivity;
import tv.ganguo.live.utils.Api;
import tv.ganguo.live.utils.DialogEnsureUtiles;

;

/**
 * 直播||小视频
 * Created by fengjh on 16/7/19.
 */
public class HomeLatestFragment extends BaseSiSiFragment implements OnRecyclerViewItemClickListener {

    private static final String TAG = "HomeLatestFragment";

    @Bind(R.id.swipeRefreshLayout_latest)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recyclerView_latest)
    RecyclerView mRecyclerView;
    @Bind(R.id.noDataLayout_latest_home)
    RelativeLayout mNodataView;
    TopicItem topic = new TopicItem();
    private int mPosition;
    private ArrayList<VideoItem> mVideoItems;
    private LatestRecyclerListAdapter mFollowRecyclerListAdapter;

    private ArrayList<TopicItem> mTopicItems;
    private boolean isMainActivity = false;

    public static HomeLatestFragment getInstance(int pos) {
        HomeLatestFragment fragment = new HomeLatestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void setMainActivity(boolean isshow) {
        isMainActivity = isshow;
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }, 2000);
            //  mVideoItems.clear();
            getData(topic.getName(), 0, 20, mSwipeRefreshLayout);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt("pos");
            setMainActivity(false);
        } else {
            setMainActivity(true);
        }
        if (mVideoItems == null) {
            mVideoItems = new ArrayList<>();
        }
        mVideoItems.clear();
        if (mTopicItems == null) {
            mTopicItems = new ArrayList<>();
        }
        mTopicItems.clear();
//        for (int i = 0; i < 10; i++) {
//            VideoItem videoItem = new VideoItem();
//            mVideoItems.add(videoItem);
//        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isMainActivity)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
//        LinearLayoutManager manager2=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
//        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
//        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mSwipeRefreshLayout.setRefreshing(true);
        mVideoItems.clear();
        mTopicItems.clear();
        getData(topic.getName(), 0, 20, mSwipeRefreshLayout);
        mFollowRecyclerListAdapter = new LatestRecyclerListAdapter(getActivity(), mVideoItems);
        mRecyclerView.setAdapter(mFollowRecyclerListAdapter);
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
                        // toast("没有更多数据了~~");
                        getData(topic.getName(), totalItemCount, 10, null);
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
        mFollowRecyclerListAdapter.setOnRecyclerViewItemClickListener(this);
    }


    private void getData(String term, int limit_begin, int limit_num, final SwipeRefreshLayout mSwipeRefreshLayout) {
        String token = (String) SharePrefsUtils.get(this.getContext(), "user", "token", "");
        String userId = (String) SharePrefsUtils.get(this.getContext(), "user", "userId", "");
        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(userId)) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("type", 2);
            requestParams.put("limit_begin", limit_begin);
            requestParams.put("limit_num", limit_num);
            requestParams.put("term_id", term);
            Api.getChannelList(this.getContext(), requestParams, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data) {
                    if (isActive) {
                        mVideoItems.clear();
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);

                        }
                        mNodataView.setVisibility(View.GONE);
                        JSONArray list = data.getJSONArray("info");
                        for (int i = 0; i < list.size(); i++) {
                            JSONObject item = list.getJSONObject(i);
                            VideoItem videoItem = new VideoItem();
                            videoItem.setRoom_id(item.getString("room_id"));
                            videoItem.setId(item.getString("id"));
                            videoItem.setUser_nicename(item.getString("user_nicename"));
                            videoItem.setAvatar(item.getString("avatar"));
                            videoItem.setChannel_creater(item.getString("channel_creater"));
                            videoItem.setChannel_location(item.getString("location"));
                            videoItem.setChannel_title(item.getString("channel_title"));
                            videoItem.setOnline_num(item.getString("online_num"));
                            videoItem.setSmeta(item.getString("smeta"));
                            videoItem.setChannel_status(item.getString("channel_status"));
                            videoItem.setPrice(item.getString("price"));
                            videoItem.setNeed_password(item.getString("need_password"));
                            mVideoItems.add(videoItem);
                        }
                        mFollowRecyclerListAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void requestFailure(int code, String msg) {
                    if (isActive) {
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }

                        //toast(msg);
                        //加载空数据图
                        if (mVideoItems.size() == 0) {
                            mNodataView.setVisibility(View.VISIBLE);
                        } else {
                            mNodataView.setVisibility(View.GONE);
                        }
                    }

                }
            });

        } else {
            openActivity(LoginActivity.class);
            this.getActivity().finish();
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_latest_home;
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        final VideoItem item = mVideoItems.get(position);
        Bundle data = new Bundle();
        data.putSerializable("videoItem", item);
        openActivity(LivingActivity.class, data);
    }
}
