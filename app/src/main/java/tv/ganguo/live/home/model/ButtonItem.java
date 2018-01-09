package tv.ganguo.live.home.model;

/**
 * Created by hxj on 2017/12/4.
 */

public class ButtonItem {

    private int res;
    private String name;
    private String url;
    private boolean isNew;

    public ButtonItem(int mRes, String mName) {
        res = mRes;
        name = mName;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int mRes) {
        res = mRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        name = mName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String mUrl) {
        url = mUrl;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean mNew) {
        isNew = mNew;
    }
}
