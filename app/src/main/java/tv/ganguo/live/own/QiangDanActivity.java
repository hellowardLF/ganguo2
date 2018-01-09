package tv.ganguo.live.own;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiActivity;
import tv.ganguo.live.home.model.DaiKuanModle;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.own.adapter.QianDanTypeAdapter;
import tv.ganguo.live.own.modle.QiangDanModle;
import tv.ganguo.live.utils.Api;

;

/**
 * Function：
 * Created by lijiefenf on 2017/12/20.
 */

public class QiangDanActivity extends BaseSiSiActivity {
    private static final String TAG = "QiangDanActivity";
    @Bind(R.id.image_top_back)
    ImageView mBack;
    @Bind(R.id.text_top_title)
    TextView mTitle;
    @Bind(R.id.qiangdang_city)
    Spinner mCity;
    /*    @Bind(R.id.qiangdang_da)
        LinearLayout mDa;
        @Bind(R.id.qiangdang_zhong)
        LinearLayout mZhong;
        @Bind(R.id.qiangdang_xiao)
        LinearLayout mXiao;*/
    @Bind(R.id.qiangdang_shenfen)
    Spinner mShenFen;
    @Bind(R.id.qiangdang_diwei)
    TextView mDingWei;
    @Bind(R.id.qiangdang_recycleview)
    RecyclerView mRv;
    @Bind(R.id.qiangdang_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.qiangdang_select)
    TextView mSelect;
    @Bind(R.id.qiandan_gridview)
    GridView mGridView;
    private AMapLocationClient locationService;

