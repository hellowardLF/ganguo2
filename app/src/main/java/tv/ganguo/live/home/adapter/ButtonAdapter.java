package tv.ganguo.live.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tv.ganguo.live.BorrowMoneyActivity;
import tv.ganguo.live.R;
import tv.ganguo.live.home.model.ButtonItem;
import tv.ganguo.live.own.WebviewActivity;

;

/**
 * Created by hxj on 2017/12/4.
 */

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {
    private List<ButtonItem> mList;
    private LayoutInflater mInflater;
    private Context mContext;
    public ButtonAdapter(Context mContext,List<ButtonItem> mList) {
        this.mContext=mContext;
        this.mList = mList;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public ButtonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_button,parent,false));
    }

    @Override
    public void onBindViewHolder(final ButtonAdapter.ViewHolder holder, final int position) {
        holder.buttonContent.setText(mList.get(position).getName());
        Glide.with(mContext).load(mList.get(position).getRes()).into(holder.buttonImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if (position==0){
                    Intent mIntent=new Intent(mContext, BorrowMoneyActivity.class);
                    mContext.startActivity(mIntent);
                }else {
                    Intent mIntent=new Intent(mContext, WebviewActivity.class);
                    mIntent.putExtra("title",mList.get(position).getName());
                    mIntent.putExtra("jump","https://www.baidu.com/");
                    mContext.startActivity(mIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView buttonContent;
        ImageView buttonImg;
        public ViewHolder(View itemView) {
            super(itemView);
            buttonContent= (TextView) itemView.findViewById(R.id.tv);
            buttonImg= (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
