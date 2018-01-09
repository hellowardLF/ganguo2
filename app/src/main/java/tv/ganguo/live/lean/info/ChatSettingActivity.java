package tv.ganguo.live.lean.info;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.smart.androidutils.images.GlideCircleTransform;


import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.utils.LCIMConstants;
import de.hdodenhof.circleimageview.CircleImageView;
import tv.ganguo.live.MainActivity;
import tv.ganguo.live.MyApplication;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiActivity;
import tv.ganguo.live.home.event.FirstEvent;
import tv.ganguo.live.intf.OnCustomClickListener;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.utils.Api;
import tv.ganguo.live.utils.DialogEnsureUtiles;

;

/**
 * Function： 聊天设置
 * Created by lijiefenf on 2017/12/20.
 */

public class ChatSettingActivity extends BaseSiSiActivity {
    private static final String TAG = "ChatSettingActivity";
    @Bind(R.id.text_top_title)
    TextView mTitle;
    @Bind(R.id.image_top_back)
    ImageView back;
    @Bind(R.id.chat_setting_updataname)
    LinearLayout upName;
    @Bind(R.id.chat_setting_avatar)
    CircleImageView avatar;
    @Bind(R.id.chat_setting_integral)
    TextView name;
    @Bind(R.id.chat_setting_id)
    TextView mUid;
    @Bind(R.id.chat_setting_switch)
    Switch mSwitch;
    @Bind(R.id.chat_setting_nick)
    TextView note;
    @Bind(R.id.chat_setting_delete_friend)
    TextView delete;
    private String friendId;
    private String avatarUrl;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_chat_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText("聊天设置");
        initView();
        initData();
        initLintener();
    }

    private void initLintener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        upName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogEnsureUtiles.showInfo(ChatSettingActivity.this, new OnCustomClickListener() {
                    @Override
                    public void onClick(String value) {
                        upName(value);
                    }
                }, "", "修改备注");
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEnsureUtiles.showConfirm(ChatSettingActivity.this, "是否要删除该好友？", new OnCustomClickListener() {
                    @Override
                    public void onClick(String value) {
                        deletFriend();
                    }
                });
            }
        });
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e(TAG, "onCheckedChanged: " + isChecked);
                isTop(isChecked);
            }
        });
    }

    private void initData() {
        getData();
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        friendId = extras.getString(LCIMConstants.PEER_ID);

    }

    public void getData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", MyApplication.get().getToken());
        jsonObject.put("friends_id", friendId);
        Api.getFriendSetting(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: " + data);
                JSONObject data1 = data.getJSONObject("data");
                if (data1 != null) {
                    setView(data1);
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

    private void setView(JSONObject data1) {
        name.setText(getString(R.string.user_name, data1.getString("user_nicename")));
        note.setText(getString(R.string.nick, TextUtils.isEmpty(data1.getString("note")) ? "无" : data1.getString("note")));
        avatarUrl = data1.getString("avatar");
        Glide.with(this)
                .load(avatarUrl)
                .asBitmap()
                .error(R.drawable.default_small_bg)
                .placeholder(R.drawable.default_small_bg)
                .into(new BitmapImageViewTarget(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        avatar.setImageDrawable(circularBitmapDrawable);
                    }
                });
        mUid.setText(getString(R.string.ganguo_id, data1.getString("id")));
        int top = data1.getIntValue("top");
        if (top == 1) {
            mSwitch.setChecked(true);
        } else {
            mSwitch.setChecked(false);
        }
    }


    private void deletFriend() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", MyApplication.get().getToken());
        jsonObject.put("friends_id", friendId);
        Api.deleteFriend(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                EventBus.getDefault().post(new FirstEvent(1));
                Log.e(TAG, "requestSuccess: " + data);
                toast(data.getString("descrp"));
                openActivity(MainActivity.class);
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
            }
        });
    }

    private void upName(final String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", MyApplication.get().getToken());
        jsonObject.put("friends_id", friendId);
        jsonObject.put("note", name);
        Api.upFriendNick(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: " + data);
                EventBus.getDefault().post(new FirstEvent(1));
                note.setText(getString(R.string.nick, name));
                Intent intent = new Intent();
                LCChatKitUser lcChatKitUser = new LCChatKitUser(friendId, name, avatarUrl);
                setResult(RESULT_OK, intent.putExtra("nice", lcChatKitUser));
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
            }
        });
    }

    private void isTop(boolean top) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", MyApplication.get().getToken());
        jsonObject.put("friends_id", friendId);
        if (top) {
            jsonObject.put("top", "1");
        } else {
            jsonObject.put("top", "0");
        }
//        jsonObject.put("name",)
        Api.isTop(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                EventBus.getDefault().post(new FirstEvent(1));
                Log.e(TAG, "requestSuccess: " + data);
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
            }
        });
    }
}
