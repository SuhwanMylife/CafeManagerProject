package com.creapple.cafe_manager;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FindingRequest extends StringRequest {

    final static private String URL = "http://chandroid.dothome.co.kr/FindingID.php";
    private Map<String,String> map;

    public FindingRequest( String userStore, String userNumber, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userStore", userStore);
        map.put("userNumber", userNumber);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
