package com.test.test.testprojand;

import java.util.HashMap;

public class HttpUtil {

    //region common function
    public static HashMap setCommonHttp(String strUrl) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("URL",strUrl);
        hashMap.put("setConnectTimeout","15000");
        return hashMap;
    }


    public static HashMap setRequestProperty() {
        HashMap<String, Object> hashMapRequestProperty = new HashMap<>();
        hashMapRequestProperty.put("Content-Type", "application/json");
        hashMapRequestProperty.put("Content-Encoding", "gzip");
        return  hashMapRequestProperty;
    }
    //endregion
}
