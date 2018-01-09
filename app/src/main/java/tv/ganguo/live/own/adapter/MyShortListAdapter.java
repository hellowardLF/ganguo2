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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import tv.ganguo.live.R;
import tv.ganguo.live.home.model.ShortVideoItem;
import tv.ganguo.live.utils.Utile;

/**
 * Function：
 * Created by lijiefenf on 2018/1/3.
 */

public class MyShortListAdapter extends RecyclerView.Adapter<MyShortListAdapter.MyShortListViweHodle> {
    private List<ShortVideoItem> list;
    private Context context;

    public MyShortListAdapter(List<ShortVideoItem> list, Context mContent) {
        this.list = list;
        this.context = mContent;
    }

    @Override
    public MyShortListViweHodle onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyShortListViweHodle(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myshortlist,parent,false));
    }

    @Override
    public void onBindViewHolder(final MyShortListViweHodle holder, final int position) {
        ShortVideoItem myShortListModle = list.get(position);
        Glide.with(context).load(myShortListModle.getVideo_thumb()).asBitmap().into(new BitmapImageViewTarget(holder.img){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(10); //设置圆角弧度
                        holder.img.setImageDrawable(circularBitmapDrawable);
                    }
                });;
        holder.time.setText(Utile.getTimeLag(myShortListModle.getCreate_time()));
        holder.looknumber.setText(myShortListModle.getView());

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
    class MyShortListViweHodle extends RecyclerView.ViewHolder{
        View view;
        ImageView img;
        TextView title,name,time,looknumber;
        public MyShortListViweHodle(View itemView) {
            super(itemView);
            view=itemView;
            looknumber= (TextView) itemView.findViewById(R.id.myshortvideo_item_looknumber);
            img= (ImageView) itemView.findViewById(R.id.myshortvideo_item_img);
            time= (TextView) itemView.findViewById(R.id.myshortvideo_item_time);
            title= (TextView) itemView.findViewById(R.id.myshortvideo_item_title);
            name= (TextView) itemView.findViewById(R.id.myshortvideo_item_name);
        }
    }
    public interface ItemOnClick{
        void itemClick(int postion);
    }
    private ItemOnClick mItemClick;
    public void setmItemClick(ItemOnClick mItemClick){
        this.mItemClick=mItemClick;
    }
}
