package day1010.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class CategoryGroupBean {
        private int id;

        private String name;

        private String imageUrl;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setImageUrl(String imageUrl){
            this.imageUrl = imageUrl;
        }
        public String getImageUrl(){
            return this.imageUrl;
        }

    public CategoryGroupBean() {
    }

    @Override
    public String toString() {
        return "CategoryGroupBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
