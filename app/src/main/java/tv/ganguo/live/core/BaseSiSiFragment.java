package tv.ganguo.live.core;

import com.smart.androidutils.fragment.BaseFragment;

import tv.ganguo.live.R;
import tv.ganguo.live.utils.ToastHelper;

/**
 * Created by fengjh on 16/9/12.
 */
public abstract class BaseSiSiFragment extends BaseFragment {

    @Override
    public void toast(String s) {
        ToastHelper.makeText(getActivity(), s, ToastHelper.LENGTH_SHORT).setAnimation(R.style.Animation_Toast).show();
    }
}
