package tv.ganguo.live.utils;

import android.content.Context;
import android.os.Build;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;
import tv.ganguo.live.intf.OnRequestDataListener;
import tv.ganguo.live.utils.MyLogUtils;
import tv.ganguo.live.view.SFProgrssDialog;

/**
 * Created by Administrator on 2016/8/19.
 * Author: XuDeLong
 */
public class Api {
    private static final String OS = "android";
    private static final String SOFT_VER = Build.VERSION.RELEASE;
    public static final String HOST = "http://116.62.173.251/";
    private static final String DO_LOGIN = HOST + "Api/SiSi/login";
    private static final String DO_REGISTER = HOST + "Api/SiSi/register";
    private static final String GET_VARCODE = HOST + "Api/SiSi/get_phone_varcode";
    private static final String GET_USERINFO = HOST + "Api/SiSi/getUserInfo";
    private static final String GET_USERDATA = HOST + "Api/SiSi/get_userinfo";
    private static final String FORGET_PASSWORD = HOST + "Api/SiSi/retrievePassword";
    public static final String SET_USERDATA = HOST + "Api/SiSi/change_userinfo";
    private static final String CHANGE_PASSWORD = HOST + "Api/SiSi/changePassword";
    private static final String FEED_BACK = HOST + "Api/SiSi/submitFeedback";
    private static final String GET_USER_ATTENTION_LIST = HOST + "Api/SiSi/getUserAttentionList";
    private static final String GET_USER_FANS_LIST = HOST + "Api/SiSi/getUserFansList";
    private static final String GET_USER_CONTRIBUTION_LIST = HOST + "Api/SiSi/getUserContributionList";
    private static final String GET_CHANNEL_LIST = HOST + "Api/SiSi/new_getLive";
    private static final String GET_ATTENTION_CHANNEL_LIST = HOST + "Api/SiSi/getAttentionLiveList";
    private static final String GET_LAUNCH_SCREEN = HOST + "Api/SiSi/getLaunchScreen";
    private static final String GET_BANNER = HOST + "Api/SiSi/getBanner";
    private static final String GET_HOMELIST = HOST + "Api/SiSi/index_getLive";
    private static final String GET_FRIENDS = HOST + "Api/SiSi/my_friends";
    private static final String DEL_FRIENDS = HOST + "Api/SiSi/del_friends";
    private static final String SET_NOTE = HOST + "Api/SiSi/note_settings";
    private static final String GET_CHATSETTING = HOST + "Api/SiSi/chat_settings";
    public static final String WEB_AUTH = HOST + "portal/Appweb/index";
    public static final String WEB_MYAUTH = HOST + "portal/Appweb/my_apply";
    public static final String SEARCH = HOST + "Api/SiSi/searchUsers";
    public static final String SET_TOP = HOST + "Api/SiSi/friends_top_save";
    public static final String START_LIVE = HOST + "Api/SiSi/new_startLive";
    public static final String WEB_EXCHANGE = HOST + "portal/Appweb/earn";
    public static final String WEB_GRADE = HOST + "portal/Appweb/grade";
    private static final String ADD_ATTENTION = HOST + "Api/SiSi/addAttention";
    private static final String GET_DAIKUANINFO = HOST + "Api/P2p/get_type_list";
    private static final String GET_POSTDAIKUANINFO = HOST + "Api/P2p/apply_loan";
    private static final String GET_DIANGDANLIST = HOST + "Api/P2p/apply_loan_lists";
    private static final String GET_DIANGDANINFO = HOST + "Api/P2p/apply_loan_lists";
    private static final String SET_LOCATION = HOST + "Api/SiSi/setLocation";
    private static final String GET_CHANNLE_INFO = HOST + "Api/SiSi/new_enterLiveRoom";
    private static final String GET_GIFTS = HOST + "Api/SiSi/getGiftList";
    private static final String SEND_GIFT = HOST + "Api/SiSi/sendGiftToAnchor";
    private static final String SEND_DANMU = HOST + "Api/SiSi/sendDanmuToAnchor";
    private static final String SEND_REPORT = HOST + "Api/SiSi/sendReport";
    private static final String ADD_JINYAN = HOST + "Api/SiSi/setDisableSendMsg";
    private static final String LIVING_REALTIME_DATA = HOST + "Api/SiSi/getLiveRoomOnlineNumEarn";
    private static final String GET_LIVE_ROOM_ONLINE_LIST = HOST + "Api/SiSi/getLiveRoomOnlineUserList";
    private static final String EXIT_LIVE_ROOM = HOST + "Api/SiSi/exitLiveRoom";
    private static final String CANCEL_ATTENTION = HOST + "Api/SiSi/cancelAttention";
    private static final String STOP_PUBLISH = HOST + "Api/SiSi/stopLive";
    private static final String GET_TERM = HOST + "/Api/SiSi/getChannelTermList";
    private static final String GET_SHARE_INFO = HOST + "Api/SiSi/getShareInfo";
    private static final String THIRD_LOGIN = HOST + "Api/SiSi/sendOauthUserInfo";
    private static final String PAY = HOST + "Api/SiSi/begin_wxpay";
    private static final String PAY_ALI = HOST + "Api/SiSi/begin_alipay";
    private static final String GET_CHARGE_PACK = HOST + "Api/SiSi/get_recharge_package";
    private static final String CHECK_UPDATE = HOST + "Api/SiSi/checkAndroidVer";
    private static final String SET_MANAGERER = HOST + "Api/SiSi/setLiveGuard";
    private static final String CANCEL_MANAGER = HOST + "Api/SiSi/CancelLiveGuard";
    private static final String GET_MANAGER_LIST = HOST + "Api/SiSi/getLiveGuardList";
    private static final String GET_TOPIC = HOST + "Api/SiSi/topic_list";
    private static final String SET_SHOP_LINK = HOST + "Api/SiSi/change_shopurl";
    private static final String GET_SHOP_LINK = HOST + "Api/SiSi/get_shopurl";
    private static final String PUBLISH_RECORD = HOST + "/Api/SiSi/getMyLiveRecordList";
    private static final String CHANGE_MOBILE = HOST + "/Api/SiSi/BindingMobile";
    private static final String GET_SYSTEM_RANK_LIST = HOST + "/Api/SiSi/getSystemRankingList";
    private static final String QIANGDAN_INFO = HOST + "/Api/SiSi/apply_loan_detail";
    private static final String SUBMIT_QIANDAN = HOST + "/Api/SiSi/apply_loan_submit";
    private static final String ADD_LIKE = HOST + "/Api/SiSi/apply_loan_detail";
    public static final String LOGIN_BG = HOST + "/data/upload/demo_video/105_clip.mp4";
    public static final String SEARCH_MUSIC = "http://apis.baidu.com/geekery/music/query";
    public static final String GET_MUSIC = "http://apis.baidu.com/geekery/music/playinfo";
    public static final String CHECK_PASS = HOST + "/Api/SiSi/checkRoomPassword";
    public static final String UP_SHORTVIDEO = HOST + "/Api/SiSi/record_video";
    public static final String SEARCH_MUSIC1 = HOST + "/Api/SiSi/searchSong";
    private static final String GET_CLASSIFY = HOST + "/Api/SiSi/getChannelTermList";
    private static final String ADD_HITS = HOST + "/Api/SiSi/add_hits";
    private static final String GET_LOOK_LIST = HOST + "/Api/SiSi/views";
    private static final String GET_SHORT_VIDEO_LIST = HOST + "/Api/SiSi/my_video_list";
    //    private static final String GET_ISAPPROVE = HOST+"/Api/SiSi/searchSong";
    public static String GET_LikePic = HOST + "/Api/SiSi/getFlotageImage";
    public static String GET_SONG_LRC = HOST + "/Api/SiSi/searchSongLyric";
    public static String GET_SHORT_LIST = HOST + "/Api/SiSi/video_index";
    public static String SEND_COMMENT = HOST + "/Api/SiSi/add_comments";
    public static String GET_COMMENT_LIST = HOST + "/Api/SiSi/video_detail";
    public static String CLEAR_ALL = HOST + "/Api/SiSi/clear_views";
    public static final String PUSH_CALLBACK = HOST + "/Api/SiSi/startLivePushCallback";
    public static final String WAP_PAY = HOST + "/portal/Appweb/chongzhi";
    public static final String WEB_Family = HOST + "portal/Family/index";


