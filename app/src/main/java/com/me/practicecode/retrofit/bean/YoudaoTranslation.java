package com.me.practicecode.retrofit.bean;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 2017/7/14.
 */
 public class YoudaoTranslation {


    /**
     * web : [{"value":["奇迹","奇迹","奇迹香水"],"key":"Miracle"},{"value":["台湾奇迹","台湾奇迹"],"key":"Taiwan Miracle"},{"value":["地球大进化","地球大进化","地球大纪行"],"key":"Miracle Planet"}]
     * query : miracle
     * translation : ["奇迹"]
     * errorCode : 0
     * basic : {"us-phonetic":"'mɪrəkl","phonetic":"'mɪrək(ə)l","uk-phonetic":"'mɪrək(ə)l","explains":["n. 奇迹，奇迹般的人或物；惊人的事例","n. (Miracle)人名；(英)米勒克尔；(意、西)米拉克莱"]}
     * l : EN2zh-CHS
     */

    private String query;
    private String errorCode;
    private BasicBean basic;
    private String l;
    private List<WebBean> web;
    private List<String> translation;

    public String getQuery() { return query;}

    public void setQuery(String query) { this.query = query;}

    public String getErrorCode() { return errorCode;}

    public void setErrorCode(String errorCode) { this.errorCode = errorCode;}

    public BasicBean getBasic() { return basic;}

    public void setBasic(BasicBean basic) { this.basic = basic;}

    public String getL() { return l;}

    public void setL(String l) { this.l = l;}

    public List<WebBean> getWeb() { return web;}

    public void setWeb(List<WebBean> web) { this.web = web;}

    public List<String> getTranslation() { return translation;}

    public void setTranslation(List<String> translation) { this.translation = translation;}

    public static class BasicBean {
        /**
         * us-phonetic : 'mɪrəkl
         * phonetic : 'mɪrək(ə)l
         * uk-phonetic : 'mɪrək(ə)l
         * explains : ["n. 奇迹，奇迹般的人或物；惊人的事例","n. (Miracle)人名；(英)米勒克尔；(意、西)米拉克莱"]
         */

        @SerializedName("us-phonetic")
        private String usphonetic;
        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukphonetic;
        private List<String> explains;

        public String getUsphonetic() { return usphonetic;}

        public void setUsphonetic(String usphonetic) { this.usphonetic = usphonetic;}

        public String getPhonetic() { return phonetic;}

        public void setPhonetic(String phonetic) { this.phonetic = phonetic;}

        public String getUkphonetic() { return ukphonetic;}

        public void setUkphonetic(String ukphonetic) { this.ukphonetic = ukphonetic;}

        public List<String> getExplains() { return explains;}

        public void setExplains(List<String> explains) { this.explains = explains;}
    }

    public static class WebBean {
        /**
         * value : ["奇迹","奇迹","奇迹香水"]
         * key : Miracle
         */

        private String key;
        private List<String> value;

        public String getKey() { return key;}

        public void setKey(String key) { this.key = key;}

        public List<String> getValue() { return value;}

        public void setValue(List<String> value) { this.value = value;}
    }

    public void show(){
        List<String> t = getTranslation();
        for (int i = 0; i < t.size(); i++) {
            Log.w("hongTest", "show:  translation = " + t.get(i)  );
        }


    }


}
