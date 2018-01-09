package tv.ganguo.live.utils;

import android.app.Activity;
import android.os.Build;


/**
 * 请求权限工具
 * Created by hxj on 2017/10/18.
 */

public class PermissionUtils {
    public static void requestPermission(Activity mContext, String[] permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                    mContext.requestPermissions(permissions, 10000);
        }
    }
}
