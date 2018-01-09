package tv.ganguo.live.home.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.smart.androidutils.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import tv.ganguo.live.R;
import tv.ganguo.live.home.intf.OnRecyclerViewItemClickListener;
import tv.ganguo.live.home.model.BannerItem;
import tv.ganguo.live.home.model.VideoItem;

;

/**
 * Created by fengjh on 16/7/19.
 */
public class FollowRecyclerListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<VideoItem> mVideoItems;

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public FollowRecyclerListAdapter(Context mContext, ArrayList<VideoItem> mVideoItems) {
        this.mContext = mContext;
        this.mVideoItems = mVideoItems;

    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder view = null;
        switch (viewType) {
            case 0:
                view=new TopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_fanxian_top, parent, false));
                break;
            case 1:
                view = new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_faxian, parent, false));
                break;
        }
        return view;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof VideoViewHolder) {

         /*   final VideoViewHolder holder= (VideoViewHolder) viewHolder;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(imageWidth, imageHeight);
            VideoItem item = mVideoItems.get(position);
            Glide.with(this.mContext).load(item.getAvatar())
                    .error(R.drawable.icon_avatar_default)
                    .transform(new GlideCircleTransform(this.mContext))
                    .into(holder.mImageLiveUserAvatar);
            Glide.with(this.mContext).load(item.getSmeta())
                    .error(R.drawable.default_bg)
                    .into(holder.mImageVideoPreview);
            holder.mTextTitle.setText(item.getChannel_title());
            holder.mTextUserLevel.setText(item.getUser_level());
            holder.mTextLiveOnlineNum.setText(item.getOnline_num());
            holder.mTextLiveTitle.setText(item.getChannel_title());
            if(StringUtils.isNotEmpty(item.getChannel_location()))
                holder.mTextLiveUserLocation.setText(item.getChannel_location());
            holder.mTextLiveUserNicename.setText(item.getUser_nicename());
            holder.mImageVideoPreview.setLayoutParams(params);
            if (StringUtils.isNotEmpty(item.getPrice()) && Integer.parseInt(item.getPrice())>0){
                holder.mTextLiveStatus.setText("付费"+item.getPrice());
            }
            if(StringUtils.isNotEmpty(item.getNeed_password()) && Integer.parseInt(item.getNeed_password())==1){
                holder.mTextLiveStatus.setText("密码");
            }

            int level = Integer.parseInt(item.getUser_level());
            if(level<5){
                holder.mTextUserLevel.setBackgroundResource(R.drawable.level1);
            }
            if(level>4 && level<9 ){
                holder.mTextUserLevel.setBackgroundResource(R.drawable.level2);
            }
            if(level>8 && level<13 ){
                holder.mTextUserLevel.setBackgroundResource(R.drawable.level3);
            }
            if(level>12 ){
                holder.mTextUserLevel.setBackgroundResource(R.drawable.level3);
            }

            holder.mLinearVideoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRecyclerViewItemClickListener != null) {
                        mOnRecyclerViewItemClickListener.onRecyclerViewItemClick(v, holder.getLayoutPosition());
                    }
                }
            });*/
            //TODO 用户等级
        } else if (viewHolder instanceof TopViewHolder) {
            TopViewHolder holder = (TopViewHolder) viewHolder;
            int bannerWidth = DensityUtils.screenWidth(mContext);
            int bannerHeight = (bannerWidth * 270) / 720;
            holder.mBanner.setLayoutParams(new LinearLayout.LayoutParams(bannerWidth, bannerHeight));
            ArrayList<BannerItem> mBannerItems = new ArrayList<>();
            BannerItem item = new BannerItem();
            item.setJump("https://www.baidu.com");
            item.setPic("http://img07.tooopen.com/images/20170316/tooopen_sy_201956178977.jpg");
            item.setTitle("这是标题");
            item.setType("1");
            mBannerItems.add(item);
            holder.mBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new BannerItemViewHolder();
                }
            }, mBannerItems)
                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
            holder.mBanner.startTurning(3000);
            LinearLayoutManager ms = new LinearLayoutManager(mContext);
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.mRecycler.setLayoutManager(ms);
            List<String> list = new ArrayList<>();
            holder.mRecycler.setAdapter(new FaXianHorizontalAdapter(mContext, list));
        }

    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        switch (position) {
            case 0:
                type = 0;
                break;
            default:
                type = 1;
                break;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    private class VideoViewHolder extends RecyclerView.ViewHolder {
        public VideoViewHolder(View itemView) {
            super(itemView);
        }

    /*    @Bind(R.id.item_linear_video_container)
        LinearLayout mLinearVideoContainer;
        @Bind(R.id.item_image_video_preview)
        ImageView mImageVideoPreview;
        @Bind(R.id.image_live_user_avatar)
        ImageView mImageLiveUserAvatar;
        @Bind(R.id.text_live_user_nicename)
        TextView mTextLiveUserNicename;
        @Bind(R.id.text_live_user_location)
        TextView mTextLiveUserLocation;
        @Bind(R.id.text_live_online_num)
        TextView mTextLiveOnlineNum;
        @Bind(R.id.text_live_status)
        TextView mTextLiveStatus;
        @Bind(R.id.text_live_title)
        TextView mTextLiveTitle;
        @Bind(R.id.tv_user_level)
        TextView mTextUserLevel;
        @Bind(R.id.text_title)
        TextView mTextTitle;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }*/
    }

    private class TopViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecycler;
        ConvenientBanner mBanner;

        public TopViewHolder(View itemView) {
            super(itemView);
            mRecycler = (RecyclerView) itemView.findViewById(R.id.fanxian_top_recycle);
            mBanner = (ConvenientBanner) itemView.findViewById(R.id.fanxian_top_banner);
        }
    }


}
