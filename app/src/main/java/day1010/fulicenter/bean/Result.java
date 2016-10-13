package day1010.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Result {
        private int retCode;

        private boolean retMsg;

        private String retData;

        public void setRetCode(int retCode){
            this.retCode = retCode;
        }
        public int getRetCode(){
            return this.retCode;
        }
        public void setRetMsg(boolean retMsg){
            this.retMsg = retMsg;
        }
        public boolean getRetMsg(){
            return this.retMsg;
        }
        public void setRetData(String retData){
            this.retData = retData;
        }
        public String getRetData(){
            return this.retData;
        }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "retCode=" + retCode +
                ", retMsg=" + retMsg +
                ", retData='" + retData + '\'' +
                '}';
    }
}
