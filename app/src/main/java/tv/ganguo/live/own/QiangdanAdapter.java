package tv.ganguo.live.own;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tv.ganguo.live.R;
import tv.ganguo.live.own.modle.QiangDanModle;

;

/**
 * Function：抢单列表的adapter
 * Created by lijiefenf on 2017/12/20.
 */

public class QiangdanAdapter extends RecyclerView.Adapter<QiangdanAdapter.ViewHodle> {
    private List<QiangDanModle> list;
    private Context mContext;

    public QiangdanAdapter(List<QiangDanModle> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ViewHodle onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodle(LayoutInflater.from(mContext).inflate(R.layout.adapter_qiangdan_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHodle holder, final int position) {
        QiangDanModle qiangDanModle = list.get(position);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQianDanClick != null) {
                    mQianDanClick.ItemClick(position);
                }
            }
        });
        holder.qiangdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQianDanClick != null) {
                    mQianDanClick.QiangDan(position);
                }
            }
        });
        holder.name.setText(qiangDanModle.getTrue_name());
        if (qiangDanModle.getAddress()!=null){
            if(qiangDanModle.getAddress().length()>7){
                holder.address.setText(qiangDanModle.getAddress().substring(0, 6) + "......");
            }else {
                holder.address.setText(qiangDanModle.getAddress());
            }
        }
        holder.money.setText(qiangDanModle.getAmount()+"元");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        holder.creat_time.setText( sdf.format(new Date(qiangDanModle.getCreate_time())));
        holder.time.setText(qiangDanModle.getTerm());
        holder.job.setText(qiangDanModle.getOccupation());
        holder.content.setText(qiangDanModle.getAmount_use());
        if (!TextUtils.isEmpty(qiangDanModle.getBirthday())){
            holder.year.setText(qiangDanModle.getBirthday());
        }
        holder.shouru.setText(qiangDanModle.getMonthly_income());
        holder.job.setText(qiangDanModle.getOccupation());
        StringBuffer stringBuffer=new StringBuffer();
        if (qiangDanModle.getProvident_fund()==0){
            stringBuffer.append("无公积金");
        }else {
            stringBuffer.append("有公积金");
        }
        if (qiangDanModle.getSocial_security()==0){
            stringBuffer.append("  无社保");
        }else {
            stringBuffer.append("  有社保");
        }
        if (qiangDanModle.getCar_production()==0){
            stringBuffer.append("  无车产");
        }else {
            stringBuffer.append("  有车产");
        }
        if (qiangDanModle.getHouse_production()==0){
            stringBuffer.append("  无房产");
        }else {
            stringBuffer.append("  有房产");
        }
        holder.property.setText(stringBuffer);

    }

    interface QianDanClick {
        void ItemClick(int postion);

        void QiangDan(int postion);
    }

    private QianDanClick mQianDanClick;

    public void setmQianDanClick(QianDanClick mQianDanClick) {
        this.mQianDanClick = mQianDanClick;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodle extends RecyclerView.ViewHolder {
        TextView name, money, time, creat_time, address, job, content, year, married, education, shouru, property;
        ImageView qiangdan;
        View view;

        public ViewHodle(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.qiangdan_name);
            money = (TextView) itemView.findViewById(R.id.qiangdan_money);
            time = (TextView) itemView.findViewById(R.id.qiangdan_time);
            creat_time = (TextView) itemView.findViewById(R.id.qiangdan_creat_time);
            address = (TextView) itemView.findViewById(R.id.qiangdan_address);
            job = (TextView) itemView.findViewById(R.id.qiangdan_job);
            content = (TextView) itemView.findViewById(R.id.qiangdan_content);
            year = (TextView) itemView.findViewById(R.id.qiangdan_year);
            married = (TextView) itemView.findViewById(R.id.qiangdan_married);
            education = (TextView) itemView.findViewById(R.id.qiangdan_education);
            shouru = (TextView) itemView.findViewById(R.id.qiangdan_shouru);
            property = (TextView) itemView.findViewById(R.id.qiangdan_property);
            qiangdan = (ImageView) itemView.findViewById(R.id.qiangdan_qiangdan);
        }
    }
}
