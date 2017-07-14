package com.me.practicecode.retrofit.bean;

import android.util.Log;

/**
 * Created by user on 2017/7/13.
 */

public class Translation {


    /**
     * content : {"err_no":0,"from":"en-EU","out":"示例","to":"zh","vendor":"ciba"}
     * status : 1
     */

    private ContentBean content;
    private int status;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ContentBean {
        /**
         * err_no : 0
         * from : en-EU
         * out : 示例
         * to : zh
         * vendor : ciba
         */

        private int err_no;
        private String from;
        private String out;
        private String to;
        private String vendor;

        public int getErr_no() { return err_no;}

        public void setErr_no(int err_no) { this.err_no = err_no;}

        public String getFrom() { return from;}

        public void setFrom(String from) { this.from = from;}

        public String getOut() { return out;}

        public void setOut(String out) { this.out = out;}

        public String getTo() { return to;}

        public void setTo(String to) { this.to = to;}

        public String getVendor() { return vendor;}

        public void setVendor(String vendor) { this.vendor = vendor;}


    }

    public void show(){
        Log.w("hongTest", "show:  status = " + status  );
        Log.w("hongTest", "show:  from = " + content.from  );
        Log.w("hongTest", "show:  to = " + content.to  );
        Log.w("hongTest", "show:  vendor = " + content.vendor  );
        Log.w("hongTest", "show:  out = " + content.out  );
        Log.w("hongTest", "show:  errNo = " + content.err_no  );
    }
}
