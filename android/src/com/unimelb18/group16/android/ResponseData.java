package com.unimelb18.group16.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseData {


    public static ArrayList<SnakeData> snakeDataArrayList = new ArrayList<SnakeData>();

    public static String jsonArray = "";

    public static void ParseResponse(String result) {
        try {

            snakeDataArrayList.clear();

            JSONObject jObject = new JSONObject(result);

            JSONArray jArray = jObject.getJSONArray("result");

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject oneObject = jArray.getJSONObject(i);
                SnakeData snakeData = new SnakeData();

                snakeData.name = oneObject.getString("name");
                snakeData.x = Float.parseFloat(oneObject.getString("x"));
                snakeData.y = Float.parseFloat(oneObject.getString("y"));
                snakeData.size = Integer.parseInt(oneObject.getString("size"));

                snakeDataArrayList.add(snakeData);

            }

            jsonArray = result;


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
