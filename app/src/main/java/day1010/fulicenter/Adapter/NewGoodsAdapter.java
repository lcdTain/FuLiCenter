package day1010.fulicenter.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.activity.GoodsDetailActivity;
import day1010.fulicenter.activity.MainActivity;
import day1010.fulicenter.bean.NewGoodsBean;
import day1010.fulicenter.utils.ImageLoader;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NewGoodsAdapter extends RecyclerView.Adapter {
    Activity context;
    List<NewGoodsBean> mList;
    String tvFooterText;
    boolean isMore;

    private int footString;

    int sortBy = I.SORT_BY_ADDTIME_DESC;

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
        sortBy();
        notifyDataSetChanged();
    }

    public NewGoodsAdapter(Activity context, List<NewGoodsBean> mList) {
        this.context = context;
        this.mList = mList;
        mList = new ArrayList<>();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
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
            footerViewHolder.tvFooter.setText(getFootString());
        } else {
            GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
            NewGoodsBean newGoodsBean = mList.get(position);
            goodsViewHolder.tvGoodsName.setText(newGoodsBean.getGoodsName());
            goodsViewHolder.tvGoodsPrince.setText(newGoodsBean.getCurrencyPrice());
            ImageLoader.downloadImg(context, goodsViewHolder.ivGoodsPicture, newGoodsBean.getGoodsThumb());
            goodsViewHolder.layoutGoods.setTag(newGoodsBean.getGoodsId());
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

    public void initData(ArrayList<NewGoodsBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getFootString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class GoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivGoodsPicture)
        ImageView ivGoodsPicture;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.tvGoodsPrince)
        TextView tvGoodsPrince;
        @Bind(R.id.layout_goods)
        LinearLayout layoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick() {
            int goodsId = (int) layoutGoods.getTag();
            MFGT.gotoGoodsDetailsActivity(context,goodsId);
//            context.startActivity(new Intent(context, GoodsDetailActivity.class)
//                    .putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId));

        }
    }
    private void sortBy(){
        Collections.sort(mList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean left, NewGoodsBean right) {
                int result = 0;
                switch (sortBy){
                    case I.SORT_BY_ADDTIME_ASC:
                        result = (int)(Long.valueOf(left.getAddTime())-Long.valueOf(right.getAddTime()));
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result = (int)(Long.valueOf(right.getAddTime())-Long.valueOf(left.getAddTime()));
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result = getPrince(left.getCurrencyPrice())-getPrince(right.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result = getPrince(right.getCurrencyPrice())-getPrince(left.getCurrencyPrice());
                        break;
                }
                return result;
            }
            private int getPrince(String price){
                price = price.substring(price.indexOf("￥")+1);
                return Integer.valueOf(price);
            }
        });
    }
}
