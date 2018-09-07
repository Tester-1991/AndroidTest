package com.shiyan.app.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 作者: created by shiyan on 2018/9/6
 **/

public class LrcUtil {

    public static String getLrcText(Context context,String fileName) {
        String lrcText = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                lrcText += line + "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }

}
