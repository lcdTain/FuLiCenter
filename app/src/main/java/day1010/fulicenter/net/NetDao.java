package day1010.fulicenter.net;

import android.content.Context;

import java.io.File;

import day1010.fulicenter.I;
import day1010.fulicenter.bean.BoutiqueBean;
import day1010.fulicenter.bean.CartBean;
import day1010.fulicenter.bean.CategoryChildBean;
import day1010.fulicenter.bean.CategoryGroupBean;
import day1010.fulicenter.bean.CollectBean;
import day1010.fulicenter.bean.GoodsDetailsBean;
import day1010.fulicenter.bean.MessageBean;
import day1010.fulicenter.bean.NewGoodsBean;
import day1010.fulicenter.bean.Result;
import day1010.fulicenter.utils.MD5;
import day1010.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NetDao {
    public static void downloadNewGoods(Context context,int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
    public static void downloadGoodsDetail(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,goodsId+"")
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }
    public static void downloadBoutique(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
    public static void downloadCategoryGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }
    public static void downloadCategoryChild(Context context,int parentId ,OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(parentId))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }
    public static void downloadCategoryGoods(Context context,int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
    public static void register(Context context, String  username, String nickname,String password, OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nickname)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }
    public static void login(Context context, String username, String password, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.PASSWORD,MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);

    }
    public static void updateNick(Context context, String username, String nick, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .targetClass(String.class)
                .execute(listener);

    }
    public static void updateAvatar(Context context, String username, File file, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,username)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);

    }
    public static void syncUserInfo(Context context, String username, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME,username)
                .targetClass(String.class)
                .execute(listener);

    }

    public static void syncCollectCount(Context context, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public static void downloadCollects(Context context, String username, int pageId, OkHttpUtils.OnCompleteListener<CollectBean[]> listener){
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.PAGE_ID,pageId+"")
                .addParam(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                .targetClass(CollectBean[].class)
                .execute(listener);
    }
    public static void deleteCollect(Context context, String username, int goodsId, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,goodsId+"")
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public static void isCollect(Context context,String username,int goodsId,OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,goodsId+"")
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public static void addCollect(Context context, String username, int goodsId, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,goodsId+"")
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void downloadCart(Context context,String username, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME,username)
                .targetClass(String.class)
                .execute(listener);
    }
    public static void updateCart(Context context, int cartId, int count, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,cartId+"")
                .addParam(I.Cart.COUNT,count+"")
                .addParam(I.Cart.IS_CHECKED,I.CART_CHECKED_DEFAULT+"")
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public static void delCart(Context context,int cartId,OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,cartId+"")
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public static void addGoodsCart(Context context, int goodsId,String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.GOODS_ID,goodsId+"")
                .addParam(I.Cart.USER_NAME,username)
                .addParam(I.Cart.COUNT,1+"")
                .addParam(I.Cart.IS_CHECKED,I.CART_CHECKED_DEFAULT+"")
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
