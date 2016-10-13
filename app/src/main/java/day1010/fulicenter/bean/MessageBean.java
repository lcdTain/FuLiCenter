package day1010.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class MessageBean {
        private boolean success;

        private String msg;

        public void setSuccess(boolean success){
            this.success = success;
        }
        public boolean getSuccess(){
            return this.success;
        }
        public void setMsg(String msg){
            this.msg = msg;
        }
        public String getMsg(){
            return this.msg;
        }

    public MessageBean() {
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
