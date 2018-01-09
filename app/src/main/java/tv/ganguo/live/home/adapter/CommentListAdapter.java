package tv.ganguo.live.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.Collections;
import java.util.List;

import tv.ganguo.live.R;
import tv.ganguo.live.home.model.CommentModle;
import tv.ganguo.live.home.model.CommentsBean;

/**
 * Function：
 * Created by lijiefenf on 2018/1/2.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentItemViewHolder> {
    private List<CommentsBean> list;
    private Context mContext;

    public CommentListAdapter(List<CommentsBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public CommentItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final CommentItemViewHolder holder, int position) {
        CommentsBean commentModle=list.get(position);
        Glide.with(mContext)
                .load(commentModle.getAvatar())
                .asBitmap()
                .error(R.drawable.default_small_bg)
                .into(new BitmapImageViewTarget(holder.avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.avatar.setImageDrawable(circularBitmapDrawable);
                    }
                });
        holder.name.setText(commentModle.getUser_nicename());
        holder.content.setText(commentModle.getContent());
        holder.time.setText(commentModle.getCreate_time());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class CommentItemViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar,gender;
        TextView name,content,time;
        public CommentItemViewHolder(View itemView) {
            super(itemView);
            avatar= (ImageView) itemView.findViewById(R.id.comment_item_avatar);
            gender= (ImageView) itemView.findViewById(R.id.comment_item_gender);
            name= (TextView) itemView.findViewById(R.id.comment_item_name);
            content= (TextView) itemView.findViewById(R.id.comment_item_content);
            time= (TextView) itemView.findViewById(R.id.comment_item_time);
        }
    }
}
