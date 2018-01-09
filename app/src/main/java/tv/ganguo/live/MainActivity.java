package tv.ganguo.live;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.smart.androidutils.utils.StringUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.cache.LCIMConversationItemCache;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import tv.ganguo.live.core.BaseSiSiActivity;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.living.PublishActivity;
import tv.ganguo.live.login.LoginActivity;
import tv.ganguo.live.own.PushActivity;
import tv.ganguo.live.shrotVideo.SnapRecorderSetting;
import tv.ganguo.live.utils.Api;
import tv.ganguo.live.utils.MyLogUtils;
import tv.ganguo.live.utils.PermissionUtils;

public class MainActivity extends BaseSiSiActivity implements View.OnClickListener {

    @Bind(R.id.ll_shou_ye)
    LinearLayout llShouYe;
    @Bind(R.id.img_msg)
    ImageView img_msg;
    @Bind(R.id.ll_mine)
    LinearLayout ll_mine;
    @Bind(R.id.img_shou_ye)
    ImageView img_shou_ye;
    @Bind(R.id.tv_shou_ye)
    TextView tv_shou_ye;
    @Bind(R.id.img_attention)
    ImageView img_attention;
    @Bind(R.id.tv_attention)
    TextView tv_attention;
    @Bind(R.id.tv_msg)
    TextView tv_msg;
    @Bind(R.id.img_mine)
    ImageView img_mine;
    @Bind(R.id.tv_mine)
    TextView tv_mine;
    @Bind(R.id.tv_red_attention)
    TextView tv_red_attention;
    @Bind(R.id.tv_red_msg)
    TextView tv_red_msg;
    @Bind(R.id.ll_red)
    LinearLayout red;
    @Bind(R.id.rl_attention)
    LinearLayout attention;
    private Animation mAnimation;
    private Animation mAnimationRequire;
    private Animation mAnimationSecond;
    private Animation mAnimationSkill;
    private Animation mAnimationSecondSkill;
    private View mV;
    private ImageView mIvCancel;
    private AnimationSet mSet;
    private AnimationSet mSet1;
    private Dialog mMainPublish;
    private LinearLayout mLlSkill;
    private LinearLayout mLlRequre;
    private AMapLocationListener mListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location && location.getErrorCode() == 0) {
                SharePrefsUtils.put(MainActivity.this, "user", "city", location.getCity());
                SharePrefsUtils.put(MainActivity.this, "user", "longitude", location.getLongitude());
                SharePrefsUtils.put(MainActivity.this, "user", "latitude", location.getLatitude());
                if (null != locationService) {
                    locationService.stop(); //停止定位服务
                }
            }
        }
    };

    private Fragment[] mTabFragment = new Fragment[4];
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private LocationService locationService;

    @OnClick(R.id.image_start_living)
    public void startLiving(View view) {
//
        showMainPublish();
    }

    /**
     * 启动 发帖和 发日记.
     */
    public void showMainPublish() {
        if (mMainPublish == null) {
            initPublishPop();
        }
        mLlSkill.setAnimation(mSet1);
        mLlRequre.setAnimation(mSet);
        mSet.startNow();
        mSet1.startNow();
        mMainPublish.show();
        mIvCancel.startAnimation(mAnimation);
    }

    /**
     * 初始化发布popupwindow.
     */
    private void initPublishPop() {
        mAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_publish);
        mAnimationRequire = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_require);
        mAnimationSecond = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_second);
        mAnimationSkill = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_skill);
        mAnimationSecondSkill = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_second_skill);

        mMainPublish = new Dialog(MainActivity.this, R.style.commondialogstyle);
        mV = View.inflate(MainActivity.this, R.layout.mainactivity_publish, null);
        mLlSkill = (LinearLayout) mV.findViewById(R.id.main_publish_skill);
        mLlRequre = (LinearLayout) mV.findViewById(R.id.main_publish_requirement);
        mIvCancel = (ImageView) mV.findViewById(R.id.main_publish_cancer);
        mV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mMainPublish.dismiss();
            }
        });
        mAnimation.setFillAfter(true);
        mSet = new AnimationSet(true);
        mSet.addAnimation(mAnimationRequire);
        mSet.addAnimation(mAnimationSecond);
        mSet.setFillAfter(true);
        mSet1 = new AnimationSet(true);
        mSet1.addAnimation(mAnimationSkill);
        mSet1.addAnimation(mAnimationSecondSkill);
        mSet1.setFillAfter(true);

        mIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPublish.dismiss();
            }
        });
        // 发布小视频

        mLlSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SnapRecorderSetting.class);
                mMainPublish.dismiss();
            }
        });
        // 发布直播

        mLlRequre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(PublishActivity.class);
                mMainPublish.dismiss();
            }
        });


        mMainPublish.setContentView(mV);
        WindowManager.LayoutParams lp = mMainPublish.getWindow().getAttributes();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.height = (int) (dm.density * 200);
        mMainPublish.getWindow().setAttributes(lp);
        Window window = mMainPublish.getWindow();
        // window.setWindowAnimations(R.style.pop_style);
        window.setGravity(Gravity.BOTTOM);
        mMainPublish.setCanceledOnTouchOutside(true);
        mMainPublish.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words", "Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    MyLogUtils.d("saved", "success!");
                }
            }
        });
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //进入到这里代表没有权限.
            PermissionUtils.requestPermission(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION});
        } else {
            if (null == locationService) {
                locationService = MyApplication.get().getLocationService();
                //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
                locationService.registerListener(mListener);
                //注册监听
                locationService.setLocationOption(locationService.getDefaultLocationClientOption());
            }
            locationService.start();
        }

        initPublishPop();
        if ("".equals(SharePrefsUtils.get(this, "user", "token", "")) || "".equals(SharePrefsUtils.get(this, "user", "userId", ""))) {
            // openActivity(LoginActivity.class);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;

        }
        PushService.setDefaultPushCallback(this, PushActivity.class);

        setDoubleBack(true);
        mFragmentManager = getSupportFragmentManager();
        mTabFragment[0] = mFragmentManager.findFragmentById(R.id.fragment_home);
        mTabFragment[1] = mFragmentManager.findFragmentById(R.id.fragment_own);
        mTabFragment[2] = mFragmentManager.findFragmentById(R.id.fragment_attention);
        mTabFragment[3] = mFragmentManager.findFragmentById(R.id.fragment_msg);
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.hide(mTabFragment[0]).hide(mTabFragment[1])
                .hide(mTabFragment[2])
                .hide(mTabFragment[3]);
        mTransaction.show(mTabFragment[0]).commit();
        llShouYe.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
        img_shou_ye.setSelected(true);
        tv_shou_ye.setSelected(true);
        red.setOnClickListener(this);
        attention.setOnClickListener(this);
        LCChatKit.getInstance().open((String) SharePrefsUtils.get(this, "user", "userId", ""), new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {

            }
        });
        JSONObject params = new JSONObject();
        try {
            String versionCode = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            params.put("ver_num", versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Api.checkUpdate(this, params, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                JSONObject info = data.getJSONObject("data");
                if (StringUtils.isNotEmpty(info.getString("package"))) {
                    checkUpgrade(info.getString("package"), info.getString("description"));
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                //toast(msg);
            }
        });
        getUnread();
        getPhoneList();
    }

    public void onEvent(LCIMIMTypeMessageEvent event) {
        getUnread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUnread();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    AlertDialog tipsAlertDialog;

    private void checkUpgrade(final String downloadUrl, String mes) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        tipsAlertDialog = builder.setTitle("提示")
                .setMessage(mes)
                .setNegativeButton("再等一下", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tipsAlertDialog.isShowing()) {
                            tipsAlertDialog.dismiss();
                        }
                    }
                })
                .setPositiveButton("更新下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri uri = Uri.parse(downloadUrl);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .create();
        tipsAlertDialog.show();
        tipsAlertDialog.setCancelable(false);
        tipsAlertDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }


    private void getUnread() {
        int num = 0;
        List<String> convIdList = LCIMConversationItemCache.getInstance().getSortedConversationList();
        for (String id : convIdList) {
            AVIMConversation conversation = LCChatKit.getInstance().getClient().getConversation(id);
            if (conversation.getMembers().size() != 2)
                continue;
            num += LCIMConversationItemCache.getInstance().getUnreadCount(conversation.getConversationId());
//
        }
        tv_red_msg.setVisibility(View.GONE);
        if (num > 0) {
            tv_red_msg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_shou_ye:
                clearColor();
                img_shou_ye.setSelected(true);
                tv_shou_ye.setSelected(true);
                mTransaction = mFragmentManager.beginTransaction();
                mTransaction.hide(mTabFragment[0]).hide(mTabFragment[1])
                        .hide(mTabFragment[2])
                        .hide(mTabFragment[3]);
                mTransaction.show(mTabFragment[0]).commit();
                break;
            case R.id.rl_attention://关注
                clearColor();
                img_attention.setSelected(true);
                tv_attention.setSelected(true);
                mTransaction = mFragmentManager.beginTransaction();
                mTransaction.hide(mTabFragment[0]).hide(mTabFragment[1]).hide(mTabFragment[2]).hide(mTabFragment[3]);
                mTransaction.show(mTabFragment[3]).commit();
                break;
            case R.id.ll_red://消息
                clearColor();
                img_msg.setSelected(true);
                tv_msg.setSelected(true);
                mTransaction = mFragmentManager.beginTransaction();
                mTransaction.hide(mTabFragment[0]).hide(mTabFragment[1])
                        .hide(mTabFragment[3]);
                mTransaction.show(mTabFragment[2]).commit();
                break;
            case R.id.ll_mine:
                clearColor();
                img_mine.setSelected(true);
                tv_mine.setSelected(true);
                mTransaction = mFragmentManager.beginTransaction();
                mTransaction.hide(mTabFragment[0]).hide(mTabFragment[1])
                        .hide(mTabFragment[2])
                        .hide(mTabFragment[3]);
                mTransaction.show(mTabFragment[1]).commit();
                break;
        }
    }

    private void clearColor() {
        img_shou_ye.setSelected(false);
        tv_shou_ye.setSelected(false);
        img_mine.setSelected(false);
        tv_mine.setSelected(false);
        img_attention.setSelected(false);
        tv_attention.setSelected(false);
        img_msg.setSelected(false);
        tv_msg.setSelected(false);
    }

    public void getPhoneList() {
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //向下移动光标
        while (cursor.moveToNext()) {
            //取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String contact = cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);

            while (phone.moveToNext()) {
                String PhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //格式化手机号
                PhoneNumber = PhoneNumber.replace("-", "");
            }
        }
    }
