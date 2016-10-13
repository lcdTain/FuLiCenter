package day1010.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class CartBean {
        private int id;

        private String userName;

        private int goodsId;

        private String goods;

        private int count;

        private boolean isChecked;

        private boolean checked;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setUserName(String userName){
            this.userName = userName;
        }
        public String getUserName(){
            return this.userName;
        }
        public void setGoodsId(int goodsId){
            this.goodsId = goodsId;
        }
        public int getGoodsId(){
            return this.goodsId;
        }
        public void setGoods(String goods){
            this.goods = goods;
        }
        public String getGoods(){
            return this.goods;
        }
        public void setCount(int count){
            this.count = count;
        }
        public int getCount(){
            return this.count;
        }
        public void setIsChecked(boolean isChecked){
            this.isChecked = isChecked;
        }
        public boolean getIsChecked(){
            return this.isChecked;
        }
        public void setChecked(boolean checked){
            this.checked = checked;
        }
        public boolean getChecked(){
            return this.checked;
        }

    public CartBean() {
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", goodsId=" + goodsId +
                ", goods='" + goods + '\'' +
                ", count=" + count +
                ", isChecked=" + isChecked +
                ", checked=" + checked +
                '}';
    }
}
