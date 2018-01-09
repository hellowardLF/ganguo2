package tv.ganguo.live;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.smart.androidutils.utils.SharePrefsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import tv.ganguo.live.core.BaseSiSiActivity;
import tv.ganguo.live.home.adapter.DaiKuanAdapter;
import tv.ganguo.live.home.model.DaiKuanModle;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.own.authorize.AuthorizeActivity;
import tv.ganguo.live.utils.Api;

/**
 * Created by hxj on 2017/12/5.
 */

public class BorrowMoneyActivity extends BaseSiSiActivity {
    private static final String TAG = "BorrowMoneyActivity";
    @Bind(R.id.image_top_back)
    ImageView mImageTopBack;
    @Bind(R.id.ed_money)
    EditText mEdMoney;
    @Bind(R.id.daikuan_content)
    EditText mContent;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_1)
    TextView mTv1;
    @Bind(R.id.tv_2)
    TextView mTv2;
    @Bind(R.id.tv_3)
    TextView mTv3;
    @Bind(R.id.rl_top)
    RelativeLayout mRlTop;
    @Bind(R.id.rl_bot)
    RelativeLayout mRlBot;
    @Bind(R.id.rl_mid)
    RelativeLayout mRlMid;
    @Bind(R.id.tv_xieyi_click)
    LinearLayout mTvXieyiClick;
    @Bind(R.id.tv_gridview)
    GridView mGridView;
    @Bind(R.id.daikuan_submit)
    TextView mSubmit;
    @Bind(R.id.daikuan_sure)
    ImageView mSure;
    boolean isSure = false;
    private List<DaiKuanModle> list;
    private DaiKuanAdapter adapter;
    private AMapLocationClient locationService;
    private String trem = "1-5天";
    private String mShen = "";
    private String mCity = "";
    private boolean isApprove=false;

    @OnClick(R.id.daikuan_submit)
    public void setmSubmit(View view) {
        if (TextUtils.isEmpty(mShen) || TextUtils.isEmpty(mCity)) {
            locationService.startLocation();
            toast("获取城市信息失败,重新定位中");
            return;
        }
        if (!isSure) {
            toast("请同意授权");
            return;
        }
        if (TextUtils.isEmpty(mEdMoney.getText())) {
            toast("请输入贷款金额");
            return;
        }
        if (TextUtils.isEmpty(mContent.getText())) {
            toast("请输入贷款用途");
            return;
        }
        String code = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsClick() == 1) {
                code = list.get(i).getId();
            }
        }
        if (TextUtils.isEmpty(code)) {
            toast("请选择贷款类型");
            return;
        }

        submit(mEdMoney.getText().toString(), code, trem, mContent.getText().toString(), mShen, mCity);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_borrow_money;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        initLocation();
        initview();
        initData();
        initListener();
    }

    private void initData() {
        getData();
    }

    private void initListener() {
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSure) {
                    mSure.setBackgroundResource(R.drawable.checkmark_circle);
                    isSure = false;
                } else {
                    mSure.setBackgroundResource(R.drawable.checkmark_circle_s);
                    isSure = true;
                }
            }
        });
    }

    private void initLocation() {
        locationService = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        option.setOnceLocation(true);
        locationService.setLocationOption(option);
        locationService.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        mCity = aMapLocation.getCity();
                        mShen = aMapLocation.getProvince();
                        locationService.stopLocation();
                    } else {
                        toast("定位失败");
                    }
                }
            }
        });
        locationService.startLocation();
    }

    private void initview() {
        adapter = new DaiKuanAdapter(list, this);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(position).setIsClick(1);
                    } else {
                        list.get(i).setIsClick(0);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

    }


    @OnClick({R.id.image_top_back, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_xieyi_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_top_back:
                finish();
                break;
            case R.id.tv_1:
                huanyuan();
                mRlTop.setVisibility(View.VISIBLE);
                mTvTime.setText("分" + generateCenterSpannableText(mTv1.getText().toString()) + "还清");
                trem = mTv1.getText().toString();
                break;
            case R.id.tv_2:
                huanyuan();
                mRlMid.setVisibility(View.VISIBLE);
                mTvTime.setText("分" + generateCenterSpannableText(mTv2.getText().toString()) + "还清");
                trem = mTv2.getText().toString();

                break;
            case R.id.tv_3:
                huanyuan();
                mRlBot.setVisibility(View.VISIBLE);
                mTvTime.setText("分" + generateCenterSpannableText(mTv3.getText().toString()) + "还清");
                trem = mTv3.getText().toString();
                break;
            case R.id.tv_xieyi_click:
                break;
        }
    }

    private void huanyuan() {
        mRlBot.setVisibility(View.INVISIBLE);
        mRlMid.setVisibility(View.INVISIBLE);
        mRlTop.setVisibility(View.INVISIBLE);
    }

    private SpannableString generateCenterSpannableText(String text) {

        SpannableString s = new SpannableString(text);
        s.setSpan(new RelativeSizeSpan(1.5f), 0, s.length(), 0);
//        s.setSpan(new RelativeSizeSpan(1.5f), 3, 4, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() , 0);
        s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        return s;
    }

    public void getData() {
        Api.getDaiKuanInfo(this, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: " + data);
                JSONObject jsonObject = new JSONObject(data);
                JSONArray data1 = jsonObject.getJSONArray("data");
                if (data1 != null) {
                    for (int i = 0; i < data1.size(); i++) {
                        DaiKuanModle daiKuanModle = JSON.parseObject(data1.getJSONObject(i).toString(), DaiKuanModle.class);
                        list.add(daiKuanModle);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    toast("没有数据了");
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
            }
        });
    }

    private void submit(String money, String type, String term, String content, String shen, String city) {
        String s = (String) SharePrefsUtils.get(this, "user", "token", "");
        if (TextUtils.isEmpty(s)) {
            toast("获取用户信息失败");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", s);
        jsonObject.put("amount", money);
        jsonObject.put("type_id", type);
        jsonObject.put("term", term);
        jsonObject.put("amount_use", content);
        jsonObject.put("province", shen);
        jsonObject.put("city", city);
        Api.postDaiKuanInfo(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: " + data);
                toast(data.getString("descrp"));
                BorrowMoneyActivity.this.finish();
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
                if (code==502){
                    openActivity(AuthorizeActivity.class);
                }
            }
        });

    }



}
