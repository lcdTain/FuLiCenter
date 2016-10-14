package day1010.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class CategoryChildBean {
        private int id;

        private int parentId;

        private String name;

        private String imageUrl;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setParentId(int parentId){
            this.parentId = parentId;
        }
        public int getParentId(){
            return this.parentId;
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

    public CategoryChildBean() {
    }

    @Override
    public String toString() {
        return "CategoryChildBean{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
