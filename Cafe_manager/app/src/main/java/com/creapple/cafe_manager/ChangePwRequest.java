package com.creapple.cafe_manager;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChangePwRequest extends StringRequest {

    final static private String URL = "http://chandroid.dothome.co.kr/ChangePw.php";
    private Map<String,String> map;

    public ChangePwRequest(String userPassword, String changePassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userPassword", userPassword);
        map.put("changePassword", changePassword);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
