package tv.ganguo.live.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import tv.ganguo.live.R;
import tv.ganguo.live.core.BaseSiSiFragment;

;

/**
 *  附近
 * Created by hxj on 2017/12/7.
 */

public class HomeFJFgt extends BaseSiSiFragment {
    @Bind(R.id.rv)
    RecyclerView mRv;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_fjr;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRv.setLayoutManager(new GridLayoutManager(null,3));
        mRv.setAdapter(new MyAdapter());
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_fjr,parent,false));
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }
        class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
