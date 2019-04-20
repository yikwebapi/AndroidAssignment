package com.example.schooldetect.jsonData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsontoData {

    public JsontoData() {

    }

    private String result = "";

    public JsontoData(String s) {
        result = s;
    }

    public String getimagepath() {

        String imagepath = "";
        if (result != "") {

            try {

                JSONObject jo = new JSONObject(result);
                JSONArray jarray = jo.getJSONArray("items");
                JSONObject jo1 = jarray.getJSONObject(0).getJSONObject("image");
                imagepath = jo1.getString("thumbnailLink");

            } catch (JSONException e) {

            }
        }
        return imagepath;
    }

    public String getAddress() {
        String address = "";
        if (result != "") {

            try {

                JSONObject jo = new JSONObject(result);
                JSONArray jarray = jo.getJSONArray("results");
                JSONObject jo1 = jarray.getJSONObject(0);
                address = jo1.getString("formatted_address");

            } catch (JSONException e) {

            }
        }
        return address;
    }
}





