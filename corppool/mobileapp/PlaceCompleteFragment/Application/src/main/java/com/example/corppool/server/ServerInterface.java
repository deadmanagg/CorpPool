package com.example.corppool.server;

import com.example.android.common.logger.Log;
import com.example.corppool.model.Feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by deepansh on 10/9/2016.
 */
public class ServerInterface  {

    private static final String urlToPostFeed = "http://jbossews-deepansh.rhcloud.com/corppool/rest/feeds";
    private static String urlToGetFeeds = "http://jbossews-deepansh.rhcloud.com/corppool/rest/feeds/";

    public static String POSTFeed(Feed feed){
        InputStream inputStream = null;
        String response = "";
        try {

            URL url = new URL(urlToPostFeed);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = feed.getAsJson();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);


            // 7. Set some headers to inform server about the type of the content
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-type", "application/json");

            DataOutputStream request = new DataOutputStream(
                    con.getOutputStream());

            request.writeBytes(jsonObject.toString());

            InputStream responseStream = new
                    BufferedInputStream(con.getInputStream());

            BufferedReader responseStreamReader =
                    new BufferedReader(new InputStreamReader(responseStream));

            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();

            response = stringBuilder.toString();
            responseStream.close();
            con.disconnect();
            //Content = sb.toString();
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        // 11. return result
        return response;
    }


    //Return the feeds based on the requested parameters (which is a feed itself)
    public static String GETFeeds(Feed reqFeed){

        InputStream inputStream = null;
        String response = "";

        try {

            urlToGetFeeds+=reqFeed.getAsJsonForSearch();
            URL url = new URL(urlToGetFeeds);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setUseCaches(false);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-type", "application/json");

            con.connect();

            int responseCode = con.getResponseCode();
            InputStream responseStream = new
                    BufferedInputStream(con.getInputStream());

            BufferedReader responseStreamReader =
                    new BufferedReader(new InputStreamReader(responseStream));

            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();

            response = stringBuilder.toString();
            responseStream.close();
            con.disconnect();
            //Content = sb.toString();
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        // 11. return result
        return response;


    }


    //Convert Sting to Json
    public static JSONArray convertResponseToJSon(String content) throws JSONException{
        return new JSONArray(content);
    }
}
