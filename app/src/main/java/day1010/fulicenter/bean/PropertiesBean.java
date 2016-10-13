package day1010.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
import java.util.ArrayList;
import java.util.List;
public class PropertiesBean {
        private int id;

        private int goodsId;

        private int colorId;

        private String colorName;

        private String colorCode;

        private String colorImg;

        private String colorUrl;

        private List<String> albums;

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
        public void setColorImg(String colorImg){
            this.colorImg = colorImg;
        }
        public String getColorImg(){
            return this.colorImg;
        }
        public void setColorUrl(String colorUrl){
            this.colorUrl = colorUrl;
        }
        public String getColorUrl(){
            return this.colorUrl;
        }
        public void setAlbums(List<String> albums){
            this.albums = albums;
        }
        public List<String> getAlbums(){
            return this.albums;
        }

    public PropertiesBean() {
    }

    @Override
    public String toString() {
        return "PropertiesBean{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", colorId=" + colorId +
                ", colorName='" + colorName + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", colorImg='" + colorImg + '\'' +
                ", colorUrl='" + colorUrl + '\'' +
                ", albums=" + albums +
                '}';
    }
}
