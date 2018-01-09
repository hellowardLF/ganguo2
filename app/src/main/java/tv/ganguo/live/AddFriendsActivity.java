package tv.ganguo.live;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import tv.ganguo.live.core.BaseSiSiActivity;

/**
 * Function：添加号的选项
 * Created by lijiefenf on 2017/12/20.
 */

public class AddFriendsActivity extends BaseSiSiActivity {
    @Bind(R.id.image_top_back)
    ImageView mBack;
    @Bind(R.id.text_top_title)
    TextView mTitle;
    @Bind(R.id.add_friend_select)
    RelativeLayout mSelect;
    @Bind(R.id.add_friend_id)
    RelativeLayout mId;
    @Bind(R.id.add_friend_phone)
    RelativeLayout mPhone;
    @Bind(R.id.add_friend_twocode)
    RelativeLayout mTwo;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText("添加朋友");
        initListener();
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
