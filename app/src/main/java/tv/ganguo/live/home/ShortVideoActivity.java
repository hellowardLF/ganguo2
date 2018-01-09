package tv.ganguo.live.home;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.smart.loginsharesdk.share.OnShareStatusListener;
import com.smart.loginsharesdk.share.ThirdShare;
import com.smart.loginsharesdk.share.onekeyshare.Type;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import tv.ganguo.live.MainActivity;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiActivity;
import tv.ganguo.live.home.adapter.CommentListAdapter;
import tv.ganguo.live.home.model.CommentModle;
import tv.ganguo.live.home.model.CommentTo;
import tv.ganguo.live.home.model.CommentsBean;
import tv.ganguo.live.home.model.ShortVideoItem;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.own.UserMainActivity;
import tv.ganguo.live.utils.Api;
import tv.ganguo.live.utils.MyVideoView;

/**
 * Function：
 * Created by lijiefenf on 2018/1/2.
 */

public class ShortVideoActivity extends BaseSiSiActivity implements OnShareStatusListener {
    private static final String TAG = "ShortVideoActivity";
    @Bind(R.id.shortvideo_avatar)
    ImageView mAvatar;
    @Bind(R.id.shortvideo_back)
    ImageView mBack;
    @Bind(R.id.shortvideo_comment)
    LinearLayout mComment;
    @Bind(R.id.shortvideo_commentnumber)
    TextView mCommentNumber;
    @Bind(R.id.shortvideo_guanzhu)
    TextView mGuanzhu;
    @Bind(R.id.shortvideo_like)
    ImageView mLike;
    @Bind(R.id.shortvideo_name)
    TextView mName;
    @Bind(R.id.shortvideo_share)
    ImageView mShare;
    @Bind(R.id.shortvideo_videoview)
    MyVideoView mVideo;
    @Bind(R.id.shortvideo_likenumber)
    TextView mLikeNumber;
    private ShortVideoItem data;
    private Dialog mDialog;
    private RecyclerView mRv;
    private SwipeRefreshLayout mSw;
    private List<CommentsBean> mList;
    private CommentListAdapter adapter;
    private LinearLayoutManager mManger;
    private Animation mAnimation;
    private Animation mAnimationRequire;
    private Animation mAnimationSecond;
    private AnimationSet mSet;
    private LinearLayout mLin;
    private LinearLayout mLin1;
    private CommentModle commentModle;
    private TextView comment;
    private Dialog mShareDialog;
    private ThirdShare mThirdShare;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_shortvideoplay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (ShortVideoItem) getIntent().getExtras().getSerializable("data");
        if (data == null) {
            toast("获取视频信息失败");
            return;
        }
        mList = new ArrayList<>();
        mThirdShare = new ThirdShare(this);
        mThirdShare.setOnShareStatusListener(this);
        initDialog();
        initShareDialog();
        initData();
        initListener();
    }

    private void initShareDialog() {
        if (mShareDialog == null) {
            mShareDialog = new Dialog(this, R.style.commondialogstyle);
            View view = View.inflate(this, R.layout.dialog_three_share, null);
            ImageView qz = (ImageView) view.findViewById(R.id.image_add_live_share_qzone);
            ImageView qq = (ImageView) view.findViewById(R.id.image_add_live_share_qq);
            ImageView wc = (ImageView) view.findViewById(R.id.image_add_live_share_wechat);
            ImageView wb = (ImageView) view.findViewById(R.id.image_add_live_share_weibo);
            ImageView wm = (ImageView) view.findViewById(R.id.image_add_live_share_wechat_moment);
            RelativeLayout mR=(RelativeLayout)view.findViewById(R.id.image_add_live_share_back);
            mLin1=(LinearLayout)view.findViewById(R.id.image_add_live_share_linearlayout);
            final String title="标题";
            final String content="这是内容";
            final String shareUrl="https://www.baidu.com";
            final String pic="http://img7.mypsd.com.cn/20121120/Mypsd_22890_201211201420480019B.jpg";
            qz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mThirdShare.setTitle(title);
                    mThirdShare.setText(content);
                    mThirdShare.setTitleUrl(shareUrl);
                    mThirdShare.setImageType(Type.IMAGE_NETWORK);
                    mThirdShare.setImageUrl(pic);
                    mThirdShare.share2QZone();
                }
            });
            qq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //toast("qq");
                    mThirdShare.setTitle(title);
                    mThirdShare.setText(content);
                    mThirdShare.setTitleUrl(shareUrl);
                    mThirdShare.setImageType(Type.IMAGE_NETWORK);
                    mThirdShare.setImageUrl(pic);
                    mThirdShare.share2QQ();
                }
            });
            wc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mThirdShare.setTitle(title);
                    mThirdShare.setText(content);
                    mThirdShare.setShareType(Type.SHARE_WEBPAGE);
                    mThirdShare.setImageType(Type.IMAGE_NETWORK);
                    mThirdShare.setImageUrl(pic);
                    mThirdShare.setUrl(shareUrl);
                    mThirdShare.share2Wechat();
                }
            });
            wb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mThirdShare.setText(content);
                    mThirdShare.setImageUrl(pic);
                    mThirdShare.share2SinaWeibo(false);

                }
            });
            wm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mThirdShare.setTitle(title);
                    mThirdShare.setText(content);
                    mThirdShare.setShareType(Type.SHARE_WEBPAGE);
                    mThirdShare.setImageType(Type.IMAGE_NETWORK);
                    mThirdShare.setImageUrl(pic);
                    mThirdShare.setUrl(shareUrl);
                    mThirdShare.share2WechatMoments();
                }
            });
            mR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            mShareDialog.setContentView(view);
            mAnimation.setFillAfter(true);
            mSet = new AnimationSet(true);
            mSet.addAnimation(mAnimationRequire);
            mSet.addAnimation(mAnimationSecond);
            mSet.setFillAfter(true);
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            DisplayMetrics dm = getResources().getDisplayMetrics();
            lp.width = dm.widthPixels;
            lp.height = (int) (dm.density * 150);
            mShareDialog.getWindow().setAttributes(lp);
            Window window = mShareDialog.getWindow();
            // window.setWindowAnimations(R.style.pop_style);
            window.setGravity(Gravity.BOTTOM);
            mShareDialog.setCanceledOnTouchOutside(true);
            mShareDialog.setCancelable(true);
        }
    }


    private void initData() {
        getCommentList();
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLike();
            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShare();
            }
        });
        mGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGuanzhu();
            }
        });
        mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentList();
            }
        });
        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        mVideo.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                toast("视频播放出错了");
                return true;
            }
        });
        mVideo.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mp.start();
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                        mp.start();

                        break;
                }
                return true;
            }


        });
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle fans_data = new Bundle();
                fans_data.putString("id", commentModle.getUid());
                fans_data.putInt("type", 2);
                openActivity(UserMainActivity.class, fans_data);
            }
        });
    }

    private void showCommentList() {
        if (mDialog == null) {
            initDialog();
        }
        mLin.setAnimation(mSet);
        mSet.startNow();
        adapter.notifyDataSetChanged();
        mDialog.show();
    }

    private void initDialog() {
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_publish);
        mAnimationRequire = AnimationUtils.loadAnimation(this, R.anim.translate_require);
        mAnimationSecond = AnimationUtils.loadAnimation(this, R.anim.translate_second);
        mDialog = new Dialog(this, R.style.commondialogstyle);
        View view = View.inflate(this, R.layout.dialog_commentlist, null);
        mDialog.setContentView(view);
        comment = (TextView) view.findViewById(R.id.comment_number);
        comment.setText(getString(R.string.comment_number, String.valueOf(123)));
        view.findViewById(R.id.comment_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        TextView submit = (TextView) view.findViewById(R.id.comment_list_submit);
        final EditText editText = (EditText) view.findViewById(R.id.comment_list_content);
        mRv = (RecyclerView) view.findViewById(R.id.comment_list_rv);
        mSw = (SwipeRefreshLayout) view.findViewById(R.id.comment_sw);
        mLin = (LinearLayout) view.findViewById(R.id.comment_linearlayout);
        adapter = new CommentListAdapter(mList, this);
        mManger = new LinearLayoutManager(this);
        mRv.setLayoutManager(mManger);
        mRv.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    toast("评论不能为空");
                    return;
                }
                sendContent(editText.getText().toString(), editText);
            }
        });

        mSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCommentList();
            }
        });
        mAnimation.setFillAfter(true);
        mSet = new AnimationSet(true);
        mSet.addAnimation(mAnimationRequire);
        mSet.addAnimation(mAnimationSecond);
        mSet.setFillAfter(true);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.height = (int) (dm.density * 350);
        mDialog.getWindow().setAttributes(lp);
        Window window = mDialog.getWindow();
        // window.setWindowAnimations(R.style.pop_style);
        window.setGravity(Gravity.BOTTOM);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
    }

    private void sendContent(final String s, final EditText editText) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", SharePrefsUtils.get(this, "user", "token", ""));
        jsonObject.put("id", data.getId());
        jsonObject.put("content", s);
        jsonObject.put("to_uid", data.getUid());
        Api.sendComment(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: " + data.toString());
                toast(data.getString("descrp"));
                commentModle.setComments_num(commentModle.getComments_num() + 1);
                mCommentNumber.setText(String.valueOf(commentModle.getComments_num()));
                comment.setText(getString(R.string.comment_number, String.valueOf(commentModle.getComments_num())));
                editText.setText("");
                String data1 = data.getString("data");
                if (!TextUtils.isEmpty(data1)) {
                    CommentTo commentTo = JSON.parseObject(data1, CommentTo.class);
                    CommentsBean bean = new CommentsBean();
                    bean.setAvatar((String) SharePrefsUtils.get(ShortVideoActivity.this, "user", "avatar", ""));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = formatter.format(new Date());
                    bean.setCreate_time(dateString);
                    bean.setContent(s);
                    bean.setUser_nicename(commentTo.getUser_nicename());
                    mList.add(0, bean);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
            }
        });
    }

    private void setGuanzhu() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", SharePrefsUtils.get(this, "user", "token", ""));
        jsonObject.put("userid", data.getUid());
        Api.addAttention(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: " + data.toString());
                toast(data.getString("descrp"));
                mGuanzhu.setVisibility(View.GONE);
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
            }
        });
    }

    private void setShare() {
        if (mShareDialog!=null){
            initShareDialog();
        }
        mLin1.setAnimation(mSet);
        mSet.startNow();
        mShareDialog.show();
    }

    private void setLike() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", SharePrefsUtils.get(this, "user", "token", ""));
        jsonObject.put("video_id", data.getId());
        Api.addHits(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Log.e(TAG, "requestSuccess: " + data);
                toast(data.getString("descrp"));
                commentModle.setHits(String.valueOf(Integer.parseInt(commentModle.getHits()) + 1));
                mLikeNumber.setText(commentModle.getHits());
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);
            }
        });
    }

    private void initview() {
        mVideo.setVideoPath(commentModle.getVideo_url());
        mVideo.requestFocus();
        mName.setText(commentModle.getUser_nicename());
        if (commentModle.getAttention_status() == 1) {
            mGuanzhu.setVisibility(View.GONE);
        } else {
            mGuanzhu.setVisibility(View.VISIBLE);
        }
        Glide.with(this)
                .load(commentModle.getAvatar())
                .asBitmap()
                .error(R.drawable.default_small_bg)
                .into(new BitmapImageViewTarget(mAvatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mAvatar.setImageDrawable(circularBitmapDrawable);
                    }
                });
        mCommentNumber.setText(String.valueOf(commentModle.getComments_num()));
        mLikeNumber.setText(String.valueOf(commentModle.getHits()));
        comment.setText(getString(R.string.comment_number, String.valueOf(commentModle.getComments_num())));
        if (commentModle.getComments() != null && commentModle.getComments().size() != 0) {
            adapter.notifyDataSetChanged();
        }
    }

    public void getCommentList() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", SharePrefsUtils.get(this, "user", "token", ""));
        jsonObject.put("id", data.getId());
        Api.getCommetnList(this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if (mSw != null && mSw.isRefreshing()) {
                    mSw.setRefreshing(false);
                }
                Log.e(TAG, "requestSuccess: " + data.toString());
                JSONObject data1 = data.getJSONObject("data");
                if (data1 != null) {
                    commentModle = new CommentModle();
//                    CommentModle commentModle = JSON.parseObject(data1.toString(), CommentModle.class);
                    commentModle.setAttention_status(data1.getIntValue("attention_status"));
                    commentModle.setAvatar(data1.getString("avatar"));
                    commentModle.setCreate_time(data1.getString("create_time"));
                    commentModle.setFavorites(data1.getString("favorites"));
                    commentModle.setHits(data1.getString("hits"));
                    commentModle.setId(data1.getString("id"));
                    commentModle.setLatitude(data1.getString("latitude"));
                    commentModle.setLongitude(data1.getString("longitude"));
                    commentModle.setStatus(data1.getString("status"));
                    commentModle.setTerm_id(data1.getString("term_id"));
                    commentModle.setUid(data1.getString("uid"));
                    commentModle.setUser_nicename(data1.getString("user_nicename"));
                    commentModle.setVideo_thumb(data1.getString("video_thumb"));
                    commentModle.setVideo_time(data1.getString("video_time"));
                    commentModle.setVideo_title(data1.getString("video_title"));
                    commentModle.setVideo_url(data1.getString("video_url"));
                    commentModle.setView(data1.getString("view"));
                    commentModle.setComments_num(data1.getIntValue("comments_num"));
                    if (!TextUtils.isEmpty(data1.getString("comments"))) {
                        JSONArray comments = data1.getJSONArray("comments");
                        mList.clear();
                        mList.addAll(JSON.parseArray(comments.toString(), CommentsBean.class));
                        commentModle.setComments(mList);
                    }
                    Collections.reverse(mList);
                    initview();
                    //                            commentModle.setComments(data1.getString("comments"));

                }
            }


            @Override

            public void requestFailure(int code, String msg) {
                if (mSw != null && mSw.isRefreshing()) {
                    mSw.setRefreshing(false);
                }
                toast(msg);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideo.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mVideo.isPlaying()) {
            mVideo.start();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }


    @Override
    public void shareSuccess(Platform platform) {

    }

    @Override
    public void shareError(Platform platform) {

    }

    @Override
    public void shareCancel(Platform platform) {

    }
}
