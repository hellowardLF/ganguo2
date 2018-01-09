package tv.ganguo.live.own;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.androidutils.utils.SharePrefsUtils;

import butterknife.Bind;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiActivity;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.own.modle.QianDanInfoModle;
import tv.ganguo.live.utils.Api;

/**
 * Function：
 * Created by lijiefenf on 2017/12/22.
 */

public class QiangDanInfoActivity extends BaseSiSiActivity {
    private static final String TAG = "QiangDanInfoActivity";
    @Bind(R.id.image_top_back)
    ImageView mBack;
    @Bind(R.id.text_top_title)
    TextView mTitle;
    @Bind(R.id.qiandan_info_che)
    TextView mChe;
    @Bind(R.id.qiandan_info_fan)
    TextView mFan;
    @Bind(R.id.qiandan_info_gongjijin)
    TextView mJijin;
    @Bind(R.id.qiandan_info_shebao)
    TextView mSheBao;
    @Bind(R.id.qiandan_info_money)
    TextView mMoney;
    @Bind(R.id.qiandan_info_year)
    TextView mYear;
    @Bind(R.id.qiandan_info_submit)
    TextView mSubmit;
    @Bind(R.id.qiandan_info_job)
    TextView mJob;
    @Bind(R.id.qiandan_info_phone)
    TextView mPhone;
    private String id;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_qiangdan_info;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDate();
        initListener();
    }
    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(id)){
                    toast("获取订单信息失败");
                }
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("loan_id",id);
                jsonObject.put("token",SharePrefsUtils.get(QiangDanInfoActivity.this,"user","token",""));
                Api.submitQianDan(QiangDanInfoActivity.this, jsonObject, new OnRequestDataListener() {
                    @Override
                    public void requestSuccess(int code, JSONObject data) {
                        Log.e(TAG, "requestSuccess: "+data.toString() );
                        toast(data.getString("descrp"));
                        if (code==200){
                            QiangDanInfoActivity.this.finish();
                        }

                    }

                    @Override
                    public void requestFailure(int code, String msg) {
                        toast(msg);
                    }
                });
            }
        });
    }
    private void initView() {
        id = getIntent().getStringExtra("id");
        mTitle.setText("订单详情");
    }
    private void initDate() {
        getData();
    }
    public void getData() {
        if (TextUtils.isEmpty(id)) {
            toast("获取订单信息失败");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", SharePrefsUtils.get(this,"user","token",""));
        jsonObject.put("loan_id", id);
        Api.getQianDanInfo(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                JSONObject data1 = data.getJSONObject("data");
                Log.e(TAG, "requestSuccess: "+data.toString() );
                if (data != null) {
                    QianDanInfoModle qianDanInfoModle = JSON.parseObject(data1.toString(), QianDanInfoModle.class);
                    setView(qianDanInfoModle);
                } else {
                    toast(data.getString("descrp"));
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
            }
        });
    }
    public void setView(QianDanInfoModle view) {
        if (view==null){
            toast("获取订单信息失败");
            return;
        }
        if (view.getSocial_security().equalsIgnoreCase("1")){
            mSheBao.setText("有社保");
        }else {
            mSheBao.setText("无社保");
        }

        if (view.getCar_production().equalsIgnoreCase("1")){
            mChe.setText("有车产");
        }else {
            mChe.setText("无车产");
        }

        if (view.getHouse_production().equalsIgnoreCase("1")){
            mFan.setText("有房产");
        }else {
            mFan.setText("无房产");
        }
        if (view.getProvident_fund().equalsIgnoreCase("1")){
            mJijin.setText("有公积金");
        }else {
            mJijin.setText("无公积金");
        }
        mMoney.setText(view.getAmount());
        mYear.setText(view.getAge()+"岁");
        mJob.setText(view.getOccupation());
        if (view.getMobile().length()==11){
            mPhone.setText(view.getMobile().replace(view.getMobile().substring(3,7),"****"));
        }
    }
}
