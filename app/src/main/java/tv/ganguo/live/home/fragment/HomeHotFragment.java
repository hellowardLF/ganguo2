package tv.ganguo.live.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.smart.androidutils.utils.DensityUtils;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.smart.androidutils.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import tv.ganguo.live.MyApplication;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiFragment;
import tv.ganguo.live.home.adapter.BannerItemViewHolder;
import tv.ganguo.live.home.adapter.ButtonAdapter;
import tv.ganguo.live.home.adapter.HotRecyclerListAdapter;
import tv.ganguo.live.home.intf.OnRecyclerViewItemClickListener;
import tv.ganguo.live.home.model.BannerItem;
import tv.ganguo.live.home.model.ButtonItem;
import tv.ganguo.live.home.model.VideoItem;
import tv.ganguo.live.home.view.CustomSwipeRefreshLayout;
import tv.ganguo.live.intf.OnCustomClickListener;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.living.LivingActivity;
import tv.ganguo.live.login.LoginActivity;
import tv.ganguo.live.own.QiangDanActivity;
import tv.ganguo.live.own.WebviewActivity;
import tv.ganguo.live.utils.Api;
import tv.ganguo.live.utils.DialogEnsureUtiles;

;

/**
 * 推荐
 * Created by fengjh on 16/7/19.
 */
public class HomeHotFragment extends BaseSiSiFragment implements OnRecyclerViewItemClickListener {
    @Bind(R.id.swipeRefreshLayout_hot)
    CustomSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recyclerView_hot)
    RecyclerView mRecyclerView;
    @Bind(R.id.noDataLayout_hot_home)
    RelativeLayout mNodataView;
    @Bind(R.id.rv)
    RecyclerView mRv;

    @Bind(R.id.img_mid)
    ImageView mImgMid;

    private ArrayList<VideoItem> mVideoItems;
    private HotRecyclerListAdapter mFollowRecyclerListAdapter;

    private ArrayList<BannerItem> mBannerItems;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    private int bannerWidth;
    private int bannerHeight;

    public static HomeHotFragment getInstance(int pos) {
        HomeHotFragment fragment = new HomeHotFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fragment.setArguments(bundle);
        return fragment;
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
            // mBannerItems.clear();
            getBanner();
            getData(0, 20, mSwipeRefreshLayout);

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            mPosition = bundle.getInt("pos");
//        }
        if (mVideoItems == null) {
            mVideoItems = new ArrayList<>();
        }
        mVideoItems.clear();
//        for (int i = 0; i < 10; i++) {
//            VideoItem videoItem = new VideoItem();
//            mVideoItems.add(videoItem);
//        }
        if (mBannerItems == null) {
            mBannerItems = new ArrayList<>();
        }
        mBannerItems.clear();
//        for (int i = 0; i < images.length; i++) {
//            BannerItem bannerItem = new BannerItem();
//            bannerItem.setPic(images[i]);
//            mBannerItems.add(bannerItem);
//        }
        bannerWidth = DensityUtils.screenWidth(getActivity());
        bannerHeight = (bannerWidth * 270) / 720;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        convenientBanner.setLayoutParams(new LinearLayout.LayoutParams(bannerWidth, bannerHeight));
//        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                BannerItem item = mBannerItems.get(position);
//                switch (item.getType()) {
//                    case "1":
//                        Bundle data = new Bundle();
//                        VideoItem vItem = new VideoItem();
//                        vItem.setRoom_id(item.getJump());
//                        data.putSerializable("videoItem", vItem);
//                        openActivity(LivingActivity.class, data);
//                        break;
//                    case "2":
//                        Bundle data1 = new Bundle();
//                        data1.putString("title", item.getTitle());
//                        data1.putString("jump", item.getJump());
//                        openActivity(WebviewActivity.class, data1);
//                        break;
//                }
//            }
//        });
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerItemViewHolder();
            }
        }, mBannerItems)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
        convenientBanner.startTurning(3000);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);

