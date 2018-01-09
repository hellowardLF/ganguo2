package tv.ganguo.live.home.model;

import java.util.List;

/**
 * Function：
 * Created by lijiefenf on 2018/1/2.
 */

public class CommentModle {


        /**
         * attention_status : 0
         * avatar : http://116.62.173.251/data/upload/avatar/5a38e527d65c3.jpg
         * comments : [{"avatar":"http://116.62.173.251/data/upload/avatar/5a38e527d65c3.jpg","content":"","create_time":"2018/01/03","id":"2","status":"1","to_uid":"156295","uid":"156295","user_nicename":"锋","video_id":"6"},{"avatar":"http://116.62.173.251/data/upload/avatar/5a38e527d65c3.jpg","content":"","create_time":"2018/01/03","id":"3","status":"1","to_uid":"156295","uid":"156295","user_nicename":"锋","video_id":"6"},{"avatar":"http://116.62.173.251/data/upload/avatar/5a38e527d65c3.jpg","content":"","create_time":"2018/01/03","id":"5","status":"1","to_uid":"156295","uid":"156295","user_nicename":"锋","video_id":"6"}]
         * create_time : 2018/01/02
         * favorites : 0
         * hits : 0
         * id : 6
         * latitude : 31.27106
         * longitude : 121.531134
         * status : 1
         * term_id : 1
         * uid : 156295
         * user_nicename : 锋
         * video_thumb : http://116.62.173.251/data/upload/thumb/5a4b1fc0ca4e1.png
         * video_time : 6293
         * video_title : 如何哈哈
         * video_url : http://116.62.173.251/data/upload/video/1514872755660.mp4
         * view : 25
         */

        private int attention_status;
        private String avatar;
        private String create_time;
        private String favorites;
        private String hits;
        private String id;
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
        private List<CommentsBean> comments;
        private int comments_num;


    public int getComments_num() {
        return comments_num;
    }

    public void setComments_num(int comments_num) {
        this.comments_num = comments_num;
    }

    public int getAttention_status() {
            return attention_status;
        }

        public void setAttention_status(int attention_status) {
            this.attention_status = attention_status;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getFavorites() {
            return favorites;
        }

        public void setFavorites(String favorites) {
            this.favorites = favorites;
        }

        public String getHits() {
            return hits;
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTerm_id() {
            return term_id;
        }

        public void setTerm_id(String term_id) {
            this.term_id = term_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_nicename() {
            return user_nicename;
        }

        public void setUser_nicename(String user_nicename) {
            this.user_nicename = user_nicename;
        }

        public String getVideo_thumb() {
            return video_thumb;
        }

        public void setVideo_thumb(String video_thumb) {
            this.video_thumb = video_thumb;
        }

        public String getVideo_time() {
            return video_time;
        }

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }

        public String getVideo_title() {
            return video_title;
        }

        public void setVideo_title(String video_title) {
            this.video_title = video_title;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }


}
