package tv.ganguo.live.view;

/**
 * Function：
 * Created by lijiefenf on 2018/1/5.
 */

public class BingModle {
    private int color;
    private int sum;
    private int number;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        sum+=number;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
