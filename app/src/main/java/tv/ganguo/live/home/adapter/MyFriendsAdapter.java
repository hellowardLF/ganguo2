package tv.ganguo.live.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.bumptech.glide.Glide;
import com.smart.androidutils.utils.SharePrefsUtils;

import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.utils.LCIMConstants;
import tv.ganguo.live.R;
import tv.ganguo.live.home.model.FriendModle;
import tv.ganguo.live.lean.Chat;
import tv.ganguo.live.utils.GlideRoundTransform;

public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.ViewHolder> {
    private static final String TAG = "MyFriendsAdapter";
    private Context mContext;
    private List<FriendModle> list;

    public MyFriendsAdapter(Context mContext, List<FriendModle> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyFriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyFriendsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sl, parent, false));
    }

    @Override

    public void onBindViewHolder(MyFriendsAdapter.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
//                        openActivity(MessageActivity.class);
                if (mClick != null) {
                    mClick.itemClick(position);
                }
            }
        });
        if (TextUtils.isEmpty(list.get(position).getNote())) {
            holder.mTextName.setText(list.get(position).getUser_nicename());
        } else {
            holder.mTextName.setText(list.get(position).getNote());
        }
        if (!TextUtils.isEmpty(list.get(position).getSignature())) {
            holder.signature.setText(list.get(position).getSignature());
        }
        if (list.get(position).getBirthday() != 0) {
            holder.age.setText(list.get(position).getBirthday() + "岁");
            holder.age.setVisibility(View.VISIBLE);
        } else {
            holder.age.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(list.get(position).getAvatar()).transform(new GlideRoundTransform(mContext, 5))
                .into(holder.mView);
        String longitude = (String) SharePrefsUtils.get(mContext, "user", "longitude", "");
        String latitude = (String) SharePrefsUtils.get(mContext, "user", "latitude", "");
        if (!TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(latitude)) {
            DPoint start = new DPoint(Double.parseDouble(latitude), Double.parseDouble(longitude));
            if (!TextUtils.isEmpty(list.get(position).getLongitude()) && !TextUtils.isEmpty(list.get(position).getLongitude())) {
                DPoint end = new DPoint(Double.parseDouble(list.get(position).getLatitude()), Double.parseDouble(list.get(position).getLongitude()));
                float v = CoordinateConverter.calculateLineDistance(start, end);
                holder.distance.setVisibility(View.VISIBLE);
                Log.e(TAG, "onBindViewHolder: " + v);
                float v1 = (float) ((int) v / 100) / 10;
                if (v1 < 0.5) {
                    holder.distance.setText("附近");
                } else {
                    holder.distance.setText(v1 + "公里");
                }
            } else {
                holder.distance.setVisibility(View.GONE);
            }
        } else {
            holder.distance.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mView;
        TextView mTextView;
        TextView mTextName;
        TextView distance;
        TextView signature;
        TextView age;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = (ImageView) itemView.findViewById(R.id.img);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
            distance = (TextView) itemView.findViewById(R.id.friend_item_distance);
            signature = (TextView) itemView.findViewById(R.id.friend_item_signature);
            age = (TextView) itemView.findViewById(R.id.friend_item_age);

        }
    }

    public interface MyFriendItemClick {
        void itemClick(int postion);
    }

    private MyFriendItemClick mClick;

    public void setItemClick(MyFriendItemClick click) {
        mClick = click;
    }
}