    public static void doRegister(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(DO_REGISTER, context, params, dialog, listener);
    }

    public static void getShortVideoList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_SHORT_LIST, context, params, dialog, listener);
    }

    public static void clearAll(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(CLEAR_ALL, context, params, dialog, listener);
    }

    public static void sendComment(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(SEND_COMMENT, context, params, null, listener);
    }


    public static void getCommetnList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_COMMENT_LIST, context, params, dialog, listener);
    }

    public static void getSongLrc(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_SONG_LRC, context, params, null, listener);
    }

    public static void addHits(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(ADD_HITS, context, params, null, listener);
    }

    public static void getLookList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_LOOK_LIST, context, params, dialog, listener);
    }

    public static void getMyShortVideoList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_SHORT_VIDEO_LIST, context, params, dialog, listener);
    }

    public static void pushCallback(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(PUSH_CALLBACK, context, params, null, listener);
    }

    public static void searchMusic1(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(SEARCH_MUSIC1, context, params, dialog, listener);
    }

    public static void getQianDanInfo(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(QIANGDAN_INFO, context, params, dialog, listener);
    }

    public static void submitQianDan(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(SUBMIT_QIANDAN, context, params, dialog, listener);
    }

    public static void getLikePic(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"waiting...");
        excutePost(GET_LikePic, context, params, null, listener);
    }

    public static void checkPass(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(CHECK_PASS, context, params, dialog, listener);
    }


    public static void addLike(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(DO_REGISTER, context, params, null, listener);
    }

    public static void getSystemRankList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_SYSTEM_RANK_LIST, context, params, dialog, listener);
    }

    public static void changeMobile(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(CHANGE_MOBILE, context, params, dialog, listener);
    }

    public static void setShopLink(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(SET_SHOP_LINK, context, params, dialog, listener);
    }

    public static void getShopLink(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_SHOP_LINK, context, params, dialog, listener);
    }

    public static void getVarCode(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_VARCODE, context, params, dialog, listener);
    }

    public static void doLogin(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(DO_LOGIN, context, params, dialog, listener);
    }

    public static void getUserInfo(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_USERINFO, context, params, dialog, listener);
    }

    public static void getTerm(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_TERM, context, params, null, listener);
    }

    public static void getUserData(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_USERDATA, context, params, dialog, listener);
    }

    public static void getUserData1(final Context context, JSONObject params, final OnRequestDataListener listener) {
        // ProgressDialog dialog = ProgressDialog.show(context, "", "请稍后...", true);
        excutePost(GET_USERINFO, context, params, null, listener);
    }

    public static void setUserData(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(SET_USERDATA, context, params, dialog, listener);
    }

    public static void forgetPassword(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(FORGET_PASSWORD, context, params, dialog, listener);
    }

    public static void changePassword(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(CHANGE_PASSWORD, context, params, dialog, listener);
    }

    public static void feedBack(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(FEED_BACK, context, params, dialog, listener);
    }

    public static void getUserAttentionList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_USER_ATTENTION_LIST, context, params, dialog, listener);
    }

    public static void getUserFansList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_USER_FANS_LIST, context, params, dialog, listener);
    }

    public static void getUserContributionList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_USER_CONTRIBUTION_LIST, context, params, dialog, listener);
    }

    public static void getChannelList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_CHANNEL_LIST, context, params, dialog, listener);
    }

    public static void getAttentionChannelList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_ATTENTION_CHANNEL_LIST, context, params, null, listener);
    }

    public static void getLaunchScreen(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_LAUNCH_SCREEN, context, params, null, listener);
    }

    public static void getBanner(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_BANNER, context, params, dialog, listener);
    }

    public static void getHomeList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_HOMELIST, context, params, dialog, listener);
    }

    public static void getFriendsList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_FRIENDS, context, params, dialog, listener);
    }

    public static void deleteFriend(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(DEL_FRIENDS, context, params, dialog, listener);
    }

    public static void upFriendNick(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(SET_NOTE, context, params, dialog, listener);
    }

    public static void getFriendSetting(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_CHATSETTING, context, params, dialog, listener);
    }

    public static void doSearch(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SEARCH, context, params, null, listener);
    }
  public static void isTop(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SET_TOP, context, params, null, listener);
    }

    public static void startLive(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(START_LIVE, context, params, null, listener);
    }

    public static void addAttention(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(ADD_ATTENTION, context, params, null, listener);
    }

    public static void getDaiKuanInfo(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_DAIKUANINFO, context, params, dialog, listener);
    }

    public static void postDaiKuanInfo(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_POSTDAIKUANINFO, context, params, dialog, listener);
    }

    public static void getQianDanList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_DIANGDANLIST, context, params, dialog, listener);
    }

    public static void setLocation(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SET_LOCATION, context, params, null, listener);
    }

    public static void getChannleInfo(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_CHANNLE_INFO, context, params, null, listener);
    }

    public static void getGifts(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //ProgressDialog dialog = ProgressDialog.show(context, "", "请稍后...", true);
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_GIFTS, context, params, null, listener);
    }

    public static void sendGift(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SEND_GIFT, context, params, null, listener);
    }

    public static void sendDanmu(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SEND_DANMU, context, params, null, listener);
    }

    public static void sendReport(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SEND_REPORT, context, params, null, listener);
    }

    public static void addJinyan(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(ADD_JINYAN, context, params, null, listener);
    }

    public static void getLiveRealTimeNum(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(LIVING_REALTIME_DATA, context, params, null, listener);
    }

    public static void getOnlineUsers(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_LIVE_ROOM_ONLINE_LIST, context, params, null, listener);
    }

    public static void exitLiveRoom(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(EXIT_LIVE_ROOM, context, params, null, listener);
    }

    public static void cancelAttention(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CANCEL_ATTENTION, context, params, null, listener);
    }

    public static void stopPublish(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(STOP_PUBLISH, context, params, dialog, listener);
    }

    public static void getClassify(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context, "请稍后...");
        excutePost(GET_CLASSIFY, context, params, dialog, listener);
    }

    public static void getPublishRecord(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(PUBLISH_RECORD, context, params, null, listener);
    }

    public static void getShareInfo(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_SHARE_INFO, context, params, null, listener);
    }
    /*public static void getIsApprove(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_ISAPPROVE, context, params, null, listener);
    }*/

    public static void thirdLogin(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(THIRD_LOGIN, context, params, null, listener);
    }

    public static void pay(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(PAY, context, params, null, listener);
    }

    public static void getChargePack(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_CHARGE_PACK, context, params, null, listener);
    }

    public static void payAli(final Context context, JSONObject params, final OnRequestDataListener listener) {
//        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(PAY_ALI, context, params, null, listener);
    }

    public static void checkUpdate(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CHECK_UPDATE, context, params, null, listener);
    }

    public static void setManager(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SET_MANAGERER, context, params, null, listener);
    }

    public static void cancelManager(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CANCEL_MANAGER, context, params, null, listener);
    }

    public static void getManagerList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_MANAGER_LIST, context, params, null, listener);
    }

    public static void getTopic(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_TOPIC, context, params, null, listener);
    }

    public static void sendCrazy(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
//        excutePost(GET_TOPIC, context, params,null, listener);
    }

    public static void getCrazy(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
//        excutePost(GET_TOPIC, context, params,null, listener);
    }

    protected static JSONObject getJsonObject(int statusCode, byte[] responseBody, OnRequestDataListener listener) {
        if (statusCode == 200) {
            String response = null;
            try {
                response = new String(responseBody, "UTF-8");
                if (response != null) {
                    JSONObject object = JSON.parseObject(response);
                    int code = object.getIntValue("code");
                    if (code != 200) {
                        String desc = object.getString("descrp");
                        if (listener != null) {
                            listener.requestFailure(code, desc);
                            return null;
                        }
                    }
                    return object;
                }
                return null;
            } catch (Exception e) {
                if (listener != null) {
                    listener.requestFailure(-1, "json解析失败!");
                }
                return null;
            }
        } else {
            if (listener != null) {
                listener.requestFailure(-1, "网络请求失败!");
            }
            return null;
        }

    }

    protected static RequestParams getRequestParams(JSONObject params) {
        RequestParams requestParams = new RequestParams();
        Iterator<?> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = (String) params.getString(key);
            requestParams.put(key, value);
        }
        return requestParams;
    }

    protected static void excutePost(String url, final Context context, JSONObject params, final SFProgrssDialog dialog, final OnRequestDataListener listener) {
        params.put("os", OS);
        params.put("soft_ver", SOFT_VER);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = getRequestParams(params);
        MyLogUtils.d("url-------->" + url);
        MyLogUtils.d("requestParams-------->" + requestParams);
        client.post(context, url, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (dialog != null && null != dialog.getContext() && null != dialog.getWindow())
                    dialog.dismiss();
                JSONObject data = getJsonObject(statusCode, responseBody, listener);
                if (data != null) {
                    MyLogUtils.d("data-------->" + data.toString());
                    listener.requestSuccess(statusCode, data);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (dialog != null && null != dialog.getContext() && null != dialog.getWindow())
                    dialog.dismiss();
                String response = null;
                try {
                    if (null != responseBody)
                        response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (listener != null) {
                    listener.requestFailure(-1, "网络请求失败!");
                }
            }
        });
    }


    public static void excuteUpload(String url, final Context context, RequestParams params, final SFProgrssDialog dialog, final OnRequestDataListener listener) {
        params.put("os", OS);
        params.put("soft_ver", SOFT_VER);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(context, url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (dialog != null)
                    dialog.dismiss();
                JSONObject data = getJsonObject(statusCode, responseBody, listener);
                if (data != null) {
                    listener.requestSuccess(statusCode, data);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (dialog != null)
                    dialog.dismiss();
                if (listener != null) {
                    listener.requestFailure(-1, "网络请求失败!");
                }
            }
        });
    }


}
