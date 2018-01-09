package tv.ganguo.live.own.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tv.ganguo.live.R;
import tv.ganguo.live.home.model.DaiKuanModle;
import tv.ganguo.live.own.modle.QianDanInfoModle;

/**
 * Function：
 * Created by lijiefenf on 2017/12/27.
 */

public class QianDanTypeAdapter extends BaseAdapter {
    private List<DaiKuanModle> list;
    private Context mContext;

    public QianDanTypeAdapter(List<DaiKuanModle> list, Context mContext) {
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
        QianDanTyepViewHodle viewHodle;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qiandanitem,parent,false);
            viewHodle=new QianDanTyepViewHodle();
            viewHodle.mTitle= (TextView) convertView.findViewById(R.id.qiangdan_item_title);
            viewHodle.mContent= (TextView) convertView.findViewById(R.id.qiangdan_item_content);
            viewHodle.view=convertView;
            convertView.setTag(viewHodle);
        }else {
            viewHodle= (QianDanTyepViewHodle) convertView.getTag();
        }
        viewHodle.mTitle.setText(list.get(position).getName());
        viewHodle.mTitle.setText(list.get(position).getQuota());
        if (list.get(position).getIsClick()==1){
            viewHodle.view.setBackgroundResource(R.color.qiangdanlinbg);
        }else {
            viewHodle.view.setBackgroundResource(R.color.white);
        }
        return convertView;
    }

    private class QianDanTyepViewHodle {
        TextView mTitle;
        TextView mContent;
        View view;
    }
}
