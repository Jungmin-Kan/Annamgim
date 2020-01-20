package com.worldofuiux.fashionprofileuikit.Block;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class AsyncblockGetData extends AsyncTask<String, Void, ArrayList<BlockValueobject>> {

    private ArrayList<BlockValueobject> blockValueobjects = new ArrayList<BlockValueobject>();
    private ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<BlockValueobject> doInBackground(String... strings) {

        String str, receiveMsg;
        URL url = null;
        try {
            url = new URL("http://192.168.43.239:3000/api/SampleTransaction");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.e("receiveMsg : ", receiveMsg);

                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = (JsonArray) jsonParser.parse(receiveMsg);

                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject object = (JsonObject) jsonArray.get(i);
                    String _$class = object.get("$class").getAsString();
                    String _asset = object.get("asset").getAsString();
                    String _newUser = object.get("newUser").getAsString();
                    String _newPrice = object.get("newPrice").getAsString();
                    String _transactionId = object.get("transactionId").getAsString();
                    String _timestamp = object.get("timestamp").getAsString();
                    BlockValueobject blockValueobject = new BlockValueobject(_$class, _asset, _newUser, _newPrice, _transactionId, _timestamp);
                    blockValueobjects.add(blockValueobject);
                }
                reader.close();
            } else {
                System.out.println("통신 결과" + conn.getResponseCode() + "에러");
            }
        } catch (Exception e) {
            Log.e("", String.valueOf(e));
        }

        return blockValueobjects;
    }
    public ArrayList<BlockValueobject> execute(ArrayList<BlockValueobject> blockValueobjects) {
        this.blockValueobjects = blockValueobjects;
        return blockValueobjects;
    }
}
