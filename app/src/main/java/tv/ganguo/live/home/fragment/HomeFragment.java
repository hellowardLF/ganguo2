package tv.ganguo.live.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.smart.androidutils.fragment.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import tv.ganguo.live.R;
import tv.ganguo.live.home.adapter.MainViewPagerAdapter;
import tv.ganguo.live.own.message.MessageActivity;
import tv.ganguo.live.search.SearchActivity;

;

/**
 * created by Jessie Pinkman
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private ArrayList<String> mTopTabTitle;
    private ArrayList<Fragment> mFragments;
    private MainViewPagerAdapter mAdapter;

    @OnClick(R.id.image_home_search)
    public void homeSearch(View view) {
        openActivity(SearchActivity.class);
    }

    @OnClick(R.id.image_home_message)
    public void homeMessage(View view) {
        //openActivity(MessageActivity.class);
//        openActivity(ConversationListActivity.class);
    }

    @OnClick(R.id.image_home_rank)
    public void imageHomeRank(View v) {
//        openActivity(ContributionAllActivity.class);
        openActivity(MessageActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        }
        mFragments.clear();
        if (mTopTabTitle == null) {
            mTopTabTitle = new ArrayList<>();
        }
        mTopTabTitle.clear();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mTopTabTitle.add("关注");
        mTopTabTitle.add("推荐");
        mTopTabTitle.add("速聊");
        mTopTabTitle.add("直播");
        mTopTabTitle.add("发现");
        mTopTabTitle.add("附近");
        for (int i = 0; i < mTopTabTitle.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        //发现
        HomeFollowFragment fragment = new HomeFollowFragment();
        //推荐
        HomeHotFragment fragment2 = HomeHotFragment.getInstance(0);
        //速聊。。通讯录
        HomeSlFgt fragment3 = new HomeSlFgt();
        //附近
        HomeFJFgt fragment4 = new HomeFJFgt();
        //小视频
        HomeLatestFragment fragment1 = HomeLatestFragment.getInstance(0);
        fragment3.setFlag(1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        mFragments.add(fragment1);
        mFragments.add(fragment);
        mFragments.add(fragment4);

        mAdapter = new MainViewPagerAdapter(getChildFragmentManager(), mFragments, mTopTabTitle);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(0);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_home;
    }
}
