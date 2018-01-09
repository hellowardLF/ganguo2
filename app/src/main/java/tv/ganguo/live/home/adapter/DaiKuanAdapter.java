package tv.ganguo.live.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tv.ganguo.live.R;
import tv.ganguo.live.home.model.DaiKuanModle;

/**
 * Function：
 * Created by lijiefenf on 2017/12/23.
 */

public class DaiKuanAdapter extends BaseAdapter {
    private List<DaiKuanModle> list;
    private Context mContext;

    public DaiKuanAdapter(List<DaiKuanModle> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GlideViewHodle viewHodle = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_daikuan, parent, false);
            viewHodle = new GlideViewHodle();
            viewHodle.content = (TextView) convertView.findViewById(R.id.daikuan_item_content);
            viewHodle.title = (TextView) convertView.findViewById(R.id.daikuan_item_title);
            viewHodle.item = convertView;
            convertView.setTag(viewHodle);
        } else {
            viewHodle = (GlideViewHodle) convertView.getTag();
        }
        if (list.get(position).getIsClick()==1){
            viewHodle.title.setBackgroundResource(R.drawable.shape_money_s);
            viewHodle.content.setBackgroundResource(R.drawable.shape_money_s);
        }else {
            viewHodle.title.setBackgroundResource(R.drawable.shape_money_n);
            viewHodle.content.setBackgroundResource(R.drawable.shape_money_n);
        }
        viewHodle.title.setText(list.get(position).getName());
        viewHodle.content.setText(list.get(position).getQuota());
        return convertView;
    }

    class GlideViewHodle {
        TextView title, content;
        View item;
    }
}