    private QiangdanAdapter adapter;
    private List<QiangDanModle> list;
    private LinearLayoutManager manager;
    private int mIndex = 0;
    private List<DaiKuanModle> mList;
    private QianDanTypeAdapter mTypeAdapter;
    private boolean isFrist = true;
    private String mType = "";
    private String shenfeng[] = {"全国", "北京市", "广东省", "山东省", "江苏省", "河南省", "上海市", "河北省", "浙江省", "香港特别行政区", "陕西省", "湖南省", "重庆市", "福建省", "天津市", "云南省", "云南省", "四川省", "广西壮族自治区",
            "安徽省", "海南省", "江西省", "湖北省", "山西省", "辽宁省", "台湾省", "黑龙江", "内蒙古自治区", "澳门特别行政区", "贵州省", "甘肃省", "青海省", "新疆维吾尔自治区", "西藏区", "吉林省", "宁夏回族自治区"};
    private String banefdjs[][] = {{"全国"},
            {"北京市"}
            , {"全部", "东莞市", "广州市", "中山市", "深圳市", "惠州市", "江门市", "珠海市", "汕头市", "佛山市", "湛江市", "河源市", "肇庆市", "潮州市", "清远市", "韶关市", "揭阳市", "阳江市", "云浮市", "茂名市", "梅州市", "汕尾市"}
            , {"全部", "济南市", "青岛市", "临沂市", "济宁市", "菏泽市", "烟台市", "泰安市", "淄博市", "潍坊市", "日照市", "威海市", "滨州市", "东营市", "聊城市", "德州市", "莱芜市", "枣庄市"}
            , {"全部", "苏州市", "徐州市", "盐城市", "无锡市", "南京市", "南通市", "连云港市", "常州市", "扬州市", "镇江市", "淮安市", "泰州市", "宿迁市"}
            , {"全部", "郑州市", "南阳市", "新乡市", "安阳市", "洛阳市", "信阳市", "平顶山市", "周口市", "商丘市", "开封市", "焦作市", "驻马店市", "濮阳市", "三门峡市", "漯河市", "许昌市", "鹤壁市", "济源市"}
            , {"全部", "上海市"}
            , {"全部", "石家庄市", "唐山市", "保定市", "邯郸市", "邢台市", "河北区", "沧州市", "秦皇岛市", "张家口市", "衡水市", "廊坊市", "承德市"},
            {"全部", "温州市", "宁波市", "杭州市", "台州市", "嘉兴市", "金华市", "湖州市", "绍兴市", "舟山市", "丽水市", "衢州市"},
            {"香港"},
            {"全部", "西安市", "咸阳市", "宝鸡市", "汉中市", "渭南市", "安康市", "榆林市", "商洛市", "延安市", "铜川市"},
            {"全部", "长沙市", "邵阳市", "常德市", "衡阳市", "株洲市", "湘潭市", "永州市", "岳阳市", "怀化市", "郴州市", "娄底市", "益阳市", "张家界市", "湘西州"},
            {"重庆市"},
            {"全部", "漳州市", "泉州市", "厦门市", "福州市", "莆田市", "宁德市", "三明市", "南平市", "龙岩市"},
            {"天津市"},
            {"全部", "昆明市", "红河州", "大理州", "文山州", "德宏州", "曲靖市", "昭通市", "楚雄州", "保山市", "玉溪市", "丽江地区", "临沧地区", "思茅地区", "西双版纳州", "怒江州", "迪庆州"},
            {"全部", "成都市", "绵阳市", "广元市", "达州市", "南充市", "德阳市", "广安市", "阿坝州", "巴中市", "遂宁市", "内江市", "凉山州", "攀枝花市", "乐山市", "自贡市", "泸州市", "雅安市", "宜宾市", "资阳市", "眉山市", "甘孜州"},
            {"全部", "贵港市", "玉林市", "北海市", "南宁市", "柳州市", "桂林市", "梧州市", "钦州市", "来宾市", "河池市", "百色市", "贺州市", "崇左市", "防城港市"},
            {"全部", "芜湖市", "合肥市", "六安市", "宿州市", "阜阳市", "安庆市", "马鞍山市", "蚌埠市", "淮北市", "淮南市", "宣城市", "黄山市", "铜陵市", "亳州市", "池州市", "巢湖市", "滁州市"},
            {"全部", "三亚市", "海口市", "琼海市", "文昌市", "东方市", "昌江县", "陵水县", "乐东县", "五指山市", "保亭县", "澄迈县", "万宁市", "儋州市", "临高县", "白沙县", "定安县", "琼中县", "屯昌县"},
            {"全部", "南昌市", "赣州市", "上饶市", "吉安市", "九江市", "新余市", "抚州市", "宜春市", "景德镇市", "萍乡市", "鹰潭市"}
            , {"全部", "武汉市", "宜昌市", "襄樊市", "荆州市", "恩施州", "孝感市", "黄冈市", "十堰市", "咸宁市", "黄石市", "仙桃市", "随州市", "天门市", "荆门市", "潜江市", "鄂州市", "神农架林区"}
            , {"全部", "太原市", "大同市", "运城市", "长治市", "晋城市", "忻州市", "临汾市", "吕梁市", "晋中市", "阳泉市", "朔州市"}
            , {"全部", "大连市", "沈阳市", "丹东市", "辽阳市", "葫芦岛市", "锦州市", "朝阳市", "营口市", "鞍山市", "抚顺市", "阜新市", "本溪市", "盘锦市", "铁岭市"}
            , {"全部", "台北市", "高雄市", "台中市", "新竹市", "基隆市", "台南市", "嘉义市"}
            , {"全部", "齐齐哈尔市", "哈尔滨市", "大庆市", "佳木斯市", "双鸭山市", "牡丹江市", "鸡西市", "黑河市", "绥化市", "鹤岗市", "伊春市", "大兴安岭地区", "七台河市"}
            , {"全部", "赤峰市", "包头市", "通辽市", "呼和浩特市", "乌海市", "鄂尔多斯市", "呼伦贝尔市", "兴安盟", "巴彦淖尔盟", "乌兰察布盟", "锡林郭勒盟", "阿拉善盟"}
            , {"澳门"}
            , {"全部", "贵阳市", "黔东南州", "黔南州", "遵义市", "黔西南州", "毕节地区", "铜仁地区", "安顺市", "六盘水市"}
            , {"全部", "兰州市", "天水市", "庆阳市", "武威市", "酒泉市", "张掖市", "陇南地区", "白银市", "定西地区", "平凉市", "嘉峪关市", "临夏回族自治州", "金昌市", "甘南州"}
            , {"全部", "西宁市", "海西州", "海东地区", "海北州", "果洛州", "玉树州", "黄南藏族自治州"}
            , {"全部", "乌鲁木齐市", "伊犁州", "昌吉州", "石河子市", "哈密地区", "阿克苏地区", "巴音郭楞州", "喀什地区", "塔城地区", "克拉玛依市", "和田地区", "阿勒泰州", "吐鲁番地区", "阿拉尔市", "博尔塔拉州", "五家渠市", "克孜勒苏州", "图木舒克市"}
            , {"全部", "拉萨市", "山南地区", "林芝地区", "日喀则地区", "阿里地区", "昌都地区", "那曲地区"}
            , {"全部", "吉林市", "长春市", "白山市", "白城市", "延边州", "松原市", "辽源市", "通化市", "四平市"}
            , {"全部", "银川市", "吴忠市", "中卫市", "石嘴山市", "固原市"}
    };

