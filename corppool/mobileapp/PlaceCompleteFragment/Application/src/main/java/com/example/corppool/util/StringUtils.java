package com.example.corppool.util;

/**
 * Created by deepansh on 10/12/2016.
 */
public class StringUtils {

    public static String substring(String inStr, int startIndex,int endIndex){

        if(startIndex<0){
            return inStr;
        }

        if(startIndex> inStr.length() || endIndex > inStr.length()){
            return inStr;
        }

        if (startIndex > endIndex){
            return inStr;
        }

        //TODO Add more validations here

        return inStr.substring(startIndex,endIndex);

    }
}
