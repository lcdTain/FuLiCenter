package day1010.fulicenter.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.BoutiqueBean;
import day1010.fulicenter.utils.ImageLoader;
import day1010.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter<BoutiqueAdapter.BoutiqueViewHolder> {
    Activity context;
    ArrayList<BoutiqueBean> mList;


    public BoutiqueAdapter(Activity context, ArrayList<BoutiqueBean> mList) {
        this.context = context;
        this.mList = new ArrayList<>();
        this.mList.addAll(mList);
    }


    @Override
    public BoutiqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoutiqueViewHolder holder = null;
        holder = new BoutiqueViewHolder(LayoutInflater.from(context).inflate(R.layout.item_boutique, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BoutiqueViewHolder holder, int position) {
        BoutiqueBean boutique = mList.get(position);
        ImageLoader.downloadImg(context, holder.ivBoutiquePicture, boutique.getImageurl());
        holder.tvBoutiqueTitle.setText(boutique.getTitle());
        holder.tvBoutiqueName.setText(boutique.getName());
        holder.tvBoutiqueDesc.setText(boutique.getDescription());
        holder.rlBoutiqueItem.setTag(boutique);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return I.TYPE_ITEM;
    }


    public void initData(ArrayList<BoutiqueBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivBoutique_picture)
        ImageView ivBoutiquePicture;
        @Bind(R.id.tvBoutique_title)
        TextView tvBoutiqueTitle;
        @Bind(R.id.tvBoutique_name)
        TextView tvBoutiqueName;
        @Bind(R.id.tvBoutique_desc)
        TextView tvBoutiqueDesc;
        @Bind(R.id.rlBoutiqueItem)
        RelativeLayout rlBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.rlBoutiqueItem)
        public void onBoutiqueClick(){
            BoutiqueBean bean = (BoutiqueBean) rlBoutiqueItem.getTag();
            MFGT.gotoBoutiqueChildActivity(context,bean);
        }
    }
}
