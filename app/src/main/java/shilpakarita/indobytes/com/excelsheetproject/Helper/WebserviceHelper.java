package shilpakarita.indobytes.com.excelsheetproject.Helper;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

public class WebserviceHelper {
    public static AsyncHttpClient client = new AsyncHttpClient();

    RequestParams requestParams;
    Context context;

    private String whizApiURL;


    

    public WebserviceHelper(Context context) {
        this.context = context;
    }

    public void getJsonData(final WebServiceCallBack callBack) {
        client.setEnableRedirects(true, true, true);
        client.get(whizApiURL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("whizApiURL",  whizApiURL);
                String response = new String(bytes);
                Log.e("response",  response);

                if (response.startsWith("<html>") || response.startsWith("<HTML>")) {
                    Toast.makeText(context, "Please check your network", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();
                    try {
                        callBack.onJSONResponse(response, "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e("onFailure:", ""+ i);
                callBack.onFailure();
            }

        });
    }

    public void getData(final String url, final String type, final WebServiceCallBack callBack) {
        client.setEnableRedirects(true, true, true);
        client.setTimeout(70000);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("whizApiURL1",  ""+whizApiURL);
                String response = new String(responseBody);
                Log.e("response1", response);

                if (response.startsWith("<html>") || response.startsWith("<HTML>")) {
                    Toast.makeText(context, "Please check your network", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        callBack.onJSONResponse(response, type);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("test789", "response" + statusCode);
            }
        });
    }

    public void postData(String url, RequestParams requestParams, final String type, final WebServiceCallBack callBack) {
        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("test789", "In Post");
                String response = new String(responseBody);
                Log.e("response2",  response);
                if (response.startsWith("<html>") || response.startsWith("<HTML>")) {
                    Toast.makeText(context, "Please check your network", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        callBack.onJSONResponse(response, type);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("test789", "response" + statusCode);
            }
        });
    }

    public void postJsonData(String url, StringEntity stringEntity, final String type, final WebServiceCallBack callBack) {
        client.post(context, url, stringEntity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("test789", "In Post1");
                String response = new String(responseBody);
                Log.e("test789", "response3" + response);
                if (response.startsWith("<html>") || response.startsWith("<HTML>")) {
                    Toast.makeText(context, "Please check your network", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        callBack.onJSONResponse(response, type);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("test789", "response" + statusCode);
            }
        });
    }
}
