package tv.ganguo.live.home.model;

import java.io.Serializable;

/**
 * Function：
 * Created by lijiefenf on 2018/1/2.
 */

public class ShortVideoItem implements Serializable{

    /**
     * id : 5
     * uid : 156295
     * term_id : 1
     * tag_id : null
     * video_title : 如何测试一下
     * video_thumb : http://116.62.173.251/data/upload/thumb/5a4b02e9ef891.png
     * video_url : http://116.62.173.251/data/upload/video/5a4b02e9f1a6d.mp4
     * video_time : 5874
     * status : 1
     * comments : 0
     * favorites : 0
     * hits : 1
     * longitude : null
     * latitude : null
     * create_time : 1514865385
     * user_nicename : 锋
     * avatar : http://116.62.173.251/data/upload/avatar/5a38e527d65c3.jpg
     * is_favorites : 1
     */




    private String avatar;
    private int comments;
    private long create_time;
    private String favorites;
    private int hits;
    private String id;
    private int is_favorites;
    private String latitude;
    private String longitude;
    private String status;
    private String term_id;
    private String uid;
    private String user_nicename;
    private String video_thumb;
    private String video_time;
    private String video_title;
    private String video_url;
    private String view;
    private String tag_id;


    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTerm_id() {
        return term_id;
    }

    public void setTerm_id(String term_id) {
        this.term_id = term_id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_time() {
        return video_time;
    }

    public void setVideo_time(String video_time) {
        this.video_time = video_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getUser_nicename() {
        return user_nicename;
    }

    public void setUser_nicename(String user_nicename) {
        this.user_nicename = user_nicename;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIs_favorites() {
        return is_favorites;
    }

    public void setIs_favorites(int is_favorites) {
        this.is_favorites = is_favorites;
    }
}
