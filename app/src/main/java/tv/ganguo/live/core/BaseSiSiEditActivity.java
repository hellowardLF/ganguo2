package tv.ganguo.live.core;

import com.smart.androidutils.activity.BaseEditActivity;
import com.umeng.analytics.MobclickAgent;

import tv.ganguo.live.R;
import tv.ganguo.live.utils.ToastHelper;

/**
 * Created by fengjh on 16/9/12.
 */
public abstract class BaseSiSiEditActivity extends BaseEditActivity {

    @Override
    public void toast(String s) {
        ToastHelper.makeText(this, s, ToastHelper.LENGTH_SHORT).setAnimation(R.style.Animation_Toast).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
