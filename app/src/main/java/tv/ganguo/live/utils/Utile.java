package tv.ganguo.live.utils;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/12.
 * Author: XuDeLong
 */
public class Utile {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static String getTimeLag(long time){
        long l = System.currentTimeMillis()/1000 - time;
        if (l<1){
            return "刚刚";
        }else if (l>1&&l<60){
            return l+"分钟前";
        }else if (l/60>=1&&l/60<24){
            return l/60+"小时前";
        }else {
            return l/60/24+"天前";
        }
    }

}
