package com.worldofuiux.fashionprofileuikit.Block;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;

import com.worldofuiux.fashionprofileuikit.MainActivity;

public class Asyncblockpost extends AsyncTask<String, String, String> {

    private String parse;
    private String name;
    private ProgressDialog progressDialog;
    HttpClient client;
    HttpPost post;


    public Asyncblockpost() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... strings) {

        try {
            HttpClient client = new DefaultHttpClient();
            String postUrl = "http://192.168.43.239:3000/api/SampleTransaction";
            HttpPost post = new HttpPost(postUrl);
            ArrayList<NameValuePair> parms = new ArrayList<NameValuePair>();
            parms.add(new BasicNameValuePair("$class", "org.example.basic.SampleTransaction"));
            parms.add(new BasicNameValuePair("asset", "org.example.basic.SampleAsset#trash"));
            parms.add(new BasicNameValuePair("newUser", "User1"));
            parms.add(new BasicNameValuePair("newPrice", "5800"));
            parms.add(new BasicNameValuePair("transactionId", "dcfe3a22304b40ee5ddb58891f68fc4664f31f042b65c1c74c644a71cfc1c3a9"));
            parms.add(new BasicNameValuePair("timestamp", "2019-11-29T23:44:22.588Z"));
            // 다국어 처리
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parms, HTTP.UTF_8);
            // 엔티티 지정 (Entity 개체 라는 의미로 개체set을 의미)
            post.setEntity(urlEncodedFormEntity);
            HttpResponse responsePost = client.execute(post);
            HttpEntity httpEntity = responsePost.getEntity();
            InputStream inputStream = httpEntity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            parse = sb.toString();
            inputStream.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.name = s;
    }
}