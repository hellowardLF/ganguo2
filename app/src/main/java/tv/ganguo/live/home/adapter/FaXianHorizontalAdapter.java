package tv.ganguo.live.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tv.ganguo.live.R;

;

/**
 * Function：
 * Created by lijiefenf on 2017/12/21.
 */

class FaXianHorizontalAdapter extends RecyclerView.Adapter<FaXianHorizontalAdapter.HorizontalViewHolder> {
    private Context mContext;
    private List<String> list;


    public FaXianHorizontalAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HorizontalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal,parent,false));
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
    class HorizontalViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle,content,yuyue;
        public HorizontalViewHolder(View itemView) {
            super(itemView);
            mTitle= (TextView) itemView.findViewById(R.id.horizontal_title);
            content= (TextView) itemView.findViewById(R.id.horizontal_content);
            yuyue= (TextView) itemView.findViewById(R.id.horizontal_yuyue);
        }
    }
}
