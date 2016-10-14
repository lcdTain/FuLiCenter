package day1010.fulicenter.bean;


public class BoutiqueBean {
    
        private int id;

        private String title;

        private String description;

        private String name;

        private String imageurl;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setDescription(String description){
            this.description = description;
        }
        public String getDescription(){
            return this.description;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setImageurl(String imageurl){
            this.imageurl = imageurl;
        }
        public String getImageurl(){
            return this.imageurl;
        }

    public BoutiqueBean() {
    }

    @Override
    public String toString() {
        return "BoutiqueBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }
}
