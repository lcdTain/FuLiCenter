package day1010.fulicenter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.BoutiqueBean;
import day1010.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> mList) {
        this.context = context;
        this.mList = new ArrayList<>();
        this.mList.addAll(mList);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new NewGoodsAdapter.FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_newgoods, parent, false));
        }
        holder = new BoutiqueViewHolder(LayoutInflater.from(context).inflate(R.layout.item_boutique, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER){
            NewGoodsAdapter.FooterViewHolder footerViewHolder = (NewGoodsAdapter.FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(getStringFooter());
        }
        BoutiqueViewHolder boutiqueViewHolder = (BoutiqueViewHolder) holder;
        BoutiqueBean boutique = mList.get(position);
        ImageLoader.downloadImg(context,boutiqueViewHolder.ivBoutiquePicture,boutique.getImageurl());
        boutiqueViewHolder.tvBoutiqueTitle.setText(boutique.getTitle());
        boutiqueViewHolder.tvBoutiqueName.setText(boutique.getName());
        boutiqueViewHolder.tvBoutiqueDesc.setText(boutique.getDescription());

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    public int getStringFooter() {
        return isMore ? R.string.load_more : R.string.no_more;
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

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