//    壬戌之秋，七月既望，苏子与客泛舟游于赤壁之下。清风徐来，
//    水波不兴。举酒属客，诵明月之诗，歌窈窕之章。少焉，月出于东山之上，
//    徘徊于斗牛之间。白露横江，水光接天。纵一苇之所如，凌万顷之茫然。
//    浩浩乎如冯虚御风，而不知其所止；飘飘乎如遗世独立，羽化而登仙。
//    于是饮酒乐甚，扣舷而歌之。歌曰：“桂棹兮兰桨，击空明兮溯流光。
//    渺渺兮予怀，望美人兮天一方。”客有吹洞箫者，倚歌而和之。其声呜呜然，
//    如怨如慕，如泣如诉，余音袅袅，不绝如缕。舞幽壑之潜蛟，泣孤舟之嫠妇。
//    苏子愀然，正襟危坐而问客曰：“何为其然也？”客曰：“月明星稀，乌鹊南飞，
//    此非曹孟德之诗乎？西望夏口，东望武昌，山川相缪，郁乎苍苍，此非孟德之困于周郎者乎？方其破荆州，
//    下江陵，顺流而东也，舳舻千里，旌旗蔽空，酾酒临江，横槊赋诗，固一世之雄也，而今安在哉？
//    况吾与子渔樵于江渚之上，侣鱼虾而友麋鹿，驾一叶之扁舟，举匏樽以相属。寄蜉蝣于天地，
//    渺沧海之一粟。哀吾生之须臾，羡长江之无穷。挟飞仙以遨游，抱明月而长终。知不可乎骤得，托遗响于悲风。”
//    苏子曰：“客亦知夫水与月乎？逝者如斯，而未尝往也；盈虚者如彼，而卒莫消长也。
//    盖将自其变者而观之，而天地曾不能一瞬；自其不变者而观之，则物与我皆无尽也，
//    而又何羡乎!且夫天地之间，物各有主,苟非吾之所有，虽一毫而莫取。惟江上之清风，
//    与山间之明月，耳得之而为声，目遇之而成色，取之无禁，用之不竭，是造物者之无尽藏也，而吾与子之所共适。”
}
