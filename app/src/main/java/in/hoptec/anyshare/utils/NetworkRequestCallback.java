package in.hoptec.anyshare.utils;

import com.androidnetworking.error.ANError;

import org.json.JSONObject;

/**
 * Created by shivesh on 29/3/18.
 */

public interface NetworkRequestCallback {

    public void onSuccess(JSONObject response)  ;
    public void onSuccessString(String response)  ;
    public void onFail(ANError job);

}
