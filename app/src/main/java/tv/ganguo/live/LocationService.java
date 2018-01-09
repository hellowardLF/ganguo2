package tv.ganguo.live;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


/**
 * @author baidu
 */
public class LocationService {
    private AMapLocationClient client = null;
    private AMapLocationClientOption mOption, DIYoption;
    private Object objLock = new Object();

    /***
     *
     * @param locationContext
     */
    public LocationService(Context locationContext) {
        synchronized (objLock) {
            if (client == null) {
                client = new AMapLocationClient(locationContext);
                client.setLocationOption(getDefaultLocationClientOption());
            }
        }
    }

    /***
     *
     * @param listener
     * @return
     */

    public boolean registerListener(AMapLocationListener listener) {
        boolean isSuccess = false;
        if (listener != null) {
            client.setLocationListener(listener);
            isSuccess = true;
        }
        return isSuccess;
    }

    public void unregisterListener(AMapLocationListener listener) {
        if (listener != null) {
            client.unRegisterLocationListener(listener);
        }
    }

    /***
     *
     * @param option
     * @return isSuccessSetOption
     */
    public boolean setLocationOption(AMapLocationClientOption option) {
        boolean isSuccess = false;
        if (option != null) {
            if (client.isStarted())
                client.startLocation();
            DIYoption = option;
            client.setLocationOption(option);
            isSuccess = true;
        }
        return isSuccess;
    }

    public AMapLocationClientOption getOption() {
        return DIYoption;
    }

    /***
     *
     * @return DefaultLocationClientOption
     */
    public AMapLocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new AMapLocationClientOption();
            mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
//			mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return mOption;
    }

    public void start() {
        synchronized (objLock) {
            if (client != null && !client.isStarted()) {
                client.startLocation();
            }
        }
    }

    public void stop() {
        synchronized (objLock) {
            if (client != null && client.isStarted()) {
                client.stopLocation();
            }
        }
    }

}
