package tv.ganguo.live.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.smart.androidutils.fragment.BaseFragment;

import java.util.Arrays;
import java.util.List;

import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import tv.ganguo.live.R;
import tv.ganguo.live.lean.ContactFragment;
import tv.ganguo.live.lean.ConversationListActivity;

;


public class HomeMsgFragment extends BaseFragment {
    @Override
    public int getLayoutResource() {
        return R.layout.activity_conversation_main;
    }
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    /**
     * 上一次点击 back 键的时间
     * 用于双击退出的判断
     */
    private static long lastBackTime = 0;

    /**
     * 当双击 back 键在此间隔内是直接触发 onBackPressed
     */
    private final int BACK_INTERVAL = 1000;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        String[] tabList = new String[]{"消息"};
        Fragment[] fragmentList = new Fragment[]{new LCIMConversationListFragment()};
        ConversationListActivity.TabFragmentAdapter adapter = new ConversationListActivity.TabFragmentAdapter(getFragmentManager(),
                Arrays.asList(fragmentList), Arrays.asList(tabList));
        viewPager.setAdapter(adapter);
    }


    private void initTabLayout() {
        String[] tabList = new String[]{"会话列表", "联系人"};
        final Fragment[] fragmentList = new Fragment[]{new LCIMConversationListFragment(), new ContactFragment()};

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabList.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList[i]));
        }

        ConversationListActivity.TabFragmentAdapter adapter = new ConversationListActivity.TabFragmentAdapter(getFragmentManager(),
                Arrays.asList(fragmentList), Arrays.asList(tabList));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
//          EventBus.getDefault().post(new ConversationFragmentUpdateEvent());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

//  @Override
//  public void onBackPressed() {
//    long currentTime = System.currentTimeMillis();
//    if (currentTime - lastBackTime < BACK_INTERVAL) {
//      super.onBackPressed();
//    } else {
//      ToastUtils.makeText(this, "双击 back 退出", ToastUtils.LENGTH_SHORT).show();
//    }
//    lastBackTime = currentTime;
//  }

    public class TabFragmentAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

//  private void gotoSquareConversation() {
//    List<LCChatKitUser> userList = CustomUserProvider.getInstance().getAllUsers();
//    List<String> idList = new ArrayList<>();
//    for (LCChatKitUser user : userList) {
//      idList.add(user.getUserId());
//    }
//    LCChatKit.getInstance().getClient().createConversation(
//      idList, getString(R.string.square), null, false, true, new AVIMConversationCreatedCallback() {
//        @Override
//        public void done(AVIMConversation avimConversation, AVIMException e) {
//          Intent intent = new Intent(ConversationListActivity.this, LCIMConversationActivity.class);
//          intent.putExtra(LCIMConstants.CONVERSATION_ID, avimConversation.getConversationId());
//          startActivity(intent);
//        }
//      });
//  }
}