//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setFocusableInTouchMode(false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return mSwipeRefreshLayout.isRefreshing();
                    }
                }
        );
        mSwipeRefreshLayout.setRefreshing(true);
        getBanner();
        mVideoItems.clear();
        getData(0, 20, mSwipeRefreshLayout);
        List<ButtonItem> mButtonItems = new ArrayList<>();

        int[] res = {R.drawable.fg1, R.drawable.fg2, R.drawable.fg3, R.drawable.fg4, R.drawable.fg5,
                R.drawable.fg6, R.drawable.fg7, R.drawable.fg8};

        String[] content = {"应急快贷", "热门贷款", "免费办卡",
                "全民商城", "同城直播", "热门视频", "流星电台", "干果游戏"};

        for (int i = 0; i < res.length; i++) {
            mButtonItems.add(new ButtonItem(res[i], content[i]));
        }

        mRv.setLayoutManager(new GridLayoutManager(null, 4));
        mRv.setAdapter(new ButtonAdapter(getActivity(), mButtonItems));
        mFollowRecyclerListAdapter = new HotRecyclerListAdapter(getActivity(), mVideoItems);
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
                        //toast("没有更多数据了~~");
                        getData(totalItemCount, 10, null);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("MainViewPagerFragment", "--------onScrolled=dx=" + dx + "---dy=" + dy);
                isSlidingToLast = dy > 0;
            }
        });
        mFollowRecyclerListAdapter.setOnRecyclerViewItemClickListener(this);
    }

    private void getData(int limit_begin, int limit_num, final SwipeRefreshLayout mSwipeRefreshLayout) {
        String token = (String) SharePrefsUtils.get(this.getContext(), "user", "token", "");
        String userId = (String) SharePrefsUtils.get(this.getContext(), "user", "userId", "");
        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(userId)) {
            JSONObject requestParams = new JSONObject();
//            requestParams.put("token",token);
            requestParams.put("type'", 1);
//            requestParams.put("term_id",3);
//            requestParams.put("type", 2);
//            requestParams.put("limit_begin", limit_begin);
//            requestParams.put("limit_num", limit_num);
            Api.getHomeList(this.getContext(), requestParams, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data) {
                    if (isActive) {
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mVideoItems.clear();
                        }
                        JSONArray list = data.getJSONArray("info");
                        for (int i = 0; i < list.size(); i++) {
                            mVideoItems.add(JSON.parseObject(list.getJSONObject(i).toString(), VideoItem.class));
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
                        toast(msg);
                    }
                }
            });

        } else {
            openActivity(LoginActivity.class);
            this.getActivity().finish();
        }
    }

    private void getBanner() {
        Api.getBanner(this.getContext(), new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                mBannerItems.clear();
                JSONArray bannerList = data.getJSONArray("data");
                for (int j = 0; j < bannerList.size(); j++) {
                    JSONObject temp = bannerList.getJSONObject(j);
                    BannerItem bannerItem = new BannerItem();
                    bannerItem.setPic(temp.getString("pic"));
                    bannerItem.setJump(temp.getString("jump"));
                    bannerItem.setTitle(temp.getString("title"));
                    bannerItem.setType(temp.getString("type"));
                    mBannerItems.add(bannerItem);
                }
                convenientBanner.notifyDataSetChanged();
            }

            @Override
            public void requestFailure(int code, String msg) {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                //toast(msg);
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_hot_home;
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        final VideoItem item = mVideoItems.get(position);
        if (StringUtils.isNotEmpty(item.getPrice()) && Integer.parseInt(item.getPrice()) > 0) {
            DialogEnsureUtiles.showConfirm(getActivity(), "该房间需要收费" + item.getPrice() + "/分钟", new OnCustomClickListener() {
                @Override
                public void onClick(String value) {
                    MyApplication app = (MyApplication) getActivity().getApplication();

                    if (app.getBalance() != null && item.getPrice() != null) {
                        if (Integer.parseInt(app.getBalance()) < Integer.parseInt(item.getPrice())) {
                            toast("余额不足，请充值");
                            return;
                        }
                    } else if (app.getBalance() == null || item.getPrice() == null) {
                        toast("余额不足，请充值");
                        return;
                    }

                    Bundle data = new Bundle();
                    data.putSerializable("videoItem", item);
                    openActivity(LivingActivity.class, data);
                }
            });

        } else if (StringUtils.isNotEmpty(item.getNeed_password()) && Integer.parseInt(item.getNeed_password()) == 1) {
            DialogEnsureUtiles.showInfo(getActivity(), new OnCustomClickListener() {
                @Override
                public void onClick(final String value) {
                    JSONObject params = new JSONObject();
                    params.put("room_id", item.getRoom_id());
                    params.put("token", (String) SharePrefsUtils.get(getContext(), "user", "token", ""));
                    params.put("room_password", value);
                    Api.checkPass(getContext(), params, new OnRequestDataListener() {
                        @Override
                        public void requestSuccess(int code, JSONObject data) {
                            Bundle data1 = new Bundle();
                            data1.putSerializable("videoItem", item);
                            data1.putString("password", value);
                            openActivity(LivingActivity.class, data1);
                        }

                        @Override
                        public void requestFailure(int code, String msg) {
                            toast(msg);
                        }
                    });
                }
            }, "", "该房间需要密码");
        } else {
            Bundle data = new Bundle();
            data.putSerializable("videoItem", item);
            openActivity(LivingActivity.class, data);
        }
    }


    @OnClick(R.id.img_mid)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_mid:
                openActivity(QiangDanActivity.class);
                break;
        }
    }
}
