package day1010.fulicenter.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.CartBean;
import day1010.fulicenter.bean.GoodsDetailsBean;
import day1010.fulicenter.bean.MessageBean;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.ImageLoader;
import day1010.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/19.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Activity context;
    ArrayList<CartBean> mList;
    @Bind(R.id.checkBox)
    CheckBox checkBox;


    public CartAdapter(Activity context, ArrayList<CartBean> list) {
        this.context = context;
        this.mList = list;
    }


    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = null;
        holder = new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        final CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        ImageLoader.downloadImg(context, holder.ivGoodsPicture, goods.getGoodsThumb());
        holder.tvUsername.setText(goods.getGoodsName());
        holder.tvGoodPrince.setText(goods.getCurrencyPrice());
        holder.checkBox.setChecked(false);
        holder.rlCartItem.setTag(cartBean);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cartBean.setChecked(isChecked);
                context.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
            }
        });
        holder.ivAdd.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return I.TYPE_ITEM;
    }


    public void initData(ArrayList<CartBean> list) {
        mList = list;
        notifyDataSetChanged();
    }


    class CartViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.checkBox)
        CheckBox checkBox;
        @Bind(R.id.ivGoodsPicture)
        ImageView ivGoodsPicture;
        @Bind(R.id.tvUsername)
        TextView tvUsername;
        @Bind(R.id.ivAdd)
        ImageView ivAdd;
        @Bind(R.id.tvGoodsCount)
        TextView tvGoodsCount;
        @Bind(R.id.ivDel)
        ImageView ivDel;
        @Bind(R.id.tvGoodPrince)
        TextView tvGoodPrince;
        @Bind(R.id.rlCartItem)
        RelativeLayout rlCartItem;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.rlCartItem)
        public void onCartClick() {
            CartBean bean = (CartBean) rlCartItem.getTag();
            // MFGT.gotoBoutiqueChildActivity(context,bean);
        }
        @OnClick(R.id.ivAdd)
        public void addCart(){
            final int position = (int) ivAdd.getTag();
            CartBean cart = mList.get(position);
            NetDao.updateCart(context, cart.getId(), cart.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()){
                        mList.get(position).setCount(mList.get(position).getCount()+1);
                        context.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                        tvGoodsCount.setText("("+(mList.get(position).getCount())+")");
                    }

                }

                @Override
                public void onError(String error) {

                }
            });

        }
        @OnClick(R.id.ivDel)
        public void delCart(){
            final int position = (int) ivAdd.getTag();
            CartBean cart = mList.get(position);
            if (cart.getCount()>1){
                NetDao.updateCart(context, cart.getId(), cart.getCount() - 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()){
                            mList.get(position).setCount(mList.get(position).getCount()-1);
                            context.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                            tvGoodsCount.setText("("+(mList.get(position).getCount())+")");
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }else{
                NetDao.delCart(context, cart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()){
                            mList.remove(position);
                            context.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

            }
        }
    }
}
