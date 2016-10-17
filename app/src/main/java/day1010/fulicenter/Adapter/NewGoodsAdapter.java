package day1010.fulicenter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.NewGoodsBean;
import day1010.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NewGoodsAdapter extends RecyclerView.Adapter {
    Context context;
    List<NewGoodsBean> mList;
    String tvFooterText;


    public NewGoodsAdapter(Context context, List<NewGoodsBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void setTvFooterText(String tvFooterText) {
        this.tvFooterText = tvFooterText;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(context, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(context, R.layout.item_newgoods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(tvFooterText);
        } else {
            GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
            NewGoodsBean newGoodsBean = mList.get(position);
            goodsViewHolder.tvGoodsName.setText(newGoodsBean.getGoodsName());
            goodsViewHolder.tvGoodsPrince.setText(newGoodsBean.getCurrencyPrice());
            ImageLoader.downloadImg(context,goodsViewHolder.ivGoodsPicture, newGoodsBean.getGoodsThumb());
        }

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


    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivGoodsPicture)
        ImageView ivGoodsPicture;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.tvGoodsPrince)
        TextView tvGoodsPrince;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