    @Override
    public int getLayoutResource() {
        return R.layout.activity_qiangdan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
        initLocation();

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
                  /*      city = aMapLocation.getCity();
                        mShen=aMapLocation.getProvince();*/
                        String poiName = aMapLocation.getProvince();
                        int i=0;
                        for (int j = 0; j < shenfeng.length; j++) {
                            if (shenfeng[j].equalsIgnoreCase(poiName)){
                                i=j;
                                break;
                            }
                        }
                        mShenFen.setSelection(i, true);
                        String city = aMapLocation.getCity();
                        String[] banefdj = banefdjs[i];
                        int j = 0;
                        for (int k = 0; k < banefdj.length; k++) {
                            if (banefdj[k].equalsIgnoreCase(city)){
                                j=k;
                                break;
                            }
                        }
                        mCity.setAdapter(new ArrayAdapter(QiangDanActivity.this, android.R.layout.simple_spinner_dropdown_item, banefdj));
                        mCity.setSelection(j, true);
                        locationService.stopLocation();
                    } else {
                        toast("定位失败");
                    }
                }
            }
        });
        locationService.startLocation();
    }

    private void initData() {
        getType();
        getData(1);

    }

    private void initListener() {
        mShenFen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + position + "    " + id);
                String[] banefdj = banefdjs[position];
                mCity.setAdapter(new ArrayAdapter(QiangDanActivity.this, android.R.layout.simple_spinner_dropdown_item, banefdj));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(1);
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter.setmQianDanClick(new QiangdanAdapter.QianDanClick() {
            @Override
            public void ItemClick(int postion) {
                Bundle bundle = new Bundle();
                bundle.putString("id", list.get(postion).getId());
                openActivityForResult(QiangDanInfoActivity.class, bundle, 10086);
            }

            @Override
            public void QiangDan(int postion) {
                toast("2");
            }
        });
        mDingWei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        mList.get(i).setIsClick(1);
                    } else {
                        mList.get(i).setIsClick(0);
                    }
                }
                mType = mList.get(position).getId();
                getData(1);
                mTypeAdapter.notifyDataSetChanged();

            }
        });
    }

    private void initView() {
        mTitle.setText("实时抢单");
        list = new ArrayList<>();
        mList = new ArrayList<>();
        mTypeAdapter = new QianDanTypeAdapter(mList, this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary);
        adapter = new QiangdanAdapter(list, this);
        manager = new LinearLayoutManager(this);
        mRv.setLayoutManager(manager);
        mRv.setAdapter(adapter);
        mGridView.setAdapter(mTypeAdapter);
        mShenFen.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, shenfeng));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10086:
                    getData(1);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @param type 1刷新 2加载
     */
    public void getData(int type) {
        JSONObject jsonObject = new JSONObject();
        if (TextUtils.isEmpty(this.mType)) {
            jsonObject.put("type_id", "1");
        } else {
            jsonObject.put("type_id", mType);
        }
        jsonObject.put("token", (String) SharePrefsUtils.get(this, "user", "token", ""));
        Api.getQianDanList(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                list.clear();
                JSONArray data1 = data.getJSONArray("data");
                Log.e(TAG, "requestSuccess: " + data1);
                if (data1 != null) {
                    for (int i = 0; i < data1.size(); i++) {
                        QiangDanModle qiangDanModle = JSON.parseObject(data1.getJSONObject(i).toString(), QiangDanModle.class);
                        list.add(qiangDanModle);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void requestFailure(int code, String msg) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                toast(msg);
            }
        });
    }

/*    @OnClick({R.id.qiangdang_xiao,
            R.id.qiangdang_zhong,
            R.id.qiangdang_da})
    public void tabClick(View v) {
        switch (v.getId()) {
            case R.id.qiangdang_xiao:
                mDa.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                mZhong.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                mXiao.setBackgroundColor(ContextCompat.getColor(this, R.color.qiangdanlinbg));
                mIndex = 0;
                getData(1);
                break;
            case R.id.qiangdang_zhong:
                mDa.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                mXiao.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                getData(1);
                mZhong.setBackgroundColor(ContextCompat.getColor(this, R.color.qiangdanlinbg));
                mIndex = 1;
                break;
            case R.id.qiangdang_da:
                mZhong.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                mXiao.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                getData(1);
                mIndex = 2;
                mDa.setBackgroundColor(ContextCompat.getColor(this, R.color.qiangdanlinbg));
                break;
        }
    }*/

    public void getType() {
        Api.getDaiKuanInfo(this, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: " + data);
                JSONObject jsonObject = new JSONObject(data);
                JSONArray data1 = jsonObject.getJSONArray("data");
                if (data1 != null) {
                    for (int i = 0; i < data1.size(); i++) {
                        DaiKuanModle daiKuanModle = JSON.parseObject(data1.getJSONObject(i).toString(), DaiKuanModle.class);
                        if (isFrist && i == 0) {
                            isFrist = !isFrist;
                            daiKuanModle.setIsClick(1);
                        }
                        mList.add(daiKuanModle);
                    }
                    mTypeAdapter.notifyDataSetChanged();
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


}
