package com.creapple.cafe_manager;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FindingPwRequest  extends StringRequest {

    final static private String URL = "http://chandroid.dothome.co.kr/FindingPw.php";
    private Map<String,String> map;

    public FindingPwRequest(String userID, String userNumber, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userNumber", userNumber);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
