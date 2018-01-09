package tv.ganguo.live.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.ganguo.live.R;
import tv.ganguo.live.home.intf.OnRecyclerViewItemClickListener;
import tv.ganguo.live.home.model.TopicItem;

;

/**
 * Created by fengjh on 16/7/19.
 */
public class TopicRecyclerListAdapter extends RecyclerView.Adapter<TopicRecyclerListAdapter.TopicViewHolder> {

    private Context mContext;
    private ArrayList<TopicItem> mTopicItems;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public TopicRecyclerListAdapter(Context mContext, ArrayList<TopicItem> mTopicItems) {
        this.mContext = mContext;
        this.mTopicItems = mTopicItems;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_latest_topic, parent, false);
//        DisplayMetrics metrics=mContext.getResources().getDisplayMetrics();
//        int width=metrics.widthPixels;
//        LinearLayout linearLayout= (LinearLayout) inflate.findViewById(R.id.linear_item_home_latest_container);
//        ViewGroup.LayoutParams lp= linearLayout.getLayoutParams();
//        lp.width= (int) (0.4*width);
//        linearLayout.setLayoutParams(lp);
        return new TopicViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final TopicViewHolder holder, int position) {
        TopicItem item = mTopicItems.get(position);
        holder.mTopicDivider.setVisibility(View.VISIBLE);
        if((position%2)!=0){
            holder.mTopicDivider.setVisibility(View.GONE);
        }
        if(position == getItemCount()-1 ||position == getItemCount()-2 ){
            holder.mTopicFrame.setBackground(null);
        }
        holder.mItemTopicName.setText(item.getContent());
        holder.mLinearItemLatestContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRecyclerViewItemClickListener != null) {
                    mOnRecyclerViewItemClickListener.onRecyclerViewItemClick(v, holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTopicItems.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_topic_name)
        TextView mItemTopicName;
        @Bind(R.id.topic_divider)
        View mTopicDivider;
        @Bind(R.id.linear_item_home_latest_container)
        LinearLayout mLinearItemLatestContainer;
        @Bind(R.id.topic_frame)
        FrameLayout mTopicFrame;
        public TopicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
