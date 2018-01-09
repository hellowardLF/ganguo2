package tv.ganguo.live.lean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationFragment;
import cn.leancloud.chatkit.cache.LCIMConversationItemCache;
import cn.leancloud.chatkit.cache.LCIMProfileCache;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.chatkit.utils.LCIMConversationUtils;
import cn.leancloud.chatkit.utils.LCIMLogUtils;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiActivity;
import tv.ganguo.live.lean.info.ChatSettingActivity;

;

/**
 * Created by Administrator on 2016/9/9.
 * Author: XuDeLong
 */
public class Chat extends BaseSiSiActivity {
    private static final String TAG = "Chat";
    @Bind(R.id.text_top_title)
    TextView mTextTopTitle;
    @Bind(R.id.top_add)
    ImageView mAdd;
    protected LCIMConversationFragment conversationFragment;
    private AVIMConversation mAVIMConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextTopTitle.setText("");
        //mTextTopTitle.setText(getIntent().getExtras().getString(LCIMConstants.PEER_ID));
        conversationFragment = (LCIMConversationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_chat);
        initByIntent(getIntent());
    }

    @OnClick(R.id.image_top_back)
    public void back(View view) {
        this.finish();
    }

    @OnClick(R.id.top_add)
    public void add(View view) {
        openActivityForResult(ChatSettingActivity.class, getIntent().getExtras(), 120);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_message_chat;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initByIntent(intent);
    }

    private void initByIntent(Intent intent) {
        if (null == LCChatKit.getInstance().getClient()) {
            showToast("please login first!");
            finish();
            return;
        }

        Bundle extras = intent.getExtras();
        if (null != extras) {
            if (extras.containsKey(LCIMConstants.PEER_ID)) {
                getConversation(extras.getString(LCIMConstants.PEER_ID));
            } else if (extras.containsKey(LCIMConstants.CONVERSATION_ID)) {
                String conversationId = extras.getString(LCIMConstants.CONVERSATION_ID);
                updateConversation(LCChatKit.getInstance().getClient().getConversation(conversationId));
            } else {
                showToast("memberId or conversationId is needed");
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 120:
                    if (mAVIMConversation!=null){
                        LCChatKitUser user= (LCChatKitUser) data.getSerializableExtra("nice");
                        LCIMProfileCache.getInstance().cacheUser(user);
                        mAVIMConversation.setName(user.getUserName());
                        mAVIMConversation.updateInfoInBackground(new AVIMConversationCallback() {
                            @Override
                            public void done(AVIMException e) {
                                Log.e(TAG, "done: "+e.getMessage() );

                                updateConversation(mAVIMConversation);
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 设置 actionBar title 以及 up 按钮事件
     *
     * @param title
     */
    protected void initActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            if (null != title) {
                actionBar.setTitle(title);
            }
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            finishActivity(RESULT_OK);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 主动刷新 UI
     *
     * @param conversation
     */
    protected void updateConversation(AVIMConversation conversation) {
        if (null != conversation) {
            conversationFragment.setConversation(conversation);
            mAdd.setVisibility(View.VISIBLE);
            mTextTopTitle.setText(conversation.getName());
//            conversation.read();
            LCIMConversationItemCache.getInstance().clearUnread(conversation.getConversationId());
            LCIMConversationUtils.getConversationName(conversation, new AVCallback<String>() {
                @Override
                protected void internalDone0(String s, AVException e) {
                    if (null != e) {
                        LCIMLogUtils.logException(e);
                    } else {
                        //initActionBar(s);
                        mTextTopTitle.setText(s);
                    }
                }
            });
        }
    }

    /**
     * 获取 conversation
     * 为了避免重复的创建，createConversation 参数 isUnique 设为 true·
     */
    protected void getConversation(final String memberId) {
        LCChatKit.getInstance().getClient().createConversation(
                Arrays.asList(memberId), "", null, false, true, new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation avimConversation, AVIMException e) {
                        if (null != e) {
                            showToast(e.getMessage());
                        } else {
                            mAVIMConversation=avimConversation;
                            updateConversation(avimConversation);
                        }
                    }
                });
    }

    /**
     * 弹出 toast
     *
     * @param content
     */
    private void showToast(String content) {
        Toast.makeText(Chat.this, content, Toast.LENGTH_SHORT).show();
    }
}