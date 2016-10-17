package day1010.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class NewGoodsBean {
        private int id;

        private int goodsId;

        private int catId;

        private String goodsName;

        private String goodsEnglishName;

        private String goodsBrief;

        private String shopPrice;

        private String currencyPrice;

        private String promotePrice;

        private String rankPrice;

        private boolean isPromote;

        private String goodsThumb;

        private String goodsImg;

        private int colorId;

        private String colorName;

        private String colorCode;

        private String colorUrl;

        private String addTime;


        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setGoodsId(int goodsId){
            this.goodsId = goodsId;
        }
        public int getGoodsId(){
            return this.goodsId;
        }
        public void setCatId(int catId){
            this.catId = catId;
        }
        public int getCatId(){
            return this.catId;
        }
        public void setGoodsName(String goodsName){
            this.goodsName = goodsName;
        }
        public String getGoodsName(){
            return this.goodsName;
        }
        public void setGoodsEnglishName(String goodsEnglishName){
            this.goodsEnglishName = goodsEnglishName;
        }
        public String getGoodsEnglishName(){
            return this.goodsEnglishName;
        }
        public void setGoodsBrief(String goodsBrief){
            this.goodsBrief = goodsBrief;
        }
        public String getGoodsBrief(){
            return this.goodsBrief;
        }
        public void setShopPrice(String shopPrice){
            this.shopPrice = shopPrice;
        }
        public String getShopPrice(){
            return this.shopPrice;
        }
        public void setCurrencyPrice(String currencyPrice){
            this.currencyPrice = currencyPrice;
        }
        public String getCurrencyPrice(){
            return this.currencyPrice;
        }
        public void setPromotePrice(String promotePrice){
            this.promotePrice = promotePrice;
        }
        public String getPromotePrice(){
            return this.promotePrice;
        }
        public void setRankPrice(String rankPrice){
            this.rankPrice = rankPrice;
        }
        public String getRankPrice(){
            return this.rankPrice;
        }
        public void setIsPromote(boolean isPromote){
            this.isPromote = isPromote;
        }
        public boolean getIsPromote(){
            return this.isPromote;
        }
        public void setGoodsThumb(String goodsThumb){
            this.goodsThumb = goodsThumb;
        }
        public String getGoodsThumb(){
            return this.goodsThumb;
        }
        public void setGoodsImg(String goodsImg){
            this.goodsImg = goodsImg;
        }
        public String getGoodsImg(){
            return this.goodsImg;
        }
        public void setColorId(int colorId){
            this.colorId = colorId;
        }
        public int getColorId(){
            return this.colorId;
        }
        public void setColorName(String colorName){
            this.colorName = colorName;
        }
        public String getColorName(){
            return this.colorName;
        }
        public void setColorCode(String colorCode){
            this.colorCode = colorCode;
        }
        public String getColorCode(){
            return this.colorCode;
        }
        public void setColorUrl(String colorUrl){
            this.colorUrl = colorUrl;
        }
        public String getColorUrl(){
            return this.colorUrl;
        }
        public void setAddTime(String addTime){
            this.addTime = addTime;
        }
        public String getAddTime(){
            return this.addTime;
        }

    public NewGoodsBean() {
    }

    @Override
    public String toString() {
        return "NewGoodsBean{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", catId=" + catId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsEnglishName='" + goodsEnglishName + '\'' +
                ", goodsBrief='" + goodsBrief + '\'' +
                ", shopPrice='" + shopPrice + '\'' +
                ", currencyPrice='" + currencyPrice + '\'' +
                ", promotePrice='" + promotePrice + '\'' +
                ", rankPrice='" + rankPrice + '\'' +
                ", isPromote=" + isPromote +
                ", goodsThumb='" + goodsThumb + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", colorId=" + colorId +
                ", colorName='" + colorName + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", colorUrl='" + colorUrl + '\'' +
                ", addTime=" + addTime +
                '}';
    }
}
