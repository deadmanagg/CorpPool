package com.example.corppool.server;

import com.example.android.common.logger.Log;
import com.example.corppool.config.AppConfigurations;
import com.example.corppool.model.Feed;
import com.example.corppool.model.User;

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
import java.net.URLEncoder;

import static  com.example.corppool.server.ExternalURLs.*;

/**
 * Created by deepansh on 10/9/2016.
 */
public class ServerInterface  {

    private static final String TAG = "ServerInterface.java";


    //urls related to

    public static String POSTFeed(Feed feed){

        String response = "";
        try {

            // 3. build jsonObject
            JSONObject jsonObject = feed.getAsJson();

            response = POST(URL_TO_POST_FEEDS,jsonObject,null);

        } catch (Exception ex) {

            Log.d(TAG,ex.toString());
        }

        // 11. return result
        return response;
    }


    //Return the feeds based on the requested parameters (which is a feed itself)
    public static String GETFeeds(Feed reqFeed){

        String response = "";

        try{
            response =GET(URL_TO_GET_FEEDS, URLEncoder.encode(URLEncoder.encode(reqFeed.getAsJsonForSearch().toString(), "UTF-8"), "UTF-8"));
        }catch(Exception e){
            Log.d(TAG, e.toString());
        }
        return response;


    }


    //Login and Register Post Calls
    public static String login (String userid,String password){

        String response = "";
        try{

            JSONObject logginDetails = new JSONObject();
            logginDetails.put("userid",userid);
            logginDetails.put("password",password);

            response = POST(URL_TO_LOGIN, logginDetails, null);

        }catch(Exception e){
            Log.d(TAG,"Logging failed --> "+e.toString());
        }

        return response;
    }

    public static String register (User user){

        String response ="";

        try{
            response = POST(URL_TO_REGISTER,user.getAsJson(),null);
        }catch(Exception e){
            Log.d(TAG,"Logging failed --> "+e.toString());
        }

        return response;
    }


    //Convert Sting to JsonArr
    public static JSONArray convertResponseToJSonArr(String content) throws JSONException{
        return new JSONArray(content);
    }

    //Convert Sting to Json
    public static JSONObject convertResponseToJSon(String content) throws JSONException{
        return new JSONObject(content);
    }

    public static String GET(String baseurl,String params){
        InputStream inputStream = null;
        String response = "";

        try {

            //Double encoding done because it has slashes, thus server was decoding and considering this as another resource path
            String urlToGet =baseurl+ params;
            URL url = new URL(urlToGet);
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

    //TODO improve to override headers, based on the argument
    public static String POST(String baseUrl,JSONObject data,String[][] headers){
        InputStream inputStream = null;
        String response = "";
        try {

            URL url = new URL(baseUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);

            String json = "";


            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);


            // 7. Set some headers to inform server about the type of the content
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-type", "application/json");

            DataOutputStream request = new DataOutputStream(
                    con.getOutputStream());

            request.writeBytes(data.toString());

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

}
