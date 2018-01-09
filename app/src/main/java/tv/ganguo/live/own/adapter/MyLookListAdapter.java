package tv.ganguo.live.own.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tv.ganguo.live.R;
import tv.ganguo.live.own.modle.MyLookListModle;
import tv.ganguo.live.own.modle.MyShortListModle;
import tv.ganguo.live.utils.Utile;

/**
 * Function：
 * Created by lijiefenf on 2018/1/3.
 */

public class MyLookListAdapter extends RecyclerView.Adapter<MyLookListAdapter.MylookListViewHodle> {
    private List<MyLookListModle> list;
    private Context mContent;

    public MyLookListAdapter(List<MyLookListModle> list, Context mContent) {
        this.list = list;
        this.mContent = mContent;
    }

    @Override
    public MylookListViewHodle onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MylookListViewHodle(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_looklist,parent,false));
    }

    @Override
    public void onBindViewHolder(final MylookListViewHodle holder, final int position) {
        MyLookListModle myLookListModle = list.get(position);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date(myLookListModle.getCreate_time()*1000));
        holder.top.setText(format);
        if (position!=0){
            String format1 = simpleDateFormat.format(new Date(list.get(position).getCreate_time()*1000));
            if (format.equalsIgnoreCase(format1)) {
                holder.top.setVisibility(View.GONE);
            }else {
                holder.top.setVisibility(View.VISIBLE);
            }
        }else {
            holder.top.setVisibility(View.VISIBLE);
        }
        Glide.with(mContent).load(myLookListModle.getAvatar()).asBitmap().error(R.drawable.icon_avatar_default).into(new BitmapImageViewTarget(holder.img){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContent.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(10); //设置圆角弧度
                holder.img.setImageDrawable(circularBitmapDrawable);
            }
        });;
        holder.content.setText(myLookListModle.getSignature());
        holder.name.setText(myLookListModle.getUser_nicename());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClick!=null){
                    mItemClick.itemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MylookListViewHodle extends RecyclerView.ViewHolder{
        View view,lin;
        ImageView img;
        TextView name,content,top;
        public MylookListViewHodle(View itemView) {
            super(itemView);
            view=itemView;
            name= (TextView) view.findViewById(R.id.mylooklist_item_nickname);
            img= (ImageView) view.findViewById(R.id.mylooklist_item_img);
            content= (TextView) view.findViewById(R.id.mylooklist_item_content);
            top= (TextView) view.findViewById(R.id.mylooklist_item_creat);
            lin=view.findViewById(R.id.mylooklist_item_lin);
        }
    }
    public interface ItemOnClick{
        void itemClick(int postion);
    }
    private MyShortListAdapter.ItemOnClick mItemClick;
    public void setmItemClick(MyShortListAdapter.ItemOnClick mItemClick){
        this.mItemClick=mItemClick;
    }
}
