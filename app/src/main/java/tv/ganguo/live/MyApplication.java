package tv.ganguo.live;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.tencent.bugly.crashreport.CrashReport;

import cn.leancloud.chatkit.LCChatKit;
import tv.ganguo.live.lean.CustomUserProvider;
import tv.ganguo.live.utils.PermissionUtils;

/**
 * Created by Administrator on 2016/9/1.
 * Author: XuDeLong
 */
public class MyApplication extends MultiDexApplication {
    private LocationService locationService;
    private final String APP_ID = "eRnl9UaPkhwjmvJPbrW9O0UY-gzGzoHsz";
    private final String APP_KEY = "GbUiKl8aRUTk0TooXR5WDQkG";
    private static MyApplication application;
    private String token;

    String balance;
    private static Context mContext;

    public static Context get5() {
        return mContext;
    }

    public static MyApplication get() {
        return application;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        QupaiHttpFinal.getInstance().initOkHttpFinal();
        mContext = getApplicationContext();
        application = this;
        // MultiDex.install(this);
        //AVOSCloud.initialize(this,APP_ID,APP_KEY);
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        //AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));

        initLocation();
//        SDKInitializer.initialize(getApplicationContext());
        CrashReport.initCrashReport(getApplicationContext(), "1ada2b4c05", true);
        initlibs();
    }

    private void initLocation() {
        locationService = new LocationService(getApplicationContext());
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
    }



    private void initlibs() {
        System.loadLibrary("QuCore-ThirdParty");
        System.loadLibrary("QuCore");
    }

    public String getToken() {
        if (token != null) {
            return token;
        } else {
            String o = (String) SharePrefsUtils.get(this, "user", "token", "");
            if (TextUtils.isEmpty(o)) {
                return "";
            } else {
                token = o;
                return o;
            }
        }
    }

    public LocationService getLocationService() {
        return locationService;
    }



}
